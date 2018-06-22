package Huffman;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class HuffmanFileCodecTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String inputfilename = "IMG_8854.JPG";
		String outputfilename = "encodeIMG_8854.JPG";
		String recoveredfilename = "recoverIMG_8854.JPG";

		HuffmanFileCodec.encodefile(inputfilename, outputfilename);
		HuffmanFileCodec.decodefile(outputfilename, recoveredfilename);

		File inputfile = new File(inputfilename);
		if (!inputfile.exists()|| !inputfile.isFile()) {
			fail("inputfile is invalide!");
		}
		File recoveredfile = new File(recoveredfilename);
		if (!recoveredfile.exists() || !recoveredfile.isFile()) {
			fail("recoveredfile is invalide!");
		}
		assertEquals(inputfile.length(), recoveredfile.length());

	}

}
