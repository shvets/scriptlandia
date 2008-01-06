#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
#
#
# Most frequently used targets:
#
#    all            -- builds
#    clobber        -- clobber the builds
#

INSTALL_BUILDDIR=../../..

include $(INSTALL_BUILDDIR)/common/Defs.gmk
include $(INSTALL_BUILDDIR)/common/Pack.gmk

ifeq ("$(PLATFORM)-$(ARCH_FAMILY)", "linux-sparc")
# disable rpm generation for linux-sparc.
# rpmbuild required and build platform is Debian
  JRE_RPM =
  JDK_RPM =
else
  JRE_RPM = j2re-rpm
  JDK_RPM = j2sdk-rpm
endif

all: sanity-bundles post-sanity-bundles bundle-jre $(JRE_RPM) bundle-jdk $(JDK_RPM) bundle-jdk-debug

clean::  bundle-jdk-debug-clobber bundle-jdk-clobber j2sdk-rpm-clobber bundle-jre-clobber j2re-rpm-clobber

j2re-rpm:
	@#
	@# Generate self extracting rpm file ...
	@#
	$(RM) -Rf $(JRE_RPM_TEMPDIR)
	@# 
	@# Create the file structure RPM expects
	@#
	$(MKDIR) -p $(JRE_RPM_TEMPDIR)/BUILD
	$(MKDIR) -p $(JRE_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH)
	$(MKDIR) -p $(JRE_RPM_TEMPDIR)/SOURCES
	$(MKDIR) -p $(JRE_RPM_TEMPDIR)/SPECS
	$(MKDIR) -p $(JRE_RPM_TEMPDIR)/SRPMS
	$(MKDIR) -p $(JRE_RPM_TEMPDIR)/tmp
	@#
	@# on RH8 rpm builds as user 'rpm'
	@#
	$(CHMOD) -R 777 $(JRE_RPM_TEMPDIR)
	@#
	@# rpm removes the input tar file built by the 'binaries' target, which 
	@# we want to keep, so we need to make a copy that rpm can devour...
	@#
	$(CD) $(OUTPUTDIR) ; $(TAR) -cf $(JRE_RPM_TEMPDIR)/SOURCES/$(JRE_RPM_TARFILE) $(JRE_TEMPORARY_DIR)
	@#
	@# Substitue the user's .rpmmacro file with one that uses a build structure
	@# relative to $(ABS_OUTPUTDIR).
	@#
	if [ -f $(RPM_MACROFILE) ]; then				\
	    ( $(MV) -f $(RPM_MACROFILE) $(RPM_MACROFILE).jre.bak );	\
	fi
	$(ECHO) "%_topdir	$(JRE_RPM_TEMPDIR)"  > $(RPM_MACROFILE)
	$(ECHO) "%_builddir	%{_topdir}/BUILD"   >> $(RPM_MACROFILE)
	$(ECHO) "%_rpmdir	%{_topdir}/RPMS"    >> $(RPM_MACROFILE)
	$(ECHO) "%_sourcedir	%{_topdir}/SOURCES" >> $(RPM_MACROFILE)
	$(ECHO) "%_specdir	%{_topdir}/SPECS"   >> $(RPM_MACROFILE)
	$(ECHO) "%_srcrpmdir	%{_topdir}/SRPMS"   >> $(RPM_MACROFILE)
	$(ECHO) "%_tmppath	%{_topdir}/tmp"     >> $(RPM_MACROFILE)
	$(ECHO) "%_rpmfilename	%{ARCH}/%{NAME}-%{VERSION}-%{RELEASE}.%{ARCH}.rpm"	>> $(RPM_MACROFILE)
	@#
	@# copy script sources used in the spec file
	@#
	$(CP) unpack_jars $(JRE_RPM_TEMPDIR)/SOURCES
	$(CP) parse_release_info.part1 $(JRE_RPM_TEMPDIR)/SOURCES
	$(CP) parse_release_info.part2 $(JRE_RPM_TEMPDIR)/SOURCES
	$(CP) link_support $(JRE_RPM_TEMPDIR)/SOURCES
	$(CP) legacy_mime_support $(JRE_RPM_TEMPDIR)/SOURCES
	$(CP) gnome_support $(JRE_RPM_TEMPDIR)/SOURCES
	$(CP) jexec $(JRE_RPM_TEMPDIR)/SOURCES
	@#
	@# generate the jre.description file
	@#
	$(SED) -e 's#__JRE_ACRONYM__#$(JRE_ACRONYM)#g'			\
	       -e 's#__JRE_SHORT_NAME__#$(JRE_SHORT_NAME)#g'		\
	       -e 's#__JRE_LONG_NAME__#$(JRE_LONG_NAME)#g'		\
	       -e 's#__JRE_OFFICIAL_NAME__#$(JRE_OFFICIAL_NAME)#g'	\
	       -e 's#__JDK_ACRONYM__#$(JDK_ACRONYM)#g'			\
	       -e 's#__JDK_SHORT_NAME__#$(JDK_SHORT_NAME)#g'		\
	       -e 's#__JDK_LONG_NAME__#$(JDK_LONG_NAME)#g'		\
	       -e 's#__JDK_OFFICIAL_NAME__#$(JDK_OFFICIAL_NAME)#g'	\
	       jre.description >  $(JRE_RPM_TEMPDIR)/SOURCES/jre.description
	$(CHMOD) u+x $(JRE_RPM_TEMPDIR)/SOURCES/jre.description
	@#
	@# generate the jre-rpm.spec file
	@#
	$(SED) -e 's#TARGET_OS#Linux#g'					\
	       -e 's#TARGET_ARCH#$(NATIVE_ARCH)#g'			\
	       -e 's#USE_EPOCH_IF_JDK#0#g'				\
	       -e 's#MAJOR_VERSION#$(JDK_MAJOR_VERSION)#g'		\
	       -e 's#MINOR_VERSION#$(JDK_MINOR_VERSION)#g'		\
	       -e 's#MICRO_VERSION#$(JDK_MICRO_VERSION)#g'		\
	       -e 's#UPDATE_VERSION#$(RPM_UPDATE_VERSION)#g'		\
	       -e 's#MILESTONE#$(MILESTONE)#g'				\
	       -e 's#BUILD_NUMBER#$(BUILD_NUMBER)#g'			\
	       -e 's#USE_UPDATE_IF_DEFINED#$(RPM_USE_UPDATE)#g'		\
	       -e 's#RPM_PKG_NAME#$(JRE_NAME)#g'			\
	       -e 's#JRE_PKG_NAME#$(JRE_NAME)#g'			\
	       -e 's#JDK_PKG_NAME#$(JDK_NAME)#g'			\
	       -e 's#OFFICIAL_PRODUCT_NAME#$(JRE_OFFICIAL_NAME)#g'	\
	       -e 's#CLASSES_JSA#%{jre_classes_jsa}#g'			\
	       -e 's#SOURCE_TARFILE#$(JRE_RPM_TARFILE)#g'		\
	       -e 's#PACKED_JARS#$(JRE_PACKED_JARS)#g'			\
	       -e 's#PACKAGE_BIN_LINKS#%{bin_default_jre_links}#g'	\
	       -e 's#PACKAGE_DOCS#%{jre_docs}#g'			\
	       -e 's#PACKAGE_DESCRIPTION#jre.description#g'		\
	       -e 's#JAVAWS_BIN#$(JRE_JAVAWS)#g'			\
	       -e 's#USE_THIS_BUILDROOT#$(JRE_RPM_BUILD_ROOT)#g'	\
	    generic-rpm.spec > $(JRE_RPM_TEMPDIR)/SPECS/jre-rpm.spec
	@# 
	@# generate the rpm file...
	@#
	$(RPM) -bb --target $(NATIVE_ARCH)-Sun-Linux $(JRE_RPM_TEMPDIR)/SPECS/jre-rpm.spec
	@#
	@# rename the generated .rpm file to the name we want
	@#
	@#
	-$(MV) $(JRE_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH)/$(TEMP_JRE_RPMFILE_NAME)	\
	       $(JRE_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH)/$(JRE_SHORT_BUNDLE_NAME).rpm
	@#
	@# zip it up for inclusion in self-extracting zip...
	@#
	$(CD) $(JRE_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH) ;				\
		$(ZIPEXE) -qryn nothing temp.rpm $(JRE_SHORT_BUNDLE_NAME).rpm
	@#
	@# combine the self-extractor and .rpm.zip into a single binary...
	@#
	$(CAT) $(UNZIPSFX) $(JRE_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH)/temp.rpm > $(JRE_RPM_TEMPDIR)/tmp/temp.bin
	$(SFX2SH) $(JRE_RPM_TEMPDIR)/tmp/temp.bin				\
		  $(JRE_IMAGE_DIR)/LICENSE				\
		  $(JRE_RPM_TEMPDIR)/tmp/$(JRE_SHORT_BUNDLE_NAME)-rpm.sh	\
		  $(OUTPUTDIR)/$(JRE_TEMPORARY_DIR)			\
		  ""							\
		  $(PLATFORM)						\
		  $(ARCH_DATA_MODEL)					\
		  "RPM"
	$(MV) $(JRE_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH)/$(JRE_SHORT_BUNDLE_NAME).rpm $(BIN_BUNDLEDIR)/$(JRE_BUNDLE_NAME).rpm
	$(MV) $(JRE_RPM_TEMPDIR)/tmp/$(JRE_SHORT_BUNDLE_NAME)-rpm.sh $(BIN_BUNDLEDIR)/$(JRE_BUNDLE_NAME)-rpm.sh
	@#
	@# Delete the .rpmmacro made for this build, and restore the user's if
	@# one exists.
	@#
	$(RM) -f $(RPM_MACROFILE)
	if [ -f $(RPM_MACROFILE).jre.bak ]; then			\
	    ( $(MV) -f $(RPM_MACROFILE).jre.bak $(RPM_MACROFILE) );	\
	fi

j2sdk-rpm:
	@# 
	@# Generate the self-extracting rpm file...
	@#
	$(RM) -Rf $(JDK_RPM_TEMPDIR)
	@# 
	@# Create the file structure RPM expects
	@#
	$(MKDIR) -p $(JDK_RPM_TEMPDIR)/BUILD
	$(MKDIR) -p $(JDK_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH)
	$(MKDIR) -p $(JDK_RPM_TEMPDIR)/SOURCES
	$(MKDIR) -p $(JDK_RPM_TEMPDIR)/SPECS
	$(MKDIR) -p $(JDK_RPM_TEMPDIR)/SRPMS
	$(MKDIR) -p $(JDK_RPM_TEMPDIR)/tmp
	@#
	@# on RH8 rpm builds as user 'rpm'
	@#
	$(CHMOD) -R 777 $(JDK_RPM_TEMPDIR)
	@#
	@# rpm removes the input tar file built by the 'binaries' target, which 
	@# we want to keep, so we need to make a copy that rpm can devour...
	@#
	$(CD) $(OUTPUTDIR) ; $(TAR) -cf $(JDK_RPM_TEMPDIR)/SOURCES/$(JDK_RPM_TARFILE) $(JDK_TEMPORARY_DIR)
	@#
	@# Substitue the user's .rpmmacro file with one that uses a build structure
	@# relative to $(ABS_OUTPUTDIR).
	@#
	if [ -f $(RPM_MACROFILE) ]; then				\
	    ( $(MV) -f $(RPM_MACROFILE) $(RPM_MACROFILE).jdk.bak );	\
	fi
	$(ECHO) "%_topdir	$(JDK_RPM_TEMPDIR)"  > $(RPM_MACROFILE)
	$(ECHO) "%_builddir	%{_topdir}/BUILD"   >> $(RPM_MACROFILE)
	$(ECHO) "%_rpmdir	%{_topdir}/RPMS"    >> $(RPM_MACROFILE)
	$(ECHO) "%_sourcedir	%{_topdir}/SOURCES" >> $(RPM_MACROFILE)
	$(ECHO) "%_specdir	%{_topdir}/SPECS"   >> $(RPM_MACROFILE)
	$(ECHO) "%_srcrpmdir	%{_topdir}/SRPMS"   >> $(RPM_MACROFILE)
	$(ECHO) "%_tmppath	%{_topdir}/tmp"     >> $(RPM_MACROFILE)
	$(ECHO) "%_rpmfilename	%{ARCH}/%{NAME}-%{VERSION}-%{RELEASE}.%{ARCH}.rpm"	>> $(RPM_MACROFILE)
	@#
	@# copy script sources used in the spec file
	@#
	$(CP) unpack_jars $(JDK_RPM_TEMPDIR)/SOURCES
	$(CP) parse_release_info.part1 $(JDK_RPM_TEMPDIR)/SOURCES
	$(CP) parse_release_info.part2 $(JDK_RPM_TEMPDIR)/SOURCES
	$(CP) link_support $(JDK_RPM_TEMPDIR)/SOURCES
	$(CP) legacy_mime_support $(JDK_RPM_TEMPDIR)/SOURCES
	$(CP) gnome_support $(JDK_RPM_TEMPDIR)/SOURCES
	$(CP) jexec $(JDK_RPM_TEMPDIR)/SOURCES
	@#
	@# generate the jdk.description file
	@#
	$(SED) -e 's#__JRE_ACRONYM__#$(JRE_ACRONYM)#g'			\
	       -e 's#__JRE_SHORT_NAME__#$(JRE_SHORT_NAME)#g'		\
	       -e 's#__JRE_LONG_NAME__#$(JRE_LONG_NAME)#g'		\
	       -e 's#__JRE_OFFICIAL_NAME__#$(JRE_OFFICIAL_NAME)#g'	\
	       -e 's#__JDK_ACRONYM__#$(JDK_ACRONYM)#g'			\
	       -e 's#__JDK_SHORT_NAME__#$(JDK_SHORT_NAME)#g'		\
	       -e 's#__JDK_LONG_NAME__#$(JDK_LONG_NAME)#g'		\
	       -e 's#__JDK_OFFICIAL_NAME__#$(JDK_OFFICIAL_NAME)#g'	\
	       jdk.description >  $(JDK_RPM_TEMPDIR)/SOURCES/jdk.description
	$(CHMOD) u+x $(JDK_RPM_TEMPDIR)/SOURCES/jdk.description
	@#
	@# generate the jdk-rpm.spec file
	@#
	$(SED) -e 's#TARGET_OS#Linux#g'					\
	       -e 's#TARGET_ARCH#$(NATIVE_ARCH)#g'			\
	       -e 's#USE_EPOCH_IF_JDK#1#g'				\
	       -e 's#MAJOR_VERSION#$(JDK_MAJOR_VERSION)#g'		\
	       -e 's#MINOR_VERSION#$(JDK_MINOR_VERSION)#g'		\
	       -e 's#MICRO_VERSION#$(JDK_MICRO_VERSION)#g'		\
	       -e 's#UPDATE_VERSION#$(RPM_UPDATE_VERSION)#g'		\
	       -e 's#MILESTONE#$(MILESTONE)#g'				\
	       -e 's#BUILD_NUMBER#$(BUILD_NUMBER)#g'			\
	       -e 's#USE_UPDATE_IF_DEFINED#$(RPM_USE_UPDATE)#g'		\
	       -e 's#RPM_PKG_NAME#$(JDK_NAME)#g'			\
	       -e 's#JRE_PKG_NAME#$(JRE_NAME)#g'			\
	       -e 's#JDK_PKG_NAME#$(JDK_NAME)#g'			\
	       -e 's#OFFICIAL_PRODUCT_NAME#$(JDK_OFFICIAL_NAME)#g'	\
	       -e 's#CLASSES_JSA#%{jdk_classes_jsa}#g'			\
	       -e 's#SOURCE_TARFILE#$(JDK_RPM_TARFILE)#g'		\
	       -e 's#PACKED_JARS#$(JDK_PACKED_JARS)#g'			\
	       -e 's#PACKAGE_BIN_LINKS#%{bin_default_jdk_links}#g'	\
	       -e 's#PACKAGE_DOCS#%{jre_docs}|%{jdk_docs}#g'		\
	       -e 's#PACKAGE_DESCRIPTION#jdk.description#g'		\
	       -e 's#JAVAWS_BIN#$(JDK_JAVAWS)#g'			\
	       -e 's#USE_THIS_BUILDROOT#$(JDK_RPM_BUILD_ROOT)#g'	\
	    generic-rpm.spec > $(JDK_RPM_TEMPDIR)/SPECS/jdk-rpm.spec
	@# 
	@# generate the rpm file...
	@#
	$(RPM) -bb --target $(NATIVE_ARCH)-Sun-Linux $(JDK_RPM_TEMPDIR)/SPECS/jdk-rpm.spec
	@#
	@# rename the generated .rpm file to the name we want
	@#
	@#
	-$(MV) $(JDK_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH)/$(TEMP_JDK_RPMFILE_NAME)	\
	       $(JDK_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH)/$(JDK_SHORT_BUNDLE_NAME).rpm
	@#
	@# zip it up for inclusion in self-extracting zip...
	@#
	$(CD) $(JDK_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH) ;				\
		$(ZIPEXE) -qryn nothing temp.rpm $(JDK_SHORT_BUNDLE_NAME).rpm
	@#
	@# combine the self-extractor and .rpm.zip into a single binary...
	@#
	$(CAT) $(UNZIPSFX) $(JDK_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH)/temp.rpm > $(JDK_RPM_TEMPDIR)/tmp/temp.bin
	$(SFX2SH) $(JDK_RPM_TEMPDIR)/tmp/temp.bin				\
		  $(JDK_IMAGE_DIR)/LICENSE				\
		  $(JDK_RPM_TEMPDIR)/tmp/$(JDK_SHORT_BUNDLE_NAME)-rpm.sh	\
		  $(OUTPUTDIR)/$(JDK_TEMPORARY_DIR)			\
		  ""							\
		  $(PLATFORM)						\
		  $(ARCH_DATA_MODEL)					\
		  "RPM"
	$(MV) $(JDK_RPM_TEMPDIR)/RPMS/$(NATIVE_ARCH)/$(JDK_SHORT_BUNDLE_NAME).rpm $(BIN_BUNDLEDIR)/$(JDK_BUNDLE_NAME).rpm
	$(MV) $(JDK_RPM_TEMPDIR)/tmp/$(JDK_SHORT_BUNDLE_NAME)-rpm.sh $(BIN_BUNDLEDIR)/$(JDK_BUNDLE_NAME)-rpm.sh
	@#
	@# Delete the .rpmmacro made for this build, and restore the user's if
	@# one exists.
	@#
	$(RM) -f $(RPM_MACROFILE)
	if [ -f $(RPM_MACROFILE).jdk.bak ]; then			\
	    ( $(MV) -f $(RPM_MACROFILE).jdk.bak $(RPM_MACROFILE) );	\
	fi

j2re-rpm-clobber:
	$(RM) -Rf $(JRE_RPM_TEMPDIR)
	$(RM) $(BIN_BUNDLEDIR)/$(JRE_BUNDLE_NAME).rpm
	$(RM) $(BIN_BUNDLEDIR)/$(JRE_BUNDLE_NAME)-rpm.sh

j2sdk-rpm-clobber:
	$(RM) -Rf $(JDK_RPM_TEMPDIR)
	$(RM) $(BIN_BUNDLEDIR)/$(JDK_BUNDLE_NAME).rpm
	$(RM) $(BIN_BUNDLEDIR)/$(JDK_BUNDLE_NAME)-rpm.sh

$(BIN_BUNDLEDIR):
	$(MKDIR) -p $@

#
# Eventually these name macros should be defined in a more central location,
#
JRE_ACRONYM       = JRE
JRE_SHORT_NAME    = Java Runtime Environment
JRE_LONG_NAME     = Java Platform Standard Edition Runtime Environment
JRE_OFFICIAL_NAME = Java(TM) Platform Standard Edition Runtime Environment

JDK_ACRONYM  	  = JDK
JDK_SHORT_NAME	  = Java Development Kit
JDK_LONG_NAME	  = Java Platform Standard Edition Development Kit
JDK_OFFICIAL_NAME = Java(TM) Platform Standard Edition Development Kit

RPM_MACROFILE = $(HOME)/.rpmmacros

JDK_RPM_TEMPDIR = $(TEMP_DIR)/jdk-rpm
JDK_RPM_BUILD_ROOT = $(JDK_RPM_TEMPDIR)/tmp/root/$(JDK_TEMPORARY_DIR)

JRE_RPM_TEMPDIR = $(TEMP_DIR)/jre-rpm
JRE_RPM_BUILD_ROOT = $(JRE_RPM_TEMPDIR)/tmp/root/$(JRE_TEMPORARY_DIR)

RPM_UPDATE_VERSION = 00
RPM_USE_UPDATE =
ifdef JDK_UPDATE_VERSION
    RPM_UPDATE_VERSION = $(JDK_UPDATE_VERSION)
    RPM_USE_UPDATE = %define use_update    yes
endif

include $(INSTALL_BUILDDIR)/common/Sanity.gmk
include $(INSTALL_BUILDDIR)/common/Bundles.gmk

.PHONY: all clean bundle-jre j2re-rpm bundle-jdk j2sdk-rpm bundle-jdk-debug \
	bundle-jdk-debug-clobber bundle-jdk-clobber j2sdk-rpm-clobber \
	bundle-jre-clobber j2re-rpm-clobber
