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

BUILDDIR = ../..
PACKAGE = java.lang.management
LIBRARY = management
PRODUCT = java
include $(BUILDDIR)/common/Defs.gmk

MGMT_SRC  = $(SHARE_SRC)/classes/java/lang/management
SMGMT_SRC = $(SHARE_SRC)/classes/sun/management

AGENTJAR = $(LIBDIR)/management-agent.jar
MANIFEST = $(SMGMT_SRC)/manifest

#
# Use mapfile
#
FILES_m = mapfile-vers
include $(BUILDDIR)/common/Mapfile-vers.gmk

#
# Files to compile
#
include FILES_c.gmk

AUTO_FILES_JAVA_DIRS = java/lang/management com/sun/management sun/management

include Exportedfiles.gmk

ifeq ($(PLATFORM),windows)

FILES_c     +=  OperatingSystem_md.c

FILES_export +=	com/sun/management/OperatingSystem.java

else # PLATFORM (i.e. solaris & linux)

FILES_c     +=  UnixOperatingSystem_md.c

FILES_export +=	com/sun/management/UnixOperatingSystem.java
endif # PLATFORM

#
# Resources
#
LOCALE_SET_DEFINITION = jre
RESOURCE_BUNDLES_COMPILED_PROPERTIES = sun/management/resources/agent.properties

#
# Find C source files
#
vpath %.c $(SHARE_SRC)/native/sun/management
vpath %.c $(PLATFORM_SRC)/native/sun/management
vpath %.c $(PLATFORM_SRC)/native/com/sun/management

#
# Access to management.h 
#

OTHER_INCLUDES += \
  -I$(SHARE_SRC)/native/sun/management 

ifeq ($(PLATFORM),windows)
  OTHER_LDLIBS += $(JVMLIB)
endif

#
# Library to compile.
#
include $(BUILDDIR)/common/Library.gmk

$(AGENTJAR): $(LIBDIR) $(TEMPDIR)/manifest
	$(BOOT_JAR_CMD) -cfm $(AGENTJAR) $(TEMPDIR)/manifest

$(TEMPDIR)/manifest: $(MANIFEST)
	$(install-file)

build: $(AGENTJAR)

clean clobber::
	$(RM) -r $(CLASSDESTDIR)/java/management
	$(RM) -r $(CLASSDESTDIR)/sun/management
	$(RM) $(TEMPDIR)/manifest $(AGENTJAR)

