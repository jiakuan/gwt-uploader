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

/**
 * An interface that can be used as a callback handler when uploader completion events are fired by
 * the Uploader component.  General usage is as follows:
 * <pre>
 * uploader.setUploadCompleteHandler(new UploadCompleteHandler() {
 *    public boolean onUploadComplete(UploadCompleteEvent event) {
 *       Window.alert("Uploader complete for file " + event.getFile().getName());
 *       return true;
 *    }
 * });
 * </pre>
 *
 * <p>Note that the 'uploadComplete' event is always fired at the end of an uploader cycle
 * (after uploadError or uploadSuccess). At this point the uploader is complete and another
 * uploader can be started.
 *
 * <p>If you want the next uploader to start automatically this is a good place to call this
 * .uploadStart().
 * Use caution when calling uploadStart inside the uploadComplete event if you also have code
 * that cancels all the uploads in a queue.
 *
 * <p>See the documentation on the {@link UploadCompleteEvent} class for more details on the data
 * available when an uploader complete event occurs.
 *
 * @author delight.wjk@gmail.com
 */
public interface UploadCompleteHandler {

  /**
   * This method is fired whenever a uploader completion event occurs in the Uploader component.
   * See the {@link UploadCompleteEvent} class for more details on the data available when this
   * event is fired.
   *
   * @param uploadCompleteEvent The details of the event that occurred.
   * @return The response to send back to the event handler function
   */
  boolean onUploadComplete(UploadCompleteEvent uploadCompleteEvent);

}
