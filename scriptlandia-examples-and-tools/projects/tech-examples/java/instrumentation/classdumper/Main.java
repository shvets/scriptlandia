import java.util.*;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("Main starting");

		int count = 0;
		while(true)
		{
			Thread.sleep(100);
			count++;
			if( count > 1000 )
			{
				System.out.println("Next");
				count = 0;
			}
		}
	}



}
