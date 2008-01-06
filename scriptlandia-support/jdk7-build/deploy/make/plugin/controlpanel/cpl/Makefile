#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
# Makefile to build the Java Plug-in components
#

DEPLOY_TOPDIR = ../../../..

LIBRARY = javacpl
SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/controlpanel/cpl
PCHFILENAME=$(OBJDIR)/$(LIBRARY).pch

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

LIBRARY_SUFFIX = cpl

CPPINCPATH = $(SRCDIR) $(subst $(LIBRARY),,$(TEMPDIR)) $(subst $(LIBRARY)/,,$(CLASSHDRDIR)) $(DEPLOY_TOPDIR)/src/plugin/win32/common $(DEPLOY_COMMON_WIN_SRC)

RSCINC = -i $(SRCDIR) -i $(subst $(LIBRARY),,$(TEMPDIR))
LDLIBS += user32.lib shell32.lib
LFLAGS += -def:$(SRCDIR)/javacpl.def

#
# Override the CCP flags (set in plugin/common/Defs.gmk) so that this program
# statically linked.  As of Visual C .NET, the support libraries for dynamic
# linking (/MD - msvcp71.dll and msvcr71.dll) are NOT supposed to be present
# in the Windows system32 directory.
#
# For the specification requirement, see:
#     http://msdn.microsoft.com/library/default.asp?url=/library/en-us/vclib/html/_crt_c_run.2d.time_libraries.asp
#

ifeq ($(VARIANT), DBG)
  CPPFLAGS1 = -MLd -W3 -Od -GX -Gm -GZ -Zi -ZI -D_DEBUG -Fd"$(OBJDIR)\debug"
else
  CPPFLAGS1 = -ML -W3 -Ox -Os -GX -Zi -DNDEBUG -Fd"$(OBJDIR)\release"
endif
 
vpath %.cpp $(SRCDIR):$(SRCDIR)/../../common:$(DEPLOY_COMMON_WIN_SRC)
vpath %.rc $(SRCDIR)

include ./FileList.gmk 

include $(JDK_MAKE_SHARED_DIR)/../Library.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk

clobber::
	$(RM) $(OBJDIR)/*.res
