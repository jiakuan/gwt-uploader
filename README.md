# GWT Uploader

An API to enable sophisticated file upload capabilities within a GWT application.

The source code was forked from http://www.moxiegroup.com/moxieapps/gwt-uploader/ and applied a few fixes.

Please see examples with source code here: [http://gwt-uploader.appspot.com/](http://gwt-uploader.appspot.com/)

## What is it?
GWT Uploader is a freely available open source GWT library that encapsulates the file upload capabilities provided by the File and XMLHttpRequest Level 2 APIs as well as the SWFUpload library.

Using GWT Uploader allows for enhanced file upload dialogs (multiple uploads, drag and drop, queues, parallel streams, etc.) and interactive interfaces (upload progress indicators, real-time throughput display, upload cancellation, etc.) within GWT applications using pure Java code that provides a consistent experience across all browsers.

If the browser is capable of handling file uploads using a modern HTML5 approach, the upload is handled using pure DOM elements and Javascript events. In the case that the browser does not support the modern approach (most notably IE 9 and earlier), the GWT Uploader component instead transparently handles the uploads via the Flash-based SWFUpload library.

## Key Features
* **Simple**:	Adds consistent file upload support to all modern browsers with a single implementation, while still supporting legacy browsers automatically.
* **Pure Java**:	The entire set of file upload capabilities are available via GWT powered Java methods (even in the case that the SWFUpload library is used), including clean interfaces for the various runtime callbacks (no need to write any JavaScript).
* **Customizable Interfaces**:	GWT Uploader exposes all the file upload capabilities as a GWT Widget, making it easy to create your own aesthetic for your upload controls or leave it simple and benefit from the multiple file selection and queued uploading. Convenient methods are also provided to enable file uploads via a familiar drag and drop approach.
* **Clean Syntax**:	The API is built using fluent methods that allow you to manage the configuration options of the the upload control using syntax that is nearly as tight as JSON.
* **Dynamic**:	GWT Uploader automatically includes the necessary plugin capabilities to expose various throughput and performance metrics of the uploads during and after an upload. Each File object has the following properties:

    * getAverageSpeed() -- Overall average upload speed, in bytes per second
    * getCurrentSpeed() -- String indicating the upload speed, in bytes per second
    * getMovingAverageSpeed() -- Speed over averaged over the last several measurements, in bytes per second
    * getPercentUploaded() -- Percentage of the file uploaded (0 to 100)
    * getSizeUploaded() -- Formatted size uploaded so far, bytes
    * getTimeElapsed() -- Number of seconds passed for this upload
    * getTimeRemaining() -- Estimated remaining upload time in seconds
These metrics allow for dynamic updates to occur in your interface, whether it's progress bars or text labels, as data is transferred for one or more files.
* **Documented**:	Every class and method of the API is thoroughly documented, including numerous code and syntax examples throughout.
* **Examples**:	The demonstration examples provide several [basic examples](http://gwt-uploader.appspot.com/) of upload interfaces that demonstrate the flexibility and integration opportunities available, with each example including a convenient "View Source" button that will allow you to see the code behind each implementation.

## Configure Maven dependency

GWT Uploader is available in Maven central repository:

http://search.maven.org/#search%7Cga%7C1%7Cgwt-uploader

To add the dependency to your `build.gradle` (for Gradle projects) or `pom.xml` (for Maven projects), use the following configuration:

For Gradle projects:

```
compile 'org.wisepersist:gwt-uploader:1.1.1'
```

For Maven projects:

```
<dependency>
    <groupId>org.wisepersist</groupId>
    <artifactId>gwt-uploader</artifactId>
    <version>1.1.1</version>
</dependency>
```

If you would like to use the 1.1.2-SNAPSHOT release, use this configuration.

For Gradle projects:

```
compile 'org.wisepersist:gwt-uploader:1.1.2-SNAPSHOT'
```

For Maven projects:

```
<dependency>
    <groupId>org.wisepersist</groupId>
    <artifactId>gwt-uploader</artifactId>
    <version>1.1.2-SNAPSHOT</version>
</dependency>
```

In order to use snapshot releases you also need to add the Sonatype snapshots repository to your POM:

```
<repositories>
    <repository>
        <id>sonatype-nexus-snapshots</id>
        <url>http://oss.sonatype.org/content/repositories/snapshots</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
        <releases>
            <enabled>false</enabled>
        </releases>
    </repository>
</repositories>
```

## How to use GWT Uploader?

TBD

## How to contribute

Use the following command to checkout the source code:

```
git clone git@github.com:jiakuan/gwt-uploader.git gwt-uploader-root
```

Here are the steps:

* Create a new issue for things to discuss
* After discussion about changes, fork the project and make changes
* Run `gradle clean build` to make sure no build errors
* Create a new pull request for review and discussion
* After confirmation, we will merge the pull request to master
