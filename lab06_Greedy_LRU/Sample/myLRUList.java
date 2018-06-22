package homework6;

public class myLRUList<Item> implements LRUList<Item>
{
	private LinkList<Item> list;
	private int capacity;
	
	public myLRUList(int capacity, float R)
	{
		this.list = new LinkList<Item>();
		this.capacity = capacity;
	}
	
	public boolean put(Item item)
	{
		if(list.removeOb(item) != null)
		{
			list.AddNode(item);
			return true;
		}
		else
		{
			if(list.getLength() >= capacity)
				list.removeTail();
			list.AddNode(item);
			return false;
		}
	}
	
	@Override
	public String toString()
	{	return list.toString();}
}
