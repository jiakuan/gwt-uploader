LATEST_TAG?=`cd ${mkfile_dir}/gwt-uploader && git tag|sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -1`
mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
mkfile_dir :=$(shell cd $(shell dirname $(mkfile_path)); pwd)

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
