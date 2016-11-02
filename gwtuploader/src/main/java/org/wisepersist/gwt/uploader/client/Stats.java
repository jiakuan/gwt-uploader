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
package org.wisepersist.gwt.uploader.client;

import com.google.gwt.core.client.JavaScriptObject;

import org.wisepersist.gwt.uploader.client.events.FileQueueErrorHandler;

/**
 * A simple bean that provides access to the statistical counters managed by the Uploader
 * component.  An instance of this object is available dynamically via the
 * {@link Uploader#getStats()} method.
 *
 * @author delight.wjk@gmail.com
 */
public final class Stats extends JavaScriptObject {

  /**
   * Instances of the Stats object can not be created directly, but should instead
   * be obtained via the {@link Uploader#getStats()} method.
   */
  protected Stats() {
  }

  /**
   * Return a 1 or 0 indicating if a file upload is currently in progress.
   *
   * @return int
   */
  public native int getUploadsInProgress() /*-{
      return (this && this.in_progress) || 0;
  }-*/;

  /**
   * Return the number of files currently in the queue.
   *
   * @return int
   */
  public native int getFilesQueued() /*-{
      return (this && this.files_queued) || 0;
  }-*/;

  /**
   * Return the number of files that have uploaded successfully (caused uploadSuccess to be fired).
   *
   * @return int
   */
  public native int getSuccessfulUploads() /*-{
      return (this && this.successful_uploads) || 0;
  }-*/;

  /**
   * Return the number of files that have had errors (excluding cancelled files).
   *
   * @return int
   */
  public native int getUploadErrors() /*-{
      return (this && this.upload_errors) || 0;
  }-*/;

  /**
   * Return the number of files that have been cancelled.
   *
   * @return int
   */
  public native int getUploadsCancelled() /*-{
      return (this && this.upload_cancelled) || 0;
  }-*/;

  /**
   * Return the number of files that caused the {@link FileQueueErrorHandler}
   * to be fired.
   *
   * @return int
   */
  public native int getQueueErrors() /*-{
      return (this && this.queue_errors) || 0;
  }-*/;

}
