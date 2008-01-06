#
# Copyright 2005-2006 Sun Microsystems, Inc.  All rights reserved.
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
PACKAGE = sun.awt
LIBRARY = splashscreen
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
include FILES_c.gmk

FILES_java = \
    java/awt/SplashScreen.java

FILES_export = \
    java/awt/SplashScreen.java


#
# Rules
#
# FIXME!!!!
#ifeq ($(PLATFORM), solaris)
#  ifneq ($(ARCH), amd64)
#    FILES_reorder += reorder-$(ARCH)
#  endif
#endif
include $(BUILDDIR)/common/Mapfile-vers.gmk
include $(BUILDDIR)/common/Library.gmk

JAVALIB=

#
# C Flags
#

CFLAGS += -DSPLASHSCREEN

ifneq ($(PLATFORM), windows)
  CFLAGS += -DWITH_X11
  CPPFLAGS += -I$(OPENWIN_HOME)/include -I$(OPENWIN_HOME)/include/X11/extensions
  OTHER_LDLIBS += -L$(OPENWIN_LIB) -lX11 -lXext $(LIBM) -lpthread
else # PLATFORM
  CFLAGS += -DWITH_WIN32
  OTHER_LDLIBS += kernel32.lib user32.lib gdi32.lib 
#$(JVMLIB) $(OBJDIR)/../../jpeg/$(OBJDIRNAME)/jpeg$(SUFFIX).lib
endif # PLATFORM

#
# Add to ambient vpath to get files in a subdirectory
#
vpath %.c   $(SHARE_SRC)/native/$(PKGDIR)/splashscreen
vpath %.c   $(SHARE_SRC)/native/$(PKGDIR)
vpath %.c   $(SHARE_SRC)/native/$(PKGDIR)/giflib
vpath %.c   $(SHARE_SRC)/native/java/util/zip/zlib-1.1.3
vpath %.c   $(SHARE_SRC)/native/$(PKGDIR)/libpng
vpath %.c   $(SHARE_SRC)/native/$(PKGDIR)/image/jpeg
vpath %.c   $(PLATFORM_SRC)/native/$(PKGDIR)/splashscreen

CPPFLAGS += -I$(PLATFORM_SRC)/native/$(PKGDIR)/splashscreen -I$(SHARE_SRC)/native/$(PKGDIR)/splashscreen
CPPFLAGS += -I$(SHARE_SRC)/native/$(PKGDIR)/image/jpeg -I$(SHARE_SRC)/native/java/util/zip/zlib-1.1.3

