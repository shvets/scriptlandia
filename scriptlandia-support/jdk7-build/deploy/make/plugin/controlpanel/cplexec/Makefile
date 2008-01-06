#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
# Makefile to build the Java Plug-in components
#

DEPLOY_TOPDIR = ../../../..

EXECUTABLE = javacpl
PROGRAM = javacplexec
SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/win32/controlpanel/cplexec
PCHFILENAME=$(OBJDIR)/$(EXECUTABLE).pch

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

CPPINCPATH = $(SRCDIR) $(subst $(EXECUTABLE),,$(TEMPDIR)/../) $(subst $(EXECUTABLE),,$(TEMPDIR)) $(subst $(EXECUTABLE)/,,$(CLASSHDRDIR)) $(DEPLOY_TOPDIR)/src/plugin/win32/common $(DEPLOY_COMMON_WIN_SRC)

RSCINC = -i $(SRCDIR) -i $(subst $(EXECUTABLE),,$(TEMPDIR)) -i $(subst $(EXECUTABLE),,$(TEMPDIR)/../)
LDLIBS += user32.lib

vpath %.cpp $(SRCDIR):$(SRCDIR)/../../common:$(DEPLOY_COMMON_WIN_SRC)
vpath %.rc $(SRCDIR)

include ./FileList.gmk 

include $(DEPLOY_TOPDIR)/make/plugin/common/exec.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk

clobber::
	$(RM) $(OBJDIR)/*.res

