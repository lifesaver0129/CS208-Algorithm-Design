package homework6;

import java.util.Map;

public class LRUTest 
{
	public static void main(String[] args)
	{
		/*Map<Integer, Integer> map = new LRULinkedHashMap<Integer, Integer>(8, 0.375f);
		String input = "1321543768659";
		
		for(int i = 0; i < input.length(); i++)
		{
			int In = input.charAt(i)-'0';
			map.put(In, In);
			System.out.printf("input is %d, list contains: %s\n", In, map);
		}*/
		
		myLRUList<Integer> list = new myLRUList<Integer>(8, 0.375f);
		list.put(1);		System.out.println(list);
		list.put(3);		System.out.println(list);
		list.put(2);		System.out.println(list);
		list.put(1);		System.out.println(list);
		list.put(5);		System.out.println(list);
		list.put(4);		System.out.println(list);
		list.put(3);		System.out.println(list);
		list.put(7);		System.out.println(list);
		list.put(6);		System.out.println(list);
		list.put(8);		System.out.println(list);
		list.put(6);		System.out.println(list);
		list.put(5);		System.out.println(list);
		list.put(9);		System.out.println(list);
		
		System.out.println("\n----------------------------------------------------------\n");
		
		CacheLRUList<Integer> cache = new CacheLRUList<Integer>(8, 0.375f);
		cache.put(1);		System.out.println(cache);
		cache.put(3);		System.out.println(cache);
		cache.put(2);		System.out.println(cache);
		cache.put(1);		System.out.println(cache);
		cache.put(5);		System.out.println(cache);
		cache.put(4);		System.out.println(cache);
		cache.put(3);		System.out.println(cache);
		cache.put(7);		System.out.println(cache);
		cache.put(6);		System.out.println(cache);
		cache.put(8);		System.out.println(cache);
		cache.put(6);		System.out.println(cache);
		cache.put(5);		System.out.println(cache);
		cache.put(9);		System.out.println(cache);
		
		System.out.println("\n----------------------------------------------------------\n");
		
		myLRUList<Integer> com1 = new myLRUList<Integer>(8, 0.375f);
		CacheLRUList<Integer> com2 = new CacheLRUList<Integer>(8, 0.375f);
		PerformanceComp per = new PerformanceComp(com1, com2, 50);
		
		for(int i = 0; i < 100; i++)
			per.simulate(100000);
		
		System.out.printf("N        raotio1    ratio2     random\n%d %.7f  %.7f  %.7f\n", 10000000, per.ratio1(), per.ratio2(),8/50.0);
	}
}
