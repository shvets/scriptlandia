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
# Makefile for building JDK out of the box management support 
#

BUILDDIR = ../..
include $(BUILDDIR)/common/Defs.gmk

MGMT_LIBDIR = $(LIBDIR)/management
MGMT_LIB_SRC = $(SHARE_SRC)/lib/management

all build:: properties aclfile jmxremotefiles

SUBDIRS = snmp jmxremote
all build clean clobber::
	$(SUBDIRS-loop)

properties: $(MGMT_LIBDIR)/management.properties

aclfile: $(MGMT_LIBDIR)/snmp.acl.template

jmxremotefiles: $(MGMT_LIBDIR)/jmxremote.password.template $(MGMT_LIBDIR)/jmxremote.access

$(MGMT_LIBDIR)/management.properties: $(MGMT_LIB_SRC)/management.properties
	$(install-file)
	$(CHMOD) 644 $@

$(MGMT_LIBDIR)/snmp.acl.template: $(MGMT_LIB_SRC)/snmp.acl.template
	$(install-file)
	$(CHMOD) 444 $@

$(MGMT_LIBDIR)/jmxremote.password.template: $(MGMT_LIB_SRC)/jmxremote.password.template
	$(install-file)
	$(CHMOD) 444 $@

$(MGMT_LIBDIR)/jmxremote.access: $(MGMT_LIB_SRC)/jmxremote.access 
	$(install-file)
	$(CHMOD) 644 $@

