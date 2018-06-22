package Huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanFileCodec {
	static HuffmanCoding huffman = new HuffmanCoding();
	static Map<Integer, String> codeTable = new HashMap<Integer, String>();
	static Map<String, Integer> decodeTable = new HashMap<String, Integer>();

	public static void encodefile(String inputfilename, String outputfilename) {

		List<Integer> input = new ArrayList<Integer>(Const.LIST_CAPACITY);
		for (int i = 0; i < (Const.LIST_CAPACITY); i++) {
			input.add(0);
		}
		FileCodec.parseFile(inputfilename, input);
		System.out.println(input);

		huffman.huffmanCoding(input, codeTable);
		System.out.println(codeTable);
		FileCodec.encodeFile(inputfilename, codeTable, outputfilename);

	}

	public static void decodefile(String inputfilename, String outputfilename) {
		huffman.getDecodeTable(codeTable, decodeTable);
		System.out.println(decodeTable);

		FileCodec.decodeFile(inputfilename, decodeTable, outputfilename);

	}
}
