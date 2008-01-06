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

BUILDDIR = ../..
LIBRARY = jdwp
PRODUCT = jpda

FILES_m = mapfile-vers

include $(BUILDDIR)/common/Defs.gmk

# Use the mapfile-vers (See the mapfile located with this Makefile)
ifdef FILES_m
include $(BUILDDIR)/common/Mapfile-vers.gmk
endif

OTHER_INCLUDES = -I$(SHARE_SRC)/transport/export \
                 -I$(SHARE_SRC)/back/export \
                 -I$(SHARE_SRC)/npt \
                 -I$(PLATFORM_SRC)/npt \
                 -I$(SHARE_SRC)/back \
                 -I$(PLATFORM_SRC)/back \
                 -I$(GENNATIVESRCDIR)/jdwp

ifneq ($(PLATFORM), windows)
  OTHER_LDLIBS += -ldl
endif # PLATFORM

#
# This turns off all assert() checking in the optimized library
#
CPPFLAGS_OPT += -DNDEBUG

#
# This controls the ability to do logging in the library.
#
CPPFLAGS_DBG += -DJDWP_LOGGING
CPPFLAGS_OPT += -DJDWP_LOGGING

#
# Files to compile.
#
FILES_c = \
        ArrayReferenceImpl.c \
        ArrayTypeImpl.c \
        ClassTypeImpl.c \
        ClassLoaderReferenceImpl.c \
        ClassObjectReferenceImpl.c \
        EventRequestImpl.c \
        FieldImpl.c \
        FrameID.c \
        MethodImpl.c \
        ObjectReferenceImpl.c \
        ReferenceTypeImpl.c \
        SDE.c \
        StackFrameImpl.c \
        StringReferenceImpl.c \
        ThreadGroupReferenceImpl.c \
        ThreadReferenceImpl.c \
        VirtualMachineImpl.c \
        bag.c \
        commonRef.c \
        classTrack.c \
        debugDispatch.c \
        debugInit.c \
        debugLoop.c \
        error_messages.c \
        eventFilter.c \
        eventHandler.c \
        eventHelper.c \
        inStream.c \
        invoker.c \
        log_messages.c \
        outStream.c \
        standardHandlers.c \
        stepControl.c \
        stream.c \
        threadControl.c \
        transport.c \
        util.c \
	linker_md.c \
	exec_md.c

#
# Rules.
#
include $(BUILDDIR)/common/Library.gmk

# We don't want to link against -ljava
JAVALIB=

#
# Add to ambient vpath so we pick up the library files
#
ifeq ($(PLATFORM), windows)
vpath %.c $(SHARE_SRC)/back:$(SHARE_SRC)/back
endif # PLATFORM
vpath %.c $(SHARE_SRC)/back:$(PLATFORM_SRC)/back

