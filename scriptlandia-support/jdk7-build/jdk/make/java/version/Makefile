#
# Copyright 1999-2007 Sun Microsystems, Inc.  All rights reserved.
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
# Makefile to generate sun/misc/Version.java
#

BUILDDIR = ../..
include $(BUILDDIR)/common/Defs.gmk

all build: $(GENSRCDIR)/sun/misc/Version.java

$(GENSRCDIR)/sun/misc/Version.java: \
		$(SHARE_SRC)/classes/sun/misc/Version-template.java
	$(prep-target)
	$(RM) $@.temp
	$(SED) -e 's/@@launcher_name@@/$(LAUNCHER_NAME)/g' \
	    -e 's/@@java_version@@/$(RELEASE)/g' \
	    -e 's/@@java_runtime_version@@/$(FULL_VERSION)/g' \
	    -e 's/@@java_runtime_name@@/$(RUNTIME_NAME)/g' \
	$< > $@.temp
	@$(MV) $@.temp $@

clean clobber:
	$(RM) $(GENSRCDIR)/sun/misc/Version.java
