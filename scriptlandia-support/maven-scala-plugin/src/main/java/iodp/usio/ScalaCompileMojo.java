package iodp.usio;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * 
 * @goal compile
 * @requiresDependencyResolution compile
 * @phase process-sources
 * @description Scala Maven Plugin
 */
public class ScalaCompileMojo extends ScalaMojo{

	public void execute() throws MojoExecutionException, MojoFailureException 
	{
		super.execute();
		setupClassesDir();
		String sourceDir = project.getBasedir().getAbsolutePath() + File.separator + scalaDir;
		File source = new File(sourceDir);
		List<String> filenames = null;
		try{
			filenames = findFiles(source,File.separator,".*\\.scala");
		}catch (Exception e) {
			throw new MojoExecutionException("Didn't find any scala source files in src/main/scala.");
		}
		List<String> outDirs = new ArrayList<String>();
		outDirs.add(buildDir + File.separator + "classes");
		String cp = getClasspath(outDirs);
		List<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-classpath");
		cp = addScalaHomeToClasspath(cp);
		command.add(cp);
		command.add("scala.tools.nsc.Main");
		command.add("-d");
		command.add(binDir);
		command.add("-sourcepath");
		command.add(sourceDir);
		for (String x:filenames)
		{
			command.add(source.getAbsolutePath() + File.separator + x);
		}
		runCommand(command.toArray(new String[0]));
	}
}
