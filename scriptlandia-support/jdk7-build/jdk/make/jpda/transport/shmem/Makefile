#
# Copyright 1999-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building JDI shared memory transport implementation
#

BUILDDIR = ../../..
LIBRARY  = dt_shmem
PRODUCT = jbug

# Mapfile only used on Solaris/Linux
FILES_m = mapfile-vers

include $(BUILDDIR)/common/Defs.gmk

# 64-bit windows does not build at -W3 if warnings are fatal
ifeq ($(PLATFORM), windows)
  ifeq ($(ARCH_DATA_MODEL), 64)
    COMPILER_WARNINGS_FATAL=false
  endif
endif

FILES_c = \
        SharedMemoryTransport.c \
        SharedMemoryConnection.c \
        shmemBack.c \
        shmemBase.c \
        shmem_md.c

OTHER_INCLUDES = -I$(INCLUDEDIR) -I$(PLATFORM_INCLUDE) \
                 -I$(SHARE_SRC)/transport/export \
                 -I$(SHARE_SRC)/transport/shmem \
                 -I$(SHARE_SRC)/back/export \
                 -I$(PLATFORM_SRC)/transport/shmem

#
# Stuff to feed into javah.
#
FILES_export = \
	com/sun/tools/jdi/SharedMemoryConnection.java \
	com/sun/tools/jdi/SharedMemoryTransportService.java

# Use the mapfile-vers (See the mapfile located with this Makefile)
ifdef FILES_m
  include $(BUILDDIR)/common/Mapfile-vers.gmk
endif

#
# Rules.
#
include $(BUILDDIR)/common/Library.gmk

# Add -export options to explicitly spell exported symbols
ifeq ($(PLATFORM), windows)
  OTHER_LCF += -export:jdwpTransport_OnLoad
endif

#
# vpaths for the transport (should not be seen by others)
#
vpath %.c $(SHARE_SRC)/native/com/sun/tools/jdi:$(SHARE_SRC)/transport/shmem:$(PLATFORM_SRC)/transport/shmem
