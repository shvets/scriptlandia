package org.apache.maven.artifact.ant;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.util.FileNameMapper;
import org.codehaus.plexus.util.StringUtils;

public class VersionMapper implements FileNameMapper, Comparator
{
    private List versions;

    private String to;

    public String[] mapFileName( String sourceFileName )
    {
        String originalFileName = new java.io.File( sourceFileName ).getName();
        for ( Iterator iter = versions.iterator(); iter.hasNext(); )
        {
            String version = (String) iter.next();
            int index = originalFileName.indexOf( version );
            if ( index >= 0 )
            {
                // remove version in artifactId-version(-classifier).type
                String baseFilename = originalFileName.substring( 0, index - 1 );
                String extension = originalFileName.substring( index + version.length() );
                String path = sourceFileName.substring( 0, sourceFileName.length() - originalFileName.length() );
                if ( "flatten".equals( to ) )
                {
                    path = "";
                }
                return new String[] { path + baseFilename + extension };
            }
        }
        return new String[] { sourceFileName };
    }

    /**
     * Set the versions identifiers that this mapper can remove from filename. The value separator used is path
     * separator.
     */
    public void setFrom( String from )
    {
        String[] split = StringUtils.split( from, File.pathSeparator );
        // sort, from lengthiest to smallest
        Arrays.sort( split, this );
        versions = Arrays.asList( split );
    }

    /**
     * By default, only filename is changed, but if this attribute is set to <code>flatten</code>, directory is
     * removed.
     */
    public void setTo( String to )
    {
        this.to = to;
    }

    public int compare( Object o1, Object o2 )
    {
        String s1 = (String) o1;
        String s2 = (String) o2;
        int lengthDiff = s2.length() - s1.length();
        return ( lengthDiff != 0 ) ? lengthDiff : s1.compareTo( s2 );
    }
}
