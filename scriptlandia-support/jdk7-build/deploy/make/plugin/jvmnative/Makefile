#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
# Makefile to build the Java Plug-in components
#

DEPLOY_TOPDIR = ../../..

LIBRARY = javaplugin_jni
SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/solaris/jvm_natives

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

ifeq ($(PLATFORM), linux)
LDLIBS = -lX11
EXTRA_CPPFLAGS = -DLINUX
else
LDLIBS = /usr/lib/libm.so -ldl -lXt -lX11 -lsocket -lnsl -lintl -lc
endif

CPPINCPATH = $(SRCDIR) $(SRCDIR)/../common $(subst $(LIBRARY),,$(TEMPDIR)) $(subst $(LIBRARY)/,,$(CLASSHDRDIR))

vpath %.c $(SRCDIR):$(SRCDIR)/../common

include ./FileList.gmk 

# Use mapfile on Solaris/Linux
#FILES_m = mapfile-vers
#ifdef FILES_m
#include $(JDK_MAKE_SHARED_DIR)/../Mapfile-vers.gmk
#endif

include $(JDK_MAKE_SHARED_DIR)/../Library.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk

