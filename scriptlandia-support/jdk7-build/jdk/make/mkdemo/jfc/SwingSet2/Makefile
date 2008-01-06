#
# Copyright 1999-2006 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile to build the SwingSet2 demo.
#

BUILDDIR = ../../..
PRODUCT = demo/jfc
DEMONAME = SwingSet2
include $(BUILDDIR)/common/Defs.gmk

DEMO_ROOT       = $(CLOSED_SRC)/share/demo/jfc/$(DEMONAME)
DEMO_TOPFILES   = ./README.txt
DEMO_MAINCLASS  = $(DEMONAME)
DEMO_MANIFEST_ATTR = SplashScreen-Image: resources/images/splash.png
DEMO_DESTDIR    = $(DEMODIR)/jfc/$(DEMONAME)

#
# Demo jar building rules.
#
include $(BUILDDIR)/common/Demo.gmk

