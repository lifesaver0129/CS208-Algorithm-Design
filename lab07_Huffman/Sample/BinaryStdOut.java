package Huffman;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BinaryStdOut {
	private  BufferedOutputStream out;

	private static int buffer; // 8-bit buffer of bits to write out
	private static int n; // number of bits remaining in buffer

	public BinaryStdOut(String fileName) {
    	File file = new File(fileName);
    	OutputStream outStream;
    	try {
			outStream = new FileOutputStream(file);
			out = new BufferedOutputStream(outStream);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	/**
	 * Write the specified bit to standard output.
	 */
	public void writeBit(boolean bit) {
		// add bit to buffer
		buffer <<= 1;
		if (bit)
			buffer |= 1;

		// if buffer is full (8 bits), write out as a single byte
		n++;
		if (n == 8)
			clearBuffer();
	}

	public void clearBuffer() {
        if (n == 0) return;
        if (n > 0) buffer <<= (8 - n);
        try {
            out.write(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        n = 0;
        buffer = 0;
    }
	public void close(){
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}