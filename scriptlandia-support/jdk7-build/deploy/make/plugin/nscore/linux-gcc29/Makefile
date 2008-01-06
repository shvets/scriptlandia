#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
# Makefile to build the Java Plug-in components
#

DEPLOY_TOPDIR = ../../../..

LIBRARY = javaplugin_nscp_gcc29
SRCDIR = $(DEPLOY_TOPDIR)/src/plugin/solaris/nscore
COMMONDIR=$(DEPLOY_TOPDIR)/src/plugin/solaris/common
SHAREDDIR = $(DEPLOY_TOPDIR)/src/plugin/share

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

COMPILER_PATH = $(GCC29_COMPILER_PATH)bin/

ifdef ALT_GCC29_PLUGIN_LIB_PATH
all::
	$(RM) $(OUTPUTDIR)/lib/$(LIBARCH)/lib$(LIBRARY).so
	if [ -f $(ALT_GCC29_PLUGIN_LIB_PATH)/lib$(LIBRARY).so ] ; then \
	  $(CP) $(ALT_GCC29_PLUGIN_LIB_PATH)/lib$(LIBRARY).so \
		$(OUTPUTDIR)/lib/$(LIBARCH); \
	elif [ -f $(JDK_IMPORT_PATH)/jre/lib/$(LIBARCH)/lib$(LIBRARY).so ] ; then \
	  $(CP) $(JDK_IMPORT_PATH)/jre/lib/$(LIBARCH)/lib$(LIBRARY).so \
	        $(OUTPUTDIR)/lib/$(LIBARCH); \
	else \
	  echo "Cannot find plugin library lib$(LIBRARY).so"; \
	  exit 1; \
	fi
else
LDLIBS = -lX11 -ldl -lc

CPPINCPATH = $(SRCDIR) $(SRCDIR)/../common \
             $(subst $(LIBRARY),,$(TEMPDIR)) \
             $(subst $(LIBRARY)/,,$(CLASSHDRDIR)) \
             $(COMMONDIR) \
             $(DEPLOY_TOPDIR)/src/plugin/share/jpiapi \
             $(SRCDIR)/../nsstub

EXTRA_CPPFLAGS	= -B $(COMPILER_PATH)/../lib/gcc-lib/ -I $(COMPILER_PATH)/../lib/gcc-lib/i386-redhat-linux/egcs-2.9*/include -DRAPTOR_API -DNEW_STREAMING_API -DXP_UNIX -DLINUX

OBJDIRNAME = obj
OBJOUTPUTDIR = $(OUTPUTDIR)/tmp/deploy/plugin
OBJDIR = $(OBJOUTPUTDIR)/$(LIBRARY)/$(OBJDIRNAME)

vpath %.c $(SRCDIR):$(SRCDIR)/../common:
vpath %.cpp $(SRCDIR):$(SRCDIR)/../common:$(SHAREDDIR)/jcomponent

include ../unix/FileList.gmk 
include $(JDK_MAKE_SHARED_DIR)/../Library.gmk
include $(DEPLOY_TOPDIR)/make/plugin/common/rules.gmk

endif

clobber::
