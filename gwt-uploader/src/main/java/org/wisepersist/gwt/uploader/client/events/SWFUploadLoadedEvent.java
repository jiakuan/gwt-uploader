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

import org.wisepersist.gwt.uploader.client.Uploader;

/**
 * Provides access to the raw information provided by the Uploader when an 'swfUploadLoaded' event
 * occurs.
 * This class should not be instantiated directly, but instead you should create a
 * {@link SWFUploadLoadedHandler} and register it via the {@link
 * Uploader#setSWFUploadLoadedHandler(SWFUploadLoadedHandler)}
 * <p>
 * Note that the 'swfUploadLoaded' event is fired by the Uploader component's internal 'flashReady'
 * event to let you know that it is safe to call Uploader methods.  Also note that these events
 * are currently only fired when the Uploader component determines that the browser does not support
 * the Ajax upload technique and is therefore utilizing the SWFUpload/Flash technique for handling
 * the upload.  If you need to determine which state the Uploader component is in, you can
 * make use of the {@link Uploader#isAjaxUploadWithProgressEventsSupported()}
 * method.
 * <p>
 *
 * @author delight.wjk@gmail.com
 */
public class SWFUploadLoadedEvent {

  /**
   * This constructor is intended for internal use only.  You should not create Uploader loaded
   * events directly, but instead should register a {@link SWFUploadLoadedHandler}.
   */
  public SWFUploadLoadedEvent() {
  }

}
