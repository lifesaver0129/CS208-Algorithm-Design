package cl09_closest_pair_points;

import java.util.*;

class Point {
	int x;
	int y;
	int z;

	public Point() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public void oneD() {
		y = 0;
		z = 0;
	}

	public void twoD() {
		z = 0;
	}

}

public class ClstPrPnts {

	private static Point[] P;

	private static Point[] P2;

	private static Point[] P3;

	private static Comparator<Point> xcmpr = new Comparator<Point>() {

		public int compare(Point o1, Point o2) {
			if (o1.x < o2.x) {
				return -1;
			} else if (o1.x > o2.x) {
				return 1;
			}
			return 0;
		}
	};
	private static Comparator<Point> ycmpr = new Comparator<Point>() {

		public int compare(Point o1, Point o2) {
			if (o1.y < o2.y) {
				return -1;
			} else if (o1.y > o2.y) {
				return 1;
			}
			return 0;
		}
	};

	private static Comparator<Point> zcmpr = new Comparator<Point>() {

		public int compare(Point o1, Point o2) {
			if (o1.z < o2.z) {
				return -1;
			} else if (o1.z > o2.z) {
				return 1;
			}
			return 0;
		}
	};

	private static void setPoint(int a, Point[] P) {
		Random random = new Random();
		System.out.println();
		int q = 100;
		for (int i = 0; i < P.length; i++) {
			P[i] = new Point();
			P[i].x = random.nextInt(q) % (q - 1 + 1);
			P[i].y = random.nextInt(q) % (q - 1 + 1);
			P[i].z = random.nextInt(q) % (q - 1 + 1);
			// P[i].z = random.nextDouble() * (q);
			// P[i].z = random.nextInt(q) % (q - 1 + 1);
			System.out.print("[" + P[i].x + ",");
			System.out.print(P[i].y + ",");
			System.out.print(P[i].z + "] ");
			if ((i + 1) % 10 == 0) {
				System.out.println();
			}
		}
		System.out.println();
	}

	private static void copy(int a, Point[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i] = new Point();
			x[i].x = P[i].x;
			x[i].y = P[i].y;
			x[i].z = P[i].z;
		}
	}

	public static void findClosest(Point[] points, Point pm1, Point pm2, int i) {

		Point[] px = points.clone();
		Arrays.sort(px, xcmpr);
		Point[] py = points.clone();
		Arrays.sort(py, ycmpr);
		Point[] pz = points.clone();
		Arrays.sort(pz, zcmpr);
		if (i == 3) {
			Point[] result = calClosest3(px, py, pz);
			pm1.x = result[0].x;
			pm1.y = result[0].y;
			pm1.z = result[0].z;
			pm2.x = result[1].x;
			pm2.y = result[1].y;
			pm2.z = result[1].z;
		} else if (i == 2) {
			Point[] result = calClosest2(px, py);
			pm1.x = result[0].x;
			pm1.y = result[0].y;
			pm2.x = result[1].x;
			pm2.y = result[1].y;
		} else {
			Point[] result = calClosest1(px);
			pm1.x = result[0].x;
			pm2.x = result[1].x;
		}

	}

	public static Point[] calClosest3(Point[] px, Point[] py, Point[] pz) {

		Point pm1 = null;
		Point pm2 = null;
		
		if (px.length <= 3) {
			double mindist = Double.MAX_VALUE;
			int x = 0;
			int y = 0;
			for (int i = 0; i < px.length; i++) {
				for (int j = i + 1; j < px.length; j++) {
					if (mindist > distance(px[i], px[j])) {
						mindist = distance(px[i], px[j]);
						x = i;
						y = j;
					}
				}
			}
			return new Point[] { px[x], px[y] };
		}

		Point[] Q = Arrays.copyOfRange(px, 0, px.length / 2);
		Point[] Qx = Q.clone();
		Arrays.sort(Qx, xcmpr);
		Point[] Qy = Q.clone();
		Arrays.sort(Qy, ycmpr);
		Point[] Qz = Q.clone();
		Arrays.sort(Qz, zcmpr);
		Point[] q = calClosest3(Qx, Qy, Qz);

		Point[] R = Arrays.copyOfRange(px, px.length / 2, px.length);
		Point[] Rx = R.clone();
		Arrays.sort(Rx, xcmpr);
		Point[] Ry = R.clone();
		Arrays.sort(Ry, ycmpr);
		Point[] Rz = R.clone();
		Arrays.sort(Rz, zcmpr);
		Point[] r = calClosest3(Rx, Ry, Rz);

		double qd = distance(q[0], q[1]);
		double rd = distance(r[0], r[1]);
		double m1 = Math.min(qd, rd);

		if (m1 == qd) {
			pm1 = q[0];
			pm2 = q[1];
		} else {
			pm1 = r[0];
			pm2 = r[1];
		}

		
		ArrayList<Point> St = new ArrayList<Point>();
		for (int i = 0; i < px.length; i++) {
			if (Math.abs(px[i].x - px[(px.length / 2 + 1)].x) < m1) {
				St.add(px[i]);
			}
		}

		Point[] Sy = St.toArray(new Point[0]);
		Arrays.sort(Sy, ycmpr);

		Point[] Sz = Sy.clone();
		Arrays.sort(Sz, zcmpr);

		if(Sy.length==0){
			return new Point[]{pm1,pm2};
		}
		Point[] fnl = calClosest2(Sy, Sz);
		return fnl;

	}

	public static Point[] calClosest2(Point[] px, Point[] py) {
		Point pm1 = null;
		Point pm2 = null;

		if (px.length <= 3) {
			double mindist = Double.MAX_VALUE;
			int x = 0;
			int y = 0;
			for (int i = 0; i < px.length; i++) {
				for (int j = i + 1; j < px.length; j++) {
					if (mindist > distance(px[i], px[j])) {
						mindist = distance(px[i], px[j]);
						x = i;
						y = j;
					}
				}
			}
			return new Point[] { px[x], px[y] };
		}

		Point[] Q = Arrays.copyOfRange(px, 0, px.length / 2);
		Point[] Qx = Q.clone();
		Arrays.sort(Qx, xcmpr);
		Point[] Qy = Q.clone();
		Arrays.sort(Qy, ycmpr);
		Point[] q = calClosest2(Qx, Qy);

		Point[] R = Arrays.copyOfRange(px, px.length / 2, px.length);
		Point[] Rx = R.clone();
		Arrays.sort(Rx, xcmpr);
		Point[] Ry = R.clone();
		Arrays.sort(Ry, ycmpr);
		Point[] r = calClosest2(Rx, Ry);

		double qd = distance(q[0], q[1]);
		double rd = distance(r[0], r[1]);
		double m1 = Math.min(qd, rd);
		if (m1 == qd) {
			pm1 = q[0];
			pm2 = q[1];
		} else {
			pm1 = r[0];
			pm2 = r[1];
		}

		ArrayList<Point> St = new ArrayList<Point>();
		for (int i = 0; i < px.length; i++) {
			if (Math.abs(px[i].x - px[(px.length / 2 + 1)].x) < m1) {
				St.add(px[i]);
			}
		}

		Point[] Sy = St.toArray(new Point[0]);
		Arrays.sort(Sy, ycmpr);

		double m2 = Double.MAX_VALUE;
		Point tm1 = null;
		Point tm2 = null;
		for (int i = 0; i < Sy.length; i++) {
			Point t1 = Sy[i];
			for (int j = i + 1; j < Sy.length; j++) {
				Point t2 = Sy[j];
				if (Math.abs(t2.y - t1.y) < m1 && Math.abs(t2.x - t1.x) < m1 && Math.abs(t2.z - t1.z) < m1) {
					if (m2 > distance(t1, t2)) {
						m2 = distance(t1, t2);
						tm1 = t1;
						tm2 = t2;
					}
				}
			}
		}
		if (m2 < m1) {
			return new Point[] { tm1, tm2 };
		} else {
			return new Point[] { pm1, pm2 };
		}
	}

	public static Point[] calClosest1(Point[] px) {
		Point pm1 = null;
		Point pm2 = null;
		Point pm3 = null;
		Point pm4 = null;

		if (px.length <= 3) {
			double mindist = Double.MAX_VALUE;
			int x = 0;
			int y = 0;
			for (int i = 0; i < px.length; i++) {
				for (int j = i + 1; j < px.length; j++) {
					if (mindist > distance(px[i], px[j])) {
						mindist = distance(px[i], px[j]);
						x = i;
						y = j;
					}
				}
			}
			return new Point[] { px[x], px[y] };
		}

		Point[] Q = Arrays.copyOfRange(px, 0, px.length / 2);
		Point[] Qx = Q.clone();
		Arrays.sort(Qx, xcmpr);
		Point[] q = calClosest1(Qx);
		
		Point[] R = Arrays.copyOfRange(px, px.length / 2, px.length);
		Point[] Rx = R.clone();
		Arrays.sort(Rx, xcmpr);
		Point[] r = calClosest1(Rx);
		
		double qm = dist1(px[px.length / 2], px[(px.length / 2) - 1]);
		double rm = dist1(px[px.length / 2], px[(px.length / 2) + 1]);
		double qd = dist1(q[0], q[1]);
		double rd = r[0].x-r[1].x;
		double m1 = Math.min(qd, rd);
		double m2 = Math.min(qm, rm);
		if (m1 == qd) {
			pm1 = q[0];
			pm2 = q[1];
		} else {
			pm1 = r[0];
			pm2 = r[1];
		}
		if (m2 == qm) {
			pm3 = px[(px.length / 2) - 1];
			pm4 = px[px.length / 2];
		} else {
			pm1 = px[px.length / 2];
			pm2 = px[(px.length / 2) + 1];
		}
		double m = Math.min(m1, m2);
		if (m == m1) {
			return new Point[] { pm1, pm2 };
		} else {
			return new Point[] { pm3, pm4 };
		}

	}

	private static double dist1(Point q, Point p) {
		return Math.pow(Math.pow(q.x - p.x, 2), 0.5);
	}
	
	private static double distance(Point q, Point p) {
		if (q.y == 0)
			return Math.pow(Math.pow(q.x - p.x, 2), 0.5);
		else if (q.z == 0)
			return Math.pow(Math.pow(q.x - p.x, 2) + Math.pow(q.y - p.y, 2), 0.5);
		else
			return Math.pow(Math.pow(q.x - p.x, 2) + Math.pow(q.y - p.y, 2) + Math.pow(q.z - p.z, 2), 0.5);
	}
	
	

	private static void print(Point pm1, Point pm2) {
		System.out.print("[" + pm1.x + "," + pm1.y + "," + pm1.z + "] ");
		System.out.println("[" + pm2.x + "," + pm2.y + "," + pm2.z + "]");
	}

	public static void start(Point[] p, int a) {
		long begin = System.nanoTime();
		Point pm1 = new Point();
		Point pm2 = new Point();
		if (a == 1)
			for (int i = 0; i < p.length; i++)
				p[i].oneD();
		if (a == 2)
			for (int i = 0; i < p.length; i++)
				p[i].twoD();
		findClosest(p, pm1, pm2, a);
		System.out.print("For " + a + " dimension,the closest pair of point should be: ");
		print(pm1, pm2);
		System.out.println("The distance is: " + Math.pow(Math.pow(pm1.x - pm2.x, 2) +
				Math.pow(pm1.y - pm2.y, 2) + Math.pow(pm1.z - pm2.z, 2), 0.5));
		long end = (System.nanoTime() - begin);
		System.out.print("Time comsuming: ");
		System.out.printf("%.9f", (double) end / 1000000000);
		System.out.println();
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Input the number of points: ");
		int a = input.nextInt();
		P = new Point[a];
		P2 = new Point[a];
		P3 = new Point[a];
		input.close();
		setPoint(a, P);
		copy(a, P2);
		copy(a, P3);
		start(P, 1);
		start(P2, 2);
		start(P3, 3);
	}
}
