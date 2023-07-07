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
 * An interface that can be used as a callback handler when uploader progress events are fired by
 * the Uploader component.  General usage is as follows:
 * <pre>
 * uploader.setUploadProgressHandler(new UploadProgressHandler() {
 *    public boolean onUploadProgress(UploadProgressEvent event) {
 *       Window.alert("Uploader progressed to " + event.getBytesTotal() + " bytes");
 *       return true;
 *    }
 * });
 * </pre>
 * <p>
 * Note that the 'uploadProgress' event is fired periodically by the control. This event is
 * useful for providing UI updates on the page.
 * <p>
 * See the documentation on the {@link UploadProgressEvent} class for more details on the data
 * available when a progress event occurs.
 *
 * @author delight.wjk@gmail.com
 */
public interface UploadProgressHandler {

  /**
   * This method is fired whenever a progress event occurs in the Uploader component.  See
   * the {@link UploadProgressEvent} class for more details on the data available when this event
   * is fired.
   *
   * @param uploadProgressEvent The details of the event that occurred.
   * @return The response to send back to the event handler function
   */
  public boolean onUploadProgress(UploadProgressEvent uploadProgressEvent);

}
