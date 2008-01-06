#
# Copyright 2003-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for reflection and annotations
#

BUILDDIR = ../../..
PACKAGE = java.lang.reflect
PRODUCT = java

include $(BUILDDIR)/common/Defs.gmk

#
# Files to compile.
#
AUTO_FILES_JAVA_DIRS = java/lang/reflect sun/reflect

#
# Install .lib file.
#
INSTALL_DOT_LIB = true

include $(BUILDDIR)/common/Classes.gmk

#
# Special rules.
#

subdirs: classes

clean:: 
	$(RM) -r $(CLASSHDRDIR)

clobber:: 
	$(RM) -r $(CLASSBINDIR)/java/lang/reflect  \
		$(CLASSBINDIR)/java/lang/annotatation

.PHONY: clean clobber
