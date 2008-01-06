#
# Copyright 1996-2006 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for character converters.
#

BUILDDIR = ../..

PACKAGE = sun.nio
PRODUCT = sun

# This re-directs all the class files to a separate location
CLASSDESTDIR = $(TEMPDIR)/classes

OTHER_JAVACFLAGS += -Xlint:serial -Werror
include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
include FILES_java.gmk
AUTO_FILES_JAVA_DIRS = sun/nio/cs/ext

# Exclude a few sources on windows
ifeq ($(PLATFORM), windows)
  AUTO_JAVA_PRUNE = sun/nio/cs/ext/COMPOUND_TEXT.java         \
                    sun/nio/cs/ext/COMPOUND_TEXT_Decoder.java \
                    sun/nio/cs/ext/COMPOUND_TEXT_Encoder.java \
                    sun/nio/cs/ext/CompoundTextSupport.java
endif # PLATFORM

# For Cygwin, command line arguments that are paths must be converted to
# windows style paths. These paths cannot be used as targets, however, because 
# the ":" in them  will interfere with GNU Make rules, generating "multiple
# target pattern" errors.

# this define is for the rule:
CHARSETS_JAR = $(LIBDIR)/charsets.jar

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

build: $(CHARSETS_JAR)

#
# Extra rules to build character converters.

SERVICE_DESCRIPTION = java.nio.charset.spi.CharsetProvider
SERVICE_DESCRIPTION_PATH = META-INF/services/$(SERVICE_DESCRIPTION)

$(CLASSDESTDIR)/$(SERVICE_DESCRIPTION_PATH): \
  $(SHARE_SRC)/classes/sun/nio/cs/ext/$(SERVICE_DESCRIPTION_PATH)
	$(install-file)

$(CHARSETS_JAR): $(FILES_class) $(CLASSDESTDIR)/$(SERVICE_DESCRIPTION_PATH)
	$(BOOT_JAR_CMD) cf $(CHARSETS_JAR) \
	      -C $(CLASSDESTDIR) sun \
	      -C $(CLASSDESTDIR) $(SERVICE_DESCRIPTION_PATH)  \
	      $(JAR_JFLAGS)
	@$(java-vm-cleanup)

clean::
	$(RM) -r $(CLASSDESTDIR)
	$(RM) $(CHARSETS_JAR)
