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
 * An interface that can be used as a callback handler when file dialog start events are fired by
 * the Uploader component.  General usage is as follows:
 * <pre>
 * uploader.setFileDialogStartHandler(new FileDialogStartHandler() {
 *    public boolean onFileDialogStart(FileDialogStartEvent event) {
 *       Window.alert("Starting the file selection dialog...");
 *       return true;
 *    }
 * });
 * </pre>
 * Note that the 'fileDialogStart' is fired after selectFile for selectFiles is called. This
 * event is fired immediately before the File Selection Dialog window is displayed. However,
 * the event may not execute until after the Dialog window is closed.
 * <p>
 * See the documentation on the {@link FileDialogStartEvent} class for more details on the data
 * available when a file dialog start event occurs.
 *
 * @author delight.wjk@gmail.com
 */
public interface FileDialogStartHandler {

  /**
   * This method is fired whenever a file dialog start event occurs in the Uploader component.  See
   * the {@link FileDialogStartEvent} class for more details on the data available when this
   * event is fired.
   *
   * @param fileDialogStartEvent The details of the event that occurred.
   * @return The response to send back to the event handler function
   */
  public boolean onFileDialogStartEvent(FileDialogStartEvent fileDialogStartEvent);

}
