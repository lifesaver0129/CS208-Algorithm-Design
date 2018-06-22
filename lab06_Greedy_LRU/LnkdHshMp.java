package cl06_lru;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("serial")
public class LnkdHshMp<K, V> extends LinkedHashMap<K, V> {
	
	private int cacheSize;
	public LnkdHshMp(int cacheSize) {
		super(16, 0.75f, true);
		this.cacheSize = cacheSize;	 
	}
	   
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		boolean r = size() > cacheSize;
		if(r){
			System.out.println("erase eldest key："+eldest.getKey());     
		}
		return r;	
	}
	
	
	
	/*	 
	public static void main(String[] args) {
		LnkdHshMp<String, String> cache = new LnkdHshMp<String, String>(5);
		
		cache.put("1", "1");
		cache.put("2", "2");
		cache.put("3", "3");
		cache.put("4", "4");
		cache.put("5", "5");		        
		System.out.println(cache.keySet());
		
        cache.get("3");
        System.out.println(cache.keySet());
        cache.get("2");
        System.out.println(cache.keySet());
		        
        cache.put("6", "6");
		cache.put("7", "7");
		System.out.println(cache.keySet());	    
	}
	*/
	
	public static int getN(int q) {
	        Random random = new Random();
	        int s = random.nextInt(q);
	        return s;
	    }

	
	public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the capacity of cache: ");
        int n = sc.nextInt();
        Map<Integer,Integer> cache=new LnkdHshMp<Integer, Integer>(n);
        
        System.out.println("Input the capacity of access numbers: ");
        int y = sc.nextInt();
        
        System.out.println("Input the maximum of input numbers: ");
        int q = sc.nextInt();
        
        //System.out.println("Input the access order once a time(after enter each number press enter): ");
        int count=0;
        for(int i=1;i<=y;i++)
        {
            //int x = sc.nextInt();
        	int x=getN(q);
        	System.out.println("Imput random number "+x+" ");
            boolean containsValue = cache.containsValue(x);
            cache.put(x,  x);  
            
            if(containsValue)
            	count++;
        }
        sc.close();
        System.out.println(cache.keySet());
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);
        df.setMinimumFractionDigits(2);
        String k = df.format(count * 100.00 / y) + "%";
        System.out.println("Hit rate for lru: "+k);
    }
}
