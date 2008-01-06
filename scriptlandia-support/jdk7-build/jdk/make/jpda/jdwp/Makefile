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
# Makefile for building JDWP
#

BUILDDIR = ../..
PACKAGE = com.sun.tools.jdwp
PRODUCT = jpda
include $(BUILDDIR)/common/Defs.gmk

#
# Where do generated Java source files go?
#
JAVA_SRC_DEST_DIR = $(GENSRCDIR)

#
# Where do generated C source files go?
# the jdwp build is what needs the generated C files
# so they are being placed there for automatic pickup by
# the value of GENNATIVESRCDIR when jdwp is being built.
#
C_SRC_DEST_DIR = $(GENNATIVESRCDIR)/jdwp

JDIPKGDIR = com/sun/tools/jdi
GEN_JDWP_JAVA_DIR = $(JAVA_SRC_DEST_DIR)/$(JDIPKGDIR)
GEN_JDWP_JAVA = $(GEN_JDWP_JAVA_DIR)/JDWP.java
GEN_JDWP_C = $(C_SRC_DEST_DIR)/JDWPCommands.h

JDWP_SPEC       = $(BUILDDIR)/jpda/jdwp/jdwp.spec
JDWPGEN_JARFILE = $(BUILDTOOLJARDIR)/jdwpgen.jar

build: $(GEN_JDWP_JAVA) $(GEN_JDWP_C)

$(GEN_JDWP_JAVA) $(GEN_JDWP_C): $(JDWP_SPEC) $(JDWPGEN_JARFILE)
	$(MKDIR) -p $(GEN_JDWP_JAVA_DIR)
	$(MKDIR) -p $(C_SRC_DEST_DIR)
	$(BOOT_JAVA_CMD) -jar $(JDWPGEN_JARFILE) $(JDWP_SPEC) \
		-jdi $(GEN_JDWP_JAVA) \
		-include $(GEN_JDWP_C)
	@$(java-vm-cleanup)

#
# Extra clean.
#
clean clobber::
	$(RM) -r $(C_SRC_DEST_DIR)
	$(RM) -r $(JAVA_SRC_DEST_DIR)/$(JDIPKGDIR)

#
# Rules
#
include $(BUILDDIR)/common/Rules.gmk

