#
# Copyright 2000-2006 Sun Microsystems, Inc.  All rights reserved.
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

# Makefile for building javazic utility & TimeZone resource files


BUILDDIR = ../..
PACKAGE = sun.javazic
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

# Time zone data file creation
TZDATA = ./tzdata/
TZDATA_VER = `$(GREP) '^tzdata' $(TZDATA)VERSION`
TZFILE = \
    africa antarctica asia australasia europe northamerica \
    pacificnew southamerica backward \
    etcetera solar87 solar88 solar89 systemv
JDKTZDATA = ./tzdata_jdk/
JDKTZFILES = gmt jdk11_backward
TZFILES = \
    $(addprefix $(TZDATA),$(TZFILE)) \
    $(addprefix $(JDKTZDATA),$(JDKTZFILES))

WORKDIR = $(TEMPDIR)/zi
INSTALLDIR = $(LIBDIR)/zi
MAPFILE = ZoneInfoMappings

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

# Add to the build rule
build: $(INSTALLDIR)/$(MAPFILE)

$(BUILDTOOLJARDIR)/javazic.jar:
	($(CD) $(BUILDDIR)/tools/javazic && $(MAKE))

$(WORKDIR)/$(MAPFILE): $(BUILDTOOLJARDIR)/javazic.jar $(TZFILES)
	$(RM) -r $(@D)
	$(prep-target)
	$(BOOT_JAVA_CMD) -jar $(BUILDTOOLJARDIR)/javazic.jar -V "$(TZDATA_VER)" -d $(WORKDIR) $(TZFILES)
	@$(java-vm-cleanup)

$(INSTALLDIR)/$(MAPFILE): $(WORKDIR)/$(MAPFILE)
	$(RM) -r $(@D)
	$(prep-target)
	$(CP) -r $(WORKDIR)/* $(@D)

clean clobber::
	$(RM) -r $(TEMPDIR) $(INSTALLDIR)
