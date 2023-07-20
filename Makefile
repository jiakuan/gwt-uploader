LATEST_TAG?=`git tag|sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -1`
PROJECT_DIR := $(shell dirname $(realpath $(lastword $(MAKEFILE_LIST))))

help:
	cat Makefile.txt

clean:
	./gradlew clean

.PHONY: build
build:
	./gradlew build

release:
	./gradlew release -Prelease.useAutomaticVersion=true

publish:
	rm -rf $$HOME/.m2/repository/org/docstr/gwt-uploader
	./gradlew build publishMavenJavaPublicationToMavenLocal publishMavenJavaPublicationToMavenRepository
