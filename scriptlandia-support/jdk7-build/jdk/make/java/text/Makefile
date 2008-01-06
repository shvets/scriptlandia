#
# Copyright 1996-2006 Sun Microsystems, Inc.  All rights reserved.
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

BUILDDIR = ../..

PACKAGE = java.text
PRODUCT = sun

include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
include FILES_java.gmk

#
# Include
#
include $(BUILDDIR)/common/Classes.gmk

#
# Rules to add data files BreakIterator
#
TEXT_CLASSES = $(BUILDTOOLCLASSDIR)/java.text/classes

# input
#
#   Notes: sun.text.resources.BreakIteratorRules no longer goes to runtime.
#     They are used at JDK build phase in order to create $(BIFILES) which
#     are used on runtime instead.
#
TEXT_SRCDIR  = $(SHARE_SRC)/classes/sun/text/resources
TEXT_SOURCES = $(TEXT_SRCDIR)/BreakIteratorRules.java \
               $(TEXT_SRCDIR)/BreakIteratorInfo.java
UNICODEDATA  = $(BUILDDIR)/tools/UnicodeData

# output
TEXT_CLASSDIR  = $(CLASSBINDIR)/sun/text/resources
BIFILES = $(TEXT_CLASSDIR)/CharacterBreakIteratorData \
          $(TEXT_CLASSDIR)/WordBreakIteratorData \
          $(TEXT_CLASSDIR)/LineBreakIteratorData \
          $(TEXT_CLASSDIR)/SentenceBreakIteratorData

ICU_FILES = $(TEXT_CLASSDIR)/unorm.icu \
            $(TEXT_CLASSDIR)/uprops.icu

# builder
GENERATEBREAKITERATORDATA_JARFILE = \
    $(BUILDTOOLJARDIR)/generatebreakiteratordata.jar

$(BIFILES): $(GENERATEBREAKITERATORDATA_JARFILE) \
    $(UNICODEDATA)/UnicodeData.txt \
    $(TEXT_SOURCES)
	$(prep-target)
	$(RM) -r $(TEXT_CLASSES)
	$(MKDIR) -p $(TEXT_CLASSES)
	$(BOOT_JAVAC_CMD) -d $(TEXT_CLASSES) \
		-sourcepath $(TEXT_SRCDIR) \
		$(TEXT_SOURCES)
	$(MKDIR) -p  $(TEXT_CLASSDIR)
	$(BOOT_JAVA_CMD) -Xbootclasspath/p:$(TEXT_CLASSES) \
		-jar $(GENERATEBREAKITERATORDATA_JARFILE) \
		-o $(TEXT_CLASSDIR) \
		-spec $(UNICODEDATA)/UnicodeData.txt
	@$(java-vm-cleanup)

build: $(BIFILES) $(ICU_FILES)

#
# Extra rules to copy unorm.icu and uprops.icu
#
$(TEXT_CLASSDIR)/unorm.icu: $(TEXT_SRCDIR)/unorm.icu
	$(install-file)

$(TEXT_CLASSDIR)/uprops.icu: $(TEXT_SRCDIR)/uprops.icu
	$(install-file)

clean clobber::
	$(RM) -r $(TEXT_CLASSES)
	$(RM) -r $(BIFILES)
	$(RM) $(ICU_FILES)

