#
# Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building the java.lang.management demo(s)
#

BUILDDIR = ../..
PRODUCT = demos
include $(BUILDDIR)/common/Defs.gmk

# Can be built in any order
SUBDIRS = FullThreadDump \
	  VerboseGC      \
	  MemoryMonitor  \
	  JTop

all build clean clobber::
	$(SUBDIRS-loop)

all build :: $(DEMODIR)/management/index.html

$(DEMODIR)/management/index.html: $(SHARE_SRC)/demo/management/index.html
	$(install-file)

