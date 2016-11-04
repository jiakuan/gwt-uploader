mkfile_path := $(abspath $(lastword $(MAKEFILE_LIST)))
mkfile_dir :=$(shell cd $(shell dirname $(mkfile_path)); pwd)

help:
	cat Makefile.help

clean:
	cd ${mkfile_dir}/gwt-uploader && gradle clean
	cd ${mkfile_dir}/gwt-uploader-demo && gradle clean
	cd ${mkfile_dir}

.PHONY: build
build:
	cd ${mkfile_dir}/gwt-uploader && gradle build
	cd ${mkfile_dir}/gwt-uploader-demo && gradle build
	cd ${mkfile_dir}

release:
	cd ${mkfile_dir}/gwt-uploader && gradle release && cd ${mkfile_dir}

publish:
	LATEST_TAG?=`git tag|tail -1`
	git checkout tags/${LATEST_TAG}
	gradle build install uploadArchives
	git checkout master
