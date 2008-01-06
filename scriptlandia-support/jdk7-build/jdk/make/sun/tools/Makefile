#
# Copyright 2005-2006 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building tools
#

BUILDDIR = ../..
PACKAGE = sun.tools
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
AUTO_FILES_JAVA_DIRS = \
  sun/tools/jinfo \
  sun/tools/jmap \
  sun/tools/jps \
  sun/tools/jstack \
  sun/tools/jstat \
  sun/tools/jstatd \
  com/sun/tools/hat \
  com/sun/tools/script/shell \
  sun/jvmstat

# Extra jstat files
FILES_copy += \
  $(CLASSDESTDIR)/sun/tools/jstat/resources/jstat_options

# Extra jhat files
JHAT_RESOURCEDIR = $(CLASSDESTDIR)/com/sun/tools/hat/resources
FILES_copy += \
  $(JHAT_RESOURCEDIR)/hat.js \
  $(JHAT_RESOURCEDIR)/oqlhelp.html \
  $(JHAT_RESOURCEDIR)/platform_names.txt

# Extra jrunscript files
JRUNSCRIPT_RESOURCEDIR = $(CLASSDESTDIR)/com/sun/tools/script/shell
FILES_copy += \
  $(JRUNSCRIPT_RESOURCEDIR)/init.js \
  $(JRUNSCRIPT_RESOURCEDIR)/messages.properties 

# Extra jvmstat files
FILES_copy += \
  $(CLASSDESTDIR)/sun/jvmstat/perfdata/resources/aliasmap

# Build should include the file copies
build: copy-files

copy-files: $(FILES_copy)

$(CLASSDESTDIR)/%: $(SHARE_SRC)/classes/%
	$(install-file)

clean clobber::
	$(RM) $(FILES_copy)

.PHONY: copy-files

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

