#
# Copyright 1997-2005 Sun Microsystems, Inc.  All rights reserved.
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

BUILDDIR = ..
PRODUCT = org
include $(BUILDDIR)/common/Defs.gmk

ifdef STANDALONE_CORBA_WS
SUBDIRS = omg se
else
SUBDIRS = omg
endif

all build clean clobber::
	$(SUBDIRS-loop)
