#
# Copyright 2006-2007 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile for the EbayServer sample code
#

BUILDDIR = ../../..

PRODUCT = java

include $(BUILDDIR)/common/Defs.gmk

SAMPLE_SRC_DIR = $(CLOSED_SHARE_SRC)/sample/webservices/EbayServer
SAMPLE_DST_DIR = $(SAMPLEDIR)/webservices/EbayServer

SAMPLE_FILES =							\
	$(SAMPLE_DST_DIR)/src/ebay/server/Main.java	\
	$(SAMPLE_DST_DIR)/src/ebay/server/Ebay.java	\
	$(SAMPLE_DST_DIR)/src/ebay/server/ImageProvider.java	\
	$(SAMPLE_DST_DIR)/build.xml \
	$(SAMPLE_DST_DIR)/build.properties \
	$(SAMPLE_DST_DIR)/eBaySvc.wsdl \
	$(SAMPLE_DST_DIR)/manifest.mf \
	$(SAMPLE_DST_DIR)/index.html \
	$(SAMPLE_DST_DIR)/nbproject/project.xml \
	$(SAMPLE_DST_DIR)/nbproject/jdk.xml \
	$(SAMPLE_DST_DIR)/nbproject/netbeans-targets.xml \
	$(SAMPLE_DST_DIR)/nbproject/file-targets.xml \
	$(SAMPLE_DST_DIR)/images/8d_2.jpg \
	$(SAMPLE_DST_DIR)/images/sun_blade_1000_h400px.jpg \
	$(SAMPLE_DST_DIR)/images/Sun_Fire_E20K_Server.jpg \

all build: $(SAMPLE_FILES)

$(SAMPLE_DST_DIR)/%: $(SAMPLE_SRC_DIR)/%
	$(install-file)

clean clobber:
	$(RM) -r $(SAMPLE_DST_DIR)

.PHONY: all build clean clobber
