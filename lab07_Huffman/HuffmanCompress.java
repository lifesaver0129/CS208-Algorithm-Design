import java.io.*;
import java.util.*;
class HuffmanNode{    //HuffmanNode哈夫曼树的节点类
    char symbol;   //字符
    int freq;  //字符的频率即权重
    HuffmanNode parent;
    HuffmanNode lchild;
    HuffmanNode rchild;
    String HuffmanCode;
    //两种构造方法
    HuffmanNode(char c, int f){
        symbol = c;
        freq = f;
        parent = lchild = rchild = null;
    }
    HuffmanNode(HuffmanNode lc, HuffmanNode rc){
        lchild = lc;
        rchild = rc;
        lc.parent = rc.parent = this;
        freq = lc.freq + rc.freq;
    }
}
public class HuffmanCompress{  //实现Huffman压缩的方法
    static int[] freq = new int[26]; 
    static String[] code = new String[26];
    public static void Count(String s){   //统计词频
        int n;
        try{
            FileReader filereader = new FileReader(s);
            while ((n = filereader.read()) != -1)
                freq[n>96 ? n-97 : n-65]++;   //不区分大小写
            filereader.close();
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public static HuffmanNode createHuffmanTree(Vector<HuffmanNode> list){   //创建Huffman树
        if(list.size() == 1){
            return list.firstElement();
        }
        else{
            Iterator<HuffmanNode> iter = list.iterator();
            HuffmanNode a = iter.next();
            HuffmanNode b = iter.next();
            while (iter.hasNext()){
                HuffmanNode c = iter.next();
                if (c.freq < a.freq && a.freq > b.freq)
                    a = c;
                else if (c.freq < b.freq && a.freq < b.freq)
                    b = c;
            }
            HuffmanNode merged = new HuffmanNode(a, b);  //合并
            list.remove(a);
            list.remove(b);
            list.add(merged);
            return createHuffmanTree(list);
        }
    }
    public static void Traverse(HuffmanNode node){   //遍历树得到Huffman编码
        if (node.parent == null){
            node.lchild.HuffmanCode = "0";
            node.rchild.HuffmanCode = "1";
        }
        else if (node.lchild == null){
            code[node.symbol-65] = node.HuffmanCode;
            return;
        }
        else{
            node.lchild.HuffmanCode = node.HuffmanCode + "0";
            node.rchild.HuffmanCode = node.HuffmanCode + "1";
        }
        Traverse(node.lchild);
        Traverse(node.rchild);
    }
    public static void Compress(String inFile, String outFile){    //压缩
        try{
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(outFile));
            for (int i=0; i < 26; ++i){
                if (freq[i] != 0){
                    System.out.print((char)(i+65) + "--------" + code[i] + "\n");
                }
            }
            FileReader fr = new FileReader(inFile);
            int ch;
            StringBuilder temp = new StringBuilder();
            while ((ch = fr.read()) != -1){
                temp.append(code[ch<97 ? ch-65 : ch-97]);
            }
            fr.close();
            String str = temp.toString();
            char[] charr = str.toCharArray();
            dos.writeInt(str.length()%8); //标记最后一个字节有多少位补0
            System.out.println();
            byte bt = 0;
            for (int i=0; i+8 < str.length(); i += 8){
                for (int j=i; j-i < 8; ++j){
                    bt <<= 1;
                    if (charr[j] == '1')
                        bt++;
                }
                dos.writeByte(bt);
            }
            for (int i=str.length()-str.length()%8; i < str.length(); ++i){
                bt <<= 1;
                if (charr[i] == '1')
                    bt++;
            }
            bt <<= (8 - str.length()%8); //补0
            dos.writeByte(bt);
            dos.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        System.out.print("输入目标文件路径：");
        Scanner in = new Scanner(System.in);
        String inFilepath = in.nextLine();
        Count(inFilepath); //统计字母权重
        Vector<HuffmanNode> v = new Vector<HuffmanNode>();
        for (int i=0; i < 26; ++i){
            if (freq[i] != 0){
                HuffmanNode temp = new HuffmanNode((char)(i+65), freq[i]);
                v.add(temp);
            }
        }
        HuffmanNode root = createHuffmanTree(v);
        Traverse(root);
        System.out.print("输入压缩后文件路径:");
        String outFilepath = in.nextLine();
        in.close();
        Compress(inFilepath, outFilepath);
    }
}