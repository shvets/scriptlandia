#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#

include	../Makefile.com

DATAFILES += $(PACKAGE_TMP_DIR)/depend

PKGFILES = etc

# Packages to list in the depend file (see ../Makefile.targ).
PKG_DEPENDENCIES = SUNWj7rt SUNWmfrun

all:	$(FILES) $(PACKAGE_TMP_DIR)/action

install: all generate-prototype make-package

generate-prototype: $(PKG_ARCHIVE) FRC
	$(CP) ../common/i.preserve $(PACKAGE_TMP_DIR)
	$(CP) postinstall $(PACKAGE_TMP_DIR)
	$(CP) space $(PACKAGE_TMP_DIR)
	$(TOUCH) $(PACKAGE_TMP_DIR)/.system.lock
	$(TOUCH) $(PACKAGE_TMP_DIR)/.systemRootModFile

include	../Makefile.targ
