LATEST_TAG?=`cd ${mkfile_dir}/gwt-uploader && git tag|sort -t. -k 1,1n -k 2,2n -k 3,3n -k 4,4n | tail -1`
mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
mkfile_dir :=$(shell cd $(shell dirname $(mkfile_path)); pwd)

help:
	cat Makefile.txt

clean:
	cd ${mkfile_dir}/gwt-uploader && ./gradlew clean
	cd ${mkfile_dir}/gwt-uploader-demo && ./gradlew clean
	cd ${mkfile_dir}

.PHONY: build
build:
	cd ${mkfile_dir}/gwt-uploader && ./gradlew build
	cd ${mkfile_dir}/gwt-uploader-demo && ./gradlew build
	cd ${mkfile_dir}

release:
	cd ${mkfile_dir}/gwt-uploader && ./gradlew release && cd ${mkfile_dir}

install:
	rm -rf ~/.m2/repository/org/wisepersist/gwt-uploader/
	cd ${mkfile_dir}/gwt-uploader && ./gradlew clean build install

publishSnapshot:
	cd ${mkfile_dir}/gwt-uploader && ./gradlew build install uploadArchives && \
	cd ${mkfile_dir}

publish:
	cd ${mkfile_dir}/gwt-uploader && git checkout tags/${LATEST_TAG} && \
	./gradlew build install uploadArchives && git checkout master && \
	cd ${mkfile_dir}

deploy:
	cd ${mkfile_dir}/gwt-uploader-demo && ./gradlew build appengineUpdate && \
	cd ${mkfile_dir}