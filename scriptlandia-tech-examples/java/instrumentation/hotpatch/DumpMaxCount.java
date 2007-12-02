import java.lang.instrument.*;
import java.io.*;

public class DumpMaxCount {
	public static void agentmain(String agentArgs, Instrumentation inst)
	{
		try
		{
			System.out.println("Redefining Reporter class ...");
			File f = new File("Reporter.class");
			byte[] reporterClassFile = new byte[(int) f.length()];
			DataInputStream in = new DataInputStream(new FileInputStream(f));
			in.readFully(reporterClassFile);
			in.close();
			ClassDefinition reporterDef = new ClassDefinition(Class.forName("Reporter"), reporterClassFile);
			inst.redefineClasses(reporterDef);
			System.out.println("Redefined Reporter class");
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
