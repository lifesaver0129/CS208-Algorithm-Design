package cl06_lru; 
 
import java.util.AbstractMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Set; 
import java.util.concurrent.ConcurrentHashMap; 
import java.util.concurrent.ConcurrentMap; 

public class LsuImprv<K, V> extends AbstractMap<K, V> { 
 
  private enum Status { 
    HOT,    
    COLD,  
    NONRES
  } 

  private static final float HOT_RATE = 0.99f;  
  private final ConcurrentMap<K, LirsEntry> backingMap; 
  private final LirsEntry header = new LirsEntry(); 
  private final int maximumHotSize; 
  private final int maximumSize; 
  private int hotSize = 0; 
  private int size = 0; 
 
  public LsuImprv(int maximumSize) { 
    this.maximumSize = maximumSize; 
    this.maximumHotSize = calculateMaxHotSize(maximumSize); 
    this.backingMap = new ConcurrentHashMap<K, LirsEntry>(maximumSize); 
  } 
 
  private static int calculateMaxHotSize(int maximumSize) { 
    int result = (int) (HOT_RATE * maximumSize); 
    return (result == maximumSize) ? maximumSize - 1 : result; 
  } 
 
  @Override 
  public V get(Object key) { 
    LirsEntry e = backingMap.get(key); 
    if (e == null) { 
      return null; 
    } 
    if (e.isResident()) { 
      e.hit(); 
    } else { 
      e.miss(); 
    } 
    return e.getValue(); 
  } 
 
  @Override 
  public V put(K key, V value) { 
    LirsEntry e = new LirsEntry(key, value); 
    LirsEntry previous = backingMap.put(key, e); 
    if (previous != null) { 
      previous.remove(); 
      return previous.value; 
    } 
    return null; 
  } 
 
  @Override 
  public V remove(Object key) { 
    // don't remove from the map here, as that would discard its recency 
    LirsEntry e = backingMap.get(key); 
    return (e == null) ? null : e.remove(); 
  } 
 
  V lookupElement(K key) { 
    LirsEntry e = backingMap.get(key); 
    if (e != null && e.isResident()) { 
      return e.getValue(); 
    } 
    return null; 
  } 
 
  @Override 
  public int size() { 
    return size; 
  } 
 
  @Override 
  public Set<Entry<K, V>> entrySet() { 
    throw new UnsupportedOperationException(); 
  } 
 
  private void pruneStack() { 
    LirsEntry bottom = stackBottom(); 
    while (bottom != null && bottom.status != Status.HOT) { 
      bottom.removeFromStack(); 
      if (bottom.status == Status.NONRES) { 
        backingMap.remove(bottom); 
      } 
 
      bottom = stackBottom(); 
    } 
  } 
 
  private LirsEntry stackTop() { 
    LirsEntry top = header.nextInStack; 
    return (top == header) ? null : top; 
  } 
 
  private LirsEntry stackBottom() { 
    LirsEntry bottom = header.previousInStack; 
    return (bottom == header) ? null : bottom; 
  } 
 
  private LirsEntry queueFront() { 
    LirsEntry front = header.nextInQueue; 
    return (front == header) ? null : front; 
  } 
 
 
  private LirsEntry queueEnd() { 
    LirsEntry end = header.previousInQueue; 
    return (end == header) ? null : end; 
  } 
 
  public String printStack() { 
    StringBuilder result = new StringBuilder(); 
    result.append("["); 
    LirsEntry e = stackTop(); 
    if (e != null) { 
      result.append(e); 
      for (e = e.nextInStack; e != header; e = e.nextInStack) { 
        result.append(", " + e); 
      } 
    } 
    result.append("]"); 
    return result.toString(); 
  } 
 
 
  public String printQueue() { 
    StringBuilder result = new StringBuilder(); 
    result.append("["); 
    LirsEntry e = queueFront(); 
    if (e != null) { 
      result.append(e); 
      for (e = e.nextInQueue; e != header; e = e.nextInQueue) { 
        result.append(", " + e); 
      } 
    } 
    result.append("]"); 
    return result.toString(); 
  } 
 
  private class LirsEntry { 
    private final K key; 
    private V value; 
 
    private Status status = Status.NONRES; 
 
    private LirsEntry previousInStack; 
    private LirsEntry nextInStack; 
 
    private LirsEntry previousInQueue; 
    private LirsEntry nextInQueue; 
 
   
    public LirsEntry(K key, V value) { 
      this.key = key; 
      this.value = value; 
 
      miss(); 
    } 
 
    
    public LirsEntry() { 
      this.key = null; 
      this.value = null; 
 
      this.previousInStack = this; 
      this.nextInStack = this; 
      this.previousInQueue = this; 
      this.nextInQueue = this; 
    } 
 
    public V getValue() { 
      return value; 
    } 
 
    public void setValue(V value) { 
      this.value = value; 
    } 
 
    public boolean isResident() { 
      return (status != Status.NONRES); 
    } 
 
    public boolean inStack() { 
      return (nextInStack != null); 
    } 
 
    public boolean inQueue() { 
      return (nextInQueue != null); 
    } 
 
    public void hit() { 
      switch (status) { 
        case HOT: 
          hotHit(); 
          break; 
        case COLD: 
          coldHit(); 
          break; 
        case NONRES: 
          throw new IllegalStateException("Can't hit a non-resident entry!"); 
        default: 
          throw new AssertionError("Hit with unknown status: " + status); 
      } 
    } 
 
   
    private void hotHit() { 
      boolean onBottom = (stackBottom() == this); 
      moveToStackTop(); 
      if (onBottom) { 
        pruneStack(); 
      } 
    } 
 
  
    private void coldHit() { 
      boolean inStack = inStack(); 
      moveToStackTop(); 
 
      if (inStack) { 
        hot(); 
        removeFromQueue(); 
        stackBottom().migrateToQueue(); 
        pruneStack(); 
      } else { 
        moveToQueueEnd(); 
      } 
    } 
 
    private void miss() { 
      if (hotSize < maximumHotSize) { 
        warmupMiss(); 
      } else { 
        fullMiss(); 
      } 
      size++; 
    } 
 
    private void warmupMiss() { 
      hot(); 
      moveToStackTop(); 
    } 
 
    private void fullMiss() { 
      if (size >= maximumSize) { 
        queueFront().evict(); 
      } 
      boolean inStack = inStack(); 
      moveToStackTop(); 
 
      if (inStack) { 
        hot(); 
        stackBottom().migrateToQueue(); 
        pruneStack(); 
      } else { 
        cold(); 
      } 
    } 
 
    private void hot() { 
      if (status != Status.HOT) { 
        hotSize++; 
      } 
      status = Status.HOT; 
    } 
 
    private void cold() { 
      if (status == Status.HOT) { 
        hotSize--; 
      } 
      status = Status.COLD; 
      moveToQueueEnd(); 
    } 
 
    @SuppressWarnings("fallthrough") 
    private void nonResident() { 
      switch (status) { 
        case HOT: 
          hotSize--; 
          // fallthrough 
        case COLD: 
          size--; 
        default: 
      } 
      status = Status.NONRES; 
    } 
 
    private void tempRemoveFromStack() { 
      if (inStack()) { 
        previousInStack.nextInStack = nextInStack; 
        nextInStack.previousInStack = previousInStack; 
      } 
    } 
 
    private void removeFromStack() { 
      tempRemoveFromStack(); 
      previousInStack = null; 
      nextInStack = null; 
    } 
 
   
    private void addToStackBefore(LirsEntry existingEntry) { 
      previousInStack = existingEntry.previousInStack; 
      nextInStack = existingEntry; 
      previousInStack.nextInStack = this; 
      nextInStack.previousInStack = this; 
    } 
 
    private void moveToStackTop() { 
      tempRemoveFromStack(); 
      addToStackBefore(header.nextInStack); 
    } 
 
    
    private void moveToStackBottom() { 
      tempRemoveFromStack(); 
      addToStackBefore(header); 
    } 
 
    private void tempRemoveFromQueue() { 
      if (inQueue()) { 
        previousInQueue.nextInQueue = nextInQueue; 
        nextInQueue.previousInQueue = previousInQueue; 
      } 
    } 
 
    private void removeFromQueue() { 
      tempRemoveFromQueue(); 
      previousInQueue = null; 
      nextInQueue = null; 
    } 
 
    private void addToQueueBefore(LirsEntry existingEntry) { 
      previousInQueue = existingEntry.previousInQueue; 
      nextInQueue = existingEntry; 
      previousInQueue.nextInQueue = this; 
      nextInQueue.previousInQueue = this; 
    } 
 
    private void moveToQueueEnd() { 
      tempRemoveFromQueue(); 
      addToQueueBefore(header); 
    } 
 
    private void migrateToQueue() { 
      removeFromStack(); 
      cold(); 
    } 
 
    private void migrateToStack() { 
      removeFromQueue(); 
      if (!inStack()) { 
        moveToStackBottom(); 
      } 
      hot(); 
    } 
 
   
    private void evict() { 
      removeFromQueue(); 
      removeFromStack(); 
      backingMap.remove(key, this); 
      nonResident(); 
      value = null; 
    } 
 
   
    private V remove() { 
      boolean wasHot = (status == Status.HOT); 
      V result = value; 
      evict(); 
 
      if (wasHot) { 
        LirsEntry end = queueEnd(); 
        if (end != null) { 
          end.migrateToStack(); 
        } 
      } 
 
      return result; 
    } 
 
    @Override 
    public String toString() { 
      return key + "=" + value + " [" + status + "]"; 
    } 
    
  } 
  public static void main(String[] args) throws Exception{
      Scanner sc = new Scanner(System.in);
      System.out.println("Input the capacity of cache: ");
      int n = sc.nextInt();
      LsuImprv<Integer,Integer> cache=new LsuImprv<Integer, Integer>(n);
      
      System.out.println("Input the capacity of access numbers: ");
      int y = sc.nextInt();
      
      System.out.println("Input the access order once a time(after enter each number press enter): ");
     // int count=0;
      for(int i=1;i<=y;i++)
      {
          int x = sc.nextInt();
      	//int x=getN();
          //boolean containsValue = cache.containsValue(x);
          cache.put(x,  x);  
          
         // if(containsValue)
          	//count++;
      }
      sc.close();
      cache.printQueue();
      cache.printStack();
    //  System.out.println(cache.keySet());
     /* 
      DecimalFormat df = new DecimalFormat();
      df.setMaximumFractionDigits(4);
      df.setMinimumFractionDigits(2);
      String k = df.format(count * 100.00 / y) + "%";
      System.out.println("Hit rate for lru: "+k);
      */
  }

public static int getN() {
	        Random random = new Random();
	        int s = random.nextInt(10);
	        return s;
	    }


}
