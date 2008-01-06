#
# Copyright 1995-2007 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building all of sun tools
#

BUILDDIR = ..
PRODUCT  = sun
include $(BUILDDIR)/common/Defs.gmk

# Rhino/Mozilla java sources
ORG_EXISTS := $(call DirExists,$(CLOSED_SRC)/share/classes/sun/org,,)
ifneq ("$(ORG_EXISTS)", "")
  ORG_SUBDIR = org
endif

# Non windows subdirs
ifneq ($(PLATFORM), windows)
  ifndef OPENJDK
    ifeq ($(PLATFORM), solaris)
      ifneq ($(ARCH), amd64)
        DGA_SUBDIR = jdga
      endif
    endif
  endif
  HEADLESS_SUBDIR = headless
  XAWT_SUBDIR     = xawt
endif

ifndef OPENJDK
  JDBC_SUBDIR = jdbc
endif
ifdef OPENJDK
  RENDER_SUBDIR = pisces
else
  RENDER_SUBDIR = dcpr
endif
SUBDIRS = jar security javazic misc net audio $(RENDER_SUBDIR) image \
	  awt splashscreen $(XAWT_SUBDIR) \
          $(HEADLESS_SUBDIR) $(DGA_SUBDIR) \
	  font jpeg cmm applet rmi beans $(JDBC_SUBDIR) \
	  jawt text nio launcher management $(ORG_SUBDIR) \
          native2ascii serialver tools jconsole

all build clean clobber::
	$(SUBDIRS-loop)

