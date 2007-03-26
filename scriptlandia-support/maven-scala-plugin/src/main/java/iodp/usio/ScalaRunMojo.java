package iodp.usio;

import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @goal run
 * @requiresDependencyResolution compile
 * @phase process-sources
 *
 */
public class ScalaRunMojo extends ScalaMojo{

	/**
	 * @parameter expression="${mainClass}"
	 * @required
	 */
	private String mainClass;

	public void execute() throws MojoExecutionException, MojoFailureException
	{
		super.execute();
		ArrayList<String> command = new ArrayList<String>();
		command.add(javaBin);
		command.add("-classpath");
		List<String> outDirs = new ArrayList<String>();
		outDirs.add(binDir);
		String classpath = addScalaHomeToClasspath(getClasspath(outDirs));
		command.add(classpath);
		command.add(mainClass);
		runCommand(command.toArray(new String[0]));
	}
}
