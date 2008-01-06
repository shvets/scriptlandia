#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
# Makefile to build the Java Plug-in components
#

DEPLOY_TOPDIR = ../../../..
LIBRARY_OUTPUT = adapter/ns7
SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/share/adapter/oji
ADAPTERCOMMONDIR = $(DEPLOY_TOPDIR)/src/plugin/share/adapter/common

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

OBJDIRNAME = obj
OBJOUTPUTDIR=$(OUTPUTDIR)/tmp/deploy/plugin
OBJDIR = $(OBJOUTPUTDIR)/$(LIBRARY_OUTPUT)/$(OBJDIRNAME)

include ./FileList.gmk

ifeq ($(PLATFORM), windows)

LIBRARY = jpioji
PCHFILENAME=$(OBJDIR)/$(LIBRARY).pch
JNIDIR = $(OUTPUTDIR)/include/win32
COREDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/nscore
COMMONDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/common
# Force this evaluation now so later CPPINCPATH sees list not shell command
MOZHDRDIRS := $(shell $(ECHO) $(subst \,/,$(MOZILLA_HEADERS_PATH))/mozilla_headers_18.win32/*)

FILES_cpp += DllMain.cpp \
	     CNS7Adapter.cpp \
	     clientLoadCOM.cpp \
	     oji_clsid.cpp
EXTRA_CPPFLAGS := -DUSRDLL -DXP_WIN32 -Fp"$(PCHFILENAME)" -YX"stdafx.h"
LFLAGS += -def:$(SRCDIR)/$(LIBRARY).def
$(OBJDIR)/StdAfx.obj: EXTRA_CPPFLAGS := -DXP_WIN32 -DUSRDLL -Fp"$(PCHFILENAME)" -Yc"stdafx.h"

else

LIBRARY = javaplugin_oji
LIB_LOCATION=$(OUTPUTDIR)/plugin/$(LIBARCH)/ns7
dummy:=$(shell $(MKDIR) -p $(LIB_LOCATION))

ifeq ($(PLATFORM), linux)
LDLIBS = -lX11 -ldl -lc
else
LDLIBS = -lX11 -ldl -lCrun -lc
endif

JNIDIR = $(OUTPUTDIR)/include/$(PLATFORM)
COREDIR = $(DEPLOY_TOPDIR)/src/plugin/solaris/nscore
COMMONDIR=  $(DEPLOY_TOPDIR)/src/plugin/solaris/common
# Force this evaluation now so later CPPINCPATH sees list not shell command
MOZHDRDIRS := $(shell $(ECHO) $(subst \,/,$(MOZILLA_HEADERS_PATH))/mozilla_headers_18/*)

FILES_cpp += GetFactory5.cpp \
	     GetLibrary.cpp \
	     CNSAdapter_NSPR.cpp
endif

INTERFACESDIR = $(DEPLOY_TOPDIR)/src/plugin/share/jpiapi
ADAPTERINCDIR = $(DEPLOY_TOPDIR)/src/plugin/share/adapter/include

CPPINCPATH = $(OUTPUTDIR)/include $(JNIDIR) \
	     $(subst $(LIBRARY_OUTPUT),,$(TEMPDIR)) \
	     $(COMMONDIR) \
	     $(SRCDIR) \
	     $(MOZHDRDIRS) \
	     $(ADAPTERCOMMONDIR) \
	     $(ADAPTERINCDIR) \
	     $(COREDIR) \
	     $(INTERFACESDIR) 

RSCINC = -i $(SRCDIR) -i $(subst $(LIBRARY),,$(TEMPDIR))

vpath %.c $(ADAPTERCOMMONDIR)
vpath %.cpp $(SRCDIR):$(ADAPTERCOMMONDIR):$(COMMONDIR)
vpath %.rc $(SRCDIR)

include $(JDK_MAKE_SHARED_DIR)/../Library.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk

clobber::
ifneq ($(PLATFORM), windows)
	$(RM) -rf $(LIB_LOCATION)
else
	$(RM) $(OBJDIR)/*.res
endif
