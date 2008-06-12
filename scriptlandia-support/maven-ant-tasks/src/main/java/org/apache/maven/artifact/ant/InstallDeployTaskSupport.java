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
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.apache.maven.artifact.repository.ArtifactRepository;

/**
 * Support for install/deploy tasks.
 *
 * @author <a href="mailto:jdillon@apache.org">Jason Dillon</a>
 * @version $Id: InstallDeployTaskSupport.java 586134 2007-10-18 21:02:18Z hboutemy $
 */
public abstract class InstallDeployTaskSupport
    extends AbstractArtifactTask
{
    protected File file;

    protected List attachedArtifacts;

    public File getFile()
    {
        return file;
    }

    public void setFile( File file )
    {
        this.file = file;
    }
    
    public Pom buildPom( ArtifactRepository localArtifactRepository )
    {
        Pom pom = super.buildPom( localArtifactRepository );

        // attach artifacts
        if (attachedArtifacts != null) {
            Iterator iter = attachedArtifacts.iterator();

            while (iter.hasNext()) {
                AttachedArtifact attached = (AttachedArtifact)iter.next();
                pom.attach( attached );
            }
        }
        
        return pom;
    }

    public AttachedArtifact createAttach()
    {
        if (attachedArtifacts == null) {
            attachedArtifacts = new ArrayList();
        }

        AttachedArtifact attach = new AttachedArtifact();
        attachedArtifacts.add(attach);

        return attach;
    }
}
