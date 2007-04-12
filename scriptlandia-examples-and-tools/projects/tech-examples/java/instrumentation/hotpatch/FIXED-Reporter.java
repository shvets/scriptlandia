import java.util.*;

public class Reporter
{
	int maxCount;
	private String topN;

	public void report(Stack s)
	{
		topN = "";
		if (s.size() == 0)
			return;

		int i = 0;
		Iterator itr = s.iterator();
		for(; itr.hasNext() && i < maxCount; i++)
		{
			topN += itr.next() + " / ";
		}
	}

	public void setMaxCount(int n)
	{
		maxCount = n;
	}

	public String getTopN()
	{
		return topN;
	}
}
