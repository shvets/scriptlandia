#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#

include		../Makefile.master

DATAFILES=	$(PACKAGE_TMP_DIR)/copyright

TMP_AWK=	$(TMP_DIR)/awk_pkginfo

FILES=		$(DATAFILES) $(PACKAGE_TMP_DIR)/pkginfo

PACKAGE=	$(notdir $(shell pwd))

CLOBBERFILES=	$(FILES) $(PACKAGE_TMP_DIR)/action $(PACKAGE_TMP_DIR)/pkginfo

