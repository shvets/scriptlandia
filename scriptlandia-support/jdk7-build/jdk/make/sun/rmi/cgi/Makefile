#
# Copyright 1996-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building rmiregistry program
#

BUILDDIR = ../../..
PACKAGE = sun.rmi
PRODUCT = sun
PROGRAM = java-rmi
include $(BUILDDIR)/common/Defs.gmk

#
# Files to compile.
#
FILES_c = java-rmi.c

#
# Rules.
#
include $(BUILDDIR)/common/Program.gmk

build: $(BINDIR)/$(PROGRAM).cgi

#
# Rename .exe to .cgi.
#

$(BINDIR)/$(PROGRAM).cgi: $(BINDIR)/$(PROGRAM).exe
	$(install-file)

clean clobber:: 
	$(RM) $(BINDIR)/$(PROGRAM).cgi

