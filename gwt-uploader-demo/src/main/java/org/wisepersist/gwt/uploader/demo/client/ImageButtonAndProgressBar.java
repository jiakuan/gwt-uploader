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
import org.wisepersist.gwt.uploader.client.events.UploadErrorEvent;
import org.wisepersist.gwt.uploader.client.events.UploadErrorHandler;
import org.wisepersist.gwt.uploader.client.events.UploadProgressEvent;
import org.wisepersist.gwt.uploader.client.events.UploadProgressHandler;
import org.wisepersist.gwt.uploader.client.events.UploadSuccessEvent;
import org.wisepersist.gwt.uploader.client.events.UploadSuccessHandler;
import org.wisepersist.gwt.uploader.client.progress.ProgressBar;

/**
 * Uploader Image Button and GWT Progress Bar example of GWT Uploader.
 *
 * @author delight.wjk@gmail.com
 */
public class ImageButtonAndProgressBar implements EntryPoint, UploaderSample {

  final Uploader uploader = new Uploader();
  private ProgressBar progressBar =
      new ProgressBar(0.0, 1.0, 0.0, new CancelProgressBarTextFormatter());
  private Image cancelButton = new Image(AppResources.INSTANCE.cancel().getSafeUri().asString());

  public void onModuleLoad() {
    //noinspection GwtToHtmlReferences
    RootPanel.get("ImageButtonAndProgressBar").add(getUploaderPanel());
  }

  @Override
  public Widget getUploaderPanel() {
    final HorizontalPanel horizontalPanel = new HorizontalPanel();

    uploader.setUploadURL("/upload")
        .setButtonImageURL(AppResources.INSTANCE.upload().getSafeUri().asString())
        .setButtonWidth(133)
        .setButtonHeight(22)
        .setFileSizeLimit("50 MB")
        .setButtonCursor(Uploader.Cursor.HAND)
        .setButtonAction(Uploader.ButtonAction.SELECT_FILE)
        .setFileQueuedHandler(new FileQueuedHandler() {
          public boolean onFileQueued(final FileQueuedEvent fileQueuedEvent) {
            // Create a Progress Bar for this file
            progressBar.setTitle(fileQueuedEvent.getFile().getName());
            progressBar.setHeight("18px");
            progressBar.setWidth("200px");

            // Add Cancel Button Image
            cancelButton.setStyleName("cancelButton");
            cancelButton.addClickHandler(new ClickHandler() {
              public void onClick(ClickEvent event) {
                uploader.cancelUpload(fileQueuedEvent.getFile().getId(), false);
                progressBar.setProgress(-1.0d);
                cancelButton.removeFromParent();
              }
            });

            horizontalPanel.add(progressBar);
            horizontalPanel.add(cancelButton);

            return true;
          }
        })
        .setFileDialogStartHandler(new FileDialogStartHandler() {
          public boolean onFileDialogStartEvent(FileDialogStartEvent fileDialogStartEvent) {
            if (uploader.getStats().getUploadsInProgress() <= 0) {
              progressBar.removeFromParent();
              cancelButton.removeFromParent();
            }
            return true;
          }
        })
        .setUploadProgressHandler(new UploadProgressHandler() {
          public boolean onUploadProgress(UploadProgressEvent uploadProgressEvent) {
            progressBar.setProgress(
                (double) uploadProgressEvent.getBytesComplete() / uploadProgressEvent
                    .getBytesTotal()
            );
            return true;
          }
        })
        .setUploadSuccessHandler(new UploadSuccessHandler() {
          public boolean onUploadSuccess(UploadSuccessEvent uploadSuccessEvent) {
            cancelButton.removeFromParent();
            return true;
          }
        })
        .setFileDialogCompleteHandler(new FileDialogCompleteHandler() {
          public boolean onFileDialogComplete(FileDialogCompleteEvent fileDialogCompleteEvent) {
            if (fileDialogCompleteEvent.getTotalFilesInQueue() > 0
                && uploader.getStats().getUploadsInProgress() <= 0) {
              progressBar.setProgress(0.0);
              uploader.startUpload();
            }
            return true;
          }
        })
        .setFileQueueErrorHandler(new FileQueueErrorHandler() {
          public boolean onFileQueueError(FileQueueErrorEvent fileQueueErrorEvent) {
            progressBar.setProgress(0.0);
            cancelButton.removeFromParent();
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
            progressBar.setProgress(0.0);
            cancelButton.removeFromParent();
            Window
                .alert(
                    "Upload of file " + uploadErrorEvent.getFile().getName() + " failed due to [" +
                    uploadErrorEvent.getErrorCode().toString() + "]: " + uploadErrorEvent
                        .getMessage()
                );
            return true;
          }
        });

    horizontalPanel.add(uploader);
    horizontalPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
    horizontalPanel.setCellHorizontalAlignment(uploader, HorizontalPanel.ALIGN_RIGHT);
    return horizontalPanel;
  }

  @Override
  public final String getUploaderCode() {
    return AppResources.INSTANCE.sourceImageButtonAndProgressBar().getText();
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
