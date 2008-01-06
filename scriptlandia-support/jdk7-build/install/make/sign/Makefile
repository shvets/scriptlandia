#
# Copyright 2007 Sun Microsystems, Inc. All rights reserved.
# SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
#
#
# Makefile for signing the Deployment Toolkit dlls
#

INSTALL_BUILDDIR=..
include $(INSTALL_BUILDDIR)/common/Defs.gmk

all:: 

all build:: sign

sign::
ifeq ($(SIGN_FLAG),1)
	@# sign plugin dlls 
	$(FULL_SIGNER) -n "Java(TM) SE Runtime Environment $(JDK_MINOR_VERSION)$(MARKET_NAME)" -i http://java.com/ $(JRE_IMAGE_DIR)/bin/ssv.dll
	$(FULL_SIGNER) -n "Java(TM) SE Runtime Environment $(JDK_MINOR_VERSION)$(MARKET_NAME)" -i http://java.com/ $(JRE_IMAGE_DIR)/bin/npjpi$(JRE_NODOTVERSION).dll
	$(FULL_SIGNER) -n "Java(TM) SE Runtime Environment $(JDK_MINOR_VERSION)$(MARKET_NAME)" -i http://java.com/ $(JRE_IMAGE_DIR)/bin/jucheck.exe
	$(FULL_SIGNER) -n "Java(TM) SE Runtime Environment $(JDK_MINOR_VERSION)$(MARKET_NAME)" -i http://java.com/ $(JRE_IMAGE_DIR)/bin/jusched.exe
	$(FULL_SIGNER) -n "Java(TM) SE Runtime Environment $(JDK_MINOR_VERSION)$(MARKET_NAME)" -i http://java.com/ $(JRE_IMAGE_DIR)/bin/jureg.exe
endif

clean:
clobber:


# this should be the last rule in this file:
all::
	@$(ECHO) $(ARCH) "sign finished:  " $(FULL_VERSION)
#
# Phonies to avoid accidents.
#
.PHONY: all sign clean clobber
