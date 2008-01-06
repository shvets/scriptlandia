#
# Copyright 2001-2006 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for locale data.
#

BUILDDIR = ../..

PACKAGE = sun.text
PRODUCT = sun

include $(BUILDDIR)/common/Defs.gmk

# Re-direct classes to another location (we are building localedata.jar)
CLASSDESTDIR = $(TEMPDIR)/classes

#
# Files
#
include FILES_java.gmk
include FILES_properties.gmk

#
# Compile the properties files
#
COMPILED_PROPERTIES_SUPERCLASS=LocaleNamesBundle

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

#
# Rules to add data files BreakIterator (for th locale)
#
TEXT_CLASSES = $(BUILDTOOLCLASSDIR)/sun.text/classes

# input
#
#   Notes: sun.text.resources.BreakIteratorRules_th no longer goes to runtime.
#     They are used at JDK build phase in order to create $(BIFILES) which
#     are used on runtime instead.
#
TEXT_SRCDIR = $(SHARE_SRC)/classes/sun/text/resources
BIRULES = $(TEXT_SRCDIR)/BreakIteratorRules.java \
          $(TEXT_SRCDIR)/BreakIteratorRules_th.java
BIINFO = $(TEXT_SRCDIR)/BreakIteratorInfo_th.java
UNICODEDATA = $(BUILDDIR)/tools/UnicodeData

# output
BIFILES = $(CLASSDESTDIR)/sun/text/resources/WordBreakIteratorData_th \
          $(CLASSDESTDIR)/sun/text/resources/LineBreakIteratorData_th

# builder
GENERATEBREAKITERATORDATA_JARFILE = \
    $(BUILDTOOLJARDIR)/generatebreakiteratordata.jar

$(BIFILES): $(GENERATEBREAKITERATORDATA_JARFILE) $(BIRULES) $(BIINFO) \
    $(UNICODEDATA)/UnicodeData.txt
	$(prep-target)
	$(MKDIR) -p $(TEXT_CLASSES)
	$(BOOT_JAVAC_CMD) -d $(TEXT_CLASSES) \
		-sourcepath $(TEXT_SRCDIR) \
		$(BIRULES) $(BIINFO)
	$(BOOT_JAVA_CMD) -Xbootclasspath/p:$(TEXT_CLASSES) \
		-jar $(GENERATEBREAKITERATORDATA_JARFILE) \
		-o $(CLASSDESTDIR)/sun/text/resources \
		-spec $(UNICODEDATA)/UnicodeData.txt \
		-language th
	@$(java-vm-cleanup)

clean::
	$(RM) -r $(TEXT_CLASSES)
	$(RM) -r $(BIFILES)
#
# End of rules to add data files for BreakIterator
#

#
# Extra rules to build locale data.
#
LOCALEDATA_JAR = $(EXTDIR)/localedata.jar

SPECIALFILES = $(CLASSDESTDIR)/sun/text/resources/thai_dict

$(CLASSDESTDIR)/sun/text/resources/% : $(TEXT_SRCDIR)/%
	$(install-file)

$(LOCALEDATA_JAR): $(EXTDIR) $(FILES_class) $(BIFILES) $(SPECIALFILES)
	$(prep-target)
	$(BOOT_JAR_CMD) -cf $@ -C $(CLASSDESTDIR) sun \
	     $(JAR_JFLAGS)
	@$(java-vm-cleanup)

build: $(LOCALEDATA_JAR)

clean clobber::
	$(RM) -r $(CLASSDESTDIR) 
	$(RM) $(LOCALEDATA_JAR)

