#
# Copyright 2007 Sun Microsystems, Inc.  All rights reserved.
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

BUILDDIR = ../../../..
PACKAGE = com.sun.net.ssl
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

#
# Files to compile
#
# Note that we implicitly includes the files in com/sun/net/ssl/internal/ssl.
# They are also compiled by make/sun/security/other/Makefile and logically 
# belong there, but compiling them here as well is fine, too.
#
AUTO_FILES_JAVA_DIRS = \
    com/sun/security/cert \
    com/sun/net/ssl 

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk
