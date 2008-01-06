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
# Makefile for building the demo applets
#

BUILDDIR = ../..
include $(BUILDDIR)/common/Defs.gmk

SUBDIRS_CLOSED = Animator  GraphLayout JumpingBox TicTacToe 
SUBDIRS = ArcTest BarChart Blink CardTest Clock DitherTest DrawTest \
          Fractal GraphicsTest MoleculeViewer \
          NervousText SimpleGraph SortDemo SpreadSheet WireFrame
ifndef OPENJDK
SUBDIRS += $(SUBDIRS_CLOSED)
endif

SUBDIRS_MAKEFLAGS += DEMO_IS_APPLET=true

all build clean clobber::
	$(SUBDIRS-loop)

clobber clean::
	$(RM) -r $(DEMODIR)/applets
