#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
# Most frequently used targets:
#
#    all            -- builds
#    clobber        -- clobber the builds
#
INSTALL_BUILDDIR=../..

include $(INSTALL_BUILDDIR)/common/Defs.gmk

all:
	$(CD) $(PLATFORM) ; $(MAKE) all

clean::
	$(CD) $(PLATFORM) ; $(MAKE) clean

#
# Phonies to avoid accidents.
#
.PHONY: all clean 
