#
# Copyright 2003-2006 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building com/sun/tools
#

BUILDDIR = ../../..
PACKAGE = com.sun.tools
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

AUTO_FILES_JAVA_DIRS = com/sun/tools/extcheck

SUBDIRS = attach
all build:
	$(SUBDIRS-loop)
clean clobber::
	$(SUBDIRS-loop)

include $(BUILDDIR)/common/Classes.gmk

