package org.wisepersist.gwt.uploader.demo.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

/**
 * @author delight.wjk@gmail.com
 */
public interface AppResources extends ClientBundle {

  AppResources INSTANCE = GWT.create(AppResources.class);

  @Source("cancel.png")
  ImageResource cancel();

  @Source("upload_new_version_button.png")
  ImageResource upload();

  @Source("ImageButtonAndProgressBar.java")
  TextResource sourceImageButtonAndProgressBar();

  @Source("ImageButtonAndProgressText.java")
  TextResource sourceImageButtonAndProgressText();

  @Source("MultiUploadWithProgressBar.java")
  TextResource sourceMultiUploadWithProgressBar();

  @Source("MultiUploadWithProgressBarAndDragAndDrop.java")
  TextResource sourceMultiUploadWithProgressBarAndDragAndDrop();

  @Source("TextButtonAndProgressText.java")
  TextResource TextButtonAndProgressText();
}
