#
# Copyright 2004-2007 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building all the samples
#

BUILDDIR = ..
PRODUCT = java
include $(BUILDDIR)/common/Defs.gmk

ifdef OPENJDK
  WEBSERVICES_SUBDIR =
else
  WEBSERVICES_SUBDIR = webservices
endif

SUBDIRS = nio jmx scripting $(WEBSERVICES_SUBDIR) nbproject

# include dtrace samples in Solaris builds
ifeq ($(PLATFORM), solaris) 
	SUBDIRS += dtrace
endif

all build clean clobber::
	$(SUBDIRS-loop)

clobber clean ::
	$(RM) -r $(SAMPLEDIR)
