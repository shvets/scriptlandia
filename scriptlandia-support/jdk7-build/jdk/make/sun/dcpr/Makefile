#
# Copyright 1997-2007 Sun Microsystems, Inc.  All rights reserved.
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

# WARNING: Make sure the OPENJDK plugs are up-to-date, see make/common/internal/BinaryPlugs.gmk

BUILDDIR = ../..
PACKAGE = sun.dc
LIBRARY = dcpr
PRODUCT = sun
include $(BUILDDIR)/common/Defs.gmk

#
# Files
#
include FILES_c.gmk

FILES_java = \
 sun/dc/DuctusRenderingEngine.java \
 sun/dc/path/FastPathProducer.java \
 sun/dc/path/PathConsumer.java \
 sun/dc/path/PathError.java \
 sun/dc/path/PathException.java \
 sun/dc/pr/PathDasher.java \
 sun/dc/pr/PathFiller.java \
 sun/dc/pr/PathStroker.java \
 sun/dc/pr/PRError.java \
 sun/dc/pr/PRException.java \
 sun/dc/pr/Rasterizer.java

FILES_export = \
	sun/dc/pr/PathDasher.java \
	sun/dc/pr/PathFiller.java \
	sun/dc/pr/PathStroker.java

#
# Rules.
#
include $(BUILDDIR)/common/Mapfile-vers.gmk
include $(BUILDDIR)/common/Library.gmk

SERVICEDIR = $(CLASSBINDIR)/META-INF/services

FILES_copy = \
	$(SERVICEDIR)/sun.java2d.pipe.RenderingEngine

build: copy-files

copy-files: $(FILES_copy)

$(SERVICEDIR)/%: $(SHARE_SRC)/classes/sun/dc/META-INF/services/%
	$(install-file)

#
# Add to the ambient vpath to pick up files in subdirectories
#
vpath %.c   $(CLOSED_SRC)/share/native/$(PKGDIR)/doe
vpath %.c   $(CLOSED_SRC)/share/native/$(PKGDIR)/path
vpath %.c   $(CLOSED_SRC)/share/native/$(PKGDIR)/pr
vpath %.c   $(CLOSED_SRC)/share/native/$(PKGDIR)/util
vpath %.c   $(SHARE_SRC)/native/sun/java2d/pipe

ifneq ($(PLATFORM), windows)
  OTHER_LDLIBS = $(LIBM)
endif # PLATFORM

CPPFLAGS += \
	-I$(CLOSED_SRC)/share/native/$(PKGDIR)/doe \
	-I$(CLOSED_SRC)/share/native/$(PKGDIR)/path \
	-I$(CLOSED_SRC)/share/native/$(PKGDIR)/pr \
	-I$(CLOSED_SRC)/share/native/$(PKGDIR)/util \
	-I$(SHARE_SRC)/native/sun/java2d/pipe \
	-I$(PLATFORM_SRC)/native/sun/java2d

