#
# Copyright 2007 Sun Microsystems, Inc.  All rights reserved.
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

BUILDDIR = ../..
PACKAGE = sun.pisces
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

#
# Files
#

#
# Include all Java source files in sun/java2d/pisces
#
AUTO_FILES_JAVA_DIRS = sun/java2d/pisces

#
# Rules.
#
include $(BUILDDIR)/common/Classes.gmk

SERVICEDIR = $(CLASSBINDIR)/META-INF/services

FILES_copy = \
	$(SERVICEDIR)/sun.java2d.pipe.RenderingEngine

build: copy-files

copy-files: $(FILES_copy)

$(SERVICEDIR)/%: $(SHARE_SRC)/classes/sun/java2d/pisces/META-INF/services/%
	$(install-file)
