#
# Copyright 1998-2007 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building javax
#

BUILDDIR = ..
include $(BUILDDIR)/common/Defs.gmk

#
# Files to compile
#
AUTO_FILES_JAVA_DIRS =       \
    javax/naming             \
    com/sun/naming/internal  \
    javax/net                \
    javax/script             \
    javax/security/auth      \
    javax/security/cert      \
    javax/security/sasl      \
    javax/smartcardio        \
    javax/tools              \
    javax/xml

# imageio uses xml, so build it last

SUBDIRS = \
	accessibility \
	print \
	swing \
	crypto \
	sound \
	sql \
	rmi \
	management \
	imageio

all build:
	$(SUBDIRS-loop)
clean clobber::
	$(SUBDIRS-loop)

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

