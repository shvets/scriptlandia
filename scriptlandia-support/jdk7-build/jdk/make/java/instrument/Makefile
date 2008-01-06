#
# Copyright 2003-2006 Sun Microsystems, Inc.  All rights reserved.
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

# Makefile for building the Java Programming Language Instrumentation Services
# agent, supporting java.lang.instrument

BUILDDIR = ../..
PACKAGE = sun.instrument
LIBRARY = instrument
PRODUCT = sun

# Configure the CFLAGS for this library.  Use static binding so that there
# are not dependencies on modules not on the search patch when invoked from
# the Windows system directory (or elsewhere).
MS_RUNTIME_STATIC=true

FILES_m = mapfile-vers

include $(BUILDDIR)/common/Defs.gmk

# Use the mapfile-vers (See the mapfile located with this Makefile)
ifdef FILES_m
include $(BUILDDIR)/common/Mapfile-vers.gmk
endif

#
# Files to compile.
#
FILES_c = \
	EncodingSupport.c \
	EncodingSupport_md.c \
	FileSystemSupport_md.c \
	InstrumentationImplNativeMethods.c \
	InvocationAdapter.c \
	JarFacade.c \
	JPLISAgent.c \
	JPLISAssert.c \
	JavaExceptions.c \
	PathCharsValidator.c \
	Reentrancy.c \
	Utilities.c

#
# -jaragent support requires zip and jar manfiest parser to be compiled
# into the instrument library.
#
IO_PLATFORM_SRC = $(PLATFORM_SRC)/native/java/io
LAUNCHER_SHARE_SRC = $(SHARE_SRC)/bin
LAUNCHER_PLATFORM_SRC = $(PLATFORM_SRC)/bin

FILES_c += \
	canonicalize_md.c

FILES_export = \
	sun/instrument/InstrumentationImpl.java

#
# This controls the ability to do logging in the library.
#
CPPFLAGS_DBG += -DJPLIS_LOGGING
CPPFLAGS_OPT += -DNO_JPLIS_LOGGING

OTHER_INCLUDES = -I$(SHARE_SRC)/instrument
OTHER_INCLUDES += -I$(PLATFORM_SRC)/instrument
OTHER_INCLUDES += -I$(IO_PLATFORM_SRC)
OTHER_INCLUDES += -I$(LAUNCHER_SHARE_SRC) -I$(LAUNCHER_PLATFORM_SRC)

#
# Create a dependency on libjli (Java Launcher Infrastructure)
#
# On UNIX, this is a relative dependency using $ORIGIN. Unfortunately, to
# do this reliably on Linux takes a different syntax than Solaris.
#
# On Windows, this is done by using the same directory as the executable
# itself, as with all the Windows libraries.
#
ifeq ($(PLATFORM), windows)
  OTHER_LDLIBS += $(OUTPUTDIR)/tmp/java/jli/$(OBJDIRNAME)/static/jli.lib
  OTHER_LCF += -export:Agent_OnAttach
  # equivalent of strcasecmp is stricmp on Windows
  CPPFLAGS_COMMON += -Dstrcasecmp=stricmp
else
  LDFLAGS += -L $(LIBDIR)/$(LIBARCH)/jli
  OTHER_LDLIBS += -ljli
  OTHER_LDLIBS += -ldl 
  ifeq ($(PLATFORM), solaris)
    LDFLAGS += -R \$$ORIGIN/jli
  endif
  ifeq ($(PLATFORM), linux)
    LDFLAGS += -z origin
    LDFLAGS += -Wl,--allow-shlib-undefined
    LDFLAGS += -Wl,-rpath -Wl,\$$ORIGIN/jli
  endif
endif

#
# Library to compile.
#
include $(BUILDDIR)/common/Library.gmk

# We don't want to link against -ljava
JAVALIB=

#
# Add to ambient vpath so we pick up the library files
#
vpath %.c $(SHARE_SRC)/instrument $(PLATFORM_SRC)/instrument
vpath %.c $(IO_PLATFORM_SRC)

