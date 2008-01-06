#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
# Makefile to build the npDeployHelper.dll
#
# To build npDeployHelper from this directory:
#    gnumake all
#

DEPLOY_TOPDIR = ../../../..

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

LIBRARY = npdeploytk

SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/toolkit/mozilla
UTILSDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/toolkit/common

MOZHDRDIR = $(subst \,/,$(MOZILLA_HEADERS_PATH)/mozilla_headers_18.win32/plugin) \
			$(subst \,/, $(MOZILLA_HEADERS_PATH)/mozilla_headers_18.win32/nspr) \
			$(subst \,/, $(MOZILLA_HEADERS_PATH)/mozilla_headers_18.win32/java)

OBJDIRNAME = obj$(SUFFIX)
OBJOUTPUTDIR=$(OUTPUTDIR)/tmp/deploy/plugin
OBJDIR = $(OBJOUTPUTDIR)/$(LIBRARY)/$(OBJDIRNAME)
DBG_OBJDIR = $(OBJOUTPUTDIR)/$(LIBRARY)/obj$(DBG_SUFFIX)
OPT_OBJDIR = $(OBJOUTPUTDIR)/$(LIBRARY)/obj$(OPT_SUFFIX)
PCHFILENAME=$(OBJDIR)/$(LIBRARY).pch

include ./FileList.gmk

EXTRA_CPPFLAGS := -TP -Gy -Zi -Fp"$(PCHFILENAME)" -DUSRDLL -D_WIN32=1 -DXP_WIN32=1 -DHW_THREADS=1 -DWINVER=0x400 -DSTDC_HEADERS=1 -DWIN32_LEAN_AND_MEAN=1 -D_X86_=1 -DOJI=1

LFLAGS += /NOLOGO -def:$(SRCDIR)/$(LIBRARY).def /SUBSYSTEM:WINDOWS /NODEFAULTLIB:nafxcw.lib -opt:noref /LIBPATH:$(DEPLOY_MSSDK)/lib

LDLIBS += urlmon.lib wininet.lib version.lib shell32.lib kernel32.lib user32.lib gdi32.lib  winmm.lib wsock32.lib advapi32.lib comctl32.lib wintrust.lib crypt32.lib

CPPINCPATH = $(MOZHDRDIR) \
             $(DEPLOY_MSSDK)/include \
             $(OUTPUTDIR)/include \
             $(TEMPDIR) \
             $(subst $(LIBRARY),,$(TEMPDIR)) \
             $(subst $(LIBRARY)/,,$(CLASSHDRDIR)) \
	     $(SRCDIR) \
	     $(SRCDIR)/../../common \
             $(UTILSDIR)	     

RSCINC = -i $(SRCDIR) -i $(TEMPDIR) -i $(OBJDIR) -i $(subst $(LIBRARY),,$(TEMPDIR))

$(OBJDIR)/StdAfx.obj: EXTRA_CPPFLAGS := $(GX_OPTION) -DWIN32_LEAN_AND_MEAN=1 -D_X86_=1 -DUSRDLL -Fp"$(PCHFILENAME)" -Yc"stdafx.h"

vpath %.cpp $(SRCDIR):$(UTILSDIR):$(SRCDIR)/../../common
vpath %.rc $(SRCDIR)

include $(JDK_MAKE_SHARED_DIR)/../Library.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk

clobber::
	$(RM) $(OBJDIR)/*.*




