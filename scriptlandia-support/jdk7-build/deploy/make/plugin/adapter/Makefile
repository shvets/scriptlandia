#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
#
# Makefile to build OJI plugin subdirectories
#
# Build each subdirectory

DEPLOY_TOPDIR = ../../..
include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

SUBDIRS += oji-adapter

ifeq ($(PLATFORM), solaris)
  SUBDIRS += ns4-adapter
endif

ifeq ($(PLATFORM), linux)
  SUBDIRS += oji-gcc29-adapter
endif

all build clobber:: $(SUBDIRS)

$(SUBDIRS)::
	$(MAKE) -C $@ $(MAKECMDGOALS)

#
# Phonies to avoid accidents.
#
.PHONY: all build clobber $(SUBDIRS)
