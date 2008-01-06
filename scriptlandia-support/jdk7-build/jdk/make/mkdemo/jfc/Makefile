#
# Copyright 1997-2007 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building the jfc demos
#

BUILDDIR = ../..
PRODUCT = demos
include $(BUILDDIR)/common/Defs.gmk

SUBDIRS =           \
    CodePointIM     \
    FileChooserDemo \
    Font2DTest      \
    Metalworks      \
    Notepad         \
    SampleTree      \
    SwingApplet     \
    TableExample

# Some demos aren't currently included in OpenJDK
ifndef OPENJDK
  SUBDIRS += Java2D SwingSet2 Stylepad
endif

all build clean clobber::
	$(SUBDIRS-loop)

