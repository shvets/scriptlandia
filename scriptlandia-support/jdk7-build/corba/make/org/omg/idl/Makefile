#
# Copyright 1999-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building all of java
#

BUILDDIR = ../../..
PACKAGE = com.sun.tools.corba.se.idl
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
include FILES_java.gmk

#
# Resources
#
LOCALE_SET_DEFINITION = jdk
RESOURCE_BUNDLES_PROPERTIES = \
	 $(PKGDIR)/idl.prp \
	 $(PKGDIR)/toJavaPortable/toJavaPortable.prp

#
# IDL files
#

IDL_FILES = orb.idl ir.idl

IDL_SRC_DIR  = $(SHARE_SRC)/classes/com/sun/tools/corba/se/idl
IDL_DEST_DIR = $(LIBDIR)

IFILES = $(IDL_FILES:%=$(IDL_DEST_DIR)/%)

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

build: $(IFILES)

$(IDL_DEST_DIR)/%: $(IDL_SRC_DIR)/%
	$(install-file)

