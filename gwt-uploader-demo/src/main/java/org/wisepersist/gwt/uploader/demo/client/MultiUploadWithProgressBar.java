/*
 * Copyright (c) 2016 WisePersist.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wisepersist.gwt.uploader.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.wisepersist.gwt.uploader.client.Uploader;
import org.wisepersist.gwt.uploader.client.events.FileDialogCompleteEvent;
import org.wisepersist.gwt.uploader.client.events.FileDialogCompleteHandler;
import org.wisepersist.gwt.uploader.client.events.FileDialogStartEvent;
import org.wisepersist.gwt.uploader.client.events.FileDialogStartHandler;
import org.wisepersist.gwt.uploader.client.events.FileQueueErrorEvent;
import org.wisepersist.gwt.uploader.client.events.FileQueueErrorHandler;
import org.wisepersist.gwt.uploader.client.events.FileQueuedEvent;
import org.wisepersist.gwt.uploader.client.events.FileQueuedHandler;
import org.wisepersist.gwt.uploader.client.events.UploadCompleteEvent;
import org.wisepersist.gwt.uploader.client.events.UploadCompleteHandler;
import org.wisepersist.gwt.uploader.client.events.UploadErrorEvent;
import org.wisepersist.gwt.uploader.client.events.UploadErrorHandler;
import org.wisepersist.gwt.uploader.client.events.UploadProgressEvent;
import org.wisepersist.gwt.uploader.client.events.UploadProgressHandler;
import org.wisepersist.gwt.uploader.client.progress.ProgressBar;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Uploader Image Button with multiple file selection and GWT Progress Bars example of
 * GWT SWF Uploader.
 *
 * @author delight.wjk@gmail.com
 */
public class MultiUploadWithProgressBar implements EntryPoint, UploaderSample {

  private final Uploader uploader = new Uploader();

  public void onModuleLoad() {
    //noinspection GwtToHtmlReferences
    RootPanel.get("MultiUploadWithProgressBar").add(getUploaderPanel());
  }

  @Override
  public Widget getUploaderPanel() {
    final VerticalPanel progressBarPanel = new VerticalPanel();
    final Map<String, ProgressBar> progressBars = new LinkedHashMap<String, ProgressBar>();
    final Map<String, Image> cancelButtons = new LinkedHashMap<String, Image>();
    uploader.setUploadURL("/upload")
        .setButtonImageURL(AppResources.INSTANCE.upload().getSafeUri().asString())
        .setButtonWidth(133)
        .setButtonHeight(22)
        .setFileSizeLimit("50 MB")
        .setButtonCursor(Uploader.Cursor.HAND)
        .setButtonAction(Uploader.ButtonAction.SELECT_FILES)
        .setFileQueuedHandler(new FileQueuedHandler() {
          public boolean onFileQueued(final FileQueuedEvent fileQueuedEvent) {
            // Create a Progress Bar for this file
            final ProgressBar progressBar =
                new ProgressBar(0.0, 1.0, 0.0, new CancelProgressBarTextFormatter());
            progressBar.setTitle(fileQueuedEvent.getFile().getName());
            progressBar.setHeight("18px");
            progressBar.setWidth("200px");
            progressBars.put(fileQueuedEvent.getFile().getId(), progressBar);

            // Add Cancel Button Image
            final Image cancelButton =
                new Image(AppResources.INSTANCE.cancel().getSafeUri().asString());
            cancelButton.setStyleName("cancelButton");
            cancelButton.addClickHandler(new ClickHandler() {
              public void onClick(ClickEvent event) {
                uploader.cancelUpload(fileQueuedEvent.getFile().getId(), false);
                progressBars.get(fileQueuedEvent.getFile().getId()).setProgress(-1.0d);
                cancelButton.removeFromParent();
              }
            });
            cancelButtons.put(fileQueuedEvent.getFile().getId(), cancelButton);

            // Add the Bar and Button to the interface
            HorizontalPanel progressBarAndButtonPanel = new HorizontalPanel();
            progressBarAndButtonPanel.add(progressBar);
            progressBarAndButtonPanel.add(cancelButton);
            progressBarPanel.add(progressBarAndButtonPanel);

            return true;
          }
        })
        .setUploadProgressHandler(new UploadProgressHandler() {
          public boolean onUploadProgress(UploadProgressEvent uploadProgressEvent) {
            ProgressBar progressBar = progressBars.get(uploadProgressEvent.getFile().getId());
            progressBar.setProgress(
                (double) uploadProgressEvent.getBytesComplete() / uploadProgressEvent
                    .getBytesTotal()
            );
            return true;
          }
        })
        .setUploadCompleteHandler(new UploadCompleteHandler() {
          public boolean onUploadComplete(UploadCompleteEvent uploadCompleteEvent) {
            cancelButtons.get(uploadCompleteEvent.getFile().getId()).removeFromParent();
            // Call upload to see if any additional files are queued
            uploader.startUpload();
            return true;
          }
        })
        .setFileDialogStartHandler(new FileDialogStartHandler() {
          public boolean onFileDialogStartEvent(FileDialogStartEvent fileDialogStartEvent) {
            if (uploader.getStats().getUploadsInProgress() <= 0) {
              // Clear the uploads that have completed, if none are in process
              progressBarPanel.clear();
              progressBars.clear();
              cancelButtons.clear();
            }
            return true;
          }
        })
        .setFileDialogCompleteHandler(new FileDialogCompleteHandler() {
          public boolean onFileDialogComplete(FileDialogCompleteEvent fileDialogCompleteEvent) {
            if (uploader.getStats().getUploadsInProgress() <= 0) {
              uploader.startUpload();
            }
            return true;
          }
        })
        .setFileQueueErrorHandler(new FileQueueErrorHandler() {
          public boolean onFileQueueError(FileQueueErrorEvent fileQueueErrorEvent) {
            Window.alert(
                "Upload of file " + fileQueueErrorEvent.getFile().getName() + " failed due to [" +
                fileQueueErrorEvent.getErrorCode().toString() + "]: " + fileQueueErrorEvent
                    .getMessage()
            );
            return true;
          }
        })
        .setUploadErrorHandler(new UploadErrorHandler() {
          public boolean onUploadError(UploadErrorEvent uploadErrorEvent) {
            cancelButtons.get(uploadErrorEvent.getFile().getId()).removeFromParent();
            Window.alert(
                "Upload of file " + uploadErrorEvent.getFile().getName() + " failed due to [" +
                uploadErrorEvent.getErrorCode().toString() + "]: " + uploadErrorEvent.getMessage()
            );
            return true;
          }
        });
    HorizontalPanel horizontalPanel = new HorizontalPanel();
    horizontalPanel.add(uploader);
    horizontalPanel.add(progressBarPanel);
    horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
    horizontalPanel.setCellHorizontalAlignment(uploader, HorizontalPanel.ALIGN_LEFT);
    horizontalPanel.setCellHorizontalAlignment(progressBarPanel, HorizontalPanel.ALIGN_RIGHT);
    return horizontalPanel;
  }

  @Override
  public final String getUploaderCode() {
    return AppResources.INSTANCE.sourceMultiUploadWithProgressBar().getText();
  }

  protected class CancelProgressBarTextFormatter extends ProgressBar.TextFormatter {

    @Override
    protected String getText(ProgressBar bar, double curProgress) {
      if (curProgress < 0) {
        return "Cancelled";
      }
      return ((int) (100 * bar.getPercent())) + "%";
    }
  }
}
