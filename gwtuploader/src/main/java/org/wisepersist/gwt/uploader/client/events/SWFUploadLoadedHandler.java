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
 * An interface that can be used as a callback handler when SWFUpload loaded events are fired by
 * the Uploader component.  General usage is as follows:
 * <pre>
 * uploader.setSWFUploadLoadedHandler(new SWFUploadLoadedHandler() {
 *    public boolean onSWFUploadLoaded(SWFUploadLoadedEvent event) {
 *       Window.alert("Uploader is ready to go");
 *       return true;
 *    }
 * });
 * </pre>
 * Note that the 'swfUploadLoaded' event is fired by the Uploader component's internal 'flashReady'
 * event to let you know that it is safe to call Uploader methods.  Also note that these events
 * are currently only fired when the Uploader component determines that the browser does not support
 * the Ajax upload technique and is therefore utilizing the SWFUpload/Flash technique for handling
 * the upload.  If you need to determine which state the Uploader component is in, you can
 * make use of the {@link org.wisepersist.gwt.uploader.client
 * .Uploader#isAjaxUploadWithProgressEventsSupported()} method.
 * <p>
 * See the documentation on the {@link SWFUploadLoadedEvent} class for more details on the data
 * available when an Uploader loaded event occurs.
 *
 * @author delight.wjk@gmail.com
 */
public interface SWFUploadLoadedHandler {

  /**
   * This method is fired whenever a Uploader loaded event occurs in the Uploader component.  See
   * the {@link SWFUploadLoadedEvent} class for more details on the data available when this
   * event is fired.
   *
   * @param swfUploadLoadedEvent The details of the event that occurred.
   * @return The response to send back to the event handler function
   */
  public boolean onSWFUploadLoaded(SWFUploadLoadedEvent swfUploadLoadedEvent);

}
