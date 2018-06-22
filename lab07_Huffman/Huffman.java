package cl07_huffman;

import java.io.*;
import java.util.*;

class HuffmanTree implements Comparable<HuffmanTree> {
	public final int frequency; 
	public HuffmanTree(int freq) {
		frequency = freq; 
		}
	public int compareTo(HuffmanTree tree) {	      
		return frequency - tree.frequency;
	}
}
			
class HuffmanLeaf extends HuffmanTree {
	public final char value; 
	public HuffmanLeaf(int freq, char val) {
		super(freq);
		value = val;	
	}	
}

class HuffmanNode extends HuffmanTree { 
	public final HuffmanTree left, right; 
	public HuffmanNode(HuffmanTree l, HuffmanTree r) {
		super(l.frequency + r.frequency);
		left = l;
		right = r;	
	}			
}

public class Huffman {
	static Map<String, String>codelist=new HashMap<String,String>();
	static Map<String, String>codelist2=new HashMap<String,String>();
	
	public static HuffmanTree buildTree(int[] charFreqs) {
		PriorityQueue<HuffmanTree> trees = new PriorityQueue<HuffmanTree>();
		for (int i = 0; i < charFreqs.length; i++)
			if (charFreqs[i] > 0)
				trees.offer(new HuffmanLeaf(charFreqs[i], (char)i));
		assert trees.size() > 0;
		while (trees.size() > 1) {
			HuffmanTree a = trees.poll();
			HuffmanTree b = trees.poll();            
			trees.offer(new HuffmanNode(a, b));	        
		}	        
		return trees.poll();	
	}
	   
	public static void printCodes(HuffmanTree tree, StringBuffer prefix) {
		assert tree != null;
		if (tree instanceof HuffmanLeaf) {
			HuffmanLeaf leaf = (HuffmanLeaf)tree;
			System.out.println(leaf.value + "\t" + leaf.frequency + "\t" + prefix); 
			String str1 = String.valueOf(prefix);
			String str2 = String.valueOf(leaf.value);
			codelist.put(str1,str2);
			codelist2.put(str2,str1);
			} 
		else if (tree instanceof HuffmanNode) {        
				HuffmanNode node = (HuffmanNode)tree;
				prefix.append('0');       
				printCodes(node.left, prefix);        
				prefix.deleteCharAt(prefix.length()-1);
				prefix.append('1');
				printCodes(node.right, prefix);
				prefix.deleteCharAt(prefix.length()-1);	    
		}	
	}

	/*public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; 
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); 
        while (line != null) { 
            buffer.append(line); 
            buffer.append("\n"); 
            line = reader.readLine(); 
        }
        reader.close();
        is.close();
    }*/
	
	public static String file2String(String filepath, String encoding) { 
		File file = new File(filepath);
		InputStreamReader reader = null; 
        StringWriter writer = new StringWriter(); 
        try { 
        	if (encoding == null || "".equals(encoding.trim())) { 
        		reader = new InputStreamReader(new FileInputStream(file), encoding); 
        	} else { 
        		reader = new InputStreamReader(new FileInputStream(file));   
        	} 
        	char[] buffer = new char[100000000]; 
        	int n = 0; 
        	while (-1 != (n = reader.read(buffer))) { 
        		writer.write(buffer, 0, n);   
        	} 
        } catch (Exception e) { 
        	e.printStackTrace(); 
        	return null; 
        } finally { 
        	if (reader != null) 
        		try { 
        			reader.close(); 
        		} catch (IOException e) { 
        			e.printStackTrace();   
        		} 
        } 
        return writer.toString();
	}
	
	public static void writecode(String incoming){
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter("/Users/lifesaver/Desktop/Write.txt"));
			for (char c : incoming.toCharArray()){
				String str1 = String.valueOf(c);
				pw.println(codelist2.get(str1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	 public static void writecode3(String filepath,String filepath2, Map codelist2) throws IOException {
		 File file = new File(filepath);
		 FileWriter writer = new FileWriter(filepath2);
		 BufferedWriter bw = new BufferedWriter(writer);
		 Reader reader = null;
		 try {
			 reader = new InputStreamReader(new FileInputStream(file));
			 int tempchar;
			 while ((tempchar = reader.read()) != -1) {
				 if (((char) tempchar) != '\r') {
					 System.out.print((char) tempchar);
					 String str1 = String.valueOf(tempchar);
					 bw.write(codelist2.get(tempchar).toString());  
				 }  
			 }
			 reader.close();
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	 }
	
	public static void writecode2(String filepath,String filepath2, Map codelist2) {  
		try {
            StringBuffer sb= new StringBuffer("");
            FileReader reader = new FileReader(filepath);
            BufferedReader br = new BufferedReader(reader);
            String str = null;
            while((str = br.readLine()) != null) {
                  sb.append(str);     
            }
            br.close();
            reader.close();
            FileWriter writer = new FileWriter(filepath2);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(sb.toString());
            bw.close();
            writer.close();
      }
     
		catch(FileNotFoundException e) {
                  e.printStackTrace();
            }
            catch(IOException e) {
                  e.printStackTrace();
            }
      }
	    	
	public static void decode(String filePath,Map codelist1){
		List<String> list = new ArrayList<String>();
		PrintWriter pw2;
        try{
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "GBK");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null){
                String m=(String) codelist1.get(lineTxt);
                	list.add(m);
                	System.out.print(m);
                	try {
                		pw2 = new PrintWriter(new FileWriter("/Users/lifesaver/Desktop/Decode.txt"));
                		pw2.println(m);
            		} catch (IOException e) {
            			e.printStackTrace();
            		}   
                }
                bufferedReader.close();
                read.close();
            }
            else{
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
       
    }
	
	
	public static void decode2(String filepath,String filepath2, Map codelist2, Map codelist1) {  
	    BufferedReader br = null;  
	    File file = null;  
	    BufferedWriter bw = null;  
	    String b = null;  
	    file = new File(filepath2);   
	    if (!file.exists() != false) {  
	    	try {  
	    		file.createNewFile();  
	    	} catch (IOException e) {  
	    		e.printStackTrace();   
	    	}      
	    }  
	    try {  
	    	bw = new BufferedWriter(new FileWriter(file));  
	    	FileReader fr = new FileReader(filepath);  
	    	br = new BufferedReader(fr);  
	    	while ((b = br.readLine()) != null) {  
	    		if(b==(String)codelist2.get("/")){
	    			bw.newLine();  
	    		}else{
	    			b=(String) codelist1.get(b);
	    			bw.write(b);
	                bw.flush(); 
	                } 
	    	}  
	    } catch (Exception e) {  
	    	e.printStackTrace();  
	    }finally {  
	    	try {  
	    		br.close();  
	    		bw.close();  
	    	} catch (IOException e) {  
	    		e.printStackTrace();   
	    	}   
	    }      
	}  
	
	public static void main(String[] args) throws IOException {
		String test = file2String("/Users/lifesaver/Desktop/ATaleofTwoCities.txt", "Unicode");
		int[] charFreqs = new int[256];
		for (char c : test.toCharArray())
			charFreqs[c]++;	    
		HuffmanTree tree = buildTree(charFreqs);
		System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
		printCodes(tree, new StringBuffer());
		System.out.println();
		
		System.out.print("Codelist");
		for (String key : codelist2.keySet()) {
		    System.out.println(key + " ：" + codelist2.get(key));
		}
		System.out.println();
		System.out.println("Decodelist");
		for (String key : codelist.keySet()) {
		    System.out.println(key + " ：" + codelist.get(key));
		}
		
		System.out.println("Input the code you want to decode");
		Scanner open =new Scanner(System.in);
		String s = open.nextLine();
		System.out.println(codelist.get(s));
		open.close();
		
		writecode(test);
		
		//writecode2("/Users/lifesaver/Desktop/ATaleofTwoCities3.txt","/Users/lifesaver/Desktop/Write.txt",codelist2);
		//writecode3("/Users/lifesaver/Desktop/ATaleofTwoCities3.txt","/Users/lifesaver/Desktop/Write.txt",codelist2);
		
		//decode("/Users/lifesaver/Desktop/Write.txt",codelist);
		decode2("/Users/lifesaver/Desktop/Write.txt","/Users/lifesaver/Desktop/decode.txt",codelist2,codelist);	
	}	
}