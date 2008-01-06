#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
# Makefile to build the ssv
#
#

DEPLOY_TOPDIR = ../../..

LIBRARY = ssv

SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/ssv
PCHFILENAME=$(OBJDIR)/$(LIBRARY).pch

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

IDL = $(SRCDIR)/$(LIBRARY).idl
TLB = $(TEMPDIR)/$(LIBRARY).tlb
GENH = $(TEMPDIR)/$(LIBRARY).h
GENC = $(TEMPDIR)/$(LIBRARY)_i.c
GENCP = $(TEMPDIR)/$(LIBRARY)_p.c
DLLDATA = $(TEMPDIR)/dlldata.c

CPPINCPATH = $(SRCDIR) $(TEMPDIR) $(subst $(LIBRARY),,$(TEMPDIR)) $(subst $(LIBRARY)/,,$(CLASSHDRDIR)) $(DEPLOY_MSSDK)/include $(DEPLOY_TOPDIR)/src/plugin/win32/common $(DEPLOY_TOPDIR)/src/plugin/win32/regutils $(DEPLOY_COMMON_WIN_SRC)

RSCINC = -i $(SRCDIR) -i $(TEMPDIR) -i $(OBJDIR) -i $(subst $(LIBRARY),,$(TEMPDIR))

EXTRA_CPPFLAGS := $(GX_OPTION) -DWIN32_LEAN_AND_MEAN=1

LDLIBS += urlmon.lib wininet.lib shlwapi.lib version.lib comctl32.lib gdi32.lib

LFLAGS += -def:$(SRCDIR)/$(LIBRARY).def -implib:"$(OBJDIR)/$(LIBRARY).lib" $(OBJDIR)/*.res /LIBPATH:$(DEPLOY_MSSDK)/lib

MTL_SWITCHES=-tlb "$(TLB)" -h "$(GENH)" -iid "$(GENC)" -proxy "$(GENCP)" -dlldata "$(DLLDATA)" -Oicf

$(OBJDIR)/StdAfx.obj: EXTRA_CPPFLAGS := $(GX_OPTION) -DWIN32_LEAN_AND_MEAN=1 -DUSRDLL -Fp"$(PCHFILENAME)" -Yc"stdafx.h" 

vpath %.idl $(SRCDIR)
vpath %.cpp $(SRCDIR):$(SRCDIR)/../common:$(SRCDIR)/../regutils:$(DEPLOY_COMMON_WIN_SRC)
vpath %.rc $(SRCDIR)

include ./FileList.gmk 

all optimized debug: $(TLB)

$(TLB):: 
	@$(prep-target)
	$(MTL) $(MTL_SWITCHES) $(IDL)

include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk
include $(JDK_MAKE_SHARED_DIR)/../Library.gmk

clobber::
	$(RM) $(TEMPDIR)/*/*.res $(TEMPDIR)/*/*.pch $(TEMPDIR)/*/*.idb $(TEMPDIR)/*.h $(TEMPDIR)/*.c $(TEMPDIR)/*.tlb
