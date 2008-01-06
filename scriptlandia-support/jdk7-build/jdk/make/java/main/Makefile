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
# Makefile for building java command line tools
#

BUILDDIR = ../..
PRODUCT = java
include $(BUILDDIR)/common/Defs.gmk

ifeq ($(PLATFORM), windows) 
SUBDIRS = java javaw
else 
SUBDIRS = java
endif 
all build clean clobber::
	$(SUBDIRS-loop)

