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
 * Provides access to the raw information provided by Uploader when a 'fileDialogComplete' event
 * occurs.
 * This class should not be instantiated directly, but instead you should create a {@link
 * FileDialogCompleteHandler} and
 * register it via the {@link org.wisepersist.gwt.uploader.client
 * .Uploader#setFileDialogCompleteHandler(FileDialogCompleteHandler)}
 * method in order to access file dialog complete events.
 * <p>
 * Note that the 'fileDialogComplete' event fires after the File Selection Dialog window has been
 * closed and all the selected files have been processed. The 'number of files queued' property
 * indicates the number of files that were queued from the dialog selection (as opposed to the
 * number of files in the queue).
 * <p>
 * If you want file uploading to begin automatically this is a good place to call {@link
 * org.wisepersist.gwt.uploader.client.Uploader#startUpload()}.
 *
 * @author delight.wjk@gmail.com
 */
public class FileDialogCompleteEvent {

  private int numberOfFilesSelected;
  private int numberOfFilesQueued;
  private int totalFilesInQueue;

  /**
   * This constructor is intended for internal use only.  You should not create file dialog
   * complete events directly, but instead should register a {@link FileDialogCompleteHandler}.
   *
   * @param numberOfFilesSelected The number of files selected in the dialog.
   * @param numberOfFilesQueued   The number of files that were queued as a result of the dialog
   *                              selection.
   * @param totalFilesInQueue     The total number of files now in the queue.
   */
  public FileDialogCompleteEvent(int numberOfFilesSelected, int numberOfFilesQueued,
                                 int totalFilesInQueue) {
    this.numberOfFilesSelected = numberOfFilesSelected;
    this.numberOfFilesQueued = numberOfFilesQueued;
    this.totalFilesInQueue = totalFilesInQueue;
  }

  /**
   * Return the number of files selected in the dialog.
   *
   * @return int
   */
  public int getNumberOfFilesQueued() {
    return numberOfFilesQueued;
  }

  /**
   * Return the number of files that were queued as a result of the dialog selection.
   *
   * @return int
   */
  public int getNumberOfFilesSelected() {
    return numberOfFilesSelected;
  }

  /**
   * Return the total number of files now in the queue.
   *
   * @return int
   */
  public int getTotalFilesInQueue() {
    return totalFilesInQueue;
  }
}
