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

import org.docstr.gwt.uploader.client.File;

/**
 * Provides access to the raw information provided by Uploader when an 'uploader complete' event
 * occurs.
 * This class should not be instantiated directly, but instead you should create a {@link
 * UploadCompleteHandler} and register it via the {@link org.docstr.gwt.uploader.client
 * .Uploader#setUploadCompleteHandler(UploadCompleteHandler)} method in order to access uploader
 * complete events.
 * <p>
 * Note that the 'uploadComplete' event is always fired at the end of an uploader cycle
 * (after uploadError or uploadSuccess). At this point the uploader is complete and another
 * uploader can be started.
 * <p>
 * If you want the next uploader to start automatically this is a good place to call this
 * .uploadStart().
 * Use caution when calling uploadStart inside the uploadComplete event if you also have code
 * that cancels all the uploads in a queue.
 *
 * @author delight.wjk@gmail.com
 */
public class UploadCompleteEvent extends UploadEvent {

  /**
   * This constructor is intended for internal use only.  You should not create uploader complete
   * events directly, but instead should register a {@link UploadCompleteHandler}.
   *
   * @param file The native javascript object containing the details of the file being uploaded.
   */
  public UploadCompleteEvent(File file) {
    super(file);
  }
}
