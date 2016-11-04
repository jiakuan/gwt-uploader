# GWT Uploader

GWT Uploader is a simple GWT wrapper for JWT decode Javascript library, so that we can use the feature in Java code directly.

Please see demo here: [https://jiakuan.github.io/gwt-uploader/](https://jiakuan.github.io/gwt-uploader/)

Use the following command to checkout the source code:

```
git clone git@github.com:jiakuan/gwt-uploader.git gwt-uploader-root
```

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

Here are the steps:

* Create a new issue for things to discuss
* After discussion about changes, fork the project and make changes
* Run `gradle clean build` to make sure no build errors
* Create a new pull request for review and discussion
* After confirmation, we will merge the pull request to master
