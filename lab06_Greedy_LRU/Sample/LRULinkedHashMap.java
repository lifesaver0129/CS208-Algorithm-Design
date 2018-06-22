package homework6;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V>
{
	private int capacity;
	
	public LRULinkedHashMap(int capacity, float R)
	{
		super(capacity, R, true);
		this.capacity = capacity;
	}
	
	@Override
	public boolean removeEldestEntry(Map.Entry<K, V> eldest)
	{
		if(size() > capacity)
			eldest.getValue();
		return size()>capacity;
	}
	
	@Override
	public String toString()
	{
		String str = "";
		for(Map.Entry<K, V> entry : entrySet())
			str = entry.getValue() + " " + str;
		return str;
	}
}
