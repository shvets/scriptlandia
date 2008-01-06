#
# Copyright 1999-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building JNDI RMI registry service provider
#

BUILDDIR = ../../../..
include $(BUILDDIR)/common/Defs.gmk

SUBDIRS = registry
all build clean clobber::
	$(SUBDIRS-loop)

clean clobber::
	$(RM) -r $(CLASSDESTDIR)/com/sun/jndi/rmi
