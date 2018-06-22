package Huffman;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

public class BinaryStdIn {
	private BufferedInputStream in;
	private static final int EOF = -1; // end of file

	private static int buffer; // one character buffer
	private static int n; // number of bits left in buffer

	public BinaryStdIn(String fileName) {
		File file = new File(fileName);
		InputStream inStream;

		try {
			inStream = new FileInputStream(file);
			in = new BufferedInputStream(inStream);
			fillBuffer();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean readBoolean() {
		if (isEmpty()) {
			throw new NoSuchElementException("Reading from empty input stream");
		}
		n--;
		boolean bit = ((buffer >> n) & 1) == 1;
		if (n == 0)
			fillBuffer();
		return bit;
	}

	private void fillBuffer() {
		try {
			buffer = in.read();
			n = 8;
		} catch (IOException e) {
			System.out.println("EOF");
			buffer = EOF;
			n = -1;
		}
	}

	public boolean isEmpty() {
		return buffer == EOF;
	}

	public void close() {
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}