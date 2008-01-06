#
# Copyright 1997-2007 Sun Microsystems, Inc.  All rights reserved.
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
PACKAGE = sun.java2d.cmm.kcms
LIBRARY = kcms
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

FILES_java = \
    sun/java2d/cmm/kcms/CMM.java

FILES_export = \
    sun/java2d/cmm/kcms/CMM.java 

ifeq ($(PLATFORM), windows)
  # Override the default version info with our own resource file (see 5043594)
  VERSIONINFO_RESOURCE = $(CLOSED_SRC)/share/native/sun/java2d/cmm/kcms/cmm.rc
  # Files built here do not compile with warning level 3 if warnings are fatal
  COMPILER_WARNINGS_FATAL=false
endif

# Rules
#
include $(BUILDDIR)/common/Mapfile-vers.gmk
include $(BUILDDIR)/common/Library.gmk

SERVICEDIR = $(CLASSBINDIR)/META-INF/services

FILES_copy = \
	$(SERVICEDIR)/sun.java2d.cmm.PCMM


build: copy-files

copy-files: $(FILES_copy)

$(SERVICEDIR)/%: $(CLOSED_SRC)/share/classes/sun/java2d/cmm/kcms/META-INF/services/%
	$(install-file)

#
# Extra rules
#
ifeq  ($(PLATFORM), linux)
LDLIBS += -lpthread
endif

clean clobber:: 
	$(RM) $(FILES_copy)

#
# Add to ambient vpath to get files in a subdirectory
#
vpath %.c   $(CLOSED_SRC)/share/native/$(PKGDIR)

ifeq ($(PLATFORM), windows)

OTHER_LDLIBS = user32.lib version.lib

clean:: res.clean

res.clean:
	$(RM) $(OBJDIR)/cmm.rc
	$(RM) $(OBJDIR)/cmm.res
	$(RM) $(OBJDIR)/cmm.h

else # PLATFORM
OTHER_LDLIBS = $(LIBM)

# Use pic instead of PIC on this library
GLOBAL_KPIC = $(PIC_CODE_SMALL)

CPPFLAGS += -I$(CLASSHDRDIR) \

endif # PLATFORM

#CFLAGS += -DJAVACMM -DFUT_CALC_EX -DNO_FUT_GCONST
CFLAGS += -DFUT_CALC_EX -DNO_FUT_GCONST

