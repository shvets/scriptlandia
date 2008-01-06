#
# Copyright 1998-2005 Sun Microsystems, Inc.  All rights reserved.
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

BUILDDIR     = ../../..
PACKAGE      = javax/swing/html/parser
PRODUCT      = swing
include $(BUILDDIR)/common/Defs.gmk

#
# Shared defintions.
#
HTML32DTD          = $(CLASSBINDIR)/javax/swing/text/html/parser/html32.bdtd
DTDBUILDER_JARFILE = $(BUILDTOOLJARDIR)/dtdbuilder.jar
DTDDATA            = $(BUILDDIR)/tools/dtdbuilder/dtds

$(HTML32DTD): $(DTDBUILDER_JARFILE)
	@$(prep-target)
	($(BOOT_JAVA_CMD) -Ddtd_home=$(DTDDATA) \
		-jar $(DTDBUILDER_JARFILE) html32 > $@) || exit 1
	@$(java-vm-cleanup)

all build: $(HTML32DTD)

include $(BUILDDIR)/common/Rules.gmk

