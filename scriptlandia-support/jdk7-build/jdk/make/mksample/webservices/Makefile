#
# Copyright 2006 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building all the samples under the webservices subdirectory.
#

BUILDDIR = ../..
PRODUCT = java
include $(BUILDDIR)/common/Defs.gmk

SUBDIRS = EbayClient EbayServer

all build clean clobber::
	$(SUBDIRS-loop)

clobber clean ::
	$(RM) -r $(SAMPLEDIR)/webservices
