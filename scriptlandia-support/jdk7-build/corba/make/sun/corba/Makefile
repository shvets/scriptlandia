#
# Copyright 2000-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Build corba core classes that are required for rmic compiler
#

BUILDDIR = ../..
include $(BUILDDIR)/common/Defs.gmk

SUBDIRS = org core
all build clean clobber::
	$(SUBDIRS-loop)
	$(RM) -r $(CLASSBINDIR)/com/sun/corba/se/internal/io

