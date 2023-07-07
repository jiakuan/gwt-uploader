package org.docstr.gwt.uploader.demo.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author jiakuanwang
 */
public class SourceCodePopupPanel extends DialogBox {

  private HTML html = new HTML();

  public SourceCodePopupPanel() {
    // PopupPanel's constructor takes 'auto-hide' as its boolean parameter.
    // If this is set, the panel closes itself automatically when the user
    // clicks outside of it.
    super(true);
    // Set the dialog box's caption.
    setText("Source Code");

    // Enable animation.
    setAnimationEnabled(true);

    // Enable glass background.
    setGlassEnabled(true);
    Button btnClose = new Button("Close");
    btnClose.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        hide();
      }
    });
    VerticalPanel panel = new VerticalPanel();
    panel.add(html);
    panel.add(btnClose);
    panel.setCellHorizontalAlignment(btnClose, HasHorizontalAlignment.ALIGN_RIGHT);
    setWidget(panel);
  }

  public void showSourceCode(String sourceCode) {
    int width = (Window.getClientWidth() - 200);
    int height = (Window.getClientHeight() - 200);
    html.setHTML(
        "<pre style='width: " + width + "px; height: " + height + "px; overflow: scroll;'>"
        + sourceCode + "</pre>");
    setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      public void setPosition(int offsetWidth, int offsetHeight) {
        int left = (Window.getClientWidth() - offsetWidth) / 2;
        int top = (Window.getClientHeight() - offsetHeight) / 2;
        setPopupPosition(left, top);
      }
    });
  }
}
