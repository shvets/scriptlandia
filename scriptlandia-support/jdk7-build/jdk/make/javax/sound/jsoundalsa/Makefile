#
# Copyright 2002-2007 Sun Microsystems, Inc.  All rights reserved.
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

BUILDDIR = ../../..
PACKAGE = javax.sound
LIBRARY = jsoundalsa
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

# this Makefile compiles the native files for alsa support in java sound

# include defines for sound
include ../SoundDefs.gmk

#
# Use mapfile
#
FILES_m = mapfile-vers
include $(BUILDDIR)/common/Mapfile-vers.gmk

#
# Files
#

FILES_c = \
	Utilities.c \
	$(DAUDIOFILES_c) \
	$(MIDIFILES_c) \
	$(PORTFILES_c)

# platform dependent files
FILES_c += \
	PLATFORM_API_LinuxOS_ALSA_CommonUtils.c   \
	PLATFORM_API_LinuxOS_ALSA_PCM.c     \
	PLATFORM_API_LinuxOS_ALSA_PCMUtils.c   \
	PLATFORM_API_LinuxOS_ALSA_MidiIn.c  \
	PLATFORM_API_LinuxOS_ALSA_MidiOut.c \
	PLATFORM_API_LinuxOS_ALSA_MidiUtils.c \
	PLATFORM_API_LinuxOS_ALSA_Ports.c

FILES_export = \
	$(DAUDIOFILES_export) \
	$(MIDIFILES_export) \
	$(PORTFILES_export)

LDFLAGS += -lasound

CPPFLAGS += \
	-DUSE_DAUDIO=TRUE \
	-DUSE_PORTS=TRUE  \
	-DUSE_PLATFORM_MIDI_OUT=TRUE \
	-DUSE_PLATFORM_MIDI_IN=TRUE \
	-I$(SHARE_SRC)/native/com/sun/media/sound

#
# Add to the ambient VPATH.
#
vpath %.c $(SHARE_SRC)/native/com/sun/media/sound
vpath %.c $(PLATFORM_SRC)/native/com/sun/media/sound
vpath %.cpp $(PLATFORM_SRC)/native/com/sun/media/sound


#
# Include rules
#
include $(BUILDDIR)/common/Library.gmk



