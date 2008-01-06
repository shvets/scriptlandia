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

#
# corba must always be built before rmi, due to rmi-iiop support
#
SUBDIRS = corba rmi

all build clean clobber::
	$(SUBDIRS-loop)
