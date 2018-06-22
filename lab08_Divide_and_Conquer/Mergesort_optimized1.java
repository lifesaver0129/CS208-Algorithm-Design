package cl08_divide_conquer;

import java.util.*;

public class Mergesort_optimized1 {
	static int invert;
	static void merge(int[] arr, int[] result, int start, int end) {
		if (start >= end)
			return;
		int len = end - start, mid = (len >> 1) + start;
		int start1 = start, end1 = mid;
		int start2 = mid + 1, end2 = end;
		merge(arr, result, start1, end1);
		merge(arr, result, start2, end2);
		int k = start;
		if(arr[end1]<=arr[start2]){
			result[k++]= arr[start1++];
			result[k++] = arr[start2++];
		}
		else{
		while (start1 <= end1 && start2 <= end2){
			if(arr[start1]<arr[start2])
			result[k++]= arr[start1++];
			else{
				result[k++] = arr[start2++];
				invert+=end1-start1+1;
			}
		}
		while (start1 <= end1)
			result[k++] = arr[start1++];
		while (start2 <= end2)
			result[k++] = arr[start2++];
		for (k = start; k <= end; k++)
			arr[k] = result[k];
	}
	}
	
	public static void merge_sort(int[] arr) {
		int len = arr.length;
		int[] result = new int[len];
		merge(arr, result, 0, len - 1);
		System.out.println("Mergesorted array: ");
		System.out.println(Arrays.toString(result));
		System.out.println("The inversion number is "+invert);
	}
	
	public static void main(String args[]){
		Scanner input = new Scanner(System.in);
		Random random = new Random();
		System.out.println("Input the length of array: ");
		int n=input.nextInt();
		int [] sort=new int[n];
		for(int i=0;i<n;i++){
	        sort[i] = random.nextInt(100)%(101);
		}
		System.out.println("Random created array: ");
		System.out.println(Arrays.toString(sort));
		merge_sort(sort);
		input.close();
	}
}
