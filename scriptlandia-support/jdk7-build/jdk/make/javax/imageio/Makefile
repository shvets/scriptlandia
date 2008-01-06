#
# Copyright 2000-2005 Sun Microsystems, Inc.  All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#

#
# Makefile for building all of JIIO (Java Image I/O) API
#

BUILDDIR = ../..
PACKAGE = javax.imageio
PRODUCT = jiio
include $(BUILDDIR)/common/Defs.gmk

#
# Files to compile
#
AUTO_FILES_JAVA_DIRS = javax/imageio com/sun/imageio 

#
# Resources
#
LOCALE_SET_DEFINITION = jre
OTHER_PROPERTIES = com/sun/imageio/plugins/common/iio-plugin.properties

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

build: copy-files

copy-files: $(FILES_copy)

.PHONY: copy-files

