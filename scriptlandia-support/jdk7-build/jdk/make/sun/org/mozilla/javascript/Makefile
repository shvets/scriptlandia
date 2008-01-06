#
# Copyright 2005-2007 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building all of sun.org.mozilla.javascript.internal.*
#

BUILDDIR = ../../../..
PACKAGE = sun.org.mozilla.javascript.internal
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

AUTO_FILES_JAVA_DIRS = sun/org/mozilla/javascript/internal

RESOURCEDIR = \
	$(CLASSDESTDIR)/sun/org/mozilla/javascript/internal/resources
	
FILES_copy = \
	$(RESOURCEDIR)/Messages.properties \
	$(RESOURCEDIR)/Messages_fr.properties

#
# Rules
#

include $(BUILDDIR)/common/Classes.gmk

all: classes copy-files

#
# Copy resource messages file for Rhino JavaScript interpreter
#

copy-files: $(FILES_copy)

$(RESOURCEDIR)/%: $(CLOSED_SRC)/share/classes/sun/org/mozilla/javascript/internal/resources/%
	$(install-file)

.PHONY: copy-files

clean clobber::
	$(RM) -rf $(CLASSDESTDIR)/sun/org/mozilla/javascript
