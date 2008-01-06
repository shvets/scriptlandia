#
# Copyright 1997-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Install a wrapper for the "rmid" command. The associated classfiles
# are built at a higher level.
#

BUILDDIR = ../../..
PACKAGE = sun.rmi.activation
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

build: stubs 

#
# Resources
#
LOCALE_SET_DEFINITION = jre
RESOURCE_BUNDLES_PROPERTIES = sun/rmi/server/resources/rmid.properties

#
# Extra dependencies.
#
classes: stubs

#
# Rules to build activation-system stubs
#

# Full package names of implementations requiring stubs
REMOTE_impls = \
	sun.rmi.server.Activation$$ActivationSystemImpl \
	java.rmi.activation.ActivationGroup

REMOTE_files = $(subst .,/,$(REMOTE_impls))
FILES_stubs = $(REMOTE_files:%=$(CLASSBINDIR)/%_Stub.class)

#
# Compile stubs for remote implementations
# (use -v1.2 stubs for activation system)
#

$(CLASSBINDIR)/%_Stub.class: $(CLASSBINDIR)/%.class
	$(RMIC) -v1.2 -classpath $(CLASSBINDIR)	\
	    	-d $(CLASSBINDIR) '$(subst /,.,$(<:$(CLASSBINDIR)/%.class=%))'
	@$(java-vm-cleanup)

stubs: $(FILES_stubs)

#
# Rules to
#
include $(BUILDDIR)/common/Classes.gmk


