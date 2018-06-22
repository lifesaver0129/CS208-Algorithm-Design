package Huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Map;

public class FileCodec {
	// read and parse file and statistic the frequency number of every byte
	public static void parseFile(String fileName, List<Integer> parseResult) {
		File file = new File(fileName);
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				int count = parseResult.get(tempbyte) + 1;
				parseResult.set(tempbyte, count);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	// read the same file and encode file according to the code table
	public static void encodeFile(String inputfileName, Map<Integer, String> codeTable, String outputfileName) {
		File file = new File(inputfileName);
		InputStream in = null;
		BinaryStdOut bso = new BinaryStdOut(outputfileName);
		int totalbits = 0;
		// reserve 32 bit for capacity information
		for (int i = 0; i < 32; i++) {
			bso.writeBit(false);
		}
		try {
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				String encodeData = codeTable.get(tempbyte);
				for (int i = 0; i < encodeData.length(); i++) {
					boolean bit = (encodeData.charAt(i) == '1') ? true : false;
					bso.writeBit(bit);
					totalbits++;
				}
			}
			bso.clearBuffer();
			in.close();
			bso.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// replace the capacity value to the file header
		replaceFileHeader(outputfileName, totalbits);
	}

	public static void replaceFileHeader(String fileName, int totalbits) {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(fileName, "rw");
			raf.seek(0);
			raf.writeInt(totalbits);

			raf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static int readFileHeader(String fileName) {
		RandomAccessFile raf;
		int totalbits = 0;
		try {
			raf = new RandomAccessFile(fileName, "rw");
			raf.seek(0);
			totalbits = raf.readInt();
			raf.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return totalbits;
	}

	// read the encode file and decode it according to the decode table
	public static void decodeFile(String fileName, Map<String, Integer> decodeTable, String outputfileName) {
		int totalbits = readFileHeader(fileName);
		
		File fileOut = new File(outputfileName);
		int bits_count = 0;
		try {
			OutputStream out = new FileOutputStream(fileOut);
			BinaryStdIn bsi = new BinaryStdIn(fileName);
			// read one bit one time
			StringBuilder sb = new StringBuilder();
			//
			for (int i = 0; i <32; i++){
				bsi.readBoolean();
			}
			while (!bsi.isEmpty() && bits_count < totalbits) {
				boolean bit = bsi.readBoolean();
				bits_count++;
				if (bit) {
					sb.append('1');
				} else {
					sb.append('0');
				}
				if (decodeTable.containsKey(sb.toString())) {
					try {
						out.write(decodeTable.get(sb.toString()));
						sb.delete(0, sb.length());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			out.close();
			bsi.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
