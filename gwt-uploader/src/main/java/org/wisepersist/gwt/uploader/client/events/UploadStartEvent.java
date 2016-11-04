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
 * Provides access to the raw information provided by Uploader when an 'uploader start' event
 * occurs.
 * This class should not be instantiated directly, but instead you should create a
 * {@link UploadStartHandler} and register it via the {@link org.wisepersist.gwt.uploader.client
 * .Uploader#setUploadStartHandler(UploadStartHandler)} method in order to access uploader start
 * events.
 * <p>
 * Note that the 'uploadStart' event is called immediately before the file is uploaded. This event
 * provides an opportunity to perform any last minute validation, add post params or do any other
 * work before the file is uploaded.
 * <p>
 * The uploader can be cancelled by returning 'false' from uploadStart in the handler. If you
 * return 'true' or do not return
 * any value then the uploader proceeds. Returning 'false' will cause an uploadError event to fired.
 *
 * @author delight.wjk@gmail.com
 */
public class UploadStartEvent extends UploadEvent {

  /**
   * This constructor is intended for internal use only.  You should not create uploader start
   * events directly, but instead should register a {@link UploadStartHandler}.
   *
   * @param file The native javascript object containing the details of the file being uploaded.
   */
  public UploadStartEvent(File file) {
    super(file);
  }

}
