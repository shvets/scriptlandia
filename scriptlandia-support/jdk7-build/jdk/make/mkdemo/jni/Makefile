#
# Copyright 1999-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building the jni demo(s)
#

BUILDDIR = ../..
PRODUCT = demos
include $(BUILDDIR)/common/Defs.gmk

SUBDIRS =

ifeq ($(PLATFORM),solaris)
  SUBDIRS += Poller
endif

all build clean clobber::
	$(SUBDIRS-loop)

