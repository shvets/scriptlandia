#
# Copyright 1998-2007 Sun Microsystems, Inc.  All rights reserved.
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

BUILDDIR  = ../../..
PACKAGE   = javax.swing.plaf
PRODUCT   = com
SWING_SRC = $(SHARE_SRC)/classes/javax/swing
include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
include FILES.gmk
AUTO_FILES_JAVA_DIRS = javax/swing/plaf sun/swing com/sun/java/swing/plaf
ifeq ($(PLATFORM), windows)
    # Don't build GTK L&F on Windows
    AUTO_JAVA_PRUNE = gtk
endif

MISC_FILES = $(MISC_SWING_FILES)
ifneq ($(PLATFORM), windows)
    # Only include GTK icons on Solaris/Linux
    MISC_FILES += $(MISC_SWING_FILES_UNIX)
endif

FILES_java = $(FILES_SWING_java)

#
# Resources
#
LOCALE_SET_DEFINITION = jre
RESOURCE_BUNDLES_COMPILED_PROPERTIES = \
	com/sun/swing/internal/plaf/basic/resources/basic.properties \
	com/sun/swing/internal/plaf/metal/resources/metal.properties \
	com/sun/swing/internal/plaf/synth/resources/synth.properties \
	com/sun/java/swing/plaf/motif/resources/motif.properties \
        com/sun/java/swing/plaf/windows/resources/windows.properties
ifneq ($(PLATFORM), windows)
    # Only compile GTK resource bundles on Solaris/Linux
    RESOURCE_BUNDLES_COMPILED_PROPERTIES += \
        com/sun/java/swing/plaf/gtk/resources/gtk.properties
endif

#
# Rules.
# Process LOGO_ICONS and Motif Icons first.
#
build: $(LOGO_ICONS) $(MISC_SWING_FILES_MOTIF_GIF) $(MISC_SWING_FILES_MOTIF_PNG) other_files

clean:: classes.clean

#
# Include
#
include $(BUILDDIR)/common/Classes.gmk

ifndef OPENJDK
$(LOGO_ICONS): $(CLASSBINDIR)/%.png: $(CLOSED_SRC)/share/classes/%.png
	$(install-file)

$(MISC_SWING_FILES_MOTIF_GIF): $(CLASSBINDIR)/%.gif: $(CLOSED_SRC)/share/classes/%.gif
	$(install-file)

$(MISC_SWING_FILES_MOTIF_PNG): $(CLASSBINDIR)/%.png: $(CLOSED_SRC)/share/classes/%.png
	$(install-file)
endif	

other_files: $(MISC_FILES)

$(CLASSBINDIR)/%.wav: $(SHARE_SRC)/classes/%.wav
	$(install-file)

$(CLASSBINDIR)/%.gif: $(SHARE_SRC)/classes/%.gif
	$(install-file)

$(CLASSBINDIR)/%.png: $(SHARE_SRC)/classes/%.png
	$(install-file)

$(CLASSBINDIR)/%.xml: $(SHARE_SRC)/classes/%.xml
	$(install-file)

$(CLASSBINDIR)/%.css: $(SHARE_SRC)/classes/%.css
	$(install-file)

$(CLASSBINDIR)/%.bdtd: $(SHARE_SRC)/classes/%.bdtd
	$(install-file)

$(CLASSBINDIR)/%.txt: $(SHARE_SRC)/classes/%.txt
	$(install-file)

