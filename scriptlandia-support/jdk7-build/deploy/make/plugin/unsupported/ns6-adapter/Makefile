#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
# Makefile to build the Java Plug-in components
#

DEPLOY_TOPDIR = ../../..

LIBRARY = ns6-adapter
SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/src/share/ns6-adapter

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

LIB_LOCATION=$(OUTPUTDIR)/plugin/$(LIBARCH)/ns6-adapter

dummy:=$(shell $(MKDIR) -p $(LIB_LOCATION))

ifeq ($(PLATFORM), linux)
LDLIBS = -lXt -lX11 -ldl -lc
else
LDLIBS = -ldl -lCrun -lc
endif

CPPINCPATH = $(SRCDIR) $(SRCDIR)/../adapter-common $(SRCDIR)/../jcomponent/public-include $(subst $(LIBRARY),,$(TEMPDIR)) $(subst $(LIBRARY)/,,$(CLASSHDRDIR))
EXTRA_CPPFLAGS = -I$(MOZILLA_HEADERS_PATH)/mozilla_headers_ns610 -I$(MOZILLA_HEADERS_PATH)/mozilla_headers_ns610/nspr -I$(MOZILLA_LIBS_PATH)/../jpi/X11/include -I$(MOZILLA_LIBS_PATH)/../jpi/X11/lib/glib/include -DRAPTOR_API -DNEW_STREAMING_API

vpath %.c $(SRCDIR):$(SRCDIR)/../adapter-common:$(DEPLOY_TOPDIR)/src/plugin/oji-plugin/src/motif/common
vpath %.cpp $(SRCDIR):$(SRCDIR)/../adapter-common:$(DEPLOY_TOPDIR)/src/plugin/oji-plugin/src/motif/common

include ./FileList.gmk 

include $(JDK_MAKE_SHARED_DIR)/../Library.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk

clobber::
	$(RM) -rf $(LIB_LOCATION)
