#
# Copyright 2007 Sun Microsystems, Inc.  All rights reserved.
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
LIBRARY = jsoundhs
PRODUCT = sun
CPLUSPLUSLIBRARY = true
include $(BUILDDIR)/common/Defs.gmk

# this Makefile compiles "closed" JavaSound library

ifdef OPENJDK

# precompiled lib will be copied by the rules in Library.gmk instead of compiling.
USE_BINARY_PLUG_LIBRARY=true

build: import-binary-plug-jsound-library

include $(BUILDDIR)/common/internal/BinaryPlugs.gmk

else # OPENJDK

# include defines for sound
include ../SoundDefs.gmk

#
# Add use of mapfile
#
FILES_m = mapfile-vers
include $(BUILDDIR)/common/Mapfile-vers.gmk

#
# Files
#
include FILES.gmk

FILES_c += $(FILES_engine) $(FILES_$(PLATFORM))

#
# Extra cc/linker flags.
#
# flags needed for all platforms
CPPFLAGS += \
	-DJAVA_SOUND -DJAVA_THREAD \
	-I$(CLOSED_SHARE_SRC)/native/com/sun/media/sound \
	-I$(CLOSED_SHARE_SRC)/native/com/sun/media/sound/engine

# system dependent flags
ifeq ($(PLATFORM), windows)
  CPPFLAGS += 	-DUSE_DIRECTSOUND=0 \
		-DUSE_EXTERNAL_SYNTH=TRUE
  LDLIBS += winmm.lib
endif # PLATFORM windows

ifeq ($(PLATFORM), linux)
endif # PLATFORM linux

ifeq ($(PLATFORM), solaris)
endif # PLATFORM solaris


#
# Add to the ambient VPATH.
#
vpath %.c $(CLOSED_SHARE_SRC)/native/com/sun/media/sound
vpath %.c $(CLOSED_SHARE_SRC)/native/com/sun/media/sound/engine
vpath %.c $(CLOSED_PLATFORM_SRC)/native/com/sun/media/sound/engine


endif # OPENJDK


#
# Include rules
#
include $(BUILDDIR)/common/Library.gmk

