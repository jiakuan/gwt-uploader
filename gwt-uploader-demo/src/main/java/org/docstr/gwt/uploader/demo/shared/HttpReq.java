package org.docstr.gwt.uploader.demo.shared;

import elemental2.core.JsError;
import elemental2.dom.*;

import java.util.ArrayList;
import java.util.List;

import static elemental2.dom.DomGlobal.console;

public class HttpReq {

  public enum Method {
    GET,
    POST,
    PUT
  }

  public interface ResponseHandler {

    void onResult(Response response);

    void onError(JsError error);
  }

  private URLSearchParams queryParams = new URLSearchParams();
  private String[][] headers = new String[0][];
  private RequestInit requestInit = RequestInit.create();
  private ResponseHandler responseHandler;

  private final String url;

  private HttpReq(String url) {
    this.url = url;
  }

  public static HttpReq create(String url) {
    return new HttpReq(url);
  }

  public HttpReq setMethod(Method method) {
    requestInit.setMethod(method.name());
    return this;
  }

  public HttpReq addQueryParam(String name, String value) {
    queryParams.append(name, value);
    return this;
  }

  public HttpReq addHeader(String name, String value) {
    headers = mergeHeaders(headers, new String[][]{{name, value}});
    return this;
  }

  public HttpReq addHeaders(String[][] headers) {
    this.headers = mergeHeaders(this.headers, headers);
    return this;
  }

  public static String[][] mergeHeaders(
      String[][] headers1, String[][] headers2) {
    List<String[]> mergedHeaders = new ArrayList<>();
    if (headers1 != null) {
      for (String[] header : headers1) {
        mergedHeaders.add(header);
      }
    }
    if (headers2 != null) {
      for (String[] header : headers2) {
        mergedHeaders.add(header);
      }
    }
    return mergedHeaders.toArray(new String[0][]);
  }

  public HttpReq setBody(FormData body) {
    this.requestInit.setBody(body);
    return this;
  }

  public HttpReq setBody(URLSearchParams body) {
    this.requestInit.setBody(body);
    return this;
  }

  public HttpReq setBody(String body) {
    this.requestInit.setBody(body);
    return this;
  }

  public HttpReq setRespHandler(ResponseHandler responseHandler) {
    this.responseHandler = responseHandler;
    return this;
  }

  public void send() {
    StringBuilder fullUrl = new StringBuilder(url);
    String queryStr = queryParams.toString();
    if (queryStr.length() > 0) {
      fullUrl.append(fullUrl.indexOf("?") > 0 ? "&" : "?")
          .append(queryStr);
    }

    requestInit.setHeaders(headers);

    DomGlobal.fetch(fullUrl.toString(), requestInit)
        .then(resp -> {
          if (responseHandler != null) {
            responseHandler.onResult(resp);
          }
          return null;
        })
        .catch_(error -> {
          if (error instanceof JsError) {
            JsError jsError = (JsError) error;
            if (responseHandler != null) {
              responseHandler.onError(jsError);
            }
          } else {
            console.error("Unknown error: " + error);
          }
          return null;
        });
  }
}
