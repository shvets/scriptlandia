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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

public abstract class ScalaMojo extends CommandMojo{

	/**
	 * @parameter
	 */
	protected String scalaHome;

	protected String scalaDir = "src" + File.separator + "main" + File.separator + "scala";
	
	protected String javaBin;

	protected String binDir;

	public void execute() throws MojoExecutionException, MojoFailureException
	{
		super.execute();
		binDir = buildDir.getAbsolutePath() + File.separator + "classes";
		javaBin = System.getenv("JAVA_HOME") + File.separator + "bin" + File.separator + "java";
		if (javaBin == null)
		{
			throw new MojoExecutionException("Couldn't locate java, try setting JAVA_HOME environment variable.");
		}
	}

	protected String addScalaHomeToClasspath(String cp)
	{
		if (scalaHome != null || (scalaHome = System.getenv("SCALA_HOME")) != null)
		{
			File sHome = new File(scalaHome);
			String libDir = sHome.getAbsolutePath() + File.separator + 
							"share" + File.separator + 
							"scala" + File.separator + 
							"lib" + File.separator;
			String compiler = libDir + "scala-compiler.jar";
			String lib = libDir + "scala-library.jar";
			cp += File.pathSeparator + compiler + File.pathSeparator + lib;
		}
		return cp;
	}
}
