/*
 * Copyright 2006 Integrated Ocean Drilling Program US Implementing Organization.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package iodp.usio;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

public abstract class CommandMojo extends AbstractMojo{
	/**
	 * @parameter expression="${project}"
	 * @required @readonly
	 */
	protected MavenProject project;

	/**
	 * @parameter expression="${project.build.directory}"
	 */
	protected File buildDir;
	
	protected List<String> env;

	public void execute()throws MojoExecutionException, MojoFailureException
	{
		env = new ArrayList<String>();
	}

	protected void runCommand(String[] command) throws MojoExecutionException
	{
		int retVal = 1;
		try {
			Runtime run = Runtime.getRuntime();
			Process p = run.exec(command,env.toArray(new String[0]));
			InputStream out = p.getInputStream();
			InputStream error = p.getErrorStream();
			StreamReader stdout = new StreamReader(out,this);
			StreamReader stderr = new StreamReader(error,this);
			stdout.start();
			stderr.start();
			retVal = p.waitFor();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (retVal != 0)
		{
			throw new MojoExecutionException("scala compiler returned non-zero value.");
		}
	}

	protected String getClasspath(List<String> outDirs)
	{
		StringBuffer path = new StringBuffer();
		Set elems = project.getDependencyArtifacts();
		Iterator ait = elems.iterator();
		while (ait.hasNext())
		{
			Artifact a = (Artifact)ait.next();
			if (a.getFile() != null)
			{
				path.append(a.getFile().getAbsolutePath() + File.pathSeparator);
			}
		}
		Iterator<String> it = outDirs.iterator();
		while (it.hasNext())
		{
			path.append(it.next() + File.pathSeparator);
		}
		String out = path.toString();
		if (out.endsWith(File.pathSeparator))
		{
			out = out.substring(0, out.length() - 1);
		}
		return out;
	}

    protected void setupClassesDir()
    {
    	if (!buildDir.exists())
    		buildDir.mkdir();
    	File classes = new File(buildDir,"classes");
    	if (!classes.exists())
    		classes.mkdir();
    }

    public void addEnvVar(String key, String value)
    {
    	env.add(key + "=" + value);
    }

    protected static List<String> findFiles(File dir, String delim, String pattern)
	{
		List<String> ls = new ArrayList<String>();
		String[] dirs = dir.list();
		for (String x:dirs)
		{
			File tmp = new File(dir,x);
			if (tmp.isDirectory())
			{
				List<String> fs = findFiles(tmp,delim,pattern);
				for (String y:fs)
				{
					ls.add(x + delim + y);
				}
			}
			else if (x.matches(pattern))
			{
				ls.add(x);
			}
		}
		return ls;
	}
}
