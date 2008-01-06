#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
# Makefile to build OJI plugin subdirectories
#
# Build each subdirectory

DEPLOY_TOPDIR = ../../..
include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

ifeq ($(PLATFORM), windows)
  SUBDIRS += win32
else
  SUBDIRS += unix
endif

ifeq ($(PLATFORM), linux)
  SUBDIRS += linux-gcc29
endif

all build clobber:: $(SUBDIRS)

$(SUBDIRS)::
	$(MAKE) -C $@ $(MAKECMDGOALS)

#
# Phonies to avoid accidents.
#
.PHONY: all build clobber $(SUBDIRS)
