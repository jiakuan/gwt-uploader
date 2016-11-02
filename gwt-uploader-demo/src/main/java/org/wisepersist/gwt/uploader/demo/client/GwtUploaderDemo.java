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

package org.wisepersist.gwt.uploader.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author delight.wjk@gmail.com
 */
public class GwtUploaderDemo implements EntryPoint {

  private Map<String, UploaderPanel> uploaderPanels = new HashMap<>();

  @Override
  public void onModuleLoad() {
    uploaderPanels.put("TextButtonAndProgressText", new TextButtonAndProgressText());
    uploaderPanels.put("ImageButtonAndProgressText", new ImageButtonAndProgressText());
    uploaderPanels.put("ImageButtonAndProgressText", new ImageButtonAndProgressBar());
    uploaderPanels.put("MultiUploadWithProgressBar", new MultiUploadWithProgressBar());
    uploaderPanels.put("MultiUploadWithProgressBarAndDragAndDrop",
                       new MultiUploadWithProgressBarAndDragAndDrop());

    for (Map.Entry<String, UploaderPanel> entry : uploaderPanels.entrySet()) {
      RootPanel.get(entry.getKey()).add(entry.getValue().getUploaderPanel());
    }
  }
}
