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

BUILDDIR = ../../../..
PACKAGE = sun.security.jgss.wrapper
PRODUCT = sun

LIBRARY = j2gss
# Force name of temp directory (needed due to LIBRARY change below)
LIBRARY_OUTPUT = j2gss

include $(BUILDDIR)/common/Defs.gmk

#
# C and Java Files
#
include FILES_c.gmk

AUTO_FILES_JAVA_DIRS = sun/security/jgss/wrapper

#
# Java files that define native methods
#
FILES_export = \
    sun/security/jgss/wrapper/GSSLibStub.java

#
# Find native code
#
vpath %.c \
  $(SHARE_SRC)/native/sun/security/jgss/wrapper \
  $(PLATFORM_SRC)/native/sun/security/jgss/wrapper

#
# Find include files
#
OTHER_INCLUDES += \
  -I$(SHARE_SRC)/native/sun/security/jgss/wrapper \
  -I$(PLATFORM_SRC)/native/sun/security/jgss/wrapper

#
# Rules
#
#FILES_class = $(FILES_java:%.java=$(CLASSDESTDIR)/%.class)

include $(BUILDDIR)/common/Mapfile-vers.gmk
include $(BUILDDIR)/common/Library.gmk

#
# Libraries to link
#
ifneq ($(PLATFORM), windows)
  OTHER_LDLIBS = -ldl $(JVMLIB)
endif
