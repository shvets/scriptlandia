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
# Makefile for building native2ascii
#

BUILDDIR = ../..
PACKAGE = sun.tools.native2ascii
PRODUCT = sun
OTHER_JAVACFLAGS += -Xlint:serial -Werror
include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
FILES_java = \
	sun/tools/native2ascii/Main.java \
        sun/tools/native2ascii/A2NFilter.java \
        sun/tools/native2ascii/N2AFilter.java

#
# Resources
#
LOCALE_SET_DEFINITION = jdk
RESOURCE_BUNDLES_JAVA = sun/tools/native2ascii/resources/MsgNative2ascii.java

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk
