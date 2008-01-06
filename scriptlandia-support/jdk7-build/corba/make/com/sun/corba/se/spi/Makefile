#
# Copyright 2002-2005 Sun Microsystems, Inc.  All rights reserved.
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

BUILDDIR = ../../../../..
PRODUCT = spi

include $(BUILDDIR)/common/Defs.gmk

# IDL code generation is moved out to an extra step before the
# actual classes are compiled
# the files should be compiled in the following order, core to be built
# before connection

ifdef STANDALONE_CORBA_WS
SUBDIRS = activation costransactions encoding extension legacy logging monitoring copyobject
else
SUBDIRS = activation                 encoding extension legacy logging monitoring copyobject
endif

all build clean clobber::
	$(SUBDIRS-loop)

