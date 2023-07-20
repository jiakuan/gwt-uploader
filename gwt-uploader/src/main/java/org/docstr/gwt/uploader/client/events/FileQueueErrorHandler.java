/*
 * Copyright (c) 2016 Document Node Pty Ltd
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
 * An interface that can be used as a callback handler when file queue error events are fired by
 * the Uploader component.  General usage is as follows:
 * <pre>
 * uploader.setFileQueueErrorEventHandler(new FileQueueErrorHandler() {
 *    public boolean onFileQueueError(FileQueueErrorEvent event) {
 *       Window.alert("File with name " + event.getFile().getName() + " failed in the queue due
 *       to: " + event.getErrorCode().toString());
 *       return true;
 *    }
 * });
 * </pre>
 * Note that the 'fileQueueError' event is fired for each file that was not queued after the File
 * Selection Dialog window is closed. A file may not be queued for several reasons such as,
 * the file exceeds the file size, the file is empty or a file or queue limit has been exceeded.
 * <p>
 * See the documentation on the {@link FileQueueErrorEvent} class for more details on the data
 * available when a file queue error event occurs.
 *
 * @author delight.wjk@gmail.com
 */
public interface FileQueueErrorHandler {

  /**
   * This method is fired whenever a file queue error event occurs in the Uploader component.  See
   * the {@link org.docstr.gwt.uploader.client.events.FileQueueErrorEvent} class for more
   * details on the data available when this event is fired.
   *
   * @param fileQueueErrorEvent The details of the event that occurred.
   * @return The response to send back to the event handler function
   */
  public boolean onFileQueueError(FileQueueErrorEvent fileQueueErrorEvent);

}
