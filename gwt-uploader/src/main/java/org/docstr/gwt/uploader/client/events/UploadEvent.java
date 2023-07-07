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
 * The base class of all events that are triggered during the uploader process,
 * and includes methods for accessing the general state of the event,
 * most notably the details of the file that is being uploaded.
 *
 * @author delight.wjk@gmail.com
 */
public abstract class UploadEvent {

  private File file;

  /**
   * We can only be created by instantiating one of our sub classes.
   *
   * @param file The native javascript object containing the details of the file being uploaded.
   */
  protected UploadEvent(File file) {
    this.file = file;
  }

  /**
   * Return the details of the file upon which the uploader event has occurred.
   *
   * @return File
   */
  public File getFile() {
    return file;
  }
}
