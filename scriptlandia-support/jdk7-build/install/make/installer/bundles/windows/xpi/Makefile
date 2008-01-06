#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#

# This is the makefile for building the J2RE installer bundles after compiling
# the j2sdk sources. Listed below are some general notes in using this makefile.
#
# 1) Requires InstallShield Package for the Web 3 which is defined as $(IPFW).
# 2) Requires MKS Toolkit (should be first thing in your PATH).
# 3) $(TEMPDIR) is the temporary building directory.
# 4) $(INSTALL_BUILDDIR) is 4 directories above this directory.
#
# Unique target : all
#


# get a standard build environment set...
INSTALL_BUILDDIR = ../../../..
PRODUCT = xpi

TEMPDIR = $(TEMP_DIR)/$(PRODUCT)
PLUGIN_TMPDIR = $(TEMP_DIR)/deploy/plugin

include $(INSTALL_BUILDDIR)/common/Defs.gmk

ifndef JDK_UPDATE_VERSION
  CONVERT_UPDATE = $(SED) -e "s/JDKUPDATE//g"
  UPDATENUMBER=0
else
  CONVERT_UPDATE = $(SED) -e "s/JDKUPDATE/_$(JDK_UPDATE_VERSION)/g"
  UPDATENUMBER=$(shell $(ECHO) $(JDK_UPDATE_VERSION) | $(NAWK) '{print substr($$0, 2, 1);}')
endif

CONVERT_MAJOR = $(SED) -e "s/JDKMAJOR/$(JDK_MAJOR_VERSION)/g"
CONVERT_MINOR = $(SED) -e "s/JDKMINOR/$(JDK_MINOR_VERSION)/g"
CONVERT_MICRO = $(SED) -e "s/JDKMICRO/$(JDK_MICRO_VERSION)/g"

PROCESS_FILES = $(CONVERT_MAJOR) | $(CONVERT_MINOR) | $(CONVERT_MICRO) | $(CONVERT_UPDATE)

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
all: $(TEMPDIR) $(TEMPDIR)/regular $(TEMPDIR)/patch xpi xpi_jc

###################### clean #############################
#
# Remove all produced files.
#
clean: 
	$(RM) $(BIN_BUNDLEDIR)/$(JRE_NAME)*.xpi
	$(RM) -r $(TEMPDIR)

$(BIN_BUNDLEDIR):
	$(MKDIR) -p $@

$(TEMPDIR):
	$(MKDIR) -p $@

$(TEMPDIR)/regular:
	$(MKDIR) -p $@

$(TEMPDIR)/patch:
	$(MKDIR) -p $@

###################### xpi ############################
#
# Create english bundle through its dependency. Copy the bundle to the 
# output directory.
#
ifneq ($(MILESTONE),fcs)
XPI_MILESTONE = -$(MILESTONE)
endif


CONVERT_MILESTONE = $(SED) -e "s/MILESTONE/$(XPI_MILESTONE)/g"
CONVERT_PLATFORM = $(SED) -e "s/PLATFORM/$(PLATFORM)/g"
CONVERT_ARCH = $(SED) -e "s/ARCH/$(ARCH)/g"
CONVERT_XMLLOC = $(SED) -e "s@DEFAULTXML@$(DEFAULT_XML)@g"
CONVERT_FULLVERSION = $(SED) -e "s@FULL_VERSION@$(FULL_VERSION)@g"
#generic xpi & java.com xpi
CONVERT_METHOD_XPI = $(SED) -e "s/INSTALLMETHOD/xpi/g"
CONVERT_METHOD_JC_XPI = $(SED) -e "s/INSTALLMETHOD/jxpi/g"

INSTALL_JS_PROCESS = $(PROCESS_FILES) | $(CONVERT_MILESTONE) | $(CONVERT_PLATFORM) | $(CONVERT_ARCH) | $(CONVERT_FULLVERSION) | $(CONVERT_XMLLOC)

xpi: $(BIN_BUNDLEDIR) install.js
	$(CP) $(PLUGIN_TMPDIR)/jinstall/obj/jinstall.exe $(TEMPDIR)/regular/jinstall.exe
	@#
	$(CAT) install.js | $(INSTALL_JS_PROCESS) | $(CONVERT_METHOD_XPI) > $(TEMPDIR)/regular/install.js
	@#
  ifeq ($(SIGN_FLAG),1)
	$(SIGNTOOL) -d $(CERTDIR) -k $(CERTNICK) -p $(CERTPASS) $(TEMPDIR)/regular
	@#
	@# somehow the rsa file must be the first file in signed xpi bundle or 
	@# Mozilla/Firefox cannot recognize it. Maybe a bug in Mozilla/Firefox
	@#
	$(CD) $(TEMPDIR)/regular; \
	$(ZIPEXE) $(BIN_BUNDLEDIR)/$(JRE_NAME)-$(BIN_BUNDLE_NAMEPART)-$(BUNDLE_DATE).xpi META-INF/zigbert.rsa; \
	$(ZIPEXE) -r -D $(BIN_BUNDLEDIR)/$(JRE_NAME)-$(BIN_BUNDLE_NAMEPART)-$(BUNDLE_DATE).xpi * -x META-INF/zigbert.rsa
	$(RM) -r $(TEMPDIR)/regular/META-INF $(TEMPDIR)/regular/install.js $(TEMPDIR)/regular/jinstall.exe
  else
	$(CD) $(TEMPDIR)/regular; $(ZIPEXE) $(BIN_BUNDLEDIR)/$(JRE_NAME)-$(BIN_BUNDLE_NAMEPART)-$(BUNDLE_DATE).xpi \
	jinstall.exe install.js
	$(RM) -f $(TEMPDIR)/regular/install.js $(TEMPDIR)/regular/jinstall.exe
  endif

xpi_jc: $(BIN_BUNDLEDIR) install.js
	$(CP) $(PLUGIN_TMPDIR)/jinstall/obj/jinstall.exe $(TEMPDIR)/regular/jinstall.exe
	@#
	$(CAT) install.js | $(INSTALL_JS_PROCESS) | $(CONVERT_METHOD_JC_XPI) > $(TEMPDIR)/regular/install.js
	@#
  ifeq ($(SIGN_FLAG),1)
	$(SIGNTOOL) -d $(CERTDIR) -k $(CERTNICK) -p $(CERTPASS) $(TEMPDIR)/regular
	@#
	@# somehow the rsa file must be the first file in signed xpi bundle or 
	@# Mozilla/Firefox cannot recognize it. Maybe a bug in Mozilla/Firefox
	@#
	$(CD) $(TEMPDIR)/regular; \
	$(ZIPEXE) $(BIN_BUNDLEDIR)/$(JRE_NAME)-$(BIN_BUNDLE_NAMEPART)-$(BUNDLE_DATE).xpi META-INF/zigbert.rsa; \
	$(ZIPEXE) -r -D $(BIN_BUNDLEDIR)/$(JRE_NAME)-$(BIN_BUNDLE_NAMEPART)-$(BUNDLE_DATE)-jc.xpi * -x META-INF/zigbert.rsa
	$(RM) -r $(TEMPDIR)/regular/META-INF $(TEMPDIR)/regular/install.js $(TEMPDIR)/regular/jinstall.exe
  else
	$(CD) $(TEMPDIR)/regular; $(ZIPEXE) $(BIN_BUNDLEDIR)/$(JRE_NAME)-$(BIN_BUNDLE_NAMEPART)-$(BUNDLE_DATE)-jc.xpi \
	jinstall.exe install.js
	$(RM) -f $(TEMPDIR)/regular/install.js $(TEMPDIR)/regular/jinstall.exe
  endif
.PHONY: all clean xpi xpi_jc
