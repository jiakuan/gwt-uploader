/*
 * Copyright (c) 2016 DocStr
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
package org.docstr.gwt.uploader.client.events;

/**
 * An interface that can be used as a callback handler when uploader error events are fired by
 * the Uploader component.  General usage is as follows:
 * <pre>
 * uploader.setUploadErrorHandler(new UploadErrorHandler() {
 *    public boolean onUploadError(UploadErrorEvent event) {
 *       Window.alert("Unable to uploader file " + event.getFile().getName());
 *       return true;
 *    }
 * });
 * </pre>
 * <p>
 * Note that the 'uploadError' event is fired any time an uploader is interrupted or does not
 * complete successfully. The error code parameter indicates the type of error that occurred.
 * <p>
 * Stopping, Cancelling or returning 'false' from uploadStart will cause uploadError to fire.
 * Uploader error will not fire for files that are cancelled but still waiting in the queue.
 * <p>
 * See the documentation on the {@link UploadErrorEvent} class for more details on the data
 * available when an uploader error event occurs.
 *
 * @author delight.wjk@gmail.com
 */
public interface UploadErrorHandler {

  /**
   * This method is fired whenever a uploader error event occurs in the Uploader component.  See
   * the {@link UploadErrorEvent} class for more details on the data available when this event is
   * fired.
   *
   * @param uploadErrorEvent The details of the event that occurred.
   * @return The response to send back to the event handler function
   */
  public boolean onUploadError(UploadErrorEvent uploadErrorEvent);

}
