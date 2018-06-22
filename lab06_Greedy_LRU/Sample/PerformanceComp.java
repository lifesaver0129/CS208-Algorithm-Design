package homework6;

import java.util.Random;

public class PerformanceComp 
{
	private LRUList<Integer> list1;
	private LRUList<Integer> list2;
	private Random ran = new Random();
	private int targeted1 = 0;
	private int targeted2 = 0;
	private int total = 0;
	private int type;
	
	public PerformanceComp(LRUList<Integer> list1,LRUList<Integer> list2, int type)
	{
		this.list1 = list1;
		this.list2 = list2;
		this.type = type;
	}
	
	public void simulate(int N)
	{
		total += N;
		while(N-- != 0)
		{
			int item = ran.nextInt(type)+1;
			if(list1.put(item))
				targeted1++;
			if(list2.put(item))
				targeted2++;
		}
	}
	
	public double ratio1()
	{	return targeted1/(double)total;}
	
	public double ratio2()
	{	return targeted2/(double)total;}
}
