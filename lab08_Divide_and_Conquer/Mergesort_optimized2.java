package cl08_divide_conquer;

import java.util.*;

public class Mergesort_optimized2 {

	static int invert;

	public static void sort(int[] arr) {
		int[] aux = arr.clone();
		sort(arr, aux, 0, arr.length - 1);
		// assert isSorted(arr);
		System.out.println("Mergesorted array: ");
		System.out.println(Arrays.toString(aux));
		System.out.println("The inversion number is " + invert);
	}

	static void sort(int[] arr, int[] aux, int start, int end) {
		if (end <= start)
			return;
		int mid = start + (end - start) / 2;
		sort(arr, aux, start, mid);
		sort(arr, aux, mid + 1, end);
		merge(aux, arr, start, mid, end);
	}

	private static void merge(int[] arr, int[] aux, int start, int mid, int end) {
		// assert isSorted(arr, start, mid);
		// assert isSorted(arr, mid + 1, end);
		for (int k = start; k <= end; k++) {
			aux[k] = arr[k];
		}
		int start1 = start, end1 = mid;
		int start2 = mid + 1, end2 = end;
		int k = start;
		while (start1 <= end1 && start2 <= end2) {
			if (arr[start1] < arr[start2])
				aux[k++] = arr[start1++];
			else {
				aux[k++] = arr[start2++];
				invert += end1 - start1 + 1;
			}
		}
		while (start1 <= end1)
			aux[k++] = arr[start1++];
		while (start2 <= end2)
			aux[k++] = arr[start2++];
		for (k = start; k <= end; k++)
			arr[k] = aux[k];

		// assert isSorted(arr, start, end);
	}

	/*
	 * 
	 * private static boolean isSorted(int[] arr) { return isSorted(arr, 0,
	 * arr.length - 1); }
	 * 
	 * private static boolean isSorted(int[] arr, int start, int end) { for (int
	 * i = start + 1; i <= end; i++) if (less(arr[i], arr[i - 1])) return false;
	 * return true; }
	 */

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Random random = new Random();
		System.out.println("Input the length of array: ");
		int n = input.nextInt();
		int[] sort = new int[n];
		for (int i = 0; i < n; i++) {
			sort[i] = random.nextInt(100) % (101);
		}
		System.out.println("Random created array: ");
		System.out.println(Arrays.toString(sort));
		sort(sort);
		input.close();
	}
}