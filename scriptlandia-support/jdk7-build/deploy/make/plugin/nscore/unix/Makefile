#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
# Makefile to build the Java Plug-in components
#

DEPLOY_TOPDIR = ../../../..

LIBRARY = javaplugin_nscp
SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/solaris/nscore
COMMONDIR=$(DEPLOY_TOPDIR)/src/plugin/solaris/common
SHAREDDIR = $(DEPLOY_TOPDIR)/src/plugin/share

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

ifeq ($(PLATFORM), linux)
LDLIBS = -lX11 -ldl -lc
else
LDLIBS = -lXt -lX11 -ldl -lCrun -lc
endif

CPPINCPATH = $(SRCDIR) $(SRCDIR)/../common \
             $(subst $(LIBRARY),,$(TEMPDIR)) \
             $(subst $(LIBRARY)/,,$(CLASSHDRDIR)) \
             $(COMMONDIR) \
             $(DEPLOY_TOPDIR)/src/plugin/share/jpiapi \
             $(SRCDIR)/../nsstub

ifeq ($(PLATFORM), linux)
EXTRA_CPPFLAGS	= -DLINUX -DRAPTOR_API -DNEW_STREAMING_API -DXP_UNIX
else
EXTRA_CPPFLAGS	= -DRAPTOR_API -DNEW_STREAMING_API -DXP_UNIX
endif

# Use a mapfile on Solaris/Linux
#FILES_m = mapfile-vers
#ifdef FILES_m
#include $(JDK_MAKE_SHARED_DIR)/../Mapfile-vers.gmk
#endif

vpath %.c $(SRCDIR):$(SRCDIR)/../common:
vpath %.cpp $(SRCDIR):$(SRCDIR)/../common

include ./FileList.gmk 

include $(JDK_MAKE_SHARED_DIR)/../Library.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk

clobber::

