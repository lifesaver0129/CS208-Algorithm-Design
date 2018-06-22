package homework6;

public class CacheLRUList<Item> implements LRUList<Item>
{
	private LinkList<Item> young;
	private LinkList<Item> old;
	private int capY;
	private int capO;
	
	public CacheLRUList(int capacity, float R)
	{
		capO = (int) (capacity*R);
		capY = capacity - capO;
		young = new LinkList<Item>();
		old = new LinkList<Item>();
	}
	
	public boolean put(Item item)
	{
		boolean tar = false;
		if(young.removeOb(item) != null)
		{
			young.AddNode(item);
			tar = true;
		}
		else if(old.removeOb(item) != null)
		{
			young.AddNode(item);
			old.AddNode(young.removeTail());
			tar = true;
		}
		else if(young.getLength() >= capY)
		{
			if(old.getLength() >= capO)
				old.removeTail();
			old.AddNode(item);
		}
		else
			young.AddNode(item);
		return tar;
	}
	
	@Override
	public String toString()
	{	return young.toString() + old.toString();}	
}
