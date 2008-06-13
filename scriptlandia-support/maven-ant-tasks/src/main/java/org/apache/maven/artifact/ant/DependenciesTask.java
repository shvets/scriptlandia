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
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactNotFoundException;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.artifact.resolver.filter.AndArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
import org.apache.maven.artifact.resolver.filter.TypeArtifactFilter;
import org.apache.maven.model.Dependency;
import org.apache.maven.project.artifact.InvalidDependencyVersionException;
import org.apache.maven.project.artifact.MavenMetadataSource;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileList;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Dependencies task, using maven-artifact.
 *
 * @author <a href="mailto:brett@apache.org">Brett Porter</a>
 * @author <a href="mailto:hboutemy@apache.org">Herve Boutemy</a>
 * @version $Id: DependenciesTask.java 633204 2008-03-03 17:46:50Z hboutemy $
 */
public class DependenciesTask
    extends AbstractArtifactWithRepositoryTask
{
    private List dependencies = new ArrayList();

    private String pathId;

    private String filesetId;

    private String sourcesFilesetId;
    
    private String javadocFilesetId;
    
    private String versionsId;

    private String useScope;

    private String type;

    private boolean verbose;

    protected void doExecute()
    {
        showVersion();
        
        ArtifactRepository localRepo = createLocalArtifactRepository();
        log( "Using local repository: " + localRepo.getBasedir(), Project.MSG_VERBOSE );

        ArtifactResolver resolver = (ArtifactResolver) lookup( ArtifactResolver.ROLE );
        ArtifactFactory artifactFactory = (ArtifactFactory) lookup( ArtifactFactory.ROLE );
        MavenMetadataSource metadataSource = (MavenMetadataSource) lookup( ArtifactMetadataSource.ROLE );

        List dependencies = this.dependencies;

        Pom pom = buildPom( localRepo );
        if ( pom != null )
        {
            if ( !dependencies.isEmpty() )
            {
                throw new BuildException( "You cannot specify both dependencies and a pom in the dependencies task" );
            }

            dependencies = pom.getDependencies();
        }
        else
        {
            // we have to have some sort of Pom object in order to satisfy the requirements for building the
            // originating Artifact below...
            pom = createDummyPom( localRepo );
        }

        if ( dependencies.isEmpty() )
        {
            log( "There were no dependencies specified", Project.MSG_WARN );
        }

        log( "Resolving dependencies...", Project.MSG_VERBOSE );

        ArtifactResolutionResult result;
        Set artifacts;

        List remoteArtifactRepositories = createRemoteArtifactRepositories( pom.getRepositories() );

        try
        {
            artifacts = MavenMetadataSource.createArtifacts( artifactFactory, dependencies, null, null, null );

            Artifact pomArtifact = artifactFactory.createBuildArtifact( pom.getGroupId(), pom.getArtifactId(), pom
                .getVersion(), pom.getPackaging() );

            List listeners = Collections.singletonList( new AntResolutionListener( getProject(), verbose ) );

            // TODO: managed dependencies
            Map managedDependencies = Collections.EMPTY_MAP;

            ArtifactFilter filter = null;
            if ( useScope != null )
            {
                filter = new ScopeArtifactFilter( useScope );
            }
            if ( type != null )
            {
                TypeArtifactFilter typeArtifactFilter = new TypeArtifactFilter( type );
                if ( filter != null )
                {
                    AndArtifactFilter andFilter = new AndArtifactFilter();
                    andFilter.add( filter );
                    andFilter.add( typeArtifactFilter );
                    filter = andFilter;
                }
                else
                {
                    filter = typeArtifactFilter;
                }
            }

            result = resolver.resolveTransitively( artifacts, pomArtifact, managedDependencies, localRepo,
                                                   remoteArtifactRepositories, metadataSource, filter, listeners );
        }
        catch ( ArtifactResolutionException e )
        {
            throw new BuildException( "Unable to resolve artifact: " + e.getMessage(), e );
        }
        catch ( ArtifactNotFoundException e )
        {
            throw new BuildException( "Dependency not found: " + e.getMessage(), e );
        }
        catch ( InvalidDependencyVersionException e )
        {
            throw new BuildException( e.getMessage(), e );
        }

        /*
        MANTTASKS-37: Do what other ant tasks do and just override the path id.
        if ( pathId != null && getProject().getReference( pathId ) != null )
        {
            throw new BuildException( "Reference ID " + pathId + " already exists" );
        }

        if ( filesetId != null && getProject().getReference( filesetId ) != null )
        {
            throw new BuildException( "Reference ID " + filesetId + " already exists" );
        }

        if ( sourcesFilesetId != null && getProject().getReference( sourcesFilesetId ) != null )
        {
            throw new BuildException( "Reference ID " + sourcesFilesetId + " already exists" );
        }
        */

        FileList fileList = new FileList();
        fileList.setDir( getLocalRepository().getPath() );

        FileSet fileSet = new FileSet();
        fileSet.setProject( getProject() );
        fileSet.setDir( fileList.getDir( getProject() ) );

        // new
        List systemFileListCollection = new ArrayList();
        // end new

        FileSet sourcesFileSet = new FileSet();
        sourcesFileSet.setDir( getLocalRepository().getPath() );

        FileSet javadocsFileSet = new FileSet();
        javadocsFileSet.setDir( getLocalRepository().getPath() );

        Set versions = new HashSet();
        
        for ( Iterator i = result.getArtifacts().iterator(); i.hasNext(); )
        {
            Artifact artifact = (Artifact) i.next();
            // new
            if(DefaultArtifact.SCOPE_SYSTEM.equals( artifact.getScope() ) ) {
              File file = artifact.getFile();

              FileList systemFileList = new FileList();
              systemFileList.setDir( file.getParentFile() );

              systemFileListCollection.add(systemFileList);

              //addArtifactToResult( localRepo, artifact, systemFileSet, systemFileList );

              String filename = file.getName();

              fileSet.createInclude().setName( filename );

               if ( systemFileList != null)
               {
                   FileList.FileName file1 = new FileList.FileName();
                   file1.setName( filename );

                   systemFileList.addConfiguredFile( file1 );
               }

               getProject().setProperty( artifact.getDependencyConflictId(), artifact.getFile().getAbsolutePath() );

            }
            else {         // end new
              addArtifactToResult( localRepo, artifact, fileSet, fileList );
            // new
            }
            // end new

            versions.add( artifact.getVersion() );

            if ( sourcesFilesetId != null )
            {
                resolveSource( artifactFactory, resolver, remoteArtifactRepositories, localRepo,
                               artifact, "sources", sourcesFileSet );
            }

            if ( javadocFilesetId != null )
            {
                resolveSource( artifactFactory, resolver, remoteArtifactRepositories, localRepo,
                               artifact, "javadoc", javadocsFileSet );
            }
        }

        if ( pathId != null )
        {
            Path path = new Path( getProject() );
            if ( versions.size() > 0 )
            {
                path.addFilelist( fileList );
                // new
                for(int i=0; i < systemFileListCollection.size(); i++) {
                  FileList systemFileList = (FileList)systemFileListCollection.get(i);
                  
                  path.addFilelist( systemFileList );
                }
              // end new
            }
            getProject().addReference( pathId, path );
        }

        defineFilesetReference( filesetId, fileSet );

        defineFilesetReference( sourcesFilesetId, sourcesFileSet );
        
        defineFilesetReference( javadocFilesetId, javadocsFileSet );
        
        if ( versionsId != null )
        {
            String versionsValue = StringUtils.join( versions.iterator(), File.pathSeparator );
            getProject().setNewProperty( versionsId, versionsValue );
        }
    }

    private void defineFilesetReference( String id, FileSet fileSet )
    {
        if ( id != null )
        {
            if ( !fileSet.hasPatterns() )
            {
                fileSet.createExclude().setName( "**/**" );
            }
            getProject().addReference( id, fileSet );
        }
    }

    private void addArtifactToResult( ArtifactRepository localRepo, Artifact artifact, FileSet toFileSet )
    {
        addArtifactToResult( localRepo, artifact, toFileSet, null );
    }

    private void addArtifactToResult( ArtifactRepository localRepo, Artifact artifact, FileSet toFileSet,
                                      FileList toFileList )
    {
        String filename = localRepo.pathOf( artifact );

        toFileSet.createInclude().setName( filename );

        if ( toFileList != null)
        {
            FileList.FileName file = new FileList.FileName();
            file.setName( filename );

            toFileList.addConfiguredFile( file );
        }

        getProject().setProperty( artifact.getDependencyConflictId(), artifact.getFile().getAbsolutePath() );
    }

    private void resolveSource( ArtifactFactory artifactFactory, ArtifactResolver resolver,
                                List remoteArtifactRepositories, ArtifactRepository localRepo,
                                Artifact artifact, String classifier, FileSet sourcesFileSet )
    {
        Artifact sourceArtifact =
            artifactFactory.createArtifactWithClassifier( artifact.getGroupId(), artifact.getArtifactId(),
                                                          artifact.getVersion(), "java-source", classifier );
        try
        {
            resolver.resolve( sourceArtifact, remoteArtifactRepositories, localRepo );

            addArtifactToResult( localRepo, sourceArtifact, sourcesFileSet );
        }
        catch ( ArtifactResolutionException e )
        {
            throw new BuildException( "Unable to resolve artifact: " + e.getMessage(), e );
        }
        catch ( ArtifactNotFoundException e )
        {
            // no source available: no problem, it's optional
        }
    }

    public List getDependencies()
    {
        return dependencies;
    }

    public void addDependency( Dependency dependency )
    {
        dependencies.add( dependency );
    }

    public String getPathId()
    {
        return pathId;
    }

    public void setPathId( String pathId )
    {
        this.pathId = pathId;
    }

    public String getFilesetId()
    {
        return filesetId;
    }

    public void setSourcesFilesetId( String filesetId )
    {
        this.sourcesFilesetId = filesetId;
    }

    public String getSourcesFilesetId()
    {
        return sourcesFilesetId;
    }

    public void setJavadocFilesetId( String filesetId )
    {
        this.javadocFilesetId = filesetId;
    }

    public String getJavadocFilesetId()
    {
        return javadocFilesetId;
    }

    public void setFilesetId( String filesetId )
    {
        this.filesetId = filesetId;
    }

    public String getVersionsId()
    {
        return versionsId;
    }

    public void setVersionsId( String versionsId )
    {
        this.versionsId = versionsId;
    }

    public void setVerbose( boolean verbose )
    {
        this.verbose = verbose;
    }

    public void setUseScope( String useScope )
    {
        this.useScope = useScope;
    }

    public void setType( String type )
    {
        this.type = type;
    }

    private void showVersion()
    {
        InputStream resourceAsStream;
        try
        {
            Properties properties = new Properties();
            resourceAsStream = DependenciesTask.class.getClassLoader().getResourceAsStream(
                "META-INF/maven/org.apache.maven/maven-ant-tasks/pom.properties" );
            if ( resourceAsStream != null )
            {
                properties.load( resourceAsStream );
            }

            String version = properties.getProperty( "version", "unknown" );
            String builtOn = properties.getProperty( "builtOn" );
            if ( builtOn != null )
            {
                log( "Maven Ant Tasks version: " + version + " built on " + builtOn, Project.MSG_VERBOSE );
            }
            else
            {
                log( "Maven Ant Tasks version: " + version, Project.MSG_VERBOSE );
            }
        }
        catch ( IOException e )
        {
            log( "Unable to determine version from Maven Ant Tasks JAR file: " + e.getMessage(), Project.MSG_WARN );
        }
    }
}
