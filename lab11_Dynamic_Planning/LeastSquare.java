package cl11_leastsquare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class LeastSquare {

	public static Line[] fls(Point[] pts, double c) {
		Line[] l1 = new Line[pts.length];
		initial(l1);
		if (pts.length == 1) {
			l1[0].setLine(pts[0], pts[0]);
			return l1;
		}
		if (pts.length == 2) {
			l1[0].setLine(pts[0], pts[1]);
			return l1;
		}
		double[][] e = new double[pts.length][pts.length];
		for (int j = 0; j < pts.length; j++) {
			for (int i = 0; i <= j; i++) {
				 e[i][j] = cal(pts, i, j);
			}
		}
		double[] m = new double[pts.length];
		m[0] = 0;
		for (int s = 1; s < pts.length; s++)
			m[s] = m[s-1]+c;

		for (int j1 = 1; j1 < pts.length; j1++) {
			m[j1] = Integer.MAX_VALUE;
			int tnb = 0;
			for (int i1 = 0; i1 < j1; i1++) {
				if (m[j1] > (e[i1][j1] + c + m[i1])) {
					m[j1] = (e[i1][j1] + c + m[i1]);
					tnb = i1;
				}
				l1[j1].setLine(pts[tnb], pts[j1]);
			}
		}

		Line l2[] = new Line[pts.length];
		initial(l2);
		int tmp = pts.length - 1;
		int tmp2 = 0;
		while (tmp != 0) {
			l2[tmp2] = l1[tmp];
			tmp = l1[tmp].x1;
			tmp2++;
		}
		l1 = l2.clone();
		return l1;
	}

	
	public static double cal(Point[] pts, int i, int j) {
		double sum = 0;
		double af = 0, bf = 0;
		double n = j - i + 1;

		if (i == j || i + 1 == j)
			return 0;
		else {
			double temp1 = 0, temp2 = 0, temp3 = 0, temp4 = 0;
			for (int a = i; a <= j; a++) {
				temp1 = pts[a].x * pts[a].y + temp1;
				temp2 = pts[a].x + temp2;
				temp3 = pts[a].y + temp3;
				temp4 = pts[a].x * pts[a].x + temp4;
			}
			temp1 = n * temp1;
			af = (temp1 - temp2 * temp3) / (n * temp4 - temp2 * temp2);
			bf = (temp3 - af * temp2) / n;
			for (int a = i; a <= j; a++) {
				sum = Math.pow(pts[a].y - af * pts[a].x - bf, 2) + sum;
			}
			return sum;
		}
	}

	public static void initial(Line[] l) {
		for (int i = 0; i < l.length; i++)
			l[i] = new Line();
	}

	public static void print(Line[] l) {
		int tmp = l.length - 1;
		while (tmp != -1) {
			if (l[tmp].x1 == -1)
				tmp--;
			else {
				System.out.print(" (" + l[tmp].x1 + "," + l[tmp].y1 + ") ");
				tmp--;
			}
		}
		System.out.println();
	}

	public static void print(Line[] l, double c) {
		int tmp = l.length - 1;
		DecimalFormat df=new DecimalFormat("#,##0.00");
		System.out.print("c="+df.format(c)+ "    |||    ");
		//System.out.print("c=" + c);
		int at=0;
		while (tmp != -1) {
			if (l[tmp].x1 == -1)
				tmp--;
			else {
				System.out.print(" (" + l[tmp].x1 + "," + l[tmp].y1 + ") ");
				tmp--;
				at++;
				if(at%10==0)
					System.out.println();
			}
		}
		System.out.println();
	}

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

	public static void transArray(List<String> filelist, double codelist[][]) {
		String[] arr = filelist.get(0).split(",");
		for (int i = 0; i < filelist.size(); i++) {
			String str[] = filelist.get(i).split(",");
			for (int j = 0; j < arr.length; j++)
				codelist[i][j] =Double.valueOf(str[j]);
		}
	}

	

	public static void main(String[] args) {
		List<String> filelist = new ArrayList<String>();
		readFile("/Users/lifesaver/Desktop/input_point.txt", filelist);
		double[][] array = new double[filelist.size()][(filelist.get(0).split(",")).length];
		transArray(filelist, array);
		Point[] pts = new Point[filelist.size()];
		Line[] l1 = new Line[filelist.size()];
		for (int ep = 0; ep < filelist.size(); ep++) {
			pts[ep] = new Point(array[ep][0], array[ep][1], ep);
		}
		double c = 0;
		for(c=4200;c<=4300;c=c+1){
			l1 = fls(pts, c);
			print(l1, c);
		}
		/*
		 * Point[] pts = new Point[3]; Line[] l1 = new Line[3]; Line[] l2 = new
		 * Line[3]; pts[0] = new Point(1, 1, 0); pts[1] = new Point(1.2, 1.8,
		 * 1); pts[2] = new Point(2, 2, 2); l1 = fls(pts, 1); print(l1, 1); l2 =
		 * fls(pts, 0.1); print(l2, 0.1);
		 */
	}

}

class Line {
	Point x;
	Point y;
	int x1;
	int y1;

	public Line() {
		this.x = new Point();
		this.y = new Point();
		this.x1 = -1;
		this.y1 = -1;
	}

	public Line(Point a, Point b) {
		this.x = a;
		this.y = b;
		this.x1 = a.num;
		this.y1 = b.num;
	}

	public void setLine(Point a, Point b) {
		this.x = a;
		this.y = b;
		this.x1 = a.num;
		this.y1 = b.num;
	}

}

class Point {
	double x;
	double y;
	int num;

	public Point() {
		this.x = 0;
		this.y = 0;
		this.num = -1;
	}

	public Point(double a, double b) {
		this.x = a;
		this.y = b;
		this.num = -1;
	}

	public Point(double a, double b, int c) {
		this.x = a;
		this.y = b;
		this.num = c;
	}

	public void setPoint(double a, double b) {
		this.x = a;
		this.y = b;
	}
}
