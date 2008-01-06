#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
# Makefile to build the Java Plug-in components
#

DEPLOY_TOPDIR = ../../..

EXECUTABLE = java_vm
PROGRAM = java_vm
SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/solaris/jvm_exec

CPPINCPATH = $(SRCDIR) $(subst $(PROGRAM),,$(TEMPDIR)) $(SRCDIR)/../common

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

ifeq ($(PLATFORM), linux)
LDLIBS = -lpthread -ldl -lc
endif

vpath %.cpp $(SRCDIR)
vpath %.c $(SRCDIR)

include ./FileList.gmk 

include $(DEPLOY_TOPDIR)/make/plugin/common/exec.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk
