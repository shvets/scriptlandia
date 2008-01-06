#
# Copyright 2000-2005 Sun Microsystems, Inc.  All rights reserved.
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

BUILDDIR = ../..
PACKAGE = java.util.logging
PRODUCT = java
include $(BUILDDIR)/common/Defs.gmk

#
# Files to compile.
#
AUTO_FILES_JAVA_DIRS = java/util/logging

#
# Resources
#
LOCALE_SET_DEFINITION = jre
RESOURCE_BUNDLES_COMPILED_PROPERTIES = \
	 sun/util/logging/resources/logging.properties

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

properties: $(LIBDIR)/logging.properties

$(LIBDIR)/logging.properties: $(SHARE_SRC)/lib/logging.properties
	$(install-file)

build: properties

