#! /bin/sh
#
# Copyright 2007 Sun Microsystems, Inc.  All rights reserved.
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

# Parse the first contiguous comment block in this script and generate
# a java comment block.  If this script is invoked with a copyright 
# year/year range, the java comment block will contain a Sun copyright.

COPYRIGHT_YEARS=$1

cat <<__END__
/*
__END__

if [ "x$COPYRIGHT_YEARS" != x ]; then
  cat <<__END__
 * Copyright $COPYRIGHT_YEARS Sun Microsystems, Inc.  All Rights Reserved.
__END__
fi

$NAWK ' /^#.*Copyright.*Sun/ { next }
        /^#([^!]|$)/ { sub(/^#/, " *"); print }
        /^$/ { print " */"; exit } ' $0
