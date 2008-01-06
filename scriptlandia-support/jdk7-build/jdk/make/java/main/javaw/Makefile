#
# Copyright 2000-2007 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building javaw.exe (for windows)
#

BUILDDIR = ../../..
PROGRAM = javaw
PRODUCT = java

#
# Statically link javaw to avoid the dependency on msvcr71.dll.  This
# must be set before Defs.gmk is included.
#
MS_RUNTIME_STATIC = true

#
# Statically link javaw to avoid the dependency on jli.dll.  This
# must be set before Program.gmk is included.
#
STATIC_JLI = true

include $(BUILDDIR)/common/Defs.gmk

OTHER_CPPFLAGS += -DJAVAW
LDLIBS_COMMON +=  user32.lib

# Override the default version info with our own resource file (see 5106536)
ifeq ($(PLATFORM), windows)
ifdef OPENJDK
    RC_FLAGS += -i "$(PLATFORM_SRC)/resource/icons"
else
    RC_FLAGS += -i "$(CLOSED_SRC)/windows/native/sun/windows"
endif
  VERSIONINFO_RESOURCE = $(PLATFORM_SRC)/resource/java.rc
endif

#
# Rules.
#
include $(BUILDDIR)/common/Program.gmk
OTHER_CPPFLAGS += -DEXPAND_CLASSPATH_WILDCARDS
OTHER_CPPFLAGS += -DLAUNCHER_NAME=\"$(LAUNCHER_NAME)\"
