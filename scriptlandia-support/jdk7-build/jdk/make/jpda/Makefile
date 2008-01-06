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
# Makefile for building all of JPDA, including JDWP, JDI, and
# example debuggers. See also included files.
#

BUILDDIR = ..
include $(BUILDDIR)/common/Defs.gmk

#
# Core.
#
SUBDIRS    = jdwp back front transport expr tty bdi gui

all build clean clobber::
	$(SUBDIRS-loop)

