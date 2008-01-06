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
# Makefile for Windows HPI DLL
#
BUILDDIR = ../../..
LIBRARY   = hpi
PRODUCT      = java
THREADDIR    = windows_threads
LIB_LOCATION = $(BINDIR)

include $(BUILDDIR)/common/Defs.gmk

# windows compiler flags
ifeq ($(PLATFORM),windows)
  CPPFLAGS_DBG += -DLOGGING
  # Files built here do not compile with warning level 3 if warnings are fatal
  COMPILER_WARNINGS_FATAL=false
endif

FILES_c = \
    linker_md.c \
    memory_md.c \
    monitor_md.c \
    path_md.c \
    socket_md.c \
    sys_api_md.c \
    system_md.c \
    threads_md.c \
    hpi.c # trailing blank required!

JVMLIB = 
JAVALIB =
OTHER_LCF = -export:DLL_Initialize
EXTRA_LIBS =


#
# Other files/flags shared between the HPIs.
#
include $(BUILDDIR)/java/hpi/hpi_common.gmk

#
# Rules for the .so file.
#
include $(BUILDDIR)/common/Library.gmk

