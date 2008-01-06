#
# Copyright 2003-2007 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for sun/nio files
#

BUILDDIR = ../..
PACKAGE = sun.nio
PRODUCT = sun

OTHER_JAVACFLAGS += -Xlint:serial -Werror
include $(BUILDDIR)/common/Defs.gmk

#
# Files to compile.
#
include FILES_java.gmk

#
# Install .lib file.
#
INSTALL_DOT_LIB = true

include $(BUILDDIR)/common/Classes.gmk

clean:: 
	$(RM) -r $(CLASSHDRDIR)
clobber:: 
	$(RM) -r $(CLASSBINDIR)/sun/nio/cs 
