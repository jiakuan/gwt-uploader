package org.wisepersist.gwt.uploader.demo.server;

import com.google.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jiakuanwang
 */
@Singleton
public class UploadServlet extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(UploadServlet.class); //NOPMD

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    log.debug("Received upload request...");
  }
}
