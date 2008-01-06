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

#
# Makefile for building all of JPS (Java Print Service) API
#

BUILDDIR = ../..
PACKAGE = javax.print
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

#
# Files to compile
#
AUTO_FILES_JAVA_DIRS = javax/print sun/print

#
# Files that just need cp.
#
SERVICEDIR = $(CLASSBINDIR)/META-INF/services

FILES_copy = \
	$(SERVICEDIR)/javax.print.StreamPrintServiceFactory \
	$(SERVICEDIR)/javax.print.PrintServiceLookup

FILES_mkdirs = \
	$(CLASSBINDIR)/META-INF \
	$(CLASSBINDIR)/META-INF/services

FILES_copydirs = \
	$(FILES_mkdirs)

#
# Resources
#
LOCALE_SET_DEFINITION = jre
RESOURCE_BUNDLES_COMPILED_PROPERTIES = sun/print/resources/serviceui.properties

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

#
# Rules that copy files.
#

build: copy-files properties

_PSFONTPROPS = \
	psfontj2d.properties            \
	psfont.properties.ja            \

PSFONTPROPS_SRC = $(SHARE_SRC)/classes/sun/print
PSFONTPROPS       = $(_PSFONTPROPS:%=$(LIBDIR)/%)

_RESOURCES_PRINT = \
        orientLandscape.png                \
        orientPortrait.png                \
        orientRevLandscape.png                \
        orientRevPortrait.png                \
        oneside.png                        \
        duplex.png                        \
        tumble.png


RESDIR_PRINT    = $(CLASSBINDIR)/sun/print/resources
RESOURCES_PRINT = $(_RESOURCES_PRINT:%=$(RESDIR_PRINT)/%)

$(LIBDIR)/%: $(PSFONTPROPS_SRC)/% $(FONTPROPS)
	$(install-file)

$(RESDIR_PRINT)/%: $(SHARE_SRC)/classes/sun/print/resources/%
	$(install-file)

properties: $(LIBDIR) $(PSFONTPROPS) $(RESOURCES_PRINT)

properties.clean:
	$(RM) $(PSFONTPROPS)
	$(RM) -r $(RESDIR_PRINT)

copy-files: $(FILES_copy)

$(SERVICEDIR)/%: $(PLATFORM_SRC)/classes/sun/print/services/%
	$(install-file)

#
# Clean up internal-use-only package.
#
clean::
	$(RM) -r $(CLASSDESTDIR)/javax/print $(CLASSDESTDIR)/sun/print

.PHONY: copy-files
