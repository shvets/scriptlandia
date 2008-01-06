#
# Copyright 1997-2007 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile to build the Stylepad demo.
#

BUILDDIR = ../../..
PRODUCT = demo/jfc
DEMONAME = Stylepad
include $(BUILDDIR)/common/Defs.gmk

DEMO_ROOT       = $(CLOSED_SRC)/share/demo/jfc/$(DEMONAME)
DEMO_TOPFILES   = ./README.txt
DEMO_MAINCLASS  = $(DEMONAME)
DEMO_DESTDIR    = $(DEMODIR)/jfc/$(DEMONAME)

# Notepad files need to be added to the demo
DEMO_EXTRA_SRCDIR = $(SHARE_SRC)/demo/jfc/Notepad
DEMO_EXTRA_FILES := Notepad.java ElementTreePanel.java \
    $(shell $(CD) $(DEMO_EXTRA_SRCDIR) \
      && $(FIND) resources $(SCM_DIRS_prune) -o -type f -print )

#
# Demo jar building rules.
#
include $(BUILDDIR)/common/Demo.gmk

