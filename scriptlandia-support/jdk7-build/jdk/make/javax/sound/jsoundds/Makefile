#
# Copyright 2003-2007 Sun Microsystems, Inc.  All rights reserved.
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
LIBRARY = jsoundds
PRODUCT = sun
CPLUSPLUSLIBRARY = true
include $(BUILDDIR)/common/Defs.gmk

# this Makefile compiles the native files for DirectSound support in java sound

# include defines for sound
include ../SoundDefs.gmk

#
# Files
#

FILES_c = \
	Utilities.c \
	$(DAUDIOFILES_c)

FILES_cpp = \
	PLATFORM_API_WinOS_DirectSound.cpp

FILES_export = \
	$(DAUDIOFILES_export)

#
# Extra cc/linker flags.
#
LDLIBS += dsound.lib winmm.lib user32.lib
CPPFLAGS += \
	-DUSE_DAUDIO=TRUE \
	-I$(SHARE_SRC)/native/com/sun/media/sound \
	-I$(DXSDK_INCLUDE_PATH)

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
