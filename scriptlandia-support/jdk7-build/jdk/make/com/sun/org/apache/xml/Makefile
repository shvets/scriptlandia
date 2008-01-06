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
# Makefile for building com.sun.org.apache.xml
#

BUILDDIR = ../../../../..
PACKAGE = com.sun.org.apache.xml
PRODUCT = xml
include $(BUILDDIR)/common/Defs.gmk

#
# Files to compile
#
AUTO_FILES_JAVA_DIRS = com/sun/org/apache/xml

#
# Resources
#
LOCALE_SET_DEFINITION = jre
NEW_RESOURCE_BUNDLES_PROPERTIES = \
   $(PKGDIR)/internal/security/resource/config.dtd \
   $(PKGDIR)/internal/security/resource/config.xml \
   $(PKGDIR)/internal/security/resource/xmlsecurity_de.properties \
   $(PKGDIR)/internal/security/resource/xmlsecurity_en.properties \

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

