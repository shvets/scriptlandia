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

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.ArtifactUtils;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.project.artifact.MavenMetadataSource;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.codehaus.plexus.PlexusContainerException;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Ant Wrapper for wagon provider installation.
 *
 * @author <a href="mailto:brett@apache.org">Brett Porter</a>
 * @version $Id: InstallWagonProviderTask.java 593888 2007-11-11 14:30:50Z hboutemy $
 */
public class InstallWagonProviderTask
    extends AbstractArtifactWithRepositoryTask
{
	private String groupId = "org.apache.maven.wagon";
	
    private String artifactId;

    private String version;

    public String getGroupId()
    {
    	return groupId;
    }
    
    public void setGroupId( String groupId )
    {
    	this.groupId = groupId;
    }

    public String getArtifactId()
    {
        return artifactId;
    }

    public void setArtifactId( String artifactId )
    {
        this.artifactId = artifactId;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion( String version )
    {
        this.version = version;
    }

    public void doExecute()
        throws BuildException
    {
        VersionRange versionRange;
        try
        {
            versionRange = VersionRange.createFromVersionSpec( version );
        }
        catch ( InvalidVersionSpecificationException e )
        {
            throw new BuildException( "Unable to get extension '" +
                ArtifactUtils.versionlessKey( groupId, artifactId ) + "' because version '" + version +
                " is invalid: " + e.getMessage(), e );
        }

        ArtifactFactory factory = (ArtifactFactory) lookup( ArtifactFactory.ROLE );
        Artifact providerArtifact = factory.createExtensionArtifact( groupId, artifactId, versionRange );

        log( "Installing provider: " + providerArtifact );

        ArtifactResolutionResult result;
        try
        {
            MavenMetadataSource metadataSource = (MavenMetadataSource) lookup( ArtifactMetadataSource.ROLE );
            ArtifactResolver resolver = (ArtifactResolver) lookup( ArtifactResolver.ROLE );
            List remoteRepositories = createRemoteArtifactRepositories();

            result = resolver.resolveTransitively( Collections.singleton( providerArtifact ),
                                                   createDummyArtifact(), createLocalArtifactRepository(),
                                                   remoteRepositories, metadataSource, null );
        }
        catch ( ArtifactResolutionException e )
        {
            throw new BuildException( "Error downloading wagon provider from the remote repository: " + e.getMessage(),
                                      e );
        }
        catch ( ArtifactNotFoundException e )
        {
            throw new BuildException( "Unable to locate wagon provider in remote repository: " + e.getMessage(), e );
        }

        try
        {
            for ( Iterator i = result.getArtifacts().iterator(); i.hasNext(); )
            {
                Artifact a = (Artifact) i.next();

                getContainer().addJarResource( a.getFile() );
            }
        }
        catch ( PlexusContainerException e )
        {
            throw new BuildException( "Unable to locate wagon provider in remote repository", e );
        }
        
        log( "Protocols now supported: " + getSupportedProtocolsAsString(), Project.MSG_VERBOSE );
    }

}
