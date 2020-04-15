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
 * Provides access to the raw information provided by Uploader when an 'uploader error' event
 * occurs.
 * This class should not be instantiated directly, but instead you should create a
 * {@link UploadErrorHandler} and register it via the {@link org.wisepersist.gwt.uploader.client
 * .Uploader#setUploadErrorHandler(UploadErrorHandler)}
 * method in order to access uploader error events.
 * <p>
 * Note that the 'uploadError' event is fired any time an uploader is interrupted or does not
 * complete successfully. The error code parameter indicates the type of error that occurred.
 * <p>
 * Stopping, Cancelling or returning 'false' from uploadStart will cause uploadError to fire.
 * Uploader error will not fire for files that are cancelled but still waiting in the queue.
 *
 * @author delight.wjk@gmail.com
 */
public class UploadErrorEvent extends UploadEvent {

  /**
   * An enumeration of reported uploaded error code types.
   */
  public enum ErrorCode {

    UNKNOWN(0),
    HTTP_ERROR(-200),
    MISSING_UPLOAD_URL(-210),
    IO_ERROR(-220),
    SECURITY_ERROR(-230),
    UPLOAD_LIMIT_EXCEEDED(-240),
    UPLOAD_FAILED(-250),
    SPECIFIED_FILE_ID_NOT_FOUND(-260),
    FILE_VALIDATION_FAILED(-270),
    FILE_CANCELLED(-280),
    UPLOAD_STOPPED(-290);

    private ErrorCode(int optionValue) {
      this.optionValue = optionValue;
    }

    private final int optionValue;

    public int toInt() {
      return optionValue;
    }
  }

  private int errorCode;
  private String message;
  private String serverData;

  /**
   * This constructor is intended for internal use only.  You should not create uploader error
   * events directly, but instead should register a {@link UploadErrorHandler}.
   *
   * @param file      The native javascript object containing the details of the file being
   *                  uploaded.
   * @param errorCode The error code returned by the Uploader component in the case of an
   *                  uploader failure.
   * @param message   A human readable error message explaining the cause of the uploader failure.
   * 
   * @param serverData   Server response (if any).
   */
  public UploadErrorEvent(File file, int errorCode, String message, String serverData) {
    super(file);
    this.errorCode = errorCode;
    this.message = message;
    this.serverData = serverData;
  }

  /**
   * The error code returned by the Uploader component in the case of an uploader failure,
   * conveniently converted to an enumeration type.
   *
   * @return ErrorCode
   */
  public ErrorCode getErrorCode() {
    if (errorCode == ErrorCode.HTTP_ERROR.toInt()) {
      return ErrorCode.HTTP_ERROR;
    } else if (errorCode == ErrorCode.MISSING_UPLOAD_URL.toInt()) {
      return ErrorCode.MISSING_UPLOAD_URL;
    } else if (errorCode == ErrorCode.IO_ERROR.toInt()) {
      return ErrorCode.IO_ERROR;
    } else if (errorCode == ErrorCode.SECURITY_ERROR.toInt()) {
      return ErrorCode.SECURITY_ERROR;
    } else if (errorCode == ErrorCode.UPLOAD_LIMIT_EXCEEDED.toInt()) {
      return ErrorCode.UPLOAD_LIMIT_EXCEEDED;
    } else if (errorCode == ErrorCode.UPLOAD_FAILED.toInt()) {
      return ErrorCode.UPLOAD_FAILED;
    } else if (errorCode == ErrorCode.SPECIFIED_FILE_ID_NOT_FOUND.toInt()) {
      return ErrorCode.SPECIFIED_FILE_ID_NOT_FOUND;
    } else if (errorCode == ErrorCode.FILE_VALIDATION_FAILED.toInt()) {
      return ErrorCode.FILE_VALIDATION_FAILED;
    } else if (errorCode == ErrorCode.FILE_CANCELLED.toInt()) {
      return ErrorCode.FILE_CANCELLED;
    } else if (errorCode == ErrorCode.UPLOAD_STOPPED.toInt()) {
      return ErrorCode.UPLOAD_STOPPED;
    } else {
      return ErrorCode.UNKNOWN;
    }
  }

  /**
   * The original numeric error code returned by the Uploader component in the case of an
   * uploader failure.
   *
   * @return int
   */
  public int getErrorCodeAsInt() {
    return errorCode;
  }

  /**
   * Return a human readable error message explaining the cause of the uploader failure.
   *
   * @return String
   */
  public String getMessage() {
    return message;
  }
  
  /**
   * Return a server response (if any).
   *
   * @return String
   */
  public String getServerData() {
    return serverData;
  }

}
