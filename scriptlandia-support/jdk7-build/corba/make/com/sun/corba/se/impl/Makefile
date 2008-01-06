#
# Copyright 1999-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building all of java
#

BUILDDIR = ../../../../..
PRODUCT = impl
include $(BUILDDIR)/common/Defs.gmk

# order is important

ifdef STANDALONE_CORBA_WS
SUBDIRS = oa naming activation txpoa interceptors logging monitoring
else
SUBDIRS = oa naming activation       interceptors logging monitoring
endif

LOG_GENDIRECTORY          = $(GENSRCDIR)/com/sun/corba/se/impl/logging
LOG_CLASS_OUTPUTDIRECTORY = $(CLASSBINDIR)/com/sun/corba/se/impl/logging
LOGSTRINGS_PROPERTIES     = LogStrings.properties
all build clean clobber::
	$(SUBDIRS-loop)

# Copy the generated LogStrings.properties to the Classes directory
all build:: $(LOG_CLASS_OUTPUTDIRECTORY)/$(LOGSTRINGS_PROPERTIES)

$(LOG_CLASS_OUTPUTDIRECTORY)/$(LOGSTRINGS_PROPERTIES): $(LOG_GENDIRECTORY)/$(LOGSTRINGS_PROPERTIES)
	$(install-file)

