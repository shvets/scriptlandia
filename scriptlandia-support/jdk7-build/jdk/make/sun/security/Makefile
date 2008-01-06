#
# Copyright 1996-2007 Sun Microsystems, Inc.  All rights reserved.
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

#
# Build classes for security tools:
#
#		* keytool
#		* jarsigner
#		* policytool
#
# Also install wrappers for all of these.
#

BUILDDIR = ../..
include $(BUILDDIR)/common/Defs.gmk

# build sun/security/jgss/wrapper on non-windows platform
JGSS_WRAPPER =
ifneq ($(PLATFORM), windows)
    JGSS_WRAPPER = jgss/wrapper
endif

# Build PKCS#11 on all platforms except 64-bit Windows.
# We exclude windows-amd64 because we don't have any
# 64-bit PKCS#11 implementations to test with on that platform.
PKCS11 = pkcs11
ifeq ($(ARCH_DATA_MODEL), 64)
  ifeq ($(PLATFORM), windows)
    PKCS11 =
  endif
endif

# Build Microsoft CryptoAPI provider only on (non-64-bit) Windows platform.
MSCAPI =
ifeq ($(PLATFORM), windows)
  ifneq ($(ARCH_DATA_MODEL), 64)
    MSCAPI = mscapi
  endif
endif

SUBDIRS = other action util tools jgss krb5 smartcardio $(PKCS11) \
          $(JGSS_WRAPPER) $(MSCAPI)

all build clean clobber::
	$(SUBDIRS-loop)

clean::
	$(RM) -r $(CLASSBINDIR)/sun/security
