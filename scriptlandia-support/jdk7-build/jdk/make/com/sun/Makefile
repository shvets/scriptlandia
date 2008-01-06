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
# Makefile for building com/sun
#

BUILDDIR = ../..
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

ifndef OPENJDK
  ORG_EXISTS := $(call DirExists,$(CLOSED_SRC)/share/classes/sun/org,,)
  ifneq ("$(ORG_EXISTS)", "") 
    SCRIPT_SUBDIR = script
  endif
endif

# Omit mirror since it's built with the apt tool.
SUBDIRS = $(SCRIPT_SUBDIR) image security crypto/provider jndi jmx \
    java inputmethods org xml rowset net/httpserver net/ssl demo \
    tools jarsigner

all build clean clobber::
	$(SUBDIRS-loop)

