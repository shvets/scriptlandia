#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
# Makefile to build the Java Plug-in components
#

DEPLOY_TOPDIR = ../../..

LIBRARY = RegUtilsMSI
SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/regutilsmsi
PCHFILENAME=$(OBJDIR)/$(LIBRARY).pch
DEPLOYCLASSHDRDIR = $(OUTPUTDIR)/tmp/deploy/deployClassHeaders

# Tell Defs.gmk that we want a static link of the MS runtime libraries
MS_RUNTIME_STATIC=true

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

CPPINCPATH = $(SRCDIR) $(subst $(LIBRARY),,$(TEMPDIR)) $(subst $(LIBRARY)/,,$(DEPLOYCLASSHDRDIR)) $(DEPLOY_TOPDIR)/src/plugin/win32/common $(DEPLOY_COMMON_WIN_SRC) $(DEPLOY_TOPDIR)/src/plugin/win32/regutils

RSCINC = -i $(SRCDIR) -i $(subst $(LIBRARY),,$(TEMPDIR))
EXTRA_CPPFLAGS := -DUSRDLL -Fp"$(PCHFILENAME)" -Yu"stdafx.h" -I"$(OBJDIR)"
LDLIBS += urlmon.lib wininet.lib version.lib shell32.lib $(OBJDIR)/Msi.lib
LFLAGS += -def:$(SRCDIR)/$(LIBRARY).def
CXXFLAGS += -I$(TEMPDIR)

$(OBJDIR)/RegInstallMSI.obj:: copy-files

$(OBJDIR)/UserProfile.obj $(OBJDIR)/common.obj: EXTRA_CPPFLAGS :=

$(OBJDIR)/StdAfx.obj: EXTRA_CPPFLAGS := -DUSRDLL -Fp"$(PCHFILENAME)" -Yc"stdafx.h"

copy-files:
	$(CP) $(DEPLOY_MSSDK)/include/msi.h $(OBJDIR)/msi.h ; \
	$(CP) $(DEPLOY_MSSDK)/include/msiquery.h $(OBJDIR)/msiquery.h ; \
	$(CP) $(DEPLOY_MSSDK)/lib/msi.lib $(OBJDIR)/msi.lib 


vpath %.cpp $(SRCDIR):$(SRCDIR)/../common:$(DEPLOY_TOPDIR)/src/plugin/win32/common:$(DEPLOY_TOPDIR)/src/common/windows/native:$(DEPLOY_TOPDIR)/src/plugin/win32/regutils
vpath %.rc $(SRCDIR)

include ./FileList.gmk 

include $(JDK_MAKE_SHARED_DIR)/../Library.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk

clobber::

build:
