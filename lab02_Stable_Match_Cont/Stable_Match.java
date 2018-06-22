package cl01_stable_matching;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

@SuppressWarnings({ "unused" })
public class Stable_Match {
	 
	public static int[] Getrs(int total)
	{
		int[] sequence = new int[total];
		int[] output = new int[total];
		for (int i = 0; i < total; i++)
		{
			sequence[i] = i;
		}
		java.util.Random random1 = new java.util.Random();
		int end = total - 1;
		for (int i = 0; i < total; i++)
		{
			int num = random1.nextInt(end + 1);
			output[i] = sequence[num];
			sequence[num] = sequence[end];
			end--; 
		}
		for (int i = 0; i < total; i++)
		{
			output[i] =output[i]+1;
		}
         return output;
     }

    
	public static void main(String[] args) {
		
        Scanner sc = new Scanner(System.in);
        int num = sc.nextInt();
        
        long begin = System.nanoTime();
        
        Woman women[] = new Woman[num];
        Man men[]= new Man[num];

        
        for(int i=0;i<num;i++){
            int[] a=Getrs(num);
            men[i]=new Man();
            men[i].setId(i+1);
            men[i].setRank(a);
        }

        for(int i=0;i<num;i++){
            int[] a=Getrs(num);
            women[i]=new Woman();
            women[i].setRank(a); 
        }
        sc.close();

        for(int i=0;i<num;i++){
            if(!men[i].isDate()){
                int chase =men[i].getRank()[men[i].getChasedp()]; 
                men[i].setChasedp(men[i].getChasedp()+1);        
               if(!women[chase-1].isDate()){                    
                   men[i].setDate(true);
                   men[i].setPresent(chase);
                   women[chase-1].setDate(true);
                   women[chase-1].setPresent(i+1);
               }
               else{
                   int current=0,previous=0;
                   for(int r=0;r<num;r++){ //获取排名
                       if(women[chase-1].getRank()[r]==(i+1)){
                    	   current=r;
                       }
                       if(women[chase-1].getRank()[r]==women[chase-1].getPresent()){
                           previous=r;
                       }
                   }
                   if(current>previous){
                       i=-1;
                   }else if(current<previous){
                       men[women[chase-1].getPresent()-1].setDate(false);
                       men[women[chase-1].getPresent()-1].setPresent(-1);
                       men[i].setDate(true);
                       men[i].setPresent(chase);
                       women[chase-1].setPresent(i+1);
                       i=-1;
                   }else{
                	   System.out.println("Rank error.");
                   }
               }
            }
        }

        for(int i=0;i<num;i++){
            System.out.println(men[i].getId()+"->"+men[i].getPresent()+" ");
        }
        long end4 = (System.nanoTime() - begin);
	    System.out.printf("Time spend "+"%.9f"+" ns",(double)end4/1000000000);
    }
    
   
}
