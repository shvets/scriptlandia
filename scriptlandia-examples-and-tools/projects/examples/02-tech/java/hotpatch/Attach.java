import com.sun.tools.attach.*;
import java.io.*;

public class Attach {
	public static void main(String[] args) throws Exception
	{
		if (args.length != 2)
		{
			System.out.println("usage: java Attach pid|textfilewithpid agentjar");
			System.exit(1);
		}

		// This program accepts two parameters:
		//    1. The pid of the JVM on which we want to load an agent
		//       or a file with the pid followed by a space on the first line
		//       (everything else in the file is ignored including the rest of the 1st line)
		//    2. The full path of the agent jar file to be loaded

		// JVM is identified by process id (pid).
		String pid = args[0];
		if ((new File(pid)).exists())
		{
			try
			{
				BufferedReader in = new BufferedReader(new FileReader(pid));
				pid = in.readLine();
				in.close();
				pid = pid.substring(0, pid.indexOf(" "));
			}
			catch(Exception e)
			{
				System.out.println(e);
				e.printStackTrace();
			}
		}
		VirtualMachine vm = VirtualMachine.attach(pid);

		// load a specified agent onto the JVM
		vm.loadAgent(args[1], null);
	}
}
