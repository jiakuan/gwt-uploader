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
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayNumber;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Label;

import org.wisepersist.gwt.uploader.client.events.FileDialogCompleteEvent;
import org.wisepersist.gwt.uploader.client.events.FileDialogCompleteHandler;
import org.wisepersist.gwt.uploader.client.events.FileDialogStartEvent;
import org.wisepersist.gwt.uploader.client.events.FileDialogStartHandler;
import org.wisepersist.gwt.uploader.client.events.FileQueueErrorEvent;
import org.wisepersist.gwt.uploader.client.events.FileQueueErrorHandler;
import org.wisepersist.gwt.uploader.client.events.FileQueuedEvent;
import org.wisepersist.gwt.uploader.client.events.FileQueuedHandler;
import org.wisepersist.gwt.uploader.client.events.UploadCompleteEvent;
import org.wisepersist.gwt.uploader.client.events.UploadCompleteHandler;
import org.wisepersist.gwt.uploader.client.events.UploadErrorEvent;
import org.wisepersist.gwt.uploader.client.events.UploadErrorHandler;
import org.wisepersist.gwt.uploader.client.events.UploadProgressEvent;
import org.wisepersist.gwt.uploader.client.events.UploadProgressHandler;
import org.wisepersist.gwt.uploader.client.events.UploadStartEvent;
import org.wisepersist.gwt.uploader.client.events.UploadStartHandler;
import org.wisepersist.gwt.uploader.client.events.UploadSuccessEvent;
import org.wisepersist.gwt.uploader.client.events.UploadSuccessHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The main GWT widget that can be constructed and then configured in order to add an uploader
 * component into a GWT application.  Basic usage is as follows:
 * <pre><code>
 * Uploader uploader = new Uploader()
 *    .setUploadURL("http://www.widgetcorp.com/uploads.php")
 *    .setButtonText("Click Here <span class='redText'>Yeah</span>")
 *    .setButtonTextStyle(".redText { color: #FF0000; }")
 *    .setButtonWidth(100)
 *    .setButtonHeight(22)
 *    .setButtonCursor(Uploader.Cursor.HAND);
 * RootPanel.get().add(uploader);
 * </code></pre>
 * Normally you'll also want to respond to various events that the GWT uploader component
 * can generate in order to notify the user about the progress and status of the uploader. E.g.
 * <pre><code>
 * uploader.setUploadProgressHandler(new UploadProgressHandler() {
 *   public boolean onUploadProgress(UploadProgressEvent uploadProgressEvent) {
 *     Window.alert("File progress: " + uploadProgressEvent.getFile().getName() + "\n" +
 *       NumberFormat.getPercentFormat().format(
 *         uploadProgressEvent.getBytesComplete() / uploadProgressEvent.getBytesTotal()
 *       ) + " Percent Complete"
 *     );
 *     return true;
 *   }
 * });
 * </code></pre>
 * Similar events are available for when the file dialog window is opened, when a new file
 * is added to the queue, when an uploader is started/completed, when errors occur, etc.
 * <p>
 * Internally the GWT Uploader component defaults to use the browser's DOM
 * and XMLHttpRequest Level 2 mechanisms to manage the file selection, tracking of the various
 * events, and handling the uploader of the file to the server.  More
 * details on the browsers which support the XMLHttpRequest Level 2 APIs can be found
 * <a href="http://caniuse.com/xhr2">here</a>. 
 *
 * @author delight.wjk@gmail.com
 */
public class Uploader extends AbsolutePanel {

  private static final int BYTES_PER_KILOBYTE = 1024;
  private static final int BYTES_PER_MEGABYTE = BYTES_PER_KILOBYTE * 1024;
  private static final int BYTES_PER_GIGABYTE = BYTES_PER_MEGABYTE * 1024;

  private boolean loaded = false;

  /**
   * An enumeration of supported button action types, which can be passed to the
   * {@link Uploader#setButtonAction(ButtonAction)} method.  The button action
   * defines the action taken when the uploader button (text, image) is clicked.
   */
  public enum ButtonAction {

    /**
     * Select a single file when the button is clicked.
     */
    SELECT_FILE(-100),

    /**
     * Select multiple files when the button is clicked.
     */
    SELECT_FILES(-110),

    /**
     * Begin the uploader process when the button is clicked.
     */
    START_UPLOAD(-120);

    private ButtonAction(int optionValue) {
      this.optionValue = optionValue;
    }

    private final int optionValue;

    public int toInt() {
      return optionValue;
    }
  }

  /**
   * An enumeration of supported button cursor types, which can be passed to the
   * {@link Uploader#setButtonCursor(Cursor)} method.  The button cursor
   * is used to define what type of mouse cursor is displayed when hovering over the
   * uploader button.
   */
  public enum Cursor {

    /**
     * Show the cursor as an arrow when the user hovers over the button.
     */
    ARROW(-1),

    /**
     * Show the cursor as a hand when the user hovers over the button.
     */
    HAND(-2);

    private Cursor(int optionValue) {
      this.optionValue = optionValue;
    }

    private final int optionValue;

    public int toInt() {
      return optionValue;
    }
  }

  /**
   * Create a new Uploader component that can be added to a GWT application, setting up
   * an element in the DOM that the uploader button elements will be rendered inside of.
   */
  public Uploader() {
    super();
  }

  private String uploadURL;

  /**
   * Convenience method for setting the 'upload_url' option of the component, either before or
   * after the widget has been added to the DOM.
   * The upload_url setting accepts a full, absolute, or relative target URL for the uploaded file.
   * Relative URLs should be relative to the document.
   * <p>
   * By default both the XMLHttpRequest Level 2/Ajax uploads will be posted to whatever URL is specified via this method.
   * However, if you'd like the Ajax uploads to be posted to a different URL, you can call the 
   * {@link #setAjaxUploadURL(String)} method to set a unique URL for the Ajax uploads.
   *
   * @param uploadURL The value to set as the 'upload_url' option on the Uploader component.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setUploadURL(String uploadURL) {
    this.uploadURL = uploadURL;
    return this;
  }

  private JSONObject httpHeaders = null;

  /**
    * Set the http headers to inject.
    * <pre><code>
    *     JSONObject httpHeaders = new JSONObject();
    *     httpHeaeders.put("header1", new JSONString("headervalue1"));
    *     httpHeaeders.put("header2", new JSONString("headervalue2"));
    *     httpHeaeders.put("header3", new JSONString("headervalue3"));
    *     uploader.setHttpHeaders("httpHeaeders");
    * </code></pre>
    *
    * @param httpHeaders The headers to inject into the xhr2 request.
    */
  public void setHttpHeaders(JSONObject httpHeaders) {
    this.httpHeaders = httpHeaders;
  }
    
  private String ajaxUploadURL;

  /**
   * By default both the XMLHttpRequest Level 2/Ajax uploads will
   * be posted to whatever URL is specified via the {@link #setUploadURL(String)} method.
   * However, if you'd like the Ajax uploads to be posted to a different URL, you can call 
   * this method to set a unique URL for the Ajax uploads.
   *
   * @param ajaxUploadURL The URL that only the Ajax uploads should be posted to (or null
   *                      to uploader the Ajax uploads to whatever
   *                      the {@link #setUploadURL(String)} option has been set to.)
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setAjaxUploadURL(String ajaxUploadURL) {
    this.ajaxUploadURL = ajaxUploadURL;
    return this;
  }

  private boolean ajaxUploadEnabled = true;
  private String filePostName = null;

  /**
   * Convenience method for setting the 'file_post_name' option of the component, either before or
   * after the widget has been added to the DOM.  
   * The file_post_name allows you to set the value name used to post the file. This is not related
   * to the file name. The default value is 'Filedata'. For maximum compatibility it is recommended
   * that the default value is used.
   *
   * @param filePostName The value to set as the 'file_post_name' option on the Uploader component.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setFilePostName(String filePostName) {
    this.filePostName = filePostName;
    return this;
  }

  private JSONObject postParams = null;

  /**
   * Convenience method for setting the 'post_params' option of the component, either before or
   * after the widget has been added to the DOM.  
   * The post_params setting defines the name/value pairs that will be posted with each uploaded
   * file. This setting accepts a simple JavaScript object. Multiple post name/value pairs should
   * be defined as demonstrated in the sample settings object. Values must be either strings or
   * numbers (as interpreted by the JavaScript typeof function).
   * <p>
   *
   * @param postParams The value to set as the 'post_params' option on the Uploader component.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setPostParams(JSONObject postParams) {
    this.postParams = postParams;
    return this;
  }

  private boolean requeueOnError = false;

  /**
   * Convenience method for setting the 'requeue_on_error' option of the component.  
   * The requeue_on_error setting may be true or false. When this setting is true any files that
   * has an uploadError (excluding fileQueue errors and the FILE_CANCELLED uploadError) is
   * returned to the front of the queue rather than being discarded. The file can be uploaded
   * again if needed. To remove the file from the queue the cancelUpload method must be called.
   * <p>
   * All the events associated with a failed uploader are still called and so the requeuing the
   * failed uploader can conflict with the Queue Plugin (or custom code that uploads the entire
   * queue). Code that automatically uploads the next file in the queue will uploader the failed
   * file over and over again if care is not taken to allow the failing uploader to be cancelled.
   *
   * @param requeueOnError The value to set as the 'requeue_on_error' option on the Uploader
   *                       component (default: false).
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setRequeueOnError(boolean requeueOnError) {
    this.requeueOnError = requeueOnError;
    return this;
  }

  private long[] httpSuccess;

  /**
   * Convenience method for setting the 'http_success' option of the component.  
   * An array that defines the HTTP Status Codes that will trigger success. 200 is always a success.
   * Also, only the 200 status code provides the serverData.
   * <p>
   * When returning and accepting an HTTP Status Code other than 200 it is not necessary for the
   * server to return content.
   *
   * @param httpSuccess The values to set as the HTTP response codes to treat as a successful
   *                    response (default: 200).
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setHttpSuccess(long... httpSuccess) {
    this.httpSuccess = httpSuccess;
    return this;
  }

  private String fileTypes;

  /**
   * Convenience method for setting the 'file_types' option of the component, either before or
   * after the widget has been added to the DOM.  
   * The file_types setting accepts a semi-colon separated list of file extensions that are
   * allowed to be selected by the user. Use '*.*' to allow all file types.
   *
   * @param fileTypes A semi-colon delimited list of file types that the file dialog should allow
   *                  the user to select.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setFileTypes(String fileTypes) {
    this.fileTypes = fileTypes;
    return this;
  }

  private String fileTypesDescription;

  /**
   * Convenience method for setting the 'file_types_description' option of the component, either
   * before or after the widget has been added to the DOM.  
   * A text description that is displayed to the user in the File Browser dialog.
   *
   * @param fileTypesDescription A text description that is displayed to the user in the File
   *                             Browser dialog.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setFileTypesDescription(String fileTypesDescription) {
    // TODO(jake): Add Ajax mode support for this option
    this.fileTypesDescription = fileTypesDescription;
    return this;
  }

  private long fileSizeLimit = 0;

  /**
   * Convenience method for setting the 'file_size_limit' option of the component, either before or
   * after the widget has been added to the DOM.  
   * The file_size_limit setting defines the maximum allowed size of a file to be uploaded.
   * This setting accepts a value and unit. Valid units are B, KB, MB and GB.
   * <p>
   * Examples: 2147483648 B, 2097152, 2097152KB, 2048 MB, 2 GB
   * <p>
   * If the unit is omitted default is KB. A value of 0 (zero) is interpreted as unlimited.
   * <p>
   * Note: This setting only applies to the user's browser. It does not affect any settings or
   * limits on the web server.
   *
   * @param fileSizeLimit The maximum allowed size of a file to be uploaded.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setFileSizeLimit(String fileSizeLimit) {
    if (fileSizeLimit != null && fileSizeLimit.length() > 0 && Character
        .isDigit(fileSizeLimit.charAt(0))) {
      String units = "";
      int i = 0;

      while (i < fileSizeLimit.length() && Character.isDigit(fileSizeLimit.charAt(i))) {
        i++;
      }
      if (i < fileSizeLimit.length() - 1) {
        // Get the units
        units = fileSizeLimit.substring(i).trim();
      }
      long size = Long.parseLong(fileSizeLimit.substring(0, i));

      if ("".equalsIgnoreCase(units)) {
        // Default to KB
        this.fileSizeLimit = size * BYTES_PER_KILOBYTE;
      } else if ("B".equalsIgnoreCase(units)) {
        this.fileSizeLimit = size;
      } else if ("KB".equalsIgnoreCase(units)) {
        this.fileSizeLimit = size * BYTES_PER_KILOBYTE;
      } else if ("MB".equalsIgnoreCase(units)) {
        this.fileSizeLimit = size * BYTES_PER_MEGABYTE;
      } else if ("GB".equalsIgnoreCase(units)) {
        this.fileSizeLimit = size * BYTES_PER_GIGABYTE;
      } else {
        // Default to unlimited
        this.fileSizeLimit = 0;
      }
    }
    return this;
  }

  private long fileUploadLimit = 0;
  private long totalFilesUploaded = 0;

  /**
   * Convenience method for setting the 'file_upload_limit' option of the component, either
   * before or after the widget has been added to the DOM.  
   * Defines the number of files allowed to be uploaded by the uploader component. This setting
   * also sets the upper bound of the file_queue_limit setting. Once the user has uploaded or
   * queued the maximum number of files she will no longer be able to queue additional files. The
   * value of 0 (zero) is interpreted as unlimited. Only successful uploads (uploads the trigger
   * the uploadSuccess event) are counted toward the uploader limit.
   * <p>
   * Note: This value is not tracked across pages and is reset when a page is refreshed. File
   * quotas should be managed by the web server.
   *
   * @param fileUploadLimit The number of files allowed to be uploaded by the uploader component.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setFileUploadLimit(long fileUploadLimit) {
    this.fileUploadLimit = fileUploadLimit;
    return this;
  }

  private long fileQueueLimit = Long.MAX_VALUE;

  /**
   * Convenience method for setting the 'file_queue_limit' option of the component, either before
   * or after the widget has been added to the DOM.  
   * Defines the number of unprocessed files allowed to be simultaneously queued. Once a file is
   * uploaded, errored, or cancelled new files can be queued in its place until the queue limit
   * has been reached. If the uploader limit (or remaining uploads allowed) is less than the queue
   * limit then the lower number is used.
   *
   * @param fileQueueLimit The number of unprocessed files allowed to be simultaneously queued.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setFileQueueLimit(long fileQueueLimit) {
    this.fileQueueLimit = fileQueueLimit;
    return this;
  }

  private String buttonImageURL;

  /**
   * Convenience method for setting the 'button_image_url' option of the component, either before
   * or after the widget has been added to the DOM.  
   * Fully qualified, absolute or relative URL to the image file to be used as the button's image.
   * <p>
   * This URL is affected by the preserve_relative_urls setting and should follow the same
   * rules as the upload_url setting.
   * <p>
   * The button image is treated as a sprite. There are 4 button states that must be represented
   * by the button image. Each button state image should be stacked above the other in this
   * order: normal, hover, down/click, disabled.
   *
   * @param buttonImageURL The URL to the image file to be used as the button's image.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setButtonImageURL(String buttonImageURL) {
    this.buttonImageURL = buttonImageURL;
    return this;
  }

  private int buttonWidth = -1;

  /**
   * Convenience method for setting the 'button_width' option of the component, either before or
   * after the widget has been added to the DOM. 
   * A number defining the width of the uploader button.
   *
   * @param buttonWidth The value to set as width of the uploader button.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setButtonWidth(int buttonWidth) {
    this.buttonWidth = buttonWidth;
    if (this.buttonWidth >= 0 && this.buttonHeight >= 0) {
      this.setWidth(this.buttonWidth + "px");
      this.setHeight(this.buttonHeight + "px");
    }
    return this;
  }

  private int buttonHeight = -1;

  /**
   * Convenience method for setting the 'button_height' option of the component, either before or
   * after the widget has been added to the DOM.  
   * A number defining the height of the uploader button.
   *
   * @param buttonHeight The value to set as height of the uploader button.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setButtonHeight(int buttonHeight) {
    this.buttonHeight = buttonHeight;
    if (this.buttonWidth >= 0 && this.buttonHeight >= 0) {
      this.setWidth(this.buttonWidth + "px");
      this.setHeight(this.buttonHeight + "px");
    }
    return this;
  }

  private String buttonText;
  private DivElement buttonTextElement;
  private DivElement buttonImageElement;

  /**
   * Convenience method for setting the 'button_text' option of the component, either before or
   * after the widget has been added to the DOM. 
   * Plain or HTML text that is displayed over the uploader button. HTML text can be further styled
   * using CSS classes and the button_text_style setting.
   *
   * @param buttonText Plain or HTML text that is displayed over the uploader button.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setButtonText(String buttonText) {
    this.buttonText = buttonText;
    if (buttonTextElement != null) {
      buttonTextElement.setInnerHTML(buttonText);
    }
    return this;
  }

  private String buttonTextStyle;
  private StyleElement buttonTextStyleElement;

  /**
   * Convenience method for setting the 'button_text_style' option of the component, either before
   * or after the widget has been added to the DOM.  
   * CSS style string that defines how the button_text is displayed.
   *
   * @param buttonTextStyle The CSS style string that defines how the button text is displayed.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setButtonTextStyle(String buttonTextStyle) {
    this.buttonTextStyle = buttonTextStyle;
    
    if (buttonTextStyleElement != null) {
      buttonTextStyleElement.setInnerText(buttonTextStyle);
    }
    return this;
  }

  private int buttonTextTopPadding = Integer.MIN_VALUE;

  /**
   * Convenience method for setting the 'button_text_top_padding' option of the component, either
   * before or after the widget has been added to the DOM.  
   * Used to vertically position the button text within the uploader component. Negative values may
   * be used.
   *
   * @param buttonTextTopPadding The amount of padding to include between the outer bounds of the
   *                             uploader component and the top of the text.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setButtonTextTopPadding(int buttonTextTopPadding) {
    this.buttonTextTopPadding = buttonTextTopPadding;
    return this;
  }

  private int buttonTextLeftPadding = Integer.MIN_VALUE;

  /**
   * Convenience method for setting the 'button_text_left_padding' option of the component, either
   * before or after the widget has been added to the DOM.  
   * Used to horizontally position the button text within the uploader component. Negative values
   * may be used.
   *
   * @param buttonTextLeftPadding The amount of padding to include between the outer bounds of the
   *                              uploader component and the left of the text.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setButtonTextLeftPadding(int buttonTextLeftPadding) {
    this.buttonTextLeftPadding = buttonTextLeftPadding;
    return this;
  }

  private ButtonAction buttonAction = ButtonAction.SELECT_FILES;

  /**
   * Convenience method for setting the 'button_action' option of the component, either before or
   * after the widget has been added to the DOM.  
   * Defines the action to take when the uploader component is clicked.
   *
   * @param buttonAction The action to take when the uploader component is clicked.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setButtonAction(ButtonAction buttonAction) {
    this.buttonAction = buttonAction;
    return this;
  }

  private boolean buttonDisabled = false;

  /**
   * Convenience method for setting the 'button_disabled' option of the component, either before or
   * after the widget has been added to the DOM.  
   * A boolean value that sets whether the uploader button is in the disabled state. When in the
   * disabled state the button will not execute any actions.
   *
   * @param buttonDisabled Whether the uploader button is in the disabled state.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setButtonDisabled(boolean buttonDisabled) {
    this.buttonDisabled = buttonDisabled;

    if (buttonImageElement != null && buttonHeight >= 0) {
      if (buttonDisabled) {
        buttonImageElement.getStyle()
            .setProperty("backgroundPosition", "0px -" + (buttonHeight * 3) + "px");
      } else {
        buttonImageElement.getStyle().setProperty("backgroundPosition", "0px 0px");
      }
    }
    return this;
  }

  /**
   * Returns whether or not the button has been set in a disabled state via the
   * {@link #setButtonDisabled(boolean)} method.
   *
   * @return 'true' if the button has been disabled (e.g. grayed out), or 'false' if the
   * button is still enabled.
   */
  public boolean getButtonDisabled() {
    return this.buttonDisabled;
  }

  private Cursor buttonCursor;

  /**
   * Convenience method for setting the 'button_cursor' option of the component, either before or
   * after the widget has been added to the DOM. 
   * Used to define what type of mouse cursor is displayed when hovering over the uploader
   * component.
   *
   * @param cursor The type of cursor to display when the mouse is hovering over the uploader
   *               component.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setButtonCursor(Cursor cursor) {
    buttonCursor = cursor;
    return this;
  }

  private JSONObject customSettings;

  /**
   * Convenience method for setting the 'custom_settings' option of the component. 
   * The custom_settings setting allows developers to safely attach additional information to a
   * uploader instance without worrying about affecting internal values or changes
   * in new GWT Uploader versions. This setting accepts a JSON object.
   * <br>
   * Once instantiated the custom settings are accessed via the {@link #getCustomSettings()}
   * method.
   *
   * @param customSettings The value to set as the 'custom_settings' option on the uploader
   *                       component.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setCustomSettings(JSONObject customSettings) {
    this.customSettings = customSettings;
    return this;
  }

  /**
   * Returns the custom settings that were stored on this uploader component instance via
   * the {@link #setCustomSettings(JSONObject)} method. The custom_settings setting allows
   * developers to safely attach additional information to a uploader instance without worrying
   * about affecting internal values or changes in new GWT Uploader versions.
   *
   * @return JSONObject
   */
  public JSONObject getCustomSettings() {
    return customSettings;
  }

  private Stats stats;

  /**
   * Returns the overall general statistics being tracked by the Uploader component, or null
   * if the widget has not yet been added to the DOM.
   *
   * @return Stats
   */
  public Stats getStats() {
    if (stats == null) {
      stats = (Stats) JavaScriptObject.createObject();
    }
    return stats;
  }

  private FileDialogStartHandler fileDialogStartHandler;

  /**
   * Set a callback handler that will be invoked whenever a file dialog start event is fired.
   *
   * @param fileDialogStartHandler The handler that should be invoked whenever a file dialog start
   *                               event occurs.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setFileDialogStartHandler(FileDialogStartHandler fileDialogStartHandler) {
    this.fileDialogStartHandler = fileDialogStartHandler;
    return this;
  }

  private FileQueuedHandler fileQueuedHandler;

  /**
   * Set a callback handler that will be invoked whenever a file queued event is fired.
   *
   * @param fileQueuedHandler The handler that should be invoked whenever a file queued event
   *                          occurs.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setFileQueuedHandler(FileQueuedHandler fileQueuedHandler) {
    this.fileQueuedHandler = fileQueuedHandler;
    return this;
  }

  private FileQueueErrorHandler fileQueueErrorHandler;

  /**
   * Set a callback handler that will be invoked whenever a file queue error event is fired.
   *
   * @param fileQueueErrorHandler The handler that should be invoked whenever a file queue error
   *                              event occurs.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setFileQueueErrorHandler(FileQueueErrorHandler fileQueueErrorHandler) {
    this.fileQueueErrorHandler = fileQueueErrorHandler;
    return this;
  }

  private FileDialogCompleteHandler fileDialogCompleteHandler;

  /**
   * Set a callback handler that will be invoked whenever a file dialog complete event is fired.
   *
   * @param fileDialogCompleteHandler The handler that should be invoked whenever a file dialog
   *                                  complete event occurs.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setFileDialogCompleteHandler(
      FileDialogCompleteHandler fileDialogCompleteHandler) {
    this.fileDialogCompleteHandler = fileDialogCompleteHandler;
    return this;
  }

  private UploadCompleteHandler uploadCompleteHandler;

  /**
   * Set a callback handler that will be invoked whenever an uploader complete event is fired.
   *
   * @param uploadCompleteHandler The handler that should be invoked whenever an uploader complete
   *                              event occurs.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setUploadCompleteHandler(UploadCompleteHandler uploadCompleteHandler) {
    this.uploadCompleteHandler = uploadCompleteHandler;
    return this;
  }

  private UploadErrorHandler uploadErrorHandler;

  /**
   * Set a callback handler that will be invoked whenever an uploader error event is fired.
   *
   * @param uploadErrorHandler The handler that should be invoked whenever an uploader error
   *                           event occurs.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setUploadErrorHandler(UploadErrorHandler uploadErrorHandler) {
    this.uploadErrorHandler = uploadErrorHandler;
    return this;
  }

  private UploadProgressHandler uploadProgressHandler;

  /**
   * Set a callback handler that will be invoked whenever an uploader progress event is fired.
   *
   * @param uploadProgressHandler The handler that should be invoked whenever an uploader progress
   *                              event occurs.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setUploadProgressHandler(UploadProgressHandler uploadProgressHandler) {
    this.uploadProgressHandler = uploadProgressHandler;
    return this;
  }

  private UploadStartHandler uploadStartHandler;

  /**
   * Set a callback handler that will be invoked whenever an uploader start event is fired.
   *
   * @param uploadStartHandler The handler that should be invoked whenever an uploader start
   *                           event occurs.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setUploadStartHandler(UploadStartHandler uploadStartHandler) {
    this.uploadStartHandler = uploadStartHandler;
    return this;
  }

  private UploadSuccessHandler uploadSuccessHandler;

  /**
   * Set a callback handler that will be invoked whenever an uploader success event is fired.
   *
   * @param uploadSuccessHandler The handler that should be invoked whenever an uploader success
   *                             event occurs.
   * @return A reference to this {@link Uploader} instance for convenient method chaining.
   */
  public Uploader setUploadSuccessHandler(UploadSuccessHandler uploadSuccessHandler) {
    this.uploadSuccessHandler = uploadSuccessHandler;
    return this;
  }

  // The current XMLHttpRequest in progress (if any), which we'll need a reference to in case the
  // user attempts to cancel an in progress upload.
  private JavaScriptObject lastXMLHttpRequest = null;

  /**
   * Cause the first file in the queue to start the uploader process.
   */
  public void startUpload() {
    if (uploadURL == null) {
      throw new IllegalStateException(
          "The 'startUpload()' method was invoked before the uploader URL was provided.  "
          + "Please call the 'setUploadURL' first."
      );
    }

    if (nativeFilesQueued.size() > 0) {
      JavaScriptObject nativeFile = nativeFilesQueued.get(0);

      // Initialize properties on Start
      nativeSetProperty(nativeFile, "startTime", System.currentTimeMillis());
      nativeSetProperty(nativeFile, "timeSinceLastEvent", System.currentTimeMillis());

      //need to keep our global stats up to date manually
      nativeSetProperty(getStats(), "in_progress", 1);

      //we need to fire it manually for the Ajax/XMLHttpRequest Level 2 case
      nativeUpdateFileProperties(nativeFile, File.Status.IN_PROGRESS.toInt());
      if (uploadStartHandler != null) {
        uploadStartHandler.onUploadStart(new UploadStartEvent(nativeFile.<File>cast()));
      }

      // Let any registered progress handlers know that we're starting at the beginning
      uploadProgressEventCallback(nativeFile.<File>cast(), 0.0,
                                  (double) nativeFile.<File>cast().getSize());

      lastXMLHttpRequest =
          nativeStartAjaxUpload(nativeFile, ajaxUploadURL != null ? ajaxUploadURL : uploadURL,
                                filePostName != null ? filePostName : "Filedata",
                                postParams != null ? postParams.getJavaScriptObject() : null,
                                httpHeaders != null ? httpHeaders.getJavaScriptObject() : null
          );
    }
  }

  // See: https://developer.mozilla.org/en/Using_files_from_web_applications
  private native JavaScriptObject nativeStartAjaxUpload(JavaScriptObject file, String url,
                                                        String filePostName,
                                                        JavaScriptObject postParams,
                                                        JavaScriptObject httpHeaders) /*-{

      var self = this;
      var xhr = new XMLHttpRequest();

      // Setup the event handlers we'll need to let the consuming application know what's going on
      xhr.upload.addEventListener('progress', function (e) {
          return self.@org.wisepersist.gwt.uploader.client.Uploader::uploadProgressEventCallback(Lorg/wisepersist/gwt/uploader/client/File;DD)(
              file,
              e.loaded,
              e.total);
      }, false);
      xhr.addEventListener('load', function () {
          return self.@org.wisepersist.gwt.uploader.client.Uploader::uploadSuccessEventCallback(Lorg/wisepersist/gwt/uploader/client/File;Ljava/lang/String;Ljava/lang/String;)(
              @org.wisepersist.gwt.uploader.client.Uploader::nativeUpdateFileProperties(Lcom/google/gwt/core/client/JavaScriptObject;I)(
                  file,
                  -4     // File.Status.COMPLETE
              ),
              xhr.status + "",
              xhr.responseText
          )
      }, false);
      xhr.addEventListener('error', function () {
          return self.@org.wisepersist.gwt.uploader.client.Uploader::uploadErrorEventCallback(Lorg/wisepersist/gwt/uploader/client/File;ILjava/lang/String;)(
              @org.wisepersist.gwt.uploader.client.Uploader::nativeUpdateFileProperties(Lcom/google/gwt/core/client/JavaScriptObject;I)(
                  file,
                  -3      // File.Status.ERROR
              ),
              -250, // UploadErrorEvent.ErrorCode.UPLOAD_FAILED
              xhr.responseText
          )
      }, false);
      xhr.addEventListener('abort', function () {
          return self.@org.wisepersist.gwt.uploader.client.Uploader::uploadErrorEventCallback(Lorg/wisepersist/gwt/uploader/client/File;ILjava/lang/String;)(
              @org.wisepersist.gwt.uploader.client.Uploader::nativeUpdateFileProperties(Lcom/google/gwt/core/client/JavaScriptObject;I)(
                  file,
                  -5      // File.Status.CANCELLED
              ),
              -280, // UploadErrorEvent.ErrorCode.FILE_CANCELLED
              "Upload Aborted"
          )
      }, false);

      xhr.open('POST', url, true);
      
      // Inject any http headers
      if (httpHeaders != null) {
          for (var key in httpHeaders) {
              xhr.setRequestHeader(key, httpHeaders[key]);
          }
      }
      
      var formData = new FormData();
      formData.append(filePostName, file);

      // Append on any post params
      if (postParams != null) {
          for (var key in postParams) {
              formData.append(key, postParams[key]);
          }
      }

      // Kick off the multipart/form-data upload
      xhr.send(formData);

      return xhr;
  }-*/;

  /**
   * Cancel the file upload for a specific file and remove from the queue.
   *
   * @param fileId            The fileId to cancel
   * @param triggerErrorEvent if true, an uploadError event will be issued
   */
  public void cancelUpload(String fileId, boolean triggerErrorEvent) {
      JavaScriptObject nativeFile = nativeFilesQueuedById.get(fileId);
      if (nativeFile != null) {

        boolean cancelledActiveUpload = false;

        // If the file being cancelled was currently in the process of being uploaded, then cancel
        // the current XMLHttpRequest as well
        if (nativeFilesQueued.size() > 0 && lastXMLHttpRequest != null) {
          final File currentFile = nativeFilesQueued.get(0).cast();
          if (nativeFile.<File>cast().getId().equals(currentFile.getId())
              && currentFile.getStatus() == File.Status.IN_PROGRESS) {
            try {
              cancelledActiveUpload = true;
              nativeAbortXMLHttpRequest(lastXMLHttpRequest);
            } catch (Throwable t) {
              // Purposefully ignoring any problems that may occur when aborting the XMLHttpRequest
              GWT.log(t.getMessage());
            }
          }
        }

        nativeUpdateFileProperties(nativeFile, File.Status.CANCELLED.toInt());

        // Keep the global stats up to date
        nativeSetProperty(getStats(), "upload_cancelled", getStats().getUploadsCancelled() + 1);

        if (triggerErrorEvent) {
          nativeSetProperty(getStats(), "upload_errors", getStats().getUploadErrors() + 1);
          if (uploadErrorHandler != null) {
            uploadErrorHandler.onUploadError(
                new UploadErrorEvent(nativeFile.<File>cast(),
                                     UploadErrorEvent.ErrorCode.FILE_CANCELLED
                                         .toInt(), "File Cancelled", null
                )
            );
          }
        }

        // If we just cancelled the upload that was in progress then we need to explicitly invoke
        // the complete handler after the file cancellation (in order to let the next potential
        // upload continue)
        if (cancelledActiveUpload) {
          uploadCompleteEventCallback(nativeFile.<File>cast());
        } else {
          // If we're not cancelling the file upload that was in progress, then we need to handle
          // pulling it out of the internal queue and statistics on our own
          nativeFilesQueued.remove(nativeFile);
          nativeFilesQueuedById.remove(nativeFile.<File>cast().getId());
          nativeSetProperty(getStats(), "files_queued", nativeFilesQueued.size());
          nativeSetProperty(getStats(), "in_progress", 0);
        }
      }
  }

  private static native void nativeAbortXMLHttpRequest(JavaScriptObject xmlHttpRequest) /*-{
      xmlHttpRequest.abort();
  }-*/;

  /**
   * Cancel the first file in the queue.
   */
  public void cancelUpload() {
      if (nativeFilesQueued.size() > 0) {
        cancelUpload(nativeFilesQueued.get(0).<File>cast().getId());
      }
  }

  /**
   * Cancel the the file with the supplied id. An uploadError event will be
   * issued.
   *
   * @param fileId The fileId to cancel
   */
  public void cancelUpload(String fileId) {
      cancelUpload(fileId, true);
  }

  /**
   * Cancel the first file in the queue.
   *
   * @param triggerErrorEvent if true, an uploadError event will be issued
   */
  public void cancelUpload(boolean triggerErrorEvent) {
      if (nativeFilesQueued.size() > 0) {
        cancelUpload(nativeFilesQueued.get(0).<File>cast().getId(), triggerErrorEvent);
      }
  }

  /**
   * Returns true if the browser appears to support an HTML5 style upload (via the XMLHttpRequest
   * Level 2 API).
   * <p>
   * Logic adapted from:
   * http://blog.new-bamboo.co.uk/2012/01/10/ridiculously-simple-ajax-uploads-with-formdata
   *
   * @return 'true' if an HTML5 style upload can be used otherwise false
   */
  public static boolean isAjaxUploadWithProgressEventsSupported() {
    return isFileAPISupported() && isAjaxUploadProgressEventsSupported() && isFormDataSupported();
  }

  private static native boolean isFileAPISupported() /*-{
      var inputElement = document.createElement('INPUT');
      inputElement.type = 'file';
      return 'files' in inputElement;
  }-*/;

  private static native boolean isAjaxUploadProgressEventsSupported() /*-{
      var xhr = new XMLHttpRequest();
      return !!(xhr && ('upload' in xhr) && ('onprogress' in xhr.upload));
  }-*/;

  private static native boolean isFormDataSupported() /*-{
      return !!window.FormData;
  }-*/;

  // Purposefully using concrete types here instead of a generic list to increase GWT performance
  private ArrayList<JavaScriptObject> nativeFilesQueued = new ArrayList<JavaScriptObject>();
  private HashMap<String, JavaScriptObject>
      nativeFilesQueuedById =
      new HashMap<String, JavaScriptObject>();

  private FileUpload fileUpload;

  @Override
  protected void onLoad() {
    if (loaded) {
      return;
    }
    loaded = true;

    // Make sure our entire panel fits the size that they wanted for the button
    if (this.buttonWidth >= 0) {
      this.setWidth(this.buttonWidth + "px");
    }
    if (this.buttonHeight >= 0) {
      this.setHeight(this.buttonHeight + "px");
    }

    if (ajaxUploadEnabled && isAjaxUploadWithProgressEventsSupported()) {

      // If the browser supports the XMLHttpRequest Level 2 type then we can just stick with a DOM/Ajax approach.

      // Use a hidden file input component to handle allowing the user to popup the file system
      // dialog (but keep it outside of the button itself so it doesn't interfere with the mouse
      // events)
      this.add(createFileUpload());

      // Create the main element that will hold all of the inner workings of the uploader component
      Label button = new Label();
      button.setWidth("100%");
      button.setHeight("100%");
      if (this.buttonCursor != null) {
        switch (this.buttonCursor) {
          case ARROW:
            button.getElement().getStyle().setCursor(Style.Cursor.DEFAULT);
            break;
          case HAND:
            button.getElement().getStyle().setCursor(Style.Cursor.POINTER);
            break;
        }
      }

      // Setup what we want to happen when someone clicks anywhere on the button
      button.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          if (buttonDisabled) {
            return;
          }
          switch (buttonAction) {
            case START_UPLOAD:
              startUpload();
              break;
            case SELECT_FILES:
              openFileDialog(fileUpload, true);
              break;
            case SELECT_FILE:
            default:
              openFileDialog(fileUpload, false);
              break;
          }
        }
      });

      button.addMouseOverHandler(new MouseOverHandler() {
        public void onMouseOver(MouseOverEvent event) {
          if (buttonImageURL != null && buttonHeight >= 0 && !buttonDisabled) {
            buttonImageElement.getStyle()
                .setProperty("backgroundPosition", "0px -" + buttonHeight + "px");
          }
        }
      });
      button.addMouseOutHandler(new MouseOutHandler() {
        public void onMouseOut(MouseOutEvent event) {
          if (buttonImageURL != null && buttonHeight >= 0 && !buttonDisabled) {
            buttonImageElement.getStyle().setProperty("backgroundPosition", "0px 0px");
          }
        }
      });
      button.addMouseDownHandler(new MouseDownHandler() {
        public void onMouseDown(MouseDownEvent event) {
          if (buttonImageURL != null && buttonHeight >= 0 && !buttonDisabled) {
            buttonImageElement.getStyle()
                .setProperty("backgroundPosition", "0px -" + (buttonHeight * 2) + "px");
          }
        }
      });
      button.addMouseUpHandler(new MouseUpHandler() {
        public void onMouseUp(MouseUpEvent event) {
          if (buttonImageURL != null && buttonHeight >= 0 && !buttonDisabled) {
            buttonImageElement.getStyle().setProperty("backgroundPosition", "0px 0px");
          }
        }
      });

      // Depending on the way they wanted the uploader button rendered, create the appropriate
      // elements in the DOM that the user will click on.
      if (this.buttonTextStyle != null) {
        buttonTextStyleElement = Document.get().createStyleElement();
        buttonTextStyleElement.setInnerText(this.buttonTextStyle);
        button.getElement().appendChild(buttonTextStyleElement);
      }
      if (this.buttonText != null) {
        buttonTextElement = Document.get().createDivElement();
        buttonTextElement.setInnerHTML(this.buttonText);
        buttonTextElement.getStyle().setWidth(100, Style.Unit.PCT);
        buttonTextElement.getStyle().setHeight(100, Style.Unit.PCT);
        if (this.buttonTextLeftPadding > Integer.MIN_VALUE) {
          buttonTextElement.getStyle().setPaddingLeft(this.buttonTextLeftPadding, Style.Unit.PX);
        }
        if (this.buttonTextTopPadding > Integer.MIN_VALUE) {
          buttonTextElement.getStyle().setPaddingTop(this.buttonTextTopPadding, Style.Unit.PX);
        }
        button.getElement().appendChild(buttonTextElement);
      }

      if (this.buttonImageURL != null) {
        buttonImageElement = Document.get().createDivElement();
        buttonImageElement.getStyle().setBackgroundImage("url(\"" + this.buttonImageURL + "\")");
        if (this.buttonDisabled) {
          buttonImageElement.getStyle()
              .setProperty("backgroundPosition", "0px -" + (buttonHeight * 3) + "px");
        } else {
          buttonImageElement.getStyle().setProperty("backgroundPosition", "0px 0px");
        }
        buttonImageElement.getStyle().setWidth(100, Style.Unit.PCT);
        buttonImageElement.getStyle().setHeight(100, Style.Unit.PCT);
        button.getElement().appendChild(buttonImageElement);
      }

      // Add the entire button to the DOM
      this.add(button);

    } else {
        
        if (uploadErrorHandler != null) {
            uploadErrorHandler.onUploadError(
                new UploadErrorEvent(null,
                                     UploadErrorEvent.ErrorCode.SECURITY_ERROR
                                         .toInt(), "No HTML5 supported!", null
                )
            );
          }
      // If the browser doesn't support the XMLHttpRequest Level 2 type
      //then throw an error?
    }
  }

  private FileUpload createFileUpload() {
    fileUpload = new FileUpload();
    fileUpload.getElement().getStyle().setDisplay(Style.Display.NONE);

    if (fileTypes != null) {
      // Convert the format that the SWFUpload/Flash parameter expects to the W3C DOM standard
      // See: http://dev.w3.org/html5/spec/states-of-the-type-attribute.html#attr-input-accept
      fileUpload.getElement()
          .setAttribute("accept", this.fileTypes.replaceAll("\\;", ",").replaceAll("\\*\\.", "."));

      // TODO(jake): Need to consider validation of this in the file queued handler as well,
      // as the browsers don't appear to consistently support the "accept" attribute
    }

    final AbsolutePanel panel = this;

    fileUpload.addChangeHandler(new ChangeHandler() {
      public void onChange(ChangeEvent event) {

        JsArray<?> selectedFiles = nativeGetSelectedFiles(fileUpload.getElement());

        // Every time a file is selected replace the FileUpload component running in the DOM so that
        // the user can continue to attempt uploading the same file multiple times (otherwise the
        // "ChangeHandler" never fires)
        panel.remove(fileUpload);
        panel.add(createFileUpload());

        addFilesToQueue(selectedFiles);

      }
    });
    return fileUpload;
  }

  private long lastFileId = 0;

  private String getNextFileId() {
    lastFileId++;
    return "Uploader_" + lastFileId;
  }

  /**
   * When implementing drag/drop support for file uploads, this method can be used to
   * pass into the uploader the list of native file references that the user dropped
   * onto the application to be uploaded.  Will normally be used in conjunction
   * with the {@link #getDroppedFiles(com.google.gwt.dom.client.NativeEvent)} method.  E.g.
   * <pre><code>
   * final Uploader uploader = new Uploader();
   * dropFilesArea.addDropHandler(new DropHandler() {
   *    public void onDrop(DropEvent event) {
   *       event.preventDefault();
   *       uploader.addFilesToQueue(
   *          Uploader.getDroppedFiles(event.getNativeEvent())
   *       );
   *    }
   * });
   * </code></pre>
   *
   * @param files An array of native file references to be added to the queue.
   */
  public void addFilesToQueue(JsArray<?> files) {
    int filesQueued = 0;

    if (files != null) {
      for (int i = 0; i < files.length(); i++) {
        JavaScriptObject nativeFile = files.get(i);

        // Make sure our maximum allowable queue size upload limit has not been exceeded
        if (nativeFilesQueued.size() >= fileQueueLimit) {
          fileQueueErrorEventCallback(nativeFile.<File>cast(),
                                      FileQueueErrorEvent.ErrorCode.QUEUE_LIMIT_EXCEEDED.toInt(),
                                      "Exceeded file queue size limit of "
                                      + fileQueueLimit
          );
          break;
        }
        if (fileUploadLimit > 0 && totalFilesUploaded >= fileUploadLimit) {

          // Keep the global stats up to date
          nativeSetProperty(getStats(), "upload_errors", getStats().getUploadErrors() + 1);

          if (uploadErrorHandler != null) {
            uploadErrorHandler
                .onUploadError(
                    new UploadErrorEvent(nativeFile.<File>cast(),
                                         UploadErrorEvent.ErrorCode.UPLOAD_LIMIT_EXCEEDED
                                             .toInt(), "Exceeded upload limit of "
                                                       + fileUploadLimit, null
                    )
                );
          }

          break;
        }

        // Ensure the file size can be determined and that the file has some contents
        if (nativeFile.<File>cast().getSize() <= 0) {
          fileQueueErrorEventCallback(nativeFile.<File>cast(),
                                      FileQueueErrorEvent.ErrorCode.ZERO_BYTE_FILE.toInt(),
                                      "File is zero bytes and cannot be uploaded.");
          continue;
        }

        // Make sure the file doesn't exceed the configured size limit
        if (exceedsFileSizeLimit(nativeFile)) {
          fileQueueErrorEventCallback(nativeFile.<File>cast(),
                                      FileQueueErrorEvent.ErrorCode.FILE_EXCEEDS_SIZE_LIMIT.toInt(),
                                      "File size exceeds allowed limit.");
          continue;
        }

        nativeSetProperty(nativeFile, "id", getNextFileId());

        filesQueued++;

        // Track each of the files that still need to be uploaded
        addFileToQueue(nativeFile);

      }
    }

    // If requested, notify the app each time the user has finished selecting a bunch of files
    if (fileDialogCompleteHandler != null) {
      fileDialogCompleteHandler
          .onFileDialogComplete(new FileDialogCompleteEvent(files != null ? files.length() : 0,
                                                            filesQueued, nativeFilesQueued.size()));
    }
  }

  /**
   * A convenience method that can be used to extract the files that a user dropped
   * on the application during a GWT drop event.  Useful when adding drag/drop support
   * for file uploads to your application.  E.g.
   * <pre><code>
   * final Uploader uploader = new Uploader();
   * dropFilesArea.addDropHandler(new DropHandler() {
   *    public void onDrop(DropEvent event) {
   *       event.preventDefault();
   *       uploader.addFilesToQueue(
   *          Uploader.getDroppedFiles(event.getNativeEvent())
   *       );
   *    }
   * });
   * </code></pre>
   *
   * @param event The native event provided by GWT that was fired when the drop occurred
   * @return An array of native file references that were dropped as part of the event,
   * which can subsequently be passed to the
   * {@link #addFileToQueue(com.google.gwt.core.client.JavaScriptObject)} method.
   */
  public static native JsArray<?> getDroppedFiles(NativeEvent event) /*-{
      return event.target.files || event.dataTransfer.files;
  }-*/;

  private void addFileToQueue(JavaScriptObject nativeFile) {
    // Track each of the files that still need to be uploaded
    nativeFilesQueued.add(nativeFile);
    nativeFilesQueuedById.put(nativeFile.<File>cast().getId(), nativeFile);

    // need to keep the global stats up to date manually
    nativeSetProperty(getStats(), "files_queued", nativeFilesQueued.size());

    // If requested, notify the app each time a new file is added to the queue
    if (fileQueuedHandler != null) {
      fileQueuedHandler.onFileQueued(
          new FileQueuedEvent(
              nativeUpdateFileProperties(nativeFile,
                                         File.Status.QUEUED.toInt()).<File>cast()
          )
      );
    }
  }

  private boolean exceedsFileSizeLimit(JavaScriptObject nativeFile) {
    long fileSize = nativeFile.<File>cast().getSize();
    return this.fileSizeLimit != 0 && fileSize > this.fileSizeLimit;
  }

  private void openFileDialog(FileUpload fileUpload, boolean multipleFiles) {

    if (multipleFiles) {
      fileUpload.getElement().setAttribute("multiple", "true");
    } else {
      fileUpload.getElement().removeAttribute("multiple");
    }

    if (fileDialogStartHandler != null) {
      fileDialogStartHandler.onFileDialogStartEvent(new FileDialogStartEvent());
    }
    InputElement.as(fileUpload.getElement()).click();
  }

  @Override
  protected void onUnload() {
    buttonTextStyleElement = null;
    buttonTextElement = null;
    buttonImageElement = null;
  }

  private boolean fileDialogStartEventCallback() {
    return fileDialogStartHandler == null || fileDialogStartHandler
        .onFileDialogStartEvent(new FileDialogStartEvent());
  }

  private boolean fileQueuedEventCallback(File file) {
    return fileQueuedHandler == null || fileQueuedHandler.onFileQueued(new FileQueuedEvent(file));
  }

  private boolean fileQueueErrorEventCallback(File file, int errorCode, String message) {
    // Keep the global stats up to date 
    nativeSetProperty(getStats(), "queue_errors", getStats().getQueueErrors() + 1);

    return fileQueueErrorHandler == null
           || fileQueueErrorHandler
               .onFileQueueError(new FileQueueErrorEvent(file, errorCode, message));
  }

  private boolean fileDialogCompleteEventCallback(int numberOfFilesSelected,
                                                  int numberOfFilesQueued,
                                                  int totalFilesInQueue) {
    return fileDialogCompleteHandler == null
           || fileDialogCompleteHandler
               .onFileDialogComplete(new FileDialogCompleteEvent(numberOfFilesSelected,
                                                                 numberOfFilesQueued,
                                                                 totalFilesInQueue));
  }

  private boolean uploadStartEventCallback(File file) {
    return uploadStartHandler == null || uploadStartHandler
        .onUploadStart(new UploadStartEvent(file));
  }

  private boolean uploadProgressEventCallback(File file, double bytesComplete, double bytesTotal) {

    //we need to do our own math to keep the file statistics up to date.
      long now = System.currentTimeMillis();
      long bytesSinceLastEvent =
          (long) (bytesComplete - nativeGetPropertyAsDouble(file, "previousBytesComplete"));
      double
          timeSinceLastEvent =
          (now - nativeGetPropertyAsDouble(file, "timeSinceLastEvent")) / 1000.0;
      double elapsedTime = (now - nativeGetPropertyAsDouble(file, "startTime")) / 1000.0;
      double currentSpeed = (bytesSinceLastEvent * 8) / timeSinceLastEvent;
      if (!Double.isInfinite(currentSpeed) && !Double.isNaN(currentSpeed)) {
        nativeAddValueToArray(file, "movingAverageHistory", currentSpeed);
      }
      nativeSetProperty(file, "bytesSinceLastEvent", bytesSinceLastEvent);
      nativeSetProperty(file, "previousBytesComplete", bytesComplete);
      nativeSetProperty(file, "timeSinceLastEvent", now);

      // consoleDebug(currentSpeed + "");

      // Set properties
      nativeSetProperty(file, "averageSpeed",
                        (bytesComplete * 8) / elapsedTime); // Bits per seconds
      nativeSetProperty(file, "currentSpeed", currentSpeed); // Bytes per seconds since last event
      nativeSetProperty(file, "movingAverageSpeed",
                        calculateMovingAverage(nativeGetPropertyAsArray(file,
                                                                        "movingAverageHistory"))
      ); // Bytes per second
      nativeSetProperty(file, "percentUploaded",
                        bytesComplete / bytesTotal * 100); // Bytes per seconds
      nativeSetProperty(file, "sizeUploaded", bytesComplete); // Bytes uploaded so far
      nativeSetProperty(file, "timeElapsed", elapsedTime); // Time since upload started
      nativeSetProperty(file, "timeRemaining",
                        (bytesTotal - bytesComplete) / (bytesComplete / elapsedTime));


    return uploadProgressHandler == null
           || uploadProgressHandler
               .onUploadProgress(
                   new UploadProgressEvent(file, (long) bytesComplete, (long) bytesTotal));
  }

  private boolean uploadErrorEventCallback(File file, int errorCode, String message) {
	  return uploadErrorEventCallback(file, errorCode, message, null);
  }
  
  private boolean uploadErrorEventCallback(File file, int errorCode, String message, String serverData) {
    // If the user manually cancelled the file upload earlier (via the cancelUpload() method),
    // then we've already invoked the error handler callback if appropriate.
    if (file.getStatus() == File.Status.CANCELLED) {
      return true;
    }

    // Keep the global stats up to date
    nativeSetProperty(getStats(), "upload_errors", getStats().getUploadErrors() + 1);

    boolean response;
    try {
      response = uploadErrorHandler == null
                 || uploadErrorHandler
                     .onUploadError(new UploadErrorEvent(file, errorCode, message, serverData));
    } finally {
      // We're running in Ajax/DOM mode so we need to explicitly invoke the complete handler after each file upload error.
        try {
          uploadCompleteEventCallback(file);
        } finally {
          // Similarly, we need to handle the requeue error logic manually
          if (requeueOnError && errorCode != File.Status.CANCELLED.toInt()) {
            addFileToQueue(file);
          }
        }
    }

    return response;
  }

  private boolean uploadSuccessEventCallback(File file, String responseReceived,
                                             String serverData) {
    boolean response = false;

      // call the success handler if the response code is one of the expected type, otherwise
      // call the error handler
      if (httpSuccess == null) {
        httpSuccess = new long[]{200};
      }
      boolean success = false;
      for (long code : httpSuccess) {
        String codeStr = code + "";
        if (codeStr.equals(responseReceived)) {
          success = true;
        }
      }

      if (success) {
        try {
          // If the user manually cancelled the file upload earlier (via the cancelUpload() method),
          // then don't allow the success handler to be invoked (but still allow the complete
          // handler to run)
          if (file.getStatus() != File.Status.CANCELLED) {
            totalFilesUploaded++;

            // If the file upload was super quick, we may not have gotten any progress events.  So,
            // let anyone who cares know that we've made it to a 100% successfully with this file
            uploadProgressEventCallback(file, file.getSize(), file.getSize());

            // Keep the global stats up to date 
            nativeSetProperty(getStats(), "successful_uploads",
                              getStats().getSuccessfulUploads() + 1);

            response = uploadSuccessHandler == null
                       || uploadSuccessHandler
                           .onUploadSuccess(
                               new UploadSuccessEvent(file, serverData, responseReceived));
          }
        } finally {
          // We're running in Ajax/DOM mode so we need to explicitly invoke the complete handler after each file is uploaded
          // successfully.
          uploadCompleteEventCallback(file);
        }
      } else {
        uploadErrorEventCallback(file, UploadErrorEvent.ErrorCode.HTTP_ERROR.toInt(),
                         "Unsuccessful server response code of: " + responseReceived, serverData);
      }

    return response;
  }

  private boolean uploadCompleteEventCallback(File file) {
    if (nativeFilesQueued.size() > 0) {
      final JavaScriptObject nativeFile = nativeFilesQueued.get(0);
      nativeFilesQueued.remove(nativeFile);
      nativeFilesQueuedById.remove(nativeFile.<File>cast().getId());
    }

    // Keep the global stats up to date 
    nativeSetProperty(getStats(), "files_queued", nativeFilesQueued.size());
    nativeSetProperty(getStats(), "in_progress", 0);

    return uploadCompleteHandler == null || uploadCompleteHandler
        .onUploadComplete(new UploadCompleteEvent(file));
  }

  private static native JsArray<?> nativeGetSelectedFiles(Element fileInputElement) /*-{
      return fileInputElement.files;
  }-*/;

  // We're using native objects instead of Java objects in order to keep the rest of the code
  // compatible with the native types returned by SWFUpload
  private static native JavaScriptObject nativeUpdateFileProperties(JavaScriptObject file,
                                                                    int fileStatus) /*-{
      file.modificationdate = file.lastModifiedDate;
      file.filestatus = fileStatus;
      return file;
  }-*/;

  private static native void nativeSetProperty(JavaScriptObject obj, String key, double value) /*-{
      obj[key] = value;
  }-*/;

  private static native void nativeSetProperty(JavaScriptObject obj, String key, String value) /*-{
      obj[key] = value;
  }-*/;

  private static native void nativeAddValueToArray(JavaScriptObject obj, String key,
                                                   double value) /*-{
      if (!obj[key]) {
          obj[key] = new Array();
      }
      obj[key].push(value);
  }-*/;

  private static native double nativeGetPropertyAsDouble(JavaScriptObject obj, String key) /*-{
      return obj[key] ? obj[key] : 0.0;
  }-*/;

  private static native JsArrayNumber nativeGetPropertyAsArray(JavaScriptObject obj,
                                                               String key) /*-{
      return obj[key] ? obj[key] : new Array();
  }-*/;

  /**
   * Implementation of SWFUpload Speed plug-in's moving average calculation based on the
   * set of data points available ported directly from the original 2.9 implementation.
   *
   * @param history An array of values calculated so far
   * @return The moving average
   */
  private static double calculateMovingAverage(JsArrayNumber history) {
    double[] vals = new double[history.length()];
    long size = history.length();
    double sum = 0.0;
    double mSum = 0.0;
    long mCount = 0;

    // Check for sufficient data
    if (size >= 8) {
      // Clone the array and Calculate sum of the values
      for (int i = 0; i < size; i++) {
        vals[i] = history.get(i);
        sum += vals[i];
      }
      double mean = sum / size;

      // Calculate variance for the set
      double varianceTemp = 0.0;
      for (int i = 0; i < size; i++) {
        varianceTemp += Math.pow(vals[i] - mean, 2);
      }
      double variance = varianceTemp / size;
      double standardDev = Math.sqrt(variance);

      // Standardize the Data
      for (int i = 0; i < size; i++) {
        vals[i] = (vals[i] - mean) / standardDev;
      }

      // Calculate the average excluding outliers
      double deviationRange = 2.0;

      for (int i = 0; i < size; i++) {
        if (vals[i] <= deviationRange && vals[i] >= -deviationRange) {
          mCount++;
          mSum += history.get(i);
        }
      }

    } else {
      // Calculate the average (not enough data points to remove outliers)
      mCount = size;
      for (int i = 0; i < size; i++) {
        mSum += history.get(i);
      }
    }

    return mSum / mCount;
  }

  private native void consoleDebug(String msg) /*-{
      $wnd.console.debug(msg);
  }-*/;
}
