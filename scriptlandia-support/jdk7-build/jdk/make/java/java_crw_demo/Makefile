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
LIBRARY = java_crw_demo
PRODUCT = sun
LIBRARY_OUTPUT = java_crw_demo

# Configure the CFLAGS for this library.

include $(BUILDDIR)/common/Defs.gmk

SRCDIR=$(SHARE_SRC)/demo/jvmti/java_crw_demo

# Use the mapfile-vers (See the mapfile located with this Makefile)
FILES_m = mapfile-vers
ifdef FILES_m
include $(BUILDDIR)/common/Mapfile-vers.gmk
endif

#
# Files to compile.
#
FILES_c = java_crw_demo.c

OTHER_INCLUDES = -I$(SRCDIR)

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

#
# Add to ambient vpath so we pick up the library files
#
vpath %.c $(SRCDIR)

