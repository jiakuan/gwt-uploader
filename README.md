# GWT JWT Deocde

GWT JWT Deocde is a simple GWT wrapper for JWT decode Javascript library, so that we can use the feature in Java code directly.

Please see demo here: [https://jiakuan.github.io/gwt-jwt-decode/](https://jiakuan.github.io/gwt-jwt-decode/)

## Configure Maven dependency

GWT JWT Deocde is available in Maven central repository:

http://search.maven.org/#search%7Cga%7C1%7Cgwt-jwt-decode

To add the dependency to your `build.gradle` (for Gradle projects) or `pom.xml` (for Maven projects), use the following configuration:

For Gradle projects:

```
compile 'org.wisepersist:gwt-jwt-decode:1.0.4'
```

For Maven projects:

```
<dependency>
    <groupId>org.wisepersist</groupId>
    <artifactId>gwt-jwt-decode</artifactId>
    <version>1.0.4</version>
</dependency>
```

If you would like to use the 1.0.5-SNAPSHOT release, use this configuration.

For Gradle projects:

```
compile 'org.wisepersist:gwt-jwt-decode:1.0.5-SNAPSHOT'
```

For Maven projects:

```
<dependency>
    <groupId>org.wisepersist</groupId>
    <artifactId>gwt-jwt-decode</artifactId>
    <version>1.0.5-SNAPSHOT</version>
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

## How to use GWT JWT Deocde?

Using GWT JWT decode is as simple as the following code:

```
import org.wisepersist.gwt.jwtdecode.client.JwtDecoder;
import org.wisepersist.gwt.jwtdecode.client.TokenJso;

final TokenJso decoded = JwtDecoder.decode(token);
GWT.log("decoded: " + decoded.toJsonPretty());
```

## How to contribute

Here are the steps:

* Create a new issue for things to discuss
* After discussion about changes, fork the project and make changes
* Run `gradle clean build` to make sure no build errors
* Create a new pull request for review and discussion
* After confirmation, we will merge the pull request to master
