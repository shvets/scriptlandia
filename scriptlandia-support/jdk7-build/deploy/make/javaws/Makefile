#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
# Makefile to build the Java Web Start components.
#
# This "wrapper" Makefile exists because on Windows, deploy.dll (under
# deploy.jar must be dynamically linked against msvcr71.dll while
# javaws.exe must be statically linked.
#
DEPLOY_TOPDIR = ../..

all clean clobber:
	$(MAKE) -f Makefile.deploy.jar $@
	$(MAKE) -f Makefile.javaws $@

installer-int installer-us:
	$(MAKE) -f Makefile.deploy.jar $@

apidoc sample jaws-sample:
	$(MAKE) -f Makefile.javaws $@

.PHONY: all clean clobber installer-int installer-us apidoc sample jaws-sample
