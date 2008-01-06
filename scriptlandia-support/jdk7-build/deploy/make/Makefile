#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#

#
#
# Makefile for building java-web-start 
#

#  
# Note: The deploy workspace is 32-bit only 
#
# Most frequently used targets:
#
#    all            -- build javaws and plugin
#    javaws-all     -- build only the javaws
#    plugin-all     -- build only the plugin
#    clobber        -- clobber javaws and plugin
#    images         -- build the jaws and plugin images 

TOPDIR = ../..
DEPLOY_TOPDIR=..
include $(DEPLOY_TOPDIR)/make/common/Defs.gmk

all:: 
	@$(ECHO) $(PLATFORM) $(ARCH) $(RELEASE) deploy build started: $(shell $(DATE) '+%y-%m-%d %H:%M')

#
# Core.
#
JAVAWS_SUBDIR = javaws
PLUGIN_SUBDIR = plugin
SUBDIRS = $(JAVAWS_SUBDIR) $(PLUGIN_SUBDIR)

ALL_DEPLOY_TARGETS = sanity javaws-all plugin-all images

ifdef DEV_ONLY
  ifdef NO_IMAGES
    ALL_DEPLOY_TARGETS = sanity javaws-all plugin-all
  endif
endif

all:: build

build: $(ALL_DEPLOY_TARGETS)

javaws: sanity-javaws
	$(CD) $(DEPLOY_TOPDIR)/make/javaws ; $(MAKE) installer-int

javaws-all: sanity-javaws
	$(CD) $(DEPLOY_TOPDIR)/make/javaws ; $(MAKE) installer-int sample apidoc

plugin-all: sanity-plugin
   ifeq ($(BUILD_PLUGIN), true)
	$(CD) $(DEPLOY_TOPDIR)/make/plugin ; $(MAKE) all 
   endif

#
# Build the images for both plugin and webstart
# Note: webstart temp image must already be built.
#
images:
	$(CD) $(DEPLOY_TOPDIR)/make/plugin ; $(MAKE) deploy-images BUILD_PLUGIN=$(BUILD_PLUGIN)

#
# Sanity checks.
#
include $(DEPLOY_TOPDIR)/make/common/Sanity.gmk

dummy := $(shell $(MKDIR) -p $(OUTPUTDIR) $(TEMPDIR))

clean: clobber

# cleanup everything. If the workspace is not being built by the control
# workspace, and if it is a Teamware workspace, then see if there are
# any files which are not under SCCS control.
clobber:
	@$(ECHO) ">>>Recursively making "$(JAVAWS_SUBDIR)" "clobber" @ `$(DATE)` ..."
	@$(CD) $(JAVAWS_SUBDIR); $(MAKE) clobber || exit 1; $(CD) ..
	@$(ECHO) "<<<Finished Recursively making "$(JAVAWS_SUBDIR)" clobber @ `$(DATE)`."
	@$(ECHO) ">>>Recursively making "$(PLUGIN_SUBDIR)" "clean" @ `$(DATE)` ..."
	@$(CD) $(PLUGIN_SUBDIR); $(MAKE) clean RELEASE="$(RELEASE)" \
		FULL_VERSION="$(FULL_VERSION)" || exit 1; $(CD) ..
	@$(ECHO) "<<<Finished Recursively making "$(PLUGIN_SUBDIR)" clean @ `$(DATE)`."
	$(CD) $(DEPLOY_TOPDIR)/make/plugin ; $(MAKE) deploy-images-clobber

# this should be the last rule in this file:
all::
	@$(ECHO) $(ARCH) "Deploy Build finished:  " $(FULL_VERSION)

#
# Phonies to avoid accidents.
#
.PHONY: all build javaws-all plugin-all images clobber clean
