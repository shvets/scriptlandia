import java.lang.instrument.*;

public class DumpPile {
	public static void agentmain(String agentArgs, Instrumentation inst)
	{
		try
		{
			System.out.println("Dumping Main.Pile ...");
			Object o = Class.forName("Main").getDeclaredField("Pile").get(null);
			System.out.println(o);
			System.out.println("Pile.size() == "+ ((java.util.Stack)o).size());
		}
		catch(Exception e)
		{
			System.out.println(e);
			e.printStackTrace();
		}
	}
}
