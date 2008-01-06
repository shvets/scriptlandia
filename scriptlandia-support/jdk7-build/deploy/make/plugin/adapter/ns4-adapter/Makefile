#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
# Makefile to build the Java Plug-in components
#

DEPLOY_TOPDIR = ../../../..
LIBRARY_OUTPUT = adapter/ns4
SHAREDIR = $(DEPLOY_TOPDIR)/src/plugin/share
SRCDIR = $(SHAREDIR)/adapter/ns4

# These two are for UNIX
STUBDIR = $(DEPLOY_TOPDIR)/src/plugin/solaris/nsstub
ADAPTERCOMMONDIR = $(SHAREDIR)/adapter/common

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

# In Linux, we need to use non-default gcc compiler (2.9)to compile the
# Netscape 4 Plug-in
CC_VER_MAJOR = $(shell $(CC) -dumpversion | sed 's/egcs-//' | cut -d'.' -f1)
ifeq ("$(CC_VER_MAJOR)", "3")
COMPILER_PATH = $(GCC29_COMPILER_PATH)bin/
EXTRA_CPPFLAGS += -B $(COMPILER_PATH)/../lib/gcc-lib/ -I $(COMPILER_PATH)/../lib/gcc-lib/i386-redhat-linux/egcs-2.9*/include 
endif

include ./FileList.gmk 

ifeq ($(PLATFORM), windows)

LIBRARY = jpinpp
PCHFILENAME=$(OBJDIR)/$(LIBRARY).pch
COREDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/nscore
COMMONDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/common

FILES_cpp += DllMain.cpp \
	     CNS4Adapter.cpp \
	     clientLoadCOM.cpp \
	     oji_clsid.cpp

EXTRA_CPPFLAGS := -DUSRDLL -Fp"$(PCHFILENAME)" -YX"stdafx.h"
LFLAGS += -def:$(SRCDIR)/$(LIBRARY).def
$(OBJDIR)/StdAfx.obj: EXTRA_CPPFLAGS := -DUSRDLL -Fp"$(PCHFILENAME)" -Yc"stdafx.h"

else 

LIBRARY = javaplugin
LIB_LOCATION=$(OUTPUTDIR)/plugin/$(LIBARCH)/ns4
dummy:=$(shell $(MKDIR) -p $(LIB_LOCATION))

ifeq ($(PLATFORM), linux)
LDLIBS = -lX11 -ldl -lnsl -lc
else
LDLIBS = -lX11 -ldl -lnsl -lCrun -lc
endif

COREDIR = $(DEPLOY_TOPDIR)/src/plugin/solaris/nscore
COMMONDIR=  $(DEPLOY_TOPDIR)/src/plugin/solaris/common

FILES_c = ns4stubs.c 
FILES_cpp += ns4adapter.cpp \
	     GetLibrary.cpp \
	     CNS4Adapter_UnixService.cpp

endif

CPPINCPATH = $(subst $(LIBRARY_OUTPUT),,$(TEMPDIR)) \
             $(COREDIR) \
	     $(COMMONDIR) \
	     $(SRCDIR) \
	     $(SHAREDIR)/jpiapi \
             $(SHAREDIR)/npapi \
             $(SHAREDIR)/adapter/include

vpath %.c $(STUBDIR)
vpath %.cpp $(SRCDIR):$(COMMONDIR):$(SHAREDIR):$(STUBDIR):$(ADAPTERCOMMONDIR)

include $(JDK_MAKE_SHARED_DIR)/../Library.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk

clobber::
ifneq ($(PLATFORM), windows)
	$(RM) -rf $(LIB_LOCATION)
endif

