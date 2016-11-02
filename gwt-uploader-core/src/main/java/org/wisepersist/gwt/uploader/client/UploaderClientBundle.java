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

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 * Client bundle for uploader related resources.
 *
 * @author delight.wjk@gmail.com
 */
public interface UploaderClientBundle extends ClientBundle {

  /**
   * The static instance ready to be used.
   */
  UploaderClientBundle INSTANCE = GWT.create(UploaderClientBundle.class);

  /**
   * SWF upload JavaScript.
   *
   * @return Text resource of SWF upload JavaScript.
   */
  @Source("resources/swfupload.js")
  TextResource swfupload();

  /**
   * SWF upload speed JavaScript.
   *
   * @return Text resource of SWF upload speed JavaScript.
   */
  @Source("resources/swfupload.speed.js")
  TextResource swfuploadSpeed();
}
