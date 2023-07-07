package org.docstr.gwt.uploader.demo.client;

import elemental2.core.JsError;
import elemental2.dom.Response;
import org.docstr.gwt.uploader.demo.shared.HttpReq;

import java.util.function.Consumer;

import static elemental2.dom.DomGlobal.console;

/**
 * @author jake
 */
public class SourceCode {
  public static void fetch(Class<?> clazz, Consumer<String> callback) {
    // org.docstr.gwt.uploader.demo.client.ImageButtonAndProgressBar
    String url = "https://raw.githubusercontent.com/jiakuan/gwt-uploader/master/gwt-uploader-demo/src/main/java/" + clazz.getName().replace('.', '/') + ".java";
    HttpReq.create(url)
        .setRespHandler(new HttpReq.ResponseHandler() {
          @Override
          public void onResult(Response response) {
            response.text().then(text -> {
              console.log(text);
              callback.accept(text);
              return null;
            });
          }

          @Override
          public void onError(JsError error) {
            console.error(error);
            callback.accept(error.message);
          }
        })
        .send();
  }
}
