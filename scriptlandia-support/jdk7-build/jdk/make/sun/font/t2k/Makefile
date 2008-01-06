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

# WARNING: Make sure the OPENJDK plugs are up-to-date, see make/common/internal/BinaryPlugs.gmk

#
# Makefile for building t2k rasteriser.
#

BUILDDIR = ../../..
PACKAGE = sun.font
LIBRARY = t2k
PRODUCT = sun

# Indicate we want the C++ compiler to do the linking.
CPLUSPLUSLIBRARY=true

# Part of the sun.font package, but doesn't rebuild classes (except
# for a few ones with native methods) so shouldn't clobber them.
DONT_CLOBBER_CLASSES=true

include $(BUILDDIR)/common/Defs.gmk

#
# Use higher optimization level
#
_OPT = $(CC_HIGHER_OPT)

#
# Files
#
include FILES_c.gmk

FILES_export = \
    java/awt/Font.java \
    sun/font/FileFont.java \
    sun/font/FontManager.java \
    sun/font/GlyphList.java \
    sun/font/NativeFont.java \
    sun/font/StrikeCache.java \
    sun/font/T2KFontScaler.java 

ifeq ($(PLATFORM), windows)

  # Files built here do not compile with warning level 3 if warnings are fatal
  COMPILER_WARNINGS_FATAL=false

  # t2k imports several shared methods from fontmanager.dll
  LDLIBS += user32.lib $(OBJDIR)/../../../sun.font/fontmanager/$(OBJDIRNAME)/fontmanager.lib

  OTHER_CFLAGS += -DCC_NOEX
  OTHER_CXXFLAGS += -DCC_NOEX

endif # PLATFORM

#
# In the OpenJDK mode we are not building anything
#
ifndef OPENJDK
include $(BUILDDIR)/common/Mapfile-vers.gmk
include $(BUILDDIR)/common/Library.gmk
endif 

#
# Add to the ambient vpath to pick up files in subdirectories
#
vpath %.c   $(SHARE_SRC)/share/native/$(PKGDIR)
vpath %.c   $(PLATFORM_SRC)/native/$(PKGDIR)
vpath %.c   $(CLOSED_SRC)/share/native/$(PKGDIR)
vpath %.c   $(CLOSED_SRC)/share/native/$(PKGDIR)/t2k
vpath %.c   $(CLOSED_SRC)/share/native/$(PKGDIR)/t2k/ttHints

vpath %.cpp   $(SHARE_SRC)/native/$(PKGDIR)
vpath %.cpp   $(CLOSED_SRC)/share/native/$(PKGDIR)

# Libraries to link, and other C flags.
#
# setup the list of libraries to link in...
ifneq ($(PLATFORM), windows)
  OTHER_LDLIBS  +=  $(LIBM) $(LIBCXX) -lfontmanager
  ifeq ($(PLATFORM), linux)
    ifeq ("$(CC_VER_MAJOR)", "3")
      OTHER_LDLIBS  += -Wl,-Bstatic -lgcc_eh -Wl,-Bdynamic
    endif                           
  else
    ifeq ($(PLATFORM), solaris)
      OTHER_LDLIBS += -lawt -L$(LIBDIR)/$(LIBARCH)/xawt -lmawt
    endif
  endif
endif


# set up compile flags..

CPPFLAGS += -I$(SHARE_SRC)/native/$(PKGDIR) \
            -I$(CLOSED_SRC)/share/native/$(PKGDIR)/t2k \
            -I$(CLOSED_SRC)/share/native/$(PKGDIR) \
            -I$(SHARE_SRC)/share/native/$(PKGDIR) \
            -I$(PLATFORM_SRC)/native/$(PKGDIR) \
            -I$(SHARE_SRC)/native/sun/java2d/loops \
            -I$(SHARE_SRC)/native/sun/java2d/pipe \
            -I$(PLATFORM_SRC)/native/sun/java2d \
            -I$(SHARE_SRC)/native/sun/java2d \
            -I$(TEMPDIR)/../fontmanager/CClassHeaders
