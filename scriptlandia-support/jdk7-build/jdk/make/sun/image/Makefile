#
# Copyright 1998-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building the generic and vis versions of medialib
#

BUILDDIR = ../..
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

SUBDIRS = generic

# solaris-sparc and solaris-sparcv9 both build 'vis'
ifeq ($(PLATFORM),solaris)
  ifeq ($(ARCH),sparc)
    SUBDIRS += vis
  endif
  ifeq ($(ARCH),sparcv9)
    SUBDIRS += vis
  endif
endif

all build clean clobber::
	$(SUBDIRS-loop)

