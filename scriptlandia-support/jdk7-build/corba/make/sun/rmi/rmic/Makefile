#
# Copyright 2003-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building sun.rmi.rmic.* and the "rmic" command
#

BUILDDIR = ../../..
PACKAGE = sun.rmi.rmic
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
include FILES.gmk

ifdef PARTNERS_CORBA
FILES_java = \
	$(IIOP_java) \
	$(PARTNERS_EDITION_java)
else
FILES_java = \
	$(IIOP_java)
endif

#
# Rules
#
include $(BUILDDIR)/common/Classes.gmk

