#
# Copyright 2004-2006 Sun Microsystems, Inc.  All rights reserved.
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

#
# Makefile for building jconsole
#

BUILDDIR = ../..
PACKAGE = sun.tools.jconsole
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

JARFILE = $(LIBDIR)/jconsole.jar

OTHER_JAVACFLAGS += -encoding iso8859-1

#
# Files
#
include FILES.gmk

AUTO_FILES_JAVA_DIRS =  sun/tools/jconsole \
			com/sun/tools/jconsole

FILES_png = $(PNG_FILES:%.png=$(CLASSBINDIR)/%.png)
FILES_gif = $(GIF_FILES:%.gif=$(CLASSBINDIR)/%.gif)

build: $(GENSRCDIR)/sun/tools/jconsole/Version.java

#
# Resources
#
LOCALE_SET_DEFINITION = jdk
RESOURCE_BUNDLES_JAVA = $(PKGDIR)/resources/JConsoleResources.java

#
# Rules.
#
include $(BUILDDIR)/common/Classes.gmk

build: $(FILES_png) $(FILES_gif) $(TEMPDIR)/manifest $(JARFILE)

$(GENSRCDIR)/sun/tools/jconsole/Version.java: \
                $(SHARE_SRC)/classes/sun/tools/jconsole/Version-template.java
	$(MKDIR) -p $(@D)
	$(SED) -e 's/@@jconsole_version@@/$(FULL_VERSION)/g' $< > $@

$(CLASSBINDIR)/$(PKGDIR)/resources/%: $(SHARE_SRC)/classes/$(PKGDIR)/resources/%
	@$(MKDIR) -p $(@D)
	$(install-file)

$(TEMPDIR)/manifest: $(SHARE_SRC)/classes/$(PKGDIR)/manifest
	$(install-file)

#
# Extra rule to build jconsole.jar
#

$(JARFILE): $(LIBDIR) $(FILES_class) $(FILES_png) $(FILES_gif) $(TEMPDIR)/manifest
	$(BOOT_JAR_CMD) -cfm $(JARFILE) $(TEMPDIR)/manifest \
               -C $(CLASSBINDIR) sun/tools/jconsole \
               -C $(CLASSBINDIR) com/sun/tools/jconsole \
		    $(JAR_JFLAGS)
	@$(java-vm-cleanup)

clean clobber::
	$(RM) $(TEMPDIR)/manifest $(JARFILE)

