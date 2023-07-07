package org.docstr.gwt.uploader.demo.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * @author delight.wjk@gmail.com
 */
public interface AppResources extends ClientBundle {

  AppResources INSTANCE = GWT.create(AppResources.class);

  @Source("cancel.png")
  ImageResource cancel();

  @Source("upload_new_version_button.png")
  ImageResource upload();
}
