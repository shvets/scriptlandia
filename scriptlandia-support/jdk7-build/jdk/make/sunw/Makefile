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

BUILDDIR = ..
PACKAGE = sunw
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
FILES_java = \
	sunw/util/EventObject.java \
        sunw/util/EventListener.java \
        sunw/io/Serializable.java

#
# include rules
#
include $(BUILDDIR)/common/Rules.gmk

#
# Rules
#
clean clobber:: .delete.classlist
	$(RM) -r $(CLASSBINDIR)/sunw

