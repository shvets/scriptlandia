#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#

#
#
# This makefile will create the jinstall.inf and jinstall.xml file
# Unique target : all
#


# get a standard build environment set...
INSTALL_BUILDDIR = ../../../..
PRODUCT = xmlinffile

TEMPDIR = $(TEMP_DIR)/$(PRODUCT)
PLUGIN_TMPDIR = $(TEMP_DIR)/deploy/plugin

include $(INSTALL_BUILDDIR)/common/Defs.gmk

ifndef JDK_UPDATE_VERSION
  UPDATE_NUMBER=0
  NODOTVERSION=$(JDK_MAJOR_VERSION)$(JDK_MINOR_VERSION)$(JDK_MICRO_VERSION)
  JRE_IFTW_BUNDLE_NAME=$(JRE_SHORT_BUNDLE_NAME)-iftw
else
  UPDATE_NUMBER=$(shell $(ECHO) $(JDK_UPDATE_VERSION) | $(NAWK) '{print substr($$0, 2, 1);}')
  NODOTVERSION=$(JDK_MAJOR_VERSION)$(JDK_MINOR_VERSION)$(JDK_MICRO_VERSION)_$(JDK_UPDATE_VERSION)
  JRE_IFTW_BUNDLE_NAME=$(JRE_SHORT_BUNDLE_NAME)-p-iftw
endif

ifndef VERSION
  VERSION=$(RELEASE).0
endif

###################### all ################################
# 
# THIS IS THE ONLY VALID TARGET (with clean). Copy the installation scripts
# and jre-image directory to the TEMP directory so we can delete/add
# files between the English and International installations. Do the two
# installations in succession.
# 
all: $(TEMPDIR) xmlinf_only

###################### clean #############################
#
# Remove all produced files.
#
clean: 
	$(RM) -r $(TEMPDIR)

$(BIN_BUNDLEDIR):
	$(MKDIR) -p $@

$(TEMPDIR):
	$(MKDIR) -p $@


###################### xmlinf_only #############################
#
# Build only the installation bootstrap
#

# generate filename and webpage equivalents of the VERSION string:
# It used to be done this way in a shell script:
#    Notice that the original methods truncate only those _0's at the end
#       of the string. The new versions removes all _0's in the string.
#
#    filever=`print $version| sed 's/\./_/g' | sed 's/\(\_0\)\+$//g'`
#    webver=`print $version| sed 's/\(\.0\)\+$//g'`
#

WEB_VERSION_STRING   = $(subst .0,, $(VERSION))
PLUGIN_FILE_VERSION  = $(JDK_MINOR_VERSION),$(JDK_MICRO_VERSION),$(UPDATE_NUMBER),$(COOKED_BUILD_NUMBER)

TMPDIR_DRIVE = $(filter %:,$(subst /, ,$(TEMPDIR)))
ifeq ($(TMPDIR_DRIVE),)
TMPDIR_DRIVE = ./
endif
TMPDIR_FILESPEC = $(patsubst $(TMPDIR_DRIVE)%,%,$(TEMPDIR))

xmlinf_only: 
  ifeq ($(ARCH_DATA_MODEL), 32)
	@#$(SH) construct.sh $(VERSION) $(HTTPSERVER)
	@#
	@# Construct the .xml and .inf file for the Java Plug-in installation
	@# bootstrap. 
	#
	@# format is construct VERSION_VERSION HTTP_SERVER
	@#
	@$(ECHO) Construction Plug-in Installation bundles for version $(VERSION)
	@$(ECHO) File version is $(JDK_MKTG_UNDERSCORE_VERSION), Web pages version is $(WEB_VERSION_STRING)
	@$(ECHO) Using file: $(JRE_NAME)-$(JDK_MKTG_UNDERSCORE_VERSION)-win.exe 
	@#
	@#construct the .inf and .xml file
	@#
	@$(ECHO) s@#PLUGIN_VERSION#@$(PLUGIN_FILE_VERSION)@g               > $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#HTTP_SERVER#@$(HTTPSERVER)@g                    >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#DEFAULTXML#@$(DEFAULT_XML)@g                    >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#PLUGIN_VERSION_FOR_FILE#@$(JDK_MKTG_UNDERSCORE_VERSION)@g      >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#JDK_VERSION#@$(JDK_VERSION)@g      >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#CAB_LOCATION#@$(CAB_LOCATION)@g    >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#MINORMICRO_VERSION#@$(JDK_MINOR_VERSION).$(JDK_MICRO_VERSION)@g      >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#JDK_MAJOR_VERSION#@$(JDK_MAJOR_VERSION)@g      >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#JDK_MINOR_VERSION#@$(JDK_MINOR_VERSION)@g      >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#JDK_MICRO_VERSION#@$(JDK_MICRO_VERSION)@g      >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#UPDATE_NUMBER#@$(UPDATE_NUMBER)@g      >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#NODOTVERSION#@$(NODOTVERSION)@g      >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#JRE_IFTW_BUNDLE_NAME#@$(JRE_IFTW_BUNDLE_NAME)@g      >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#FULL_VERSION#@$(FULL_VERSION)@g      >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#TMPDIR_DRIVE#@$(TMPDIR_DRIVE)@g                        >> $(TEMPDIR)/cab-only-tempfile.sed
	@$(ECHO) s@#TMPDIR_FILESPEC#@$(subst /,\\\\,$(TMPDIR_FILESPEC))@g  >> $(TEMPDIR)/cab-only-tempfile.sed
	$(SED) -f $(TEMPDIR)/cab-only-tempfile.sed  plugin_jinstall_original.inf > $(TEMPDIR)/jinstall-$(JDK_MKTG_UNDERSCORE_VERSION)_temp.inf
	$(SED) -f $(TEMPDIR)/cab-only-tempfile.sed  plugin_jinstall_original.xml > $(TEMPDIR)/$(FULL_VERSION).xml
	@#
	$(CP) $(PLUGIN_TMPDIR)/jinstall/obj/jinstall.exe $(TEMPDIR)/jinstall.exe
	$(CAT) $(TEMPDIR)/jinstall-$(JDK_MKTG_UNDERSCORE_VERSION)_temp.inf | $(SED) -e "s/#INSTALLMETHOD#/plugin/g" > $(TEMPDIR)/jinstall-$(JDK_MKTG_UNDERSCORE_VERSION).inf
	$(CAT) $(TEMPDIR)/jinstall-$(JDK_MKTG_UNDERSCORE_VERSION)_temp.inf | $(SED) -e "s/#INSTALLMETHOD#/jcab/g" > $(TEMPDIR)/jinstall-$(JDK_MKTG_UNDERSCORE_VERSION)-jc.inf
	$(CP) $(TEMPDIR)/$(FULL_VERSION).xml $(BIN_BUNDLEDIR)
endif

.PHONY: all clean xmlinf_only
