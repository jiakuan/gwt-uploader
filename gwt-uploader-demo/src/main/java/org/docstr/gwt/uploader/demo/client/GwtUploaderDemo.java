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

package org.docstr.gwt.uploader.demo.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import java.util.HashMap;
import java.util.Map;

import static elemental2.dom.DomGlobal.console;

/**
 * @author delight.wjk@gmail.com
 */
public class GwtUploaderDemo implements EntryPoint {

  private Map<String, UploaderSample> uploaderPanels = new HashMap<>();
  private SourceCodePopupPanel sourceCodePopup = new SourceCodePopupPanel();

  @Override
  public void onModuleLoad() {
    console.log("GwtUploaderDemo.onModuleLoad() - " + ImageButtonAndProgressBar.class.getName());

    uploaderPanels.put("TextButtonAndProgressText", new TextButtonAndProgressText());
    uploaderPanels.put("ImageButtonAndProgressText", new ImageButtonAndProgressText());
    uploaderPanels.put("ImageButtonAndProgressBar", new ImageButtonAndProgressBar());
    uploaderPanels.put("MultiUploadWithProgressBar", new MultiUploadWithProgressBar());
    uploaderPanels.put("MultiUploadWithProgressBarAndDragAndDrop",
        new MultiUploadWithProgressBarAndDragAndDrop());

    for (Map.Entry<String, UploaderSample> entry : uploaderPanels.entrySet()) {
      final UploaderSample sample = entry.getValue();
      final Widget uploaderPanel = sample.getUploaderPanel();
      final Button btnViewSource = new Button("View Source");
      btnViewSource.getElement().getStyle().setMarginTop(10, Style.Unit.PX);
      btnViewSource.addClickHandler(event -> {
        sourceCodePopup.showSourceCode("Loading source code...");
        SourceCode.fetch(sample.getClass(), source ->
            sourceCodePopup.showSourceCode(source));
      });
      VerticalPanel panel = new VerticalPanel();
      panel.add(uploaderPanel);
      panel.add(btnViewSource);
      RootPanel.get(entry.getKey()).add(panel);
    }
  }
}
