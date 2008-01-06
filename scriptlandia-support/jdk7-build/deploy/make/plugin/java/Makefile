#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
# Makefile to build the Java Plug-in classes, native headers, and JAR file.
#

DEPLOY_TOPDIR = ../../..

include $(DEPLOY_TOPDIR)/make/plugin/common/Defs.gmk

include ./FileList.gmk

# for DEPLOYCLASSDESTDIR
include ../../common/DeployRules.gmk

VPATH.java = $(PLUGIN_PLATFORM_SRC)/classes$(CLASSPATH_SEPARATOR)$(JAVASRCDIR)
JAVAC      = $(BINDIR)/javac
JAR        = $(BINDIR)/jar

LANGUAGE_VERSION = -source 1.4
JAVACFLAGS += -deprecation
JAVACFLAGS += -classpath "$(CLASSBINDIR)$(CLASSPATH_SEPARATOR)$(CLASSDIR)$(CLASSPATH_SEPARATOR)$(DEPLOYCLASSDESTDIR)"

CLASSDESTDIR = $(CLASSDIR)

$(OBJDIR)/.class.headers.$(ARCH): CLASSDESTDIR := "$(CLASSBINDIR)$(CLASSPATH_SEPARATOR)$(CLASSDIR)$(CLASSPATH_SEPARATOR)$(DEPLOYCLASSDESTDIR)"

all: build

include $(JDK_MAKE_SHARED_DIR)/../Rules.gmk

build: $(PLUGIN_JARFILE)

$(CLASSDESTDIR)/%.class: $(PLUGIN_PLATFORM_SRC)/classes/%.java
	@$(RM) $@
	@$(ECHO) $? >> $(TEMPDIR)/.classes.list

$(CLASSDESTDIR)/%.class: $(JAVASRCDIR)/%.java
	@$(RM) $@
	@$(ECHO) $? >> $(TEMPDIR)/.classes.list

# Redirect zh_HK java files to tmp directory which created from zh_TW
$(CLASSDESTDIR)/%_zh_HK.class: $(DEPLOY_L10N_TMP_OUTPUTDIR)/%_zh_HK.java
	@$(RM) $@
	@$(ECHO) $< >> $(TEMPDIR)/.classes.list

$(PLUGIN_JARFILE): classes
	@if [ -s $(TEMPDIR)/.classes.list ] ; \
        then \
		$(ECHO) Rebuilding: $(PLUGIN_JARFILE) ;\
		$(CP) -f $(JAVASRCDIR)/sun/plugin/util/*.gif $(CLASSDIR)/sun/plugin/util; \
		$(CP) -f $(JAVASRCDIR)/sun/plugin/util/*.png $(CLASSDIR)/sun/plugin/util; \
		$(MKDIR) -p $(dir $(PLUGIN_JARFILE)); \
		cd $(CLASSDIR); $(JAR) cf0 $(PLUGIN_JARFILE) *; \
        fi

clean:: clobber

clobber:: 
	$(RM) -r $(CLASSDIR)
	$(RM) $(PLUGIN_JARFILE)

#deal zh_HK resource
include $(DEPLOY_TOPDIR)/make/common/Resources.gmk
