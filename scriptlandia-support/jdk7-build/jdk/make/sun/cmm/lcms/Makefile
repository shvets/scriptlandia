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
PACKAGE = sun.java2d.cmm.lcms
LIBRARY = lcms
PRODUCT = sun

include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
ifeq ($(PLATFORM), windows)
include FILES_c_windows.gmk
else # PLATFORM
include FILES_c_unix.gmk
endif # PLATFORM

FILES_java =                                            \
	$(TAGDIR)sun/java2d/cmm/lcms/LCMS.java              \
	$(TAGDIR)sun/java2d/cmm/lcms/LCMSImageLayout.java   \
	$(TAGDIR)sun/java2d/cmm/lcms/LCMSTransform.java

FILES_export = sun/java2d/cmm/lcms/LCMS.java

#
# Rules
#
include $(BUILDDIR)/common/Mapfile-vers.gmk
include $(BUILDDIR)/common/Library.gmk

SERVICEDIR = $(CLASSBINDIR)/META-INF/services

FILES_copy = \
	$(SERVICEDIR)/sun.java2d.cmm.PCMM

build: copy-files

copy-files: $(FILES_copy)

$(SERVICEDIR)/%: $(SHARE_SRC)/classes/sun/java2d/cmm/lcms/META-INF/services/%
	$(install-file)

#
# Extra rules
#

ifeq ($(PLATFORM), solaris)
CFLAGS := $(subst -xc99=%none,-xc99=no_lib,$(CFLAGS))
endif

clean clobber:: 
	$(RM) $(FILES_copy)

vpath %.c   $(SHARE_SRC)/native/$(PKGDIR)
vpath %.c   $(SHARE_SRC)/native/sun/java2d

ifeq ($(PLATFORM), windows)

OTHER_LDLIBS = user32.lib version.lib $(OBJDIR)/../../../sun.awt/awt/$(OBJDIRNAME)/awt.lib
OTHER_INCLUDES += -I$(SHARE_SRC)/native/sun/java2d \
                  -I$(SHARE_SRC)/native/sun/awt/debug

clean:: res.clean

res.clean:
	$(RM) $(OBJDIR)/cmm.rc
	$(RM) $(OBJDIR)/cmm.res
	$(RM) $(OBJDIR)/cmm.h

else
OTHER_LDLIBS = $(LIBM) -lawt -L$(LIBDIR)/$(LIBARCH)/xawt 
CPPFLAGS += -I$(SHARE_SRC)/native/sun/java2d \
            -I$(SHARE_SRC)/native/sun/awt/debug
endif

