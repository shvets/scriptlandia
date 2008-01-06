#
# Copyright 1996-2005 Sun Microsystems, Inc.  All rights reserved.
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

BUILDDIR = ../../../../..

include $(BUILDDIR)/common/Defs.gmk

SUBDIRS = rmic

all build clean clobber::
	$(SUBDIRS-loop)

clean clobber::
	$(RM) -r $(CLASSBINDIR)/sun/rmi $(CLASSBINDIR)/com/sun/rmi
