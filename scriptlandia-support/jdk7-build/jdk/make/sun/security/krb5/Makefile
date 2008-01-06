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
PACKAGE = sun.security.krb5
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

#
# Java Files
#
AUTO_FILES_JAVA_DIRS = sun/security/krb5

ifeq ($(PLATFORM), windows)
#
# Java files that define native methods
#
FILES_export = \
	sun/security/krb5/Credentials.java \
	sun/security/krb5/Config.java
include FILES_c_windows.gmk
# We need some extra libs for win32
LIBRARY = w2k_lsa_auth
EXTRA_LIBS += Secur32.lib netapi32.lib\
 kernel32.lib user32.lib gdi32.lib winspool.lib comdlg32.lib\
 advapi32.lib shell32.lib ole32.lib oleaut32.lib uuid.lib odbc32.lib\
 odbccp32.lib wsock32.lib
endif # PLATFORM

#
# Find native code
#
vpath %.c \
  $(PLATFORM_SRC)/native/sun/security/krb5

JGSS_NATIVE_SRC=$(PLATFORM_SRC)/native/sun/security/krb5
JGSS_NATIVE_DIR_EXISTS := $(shell if [ -d $(JGSS_NATIVE_SRC) ] ; then echo true; else echo false; fi)

#
# Rules
#
ifeq ($(PLATFORM), windows)
  ifeq ($(JGSS_NATIVE_DIR_EXISTS), true)
    include $(BUILDDIR)/common/Library.gmk
  endif
else
include $(BUILDDIR)/common/Classes.gmk
endif # PLATFORM

#
# Libraries to link
#
ifeq ($(PLATFORM), windows)
  OTHER_LDLIBS = $(JVMLIB)
else
  OTHER_LDLIBS = -ldl $(JVMLIB)
endif
