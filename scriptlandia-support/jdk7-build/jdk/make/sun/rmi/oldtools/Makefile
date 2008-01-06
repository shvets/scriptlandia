#
# Copyright 1997-2003 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building the old java compiler.
#

BUILDDIR = ../../..
PRODUCT	 = oldjavac

include $(BUILDDIR)/common/Defs.gmk

#
# Files to compile.
#
include FILES_java.gmk

#
# Resources
#
LOCALE_SET_DEFINITION = j2sdk
RESOURCE_BUNDLES_PROPERTIES = sun/tools/javac/resources/javac.properties

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

clean clobber::
	$(RM) -r $(CLASSBINDIR)/sun/tools/asm
	$(RM) -r $(CLASSBINDIR)/sun/tools/java
	$(RM) -r $(CLASSBINDIR)/sun/tools/javac
	$(RM) -r $(CLASSBINDIR)/sun/tools/tree
	$(RM) -r $(CLASSBINDIR)/sun/tools/util

