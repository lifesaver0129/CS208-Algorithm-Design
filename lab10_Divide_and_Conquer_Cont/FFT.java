package cl10_FFT;

import static java.lang.Math.*;

public class FFT {

	public static int bitReverse(int n, int bits) {
		int reversedN = n;
		int count = bits - 1;

		n >>= 1;
		while (n > 0) {
			reversedN = (reversedN << 1) | (n & 1);
			count--;
			n >>= 1;
		}

		return ((reversedN << count) & ((1 << bits) - 1));
	}

	static void fft(Complex[] a) {

		int bits = (int) (log(a.length) / log(2));
		for (int j = 1; j < a.length / 2; j++) {
			int swapPos = bitReverse(j, bits);
			Complex temp = a[j];
			a[j] = a[swapPos];
			a[swapPos] = temp;
		}

		for (int N = 2; N <= a.length; N <<= 1) {
			for (int i = 0; i < a.length; i += N) {
				for (int k = 0; k < N / 2; k++) {

					int evenIndex = i + k;
					int oddIndex = i + k + (N / 2);
					Complex even = a[evenIndex];
					Complex odd = a[oddIndex];

					double term = (-2 * PI * k) / (double) N;
					Complex exp = (new Complex(cos(term), sin(term)).mult(odd));

					a[evenIndex] = even.add(exp);
					a[oddIndex] = even.sub(exp);
				}
			}
		}
	}

	static Complex[] fft2(Complex[] a) {
		if (a.length == 1)
			return a;
		else {
			Complex[] e = new Complex[a.length / 2];
			Complex[] d = new Complex[a.length / 2];
			for (int i = 0; i < a.length / 2; i++) {
				e[i] = new Complex(a[2 * i].re, a[2 * i].im);
				d[i] = new Complex(a[2 * i + 1].re, a[2 * i + 1].im);
			}
			fft2(e);
			fft2(d);
			for (int k = 0; k < (a.length / 2); k++) {
				double term = (-2 * PI * k) / a.length;
				Complex exp = (new Complex(cos(term), sin(term)));
				a[k] = e[k].add(exp.mult(d[k]));
				a[k + a.length / 2] = e[k].sub(exp.mult(d[k]));
			}
			return a;
		}
	}

	static double f(int dc, double ma1, int fr1, double ma2, int fr2, int frs, int t) {
		return dc + ma1 * sin(2 * PI * fr1 * t / frs) + ma2 * sin(2 * PI * fr2 * t / frs);
	}

	static double f(int dc, double ma1, int fr1, int frs, int t) {
		return dc + ma1 * sin(2 * PI * fr1 * t / frs);
	}

	static void print(Complex cinput[]) {

		int b = 0;
		for (Complex c : cinput) {
			System.out.println(b + " " + c);
			b++;
		}
	}

	public static void main(String[] args) {
		double[] input = new double[64];
		for (int i = 0; i < input.length; i++) {
			input[i] = f(2, 3.0, 25, 1.5, 50, 64, i);

		}
		//double input []={0,1,0,-1};
		Complex[] cinput = new Complex[input.length];
		Complex[] cinput2 = new Complex[input.length];
		for (int i = 0; i < input.length; i++) {
			cinput[i] = new Complex(input[i], 0.0);
			cinput2[i] = new Complex(input[i], 0.0);
		}
		fft(cinput);
		print(cinput);
		// print(fft2(cinput2));

	}
}

class Complex {
	public double re;
	public double im;

	public Complex() {
		this(0, 0);
	}

	public Complex(double r, double i) {
		re = r;
		im = i;
	}

	public Complex add(Complex b) {
		return new Complex(this.re + b.re, this.im + b.im);
	}

	public Complex sub(Complex b) {
		return new Complex(this.re - b.re, this.im - b.im);
	}

	public Complex mult(Complex b) {
		return new Complex(this.re * b.re - this.im * b.im, this.re * b.im + this.im * b.re);
	}

	@Override
	public String toString() {
		return String.format("(%f,%f)", re, im);
	}
}
