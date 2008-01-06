#
# Copyright 2004 Sun Microsystems, Inc.  All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#
#

BUILDDIR = ../../..
PRODUCT = demo/jvmti
DEMONAME = hprof
include $(BUILDDIR)/common/Defs.gmk

DEMO_ROOT       = $(SHARE_SRC)/demo/jvmti/$(DEMONAME)
DEMO_TOPFILES   = ./README.txt
DEMO_PSRCDIR    = $(PLATFORM_SRC)/demo/jvmti/$(DEMONAME)
DEMO_DESTDIR    = $(DEMODIR)/jvmti/$(DEMONAME)

DEMO_OBJECTS    = java_crw_demo.$(OBJECT_SUFFIX)

ifeq ($(PLATFORM), windows)
  EXTRA_LIBS += wsock32.lib winmm.lib
endif
ifeq ($(PLATFORM), solaris)
  OTHER_LDLIBS += $(LIBSOCKET) -lnsl -ldl
endif
ifeq ($(PLATFORM), linux)
  OTHER_LDLIBS += $(LIBSOCKET) -lnsl -ldl -lpthread
endif

#
# Demo jar building rules.
#
include $(BUILDDIR)/common/Demo.gmk

