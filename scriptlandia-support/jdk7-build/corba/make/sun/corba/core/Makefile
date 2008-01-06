#
# Copyright 1997-2005 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for building RMI/IIOP
#

BUILDDIR = ../../..
PACKAGE = com.sun.corba.se.internal.io
PRODUCT = sun
LIBRARY = ioser12
include $(BUILDDIR)/common/Defs.gmk

#
# Use mapfile
#
FILES_m = mapfile-vers
include $(BUILDDIR)/common/Mapfile-vers.gmk

#
# Files to compile.
#
CORBA_JMK_DIRECTORY=$(TOPDIR)/make/com/sun/corba/minclude/
include $(CORBA_JMK_DIRECTORY)javax_rmi.jmk
include $(CORBA_JMK_DIRECTORY)javax_rmi_CORBA.jmk
include $(CORBA_JMK_DIRECTORY)javax_transaction.jmk
include $(CORBA_JMK_DIRECTORY)javax_activity.jmk
include $(CORBA_JMK_DIRECTORY)ioser_io.jmk
include $(CORBA_JMK_DIRECTORY)sun_corba.jmk

ifdef STANDALONE_CORBA_WS
# FIXUP: What is this all about?
OTHER_LDFLAGS=-L$(BOOTDIR)/jre/lib/$(ARCH) -L$(BOOTDIR)/jre/lib/$(LIBARCH)/native_threads -ljvm
OTHER_INCLUDES+=-ICClassHeaders -I$(BOOTDIR)/include -I$(BOOTDIR)/include/$(PLATFORM)
else
OTHER_LDLIBS=$(JVMLIB)
OTHER_INCLUDES+=-ICClassHeaders -I$(BOOTDIR)/include -I$(BOOTDIR)/include/$(PLATFORM)
endif


FILES_c = ioser.c

FILES_java = \
	$(javax_rmi_java) \
	$(javax_rmi_CORBA_java) \
	$(javax_transaction_java) \
	$(javax_activity_java) \
	$(IOSER_IO_java) \
	$(sun_corba_java)

#
# Generate header files for.
#
FILES_export = \
	com/sun/corba/se/internal/io/IIOPInputStream.java \
	com/sun/corba/se/internal/io/IIOPOutputStream.java \
	com/sun/corba/se/internal/io/ObjectStreamClass.java \
	com/sun/corba/se/internal/io/LibraryManager.java 
#
# Resources
#
LOCALE_SET_DEFINITION = jre
RESOURCE_BUNDLES_PROPERTIES = \
      com/sun/corba/se/impl/orbutil/resources/sunorb.properties

#
# Rules
#
include $(BUILDDIR)/common/Library.gmk

#
# Extra clean rules because we build more than one package.
#
clean:: classheaders.clean objects.clean
	$(RM) -r $(CLASSBINDIR)/javax/rmi
	$(RM) -r $(CLASSBINDIR)/javax/transaction
	$(RM) -r $(CLASSBINDIR)/javax/activity
	$(RM) -r $(CLASSBINDIR)/com/sun/corba/se/impl

