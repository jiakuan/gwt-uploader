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

package org.wisepersist.gwt.uploader.client.events;

import org.wisepersist.gwt.uploader.client.Uploader;

/**
 * An interface that can be used as a callback handler when file dialog complete events are fired by
 * the Uploader component.  General usage is as follows:
 * <pre>
 * uploader.setFileDialogCompleteHandler(new FileDialogCompleteHandler() {
 *    public boolean onFileDialogComplete(FileDialogCompleteEvent event) {
 *       Window.alert(event.getTotalFilesInQueue() + " are ready to uploader");
 *       return true;
 *    }
 * });
 * </pre>
 *
 * <p>Note that the 'fileDialogComplete' event fires after the File Selection Dialog window has
 * been closed and all the selected files have been processed. The 'number of files queued'
 * property indicates the number of files that were queued from the dialog selection (as opposed
 * to the number of files in the queue).
 *
 * <p>If you want file uploading to begin automatically this is a good place to call
 * {@link Uploader#startUpload()}.
 *
 * <p>See the documentation on the {@link FileDialogCompleteEvent} class for more details on the
 * data available when a file dialog complete event occurs.
 *
 * @author delight.wjk@gmail.com
 */
public interface FileDialogCompleteHandler {

  /**
   * This method is fired whenever a file dialog complete event occurs in the Uploader component.
   * See the {@link FileDialogCompleteEvent} class for more details on the data available when
   * this event is fired.
   *
   * @param fileDialogCompleteEvent The details of the event that occurred.
   * @return The response to send back to the event handler function
   */
  boolean onFileDialogComplete(FileDialogCompleteEvent fileDialogCompleteEvent);

}
