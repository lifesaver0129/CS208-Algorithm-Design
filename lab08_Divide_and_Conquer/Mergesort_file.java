package cl08_divide_conquer;

import java.io.*;
import java.util.*;

class People implements Comparable {
	int id;
	int value;
	int rank;

	public People(int id, int value) {
		this.id = id;
		this.value = value;
	}

	public int compareTo(Object o) {
		People people = (People) o;
		if (value > people.value) {
			return -1;
		}
		if (value < people.value) {
			return 1;
		}
		return 0;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}

public class Mergesort_file {
	public static void readFile(String fileName, List<String> filelist) {
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				filelist.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void transArray(List<String> filelist, int codelist[][]) {
		String[] arr = filelist.get(0).split(",");
		for (int i = 0; i < filelist.size(); i++) {
			String str[] = filelist.get(i).split(",");
			for (int j = 0; j < arr.length; j++)
				codelist[i][j] = Integer.parseInt(str[j]);
		}
	}

	public static void transList(List peopleList, int codelist[]) {
		for (int i = 1; i < codelist.length; i++) {
			peopleList.add(new People(i, codelist[i]));
		}
		for (int i = 0; i < peopleList.size(); i++) {
			People people = (People) peopleList.get(i);
		}
		Collections.sort(peopleList);
		for (int i = 0; i < peopleList.size(); i++) {
			People people = (People) peopleList.get(i);
			people.setRank(i + 1);
		}
		for (int i = 1; i < codelist.length; i++) {
			for (int j = 0; j < peopleList.size(); j++) {
				People people = (People) peopleList.get(j);
				if (people.id == i)
					codelist[i] = people.rank;
			}
		}
	}

	public static void print(int codelist[][]) {
		for (int i = 0; i < codelist.length; i++) {
			for (int j = 0; j < codelist[i].length; j++)
				System.out.print(codelist[i][j] + " ");
			System.out.println();
		}
	}

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
		while (start1 <= end1 && start2 <= end2) {
			if (arr[start1] < arr[start2])
				result[k++] = arr[start1++];
			else {
				result[k++] = arr[start2++];
				invert += end1 - start1 + 1;
			}
		}
		while (start1 <= end1)
			result[k++] = arr[start1++];
		while (start2 <= end2)
			result[k++] = arr[start2++];
		for (k = start; k <= end; k++)
			arr[k] = result[k];
	}

	public static int mergeSort(int[] arr) {
		int f = invert;
		int len = arr.length;
		int[] result = new int[len];
		merge(arr, result, 0, len - 1);
		f = invert - f;
		return f;
	}

	public static void tmp(int a[], int b[]) {
		for (int i = 0; i < a.length - 1; i++) {
			b[i] = a[i + 1];
		}
	}

	public static void creat(int a[], int b[], int c) {
		for (int i = 0; i < a.length; i++) {
			b[i] = a[i];
		}
		b[a.length] = c;
	}

	public static void sortByInvert(int[][] ob, final int[] order) {
		Arrays.sort(ob, new Comparator<Object>() {
			public int compare(Object o1, Object o2) {
				int[] one = (int[]) o1;
				int[] two = (int[]) o2;
				for (int i = 0; i < order.length; i++) {
					int k = order[i];
					if (one[k] > two[k]) {
						return 1;
					} else if (one[k] < two[k]) {
						return -1;
					} else {
						continue;
					}
				}
				return 0;
			}
		});
	}

	public static void writeFile(String filepath, int a[][]) throws IOException {
		File file = new File(filepath);
		FileWriter out = new FileWriter(file);
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				out.write(a[i][j] + " ");
			}
			out.write("\r\n");
		}
		out.close();

	}

	public static void main(String[] args) throws IOException {
		List<String> filelist = new ArrayList<String>();
		readFile("/Users/lifesaver/Desktop/msd_1m.txt", filelist);
		int[][] codelist = new int[filelist.size()][(filelist.get(0).split(",")).length];
		int[][] codelistnew = new int[filelist.size()][(filelist.get(0).split(",")).length + 1];
		transArray(filelist, codelist);
		for (int i = 0; i < filelist.size(); i++) {
			int[] codelisttmp = new int[(filelist.get(0).split(",")).length - 1];
			List<People> peopleList = new ArrayList<People>();
			transList(peopleList, codelist[i]);
			tmp(codelist[i], codelisttmp);
			creat(codelist[i], codelistnew[i], mergeSort(codelisttmp));
		}
		System.out.println("Data after counting inversion number: ");
		print(codelistnew);
		sortByInvert(codelistnew,new int[]{codelistnew[0].length-1});
		writeFile("/Users/lifesaver/Desktop/SimilarityMetric.txt", codelistnew);
	}
}
