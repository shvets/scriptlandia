#
# Copyright 1998-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building the JDI back-end implementation
#

BUILDDIR = ../../..
LIBRARY = dt_socket
PRODUCT = jbug

# Mapfile only used on Solaris/Linux
FILES_m = mapfile-vers

include $(BUILDDIR)/common/Defs.gmk

ifeq ($(PLATFORM), windows)
  # Files built here do not compile with warning level 3 if warnings are fatal
  COMPILER_WARNINGS_FATAL=false
endif

ifeq ($(PLATFORM), linux)
  OTHER_LDLIBS += -lnsl $(LIBSOCKET) -lpthread
endif

ifeq ($(PLATFORM), solaris)
  OTHER_LDLIBS += -lnsl $(LIBSOCKET)
endif

ifeq ($(PLATFORM), windows)
  OTHER_LDLIBS += ws2_32.lib
endif

OTHER_INCLUDES = -I$(INCLUDEDIR) -I$(PLATFORM_INCLUDE) \
                 -I$(SHARE_SRC)/transport/export \
                 -I$(SHARE_SRC)/transport/socket \
                 -I$(PLATFORM_SRC)/transport/socket \
                 -I$(SHARE_SRC)/back/export \
                 -I$(PLATFORM_SRC)/back

#
# Files to compile.
#
FILES_c = \
        socketTransport.c \
        socket_md.c

# Use the mapfile-vers (See the mapfile located with this Makefile)
ifdef FILES_m
  include $(BUILDDIR)/common/Mapfile-vers.gmk
endif

#
# Rules.
#
include $(BUILDDIR)/common/Library.gmk

# We don't want to link against -ljava
JAVALIB=

# Add -export options to explicitly spell exported symbols
ifeq ($(PLATFORM), windows)
  OTHER_LCF += -export:jdwpTransport_OnLoad
endif

#
# Add to ambient vpath so we pick up the library files
#
vpath %.c $(SHARE_SRC)/transport/socket:$(PLATFORM_SRC)/transport/socket
