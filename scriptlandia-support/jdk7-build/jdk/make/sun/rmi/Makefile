#
# Copyright 2003-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Build rmi tools, and sun.rmi.* implementation classes
#
BUILDDIR = ../..
include $(BUILDDIR)/common/Defs.gmk

#
# Order is important -- you can't build stubs without building rmic first.
# We need corbalogsources to be built first, since it builds LogWrappers to
# log the exceptions and generate the right minorcodes. These classes are
# references in com/sun/corba/se/impl/io and util classes which in turn will
# be built from rmic Makefile.
#
SUBDIRS = oldtools rmic

SUBDIRS += rmi registry rmid
ifeq ($(PLATFORM), windows)
    SUBDIRS += cgi
endif # PLATFORM

# Then do the rest
all build clean clobber ::
	$(SUBDIRS-loop)

clean::
	$(RM) -r $(CLASSBINDIR)/sun/rmi $(CLASSBINDIR)/com/sun/rmi
