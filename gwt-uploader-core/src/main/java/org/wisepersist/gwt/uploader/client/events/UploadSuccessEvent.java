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
 * Provides access to the raw information provided by Uploader when an 'uploader success' event
 * occurs.
 * This class should not be instantiated directly, but instead you should create a
 * {@link UploadSuccessHandler} and register it via the {@link org.wisepersist.gwt.uploader.client
 * .Uploader#setUploadSuccessHandler(UploadSuccessHandler)}
 * method in order to access uploader success events.
 *
 * <p>Note that 'uploadSuccess' is fired when the entire uploader has been transmitted and the
 * server returns a HTTP 200 status code. Any data outputted by the server is available in the
 * server data property.
 *
 * <p>Due to some bugs in the Flash Player the server response may not be acknowledged and no
 * uploadSuccess event is fired by Flash. In this case the {@link org.wisepersist.gwt.uploader
 * .client.Uploader#setAssumeSuccessTimeout(long)}
 * setting is checked to see if enough time has passed to fire uploadSuccess anyway. In this
 * case the received response parameter will be false.
 *
 * <p>The {@link org.wisepersist.gwt.uploader.client.Uploader#setHttpSuccess(long...)} setting
 * allows uploadSuccess to be fired for HTTP status codes other than 200. In this case no server
 * data is available from the Flash Player.
 *
 * <p>At this point the uploader is not yet complete. Another uploader cannot be started from
 * uploadSuccess.
 *
 * @author delight.wjk@gmail.com
 */
public class UploadSuccessEvent extends AbstractUploadEvent {

  private String serverData;
  private String responseReceived;

  /**
   * This constructor is intended for internal use only.  You should not create uploader success
   * events directly, but instead should register a {@link UploadSuccessHandler}.
   *
   * @param file The native javascript object containing the details of the file being uploaded.
   * @param serverData Any HTTP response data sent from the server at the completion of the
   *                   uploader.
   * @param responseReceived The HTTP response code sent from the server at the completion of the
   *                         uploader.
   */
  public UploadSuccessEvent(final File file, final String serverData,
                            final String responseReceived) {
    super(file);
    this.serverData = serverData;
    this.responseReceived = responseReceived;
  }

  /**
   * Return any HTTP response data sent from the server at the completion of the uploader.
   *
   * @return String
   */
  public final String getServerData() {
    return serverData;
  }

  /**
   * Return the HTTP response code sent from the server at the completion of the uploader.
   *
   * @return String
   */
  public final String getResponseReceived() {
    return responseReceived;
  }

}
