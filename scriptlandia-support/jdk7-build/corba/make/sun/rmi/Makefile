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
# Build rmi tools, and sun.rmi.* implementation classes
#
BUILDDIR = ../..
include $(BUILDDIR)/common/Defs.gmk

LOG_GENDIRECTORY  = $(GENSRCDIR)/com/sun/corba/se/impl/logging
LOG_CLASS_OUTPUTDIRECTORY = $(CLASSBINDIR)/com/sun/corba/se/impl/logging
LOGSTRINGS_PROPERTIES     = LogStrings.properties
#
# Order is important -- you can't build stubs without building rmic first.
# We need corbalogsources to be built first, since it builds LogWrappers to
# log the exceptions and generate the right minorcodes. These classes are
# references in com/sun/corba/se/impl/io and util classes which in turn will
# be built from rmic Makefile.
#
CORBALOG_SUBDIRS = corbalogsources corbalogcompile rmic

# Do these subdirs first
OTHERSUBDIRS=$(CORBALOG_SUBDIRS)
all build clean clobber ::
	$(OTHERSUBDIRS-loop)

$(LOG_CLASS_OUTPUTDIRECTORY)/$(LOGSTRINGS_PROPERTIES):  $(LOG_GENDIRECTORY)/$(LOGSTRINGS_PROPERTIES)
	$(install-file)

# Then do the rest
all build clean clobber :: $(LOG_CLASS_OUTPUTDIRECTORY)/$(LOGSTRINGS_PROPERTIES)
	$(SUBDIRS-loop)

clean::
	$(RM) -r $(CLASSBINDIR)/sun/rmi $(CLASSBINDIR)/com/sun/rmi
