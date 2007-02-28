import java.util.*;

public class Main {
	static Stack Pile = new Stack();
	static Random rand = new Random();
	public static void main(String[] args) throws Exception {
		System.out.println("Main starting");
		Reporter a = new Reporter();
		a.setMaxCount(Logging.QueueMaxCount());
		int count = 0;
		while(true)
		{
			Thread.sleep(2);
			processQueue();
			a.report(Pile);
			count++;
			if( count > 1000 )
			{
				System.out.println(a.getTopN());
				count = 0;
			}
		}
	}

	static void processQueue()
	{
		if (rand.nextBoolean())
			Pile.push(new Integer(rand.nextInt(10)));
		else if (Pile.size() > 0)
			Pile.pop();
	}



}
