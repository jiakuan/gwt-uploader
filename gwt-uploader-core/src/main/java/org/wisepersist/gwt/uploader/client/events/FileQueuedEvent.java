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

import org.wisepersist.gwt.uploader.client.File;

/**
 * Provides access to the raw information provided by Uploader when a 'fileQueued' event occurs.
 * This class should not be instantiated directly, but instead you should create a {@link
 * FileQueuedHandler} and register it via the {@link org.wisepersist.gwt.uploader.client
 * .Uploader#setFileQueuedHandler(FileQueuedHandler)} method in order to access file queued events.
 *
 * <p>Note that the 'fileQueued' event is fired for each file that is queued after the File
 * Selection Dialog window is closed.
 *
 * @author delight.wjk@gmail.com
 */
public class FileQueuedEvent {

  private File file;

  /**
   * This constructor is intended for internal use only.  You should not create file queued events
   * directly, but instead should register a {@link FileQueuedHandler}.
   *
   * @param file The file instance that was added to the queue.
   */
  public FileQueuedEvent(final File file) {
    this.file = file;
  }

  /**
   * Return the details of the file that was added to the queue.
   *
   * @return File
   */
  public final File getFile() {
    return file;
  }
}
