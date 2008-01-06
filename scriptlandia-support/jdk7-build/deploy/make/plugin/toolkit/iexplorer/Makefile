#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
# Makefile to build DeployTk
#
# To build DeployTk from this directory:
#    gnumake all
#

DEPLOY_TOPDIR = ../../../..

LIBRARY = deploytk

HELPERSRCDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/toolkit
SRCDIR = $(HELPERSRCDIR)/iexplorer
PCHFILENAME=$(OBJDIR)/$(LIBRARY).pch

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

IDL = $(SRCDIR)/$(LIBRARY).idl
TLB = $(TEMPDIR)/$(LIBRARY).tlb
GENH = $(TEMPDIR)/$(LIBRARY)Module.h
GENC = $(TEMPDIR)/$(LIBRARY)_i.c
GENCP = $(TEMPDIR)/$(LIBRARY)_p.c
DLLDATA = $(TEMPDIR)/dlldata.c

CPPINCPATH = $(SRCDIR) $(TEMPDIR) $(subst $(LIBRARY),,$(TEMPDIR)) $(subst $(LIBRARY)/,,$(CLASSHDRDIR)) $(HELPERSRCDIR)/common $(DEPLOY_MSSDK)/include $(HELPERSRCDIR)/../common

RSCINC = -i $(SRCDIR) -i $(TEMPDIR) -i $(OBJDIR) -i $(subst $(LIBRARY),,$(TEMPDIR))

EXTRA_CPPFLAGS := $(GX_OPTION) -DWIN32_LEAN_AND_MEAN=1

LDLIBS += urlmon.lib wininet.lib shlwapi.lib version.lib comctl32.lib wintrust.lib wsock32.lib crypt32.lib

LFLAGS += -def:$(SRCDIR)/$(LIBRARY).def -implib:"$(OBJDIR)/$(LIBRARY).lib" $(OBJDIR)/*.res /LIBPATH:$(DEPLOY_MSSDK)/lib

MTL_SWITCHES=-tlb "$(TLB)" -h "$(GENH)" -iid "$(GENC)" -proxy "$(GENCP)" -dlldata "$(DLLDATA)" -Oicf

$(OBJDIR)/StdAfx.obj: EXTRA_CPPFLAGS := $(GX_OPTION) -DWIN32_LEAN_AND_MEAN=1 -DUSRDLL -Fp"$(PCHFILENAME)" -Yc"stdafx.h"

vpath %.idl $(SRCDIR)
vpath %.cpp $(SRCDIR):$(HELPERSRCDIR)/common:$(HELPERSRCDIR)/../common
vpath %.rc $(SRCDIR)

include ./FileList.gmk 

all optimized debug: $(TLB)

$(TLB):: 
	@$(prep-target)
	$(MTL) $(MTL_SWITCHES) $(IDL)

include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk
include $(JDK_MAKE_SHARED_DIR)/../Library.gmk

clobber::
	$(RM) $(TEMPDIR)/*/*.res $(TEMPDIR)/*/*.pch $(TEMPDIR)/*/*.idb $(TEMPDIR)/*.tlb $(TEMPDIR)/*.h $(TEMPDIR)/*.c
