#
# Copyright 1995-2006 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building all of java
#

PRODUCT=java
BUILDDIR=..
include $(BUILDDIR)/common/Defs.gmk

#
# The order of subdirs here is important
#
SUBDIRS += hpi version jvm redist verify fdlibm java sun_nio jli main zip

# Others
#    Note: java_crw_demo java_hprof_demo are demos but must be delivered built in sdk
SUBDIRS += security npt java_crw_demo java_hprof_demo \
	    math awt util text applet net nio \
	    sql rmi jar beans logging management instrument


ifeq ($(PLATFORM), solaris)
  ifeq ($(ARCH_DATA_MODEL), 32)
    SUBDIRS += jexec
  endif
endif # PLATFORM

ifeq ($(PLATFORM), linux)
  SUBDIRS += jexec
endif # PLATFORM

all build clean clobber::
	$(SUBDIRS-loop)

