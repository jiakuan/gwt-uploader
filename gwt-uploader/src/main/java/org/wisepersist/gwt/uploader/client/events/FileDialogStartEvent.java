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
 * Provides access to the raw information provided by Uploader when a 'fileDialogStart' event
 * occurs.
 * This class should not be instantiated directly, but instead you should create a {@link
 * FileDialogStartHandler} and register it via the {@link org.wisepersist.gwt.uploader.client
 * .Uploader#setFileDialogStartHandler(FileDialogStartHandler)} method in order to access file
 * dialog start events.
 * <p>
 * Note that the 'fileDialogStart' is fired after selectFile for selectFiles is called. This
 * event is fired immediately before the File Selection Dialog window is displayed. However,
 * the event may not execute until after the Dialog window is closed.
 * <p>
 *
 * @author delight.wjk@gmail.com
 */
public class FileDialogStartEvent {

  /**
   * This constructor is intended for internal use only.  You should not create file dialog start
   * events directly, but instead should register a {@link FileDialogStartHandler}.
   */
  public FileDialogStartEvent() {
  }

}
