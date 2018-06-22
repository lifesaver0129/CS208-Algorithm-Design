/**
 * 
 * @author zhaoyao
 *
 */
package algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 类Human包含着男人或女人的基本信息，包括姓名、异性列表（包含异性名字及其分数，已按照分数由高到低排序）、当前表白的位置、
 * 异性名称、是否单身、恋爱史
 * @version 1.0 1 Mar 2017
 * @author 	zhao yao
 */
public class Human {
    public String name;
    public Map<String, Integer> map_expect;
    Iterator<Map.Entry<String, Integer>> it_expect;
    public String company;
    public boolean isfree;
    public ArrayList<String> history;
    
//  构造函数，异性列表创建对象，但为空
    public Human(String name){
        this.name = name;
        this.map_expect = new LinkedHashMap<String, Integer>();
        company = "";
        isfree  = true;
        history = new ArrayList<String>();
    }
// 给异性列表赋值
    public void addexpectlist(Map<String, Integer> expectlist){
        this.map_expect = expectlist;
        it_expect = this.map_expect.entrySet().iterator();
    }
// 获取此人的异性列表
    public Map<String, Integer> getexpectlist(){
        return map_expect;
    }
//  与入参的人约会
    public void date(String name){
        company = name;
        isfree  = false;
    }
//  与入参的人分手，写入恋爱史
    public void part(String name){
        company = "";
        isfree  = true;
        history.add(name);
    }
//  辅助调试，打印自身的信息
    public void printself(){
        System.out.println("=====================================");
        System.out.println("Name:"+this.name);
        System.out.println("Company's Name:"+this.company);
        System.out.println("map_expect:");
        System.out.println(this.map_expect);
        System.out.println("history:");
        System.out.println(this.history);
        System.out.println("=====================================");
    }
}

