#
# Copyright 2004-2006 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building the jvmti demo(s)
#

BUILDDIR = ../..
PRODUCT = demos
include $(BUILDDIR)/common/Defs.gmk

# Can be built in any order, the JRE version of hprof and java_crw_demo are
#   really built in make/java.
#   The hprof target here just deliveres the sources and README files.
#   The java_crw_demo and agent_util files are copied into each demo that
#   uses them.
SUBDIRS = \
	  versionCheck \
          gctest \
	  heapViewer \
	  heapTracker \
	  minst \
	  mtrace \
	  waiters \
	  hprof

all build clean clobber::
	$(SUBDIRS-loop)

all build :: $(DEMODIR)/jvmti/index.html

$(DEMODIR)/jvmti/index.html: $(SHARE_SRC)/demo/jvmti/index.html
	$(install-file)

