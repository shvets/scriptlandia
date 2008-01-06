#
# Copyright 2004-2005 Sun Microsystems, Inc.  All rights reserved.
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
LIBRARY = npt
PRODUCT = sun
LIBRARY_OUTPUT = npt

# Configure the CFLAGS for this library.

FILES_m = mapfile-vers

include $(BUILDDIR)/common/Defs.gmk

SRCDIR=$(SHARE_SRC)/npt
PSRCDIR=$(PLATFORM_SRC)/npt

# Use the mapfile-vers (See the mapfile located with this Makefile)
ifdef FILES_m
include $(BUILDDIR)/common/Mapfile-vers.gmk
endif

#
# Files to compile.
#
FILES_c = 			\
	npt.c 	\
	utf.c 	\
	utf_md.c

OTHER_INCLUDES = -I$(SRCDIR) -I$(PSRCDIR)

#
# This removes all asserts in the optimized version
#
CPPFLAGS_OPT += -DNDEBUG

#
# Library to compile.
#
include $(BUILDDIR)/common/Library.gmk

# We don't want to link against -ljava
JAVALIB=

# Add -export options to explicitly spell exported symbols
ifeq ($(PLATFORM), windows)
  OTHER_LCF += -export:nptInitialize -export:nptTerminate
endif

#
# Add to ambient vpath so we pick up the library files
#
vpath %.c $(SRCDIR):$(PSRCDIR)

