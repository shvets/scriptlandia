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
# Makefile for building all the demos
#

BUILDDIR = ..
PRODUCT = demos
include $(BUILDDIR)/common/Defs.gmk

SUBDIRS = applets jfc jvmti management jni scripting jpda

all build:: nbproject
	$(SUBDIRS-loop)

nbproject:
	$(RM) -r $(DEMODIR)/nbproject
	$(MKDIR) -p $(DEMODIR)
	( $(CD) $(SHARE_SRC)/demo && $(TAR) -cf - \
	  `find nbproject $(SCM_DIRS_prune) -o -type f -print` ) | \
	  ( $(CD) $(DEMODIR) && $(TAR) -xf - )
ifndef OPENJDK
	( $(CD) $(CLOSED_SHARE_SRC)/demo && $(TAR) -cf - \
	  `find nbproject $(SCM_DIRS_prune) -o -type f -print` ) | \
	  ( $(CD) $(DEMODIR) && $(TAR) -xf - )
endif

clean clobber::
	$(SUBDIRS-loop)
	$(RM) -r $(DEMODIR)
	$(RM) -r $(DEMOCLASSDIR)

