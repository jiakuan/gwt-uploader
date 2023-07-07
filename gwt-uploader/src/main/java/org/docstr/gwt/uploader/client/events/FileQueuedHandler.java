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
 * An interface that can be used as a callback handler when file queued events are fired by
 * the Uploader component.  General usage is as follows:
 * <pre>
 * uploader.setFileQueuedEventHandler(new FileQueuedHandler() {
 *    public boolean onFileQueued(FileQueuedEvent event) {
 *       Window.alert("File with name " + event.getFile().getName() + " is ready to uploader");
 *       return true;
 *    }
 * });
 * </pre>
 * Note that the 'fileQueued' event is fired for each file that is queued after the File
 * Selection Dialog window is closed.
 * <p>
 * See the documentation on the {@link FileQueuedEvent} class for more details on the data
 * available when a file queued event occurs.
 *
 * @author delight.wjk@gmail.com
 */
public interface FileQueuedHandler {

  /**
   * This method is fired whenever a file queued event occurs in the Uploader component.  See
   * the {@link FileQueuedEvent} class for more details on the data available when this event is
   * fired.
   *
   * @param fileQueuedEvent The details of the event that occurred.
   * @return The response to send back to the event handler function
   */
  public boolean onFileQueued(FileQueuedEvent fileQueuedEvent);

}
