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
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import org.wisepersist.gwt.uploader.client.Uploader;
import org.wisepersist.gwt.uploader.client.events.FileDialogCompleteEvent;
import org.wisepersist.gwt.uploader.client.events.FileDialogCompleteHandler;
import org.wisepersist.gwt.uploader.client.events.FileQueueErrorEvent;
import org.wisepersist.gwt.uploader.client.events.FileQueueErrorHandler;
import org.wisepersist.gwt.uploader.client.events.UploadErrorEvent;
import org.wisepersist.gwt.uploader.client.events.UploadErrorHandler;
import org.wisepersist.gwt.uploader.client.events.UploadProgressEvent;
import org.wisepersist.gwt.uploader.client.events.UploadProgressHandler;
import org.wisepersist.gwt.uploader.client.events.UploadSuccessEvent;
import org.wisepersist.gwt.uploader.client.events.UploadSuccessHandler;

/**
 * Uploader Image Button and Progress text label example of GWT Uploader.
 *
 * @author delight.wjk@gmail.com
 */
public class ImageButtonAndProgressText implements EntryPoint, UploaderPanel {

  private final Label progressLabel = new Label();
  private final Uploader uploader = new Uploader();

  public void onModuleLoad() {
    //noinspection GwtToHtmlReferences
    RootPanel.get("ImageButtonAndProgressText").add(getUploaderPanel());
  }

  @Override
  public Widget getUploaderPanel() {
    progressLabel.setStyleName("progressLabel");
    uploader.setUploadURL("/DevNullUploadServlet")
        .setButtonImageURL(
            GWT.getModuleBaseURL() + "resources/images/buttons/upload_new_version_button.png")
        .setButtonWidth(133)
        .setButtonHeight(22)
        .setFileSizeLimit("50 MB")
        .setButtonCursor(Uploader.Cursor.HAND)
        .setButtonAction(Uploader.ButtonAction.SELECT_FILE)
        .setUploadProgressHandler(new UploadProgressHandler() {
          public boolean onUploadProgress(UploadProgressEvent uploadProgressEvent) {
            progressLabel.setText(
                NumberFormat.getPercentFormat().format(
                    uploadProgressEvent.getBytesComplete() / uploadProgressEvent.getBytesTotal()
                )
            );
            return true;
          }
        })
        .setUploadSuccessHandler(new UploadSuccessHandler() {
          public boolean onUploadSuccess(UploadSuccessEvent uploadSuccessEvent) {
            resetText();
            StringBuilder sb = new StringBuilder();
            sb.append("File ").append(uploadSuccessEvent.getFile().getName())
                .append(" uploaded successfully at ")
                .append(NumberFormat.getDecimalFormat().format(
                    uploadSuccessEvent.getFile().getAverageSpeed() / 1024
                ))
                .append(" kilobit per second");
            progressLabel.setText(sb.toString());
            return true;
          }
        })
        .setFileDialogCompleteHandler(new FileDialogCompleteHandler() {
          public boolean onFileDialogComplete(FileDialogCompleteEvent fileDialogCompleteEvent) {
            if (fileDialogCompleteEvent.getTotalFilesInQueue() > 0
                && uploader.getStats().getUploadsInProgress() <= 0) {
              progressLabel.setText("0%");
              uploader.startUpload();
            }
            return true;
          }
        })
        .setFileQueueErrorHandler(new FileQueueErrorHandler() {
          public boolean onFileQueueError(FileQueueErrorEvent fileQueueErrorEvent) {
            resetText();
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
            resetText();
            Window
                .alert(
                    "Upload of file " + uploadErrorEvent.getFile().getName() + " failed due to [" +
                    uploadErrorEvent.getErrorCode().toString() + "]: " + uploadErrorEvent
                        .getMessage()
                );
            return true;
          }
        });

    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.add(uploader);
    verticalPanel.setCellHorizontalAlignment(uploader, HorizontalPanel.ALIGN_LEFT);
    verticalPanel.setCellHorizontalAlignment(progressLabel, HorizontalPanel.ALIGN_LEFT);
    return verticalPanel;
  }

  private void resetText() {
    progressLabel.setText("");
  }
}
