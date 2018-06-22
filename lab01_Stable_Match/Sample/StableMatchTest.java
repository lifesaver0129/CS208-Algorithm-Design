package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import algorithm.FactoryHuman;
import algorithm.Human;
import algorithm.StableMatch;

public class StableMatchTest {
	StableMatch stableMatch;
	int n;
	@Before
	public void setUp() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入算法维度：");

		n = sc.nextInt();
		sc.close();
		
//      构造输入
        long ts = System.currentTimeMillis();
		
        FactoryHuman factoryHuman     = new FactoryHuman();
        Map<String, Human>  manlist   = new HashMap<String, Human>();
        Map<String, Human>  womanlist = new HashMap<String, Human>();
        List<String> manset   = new ArrayList<String>();
        List<String> womanset = new ArrayList<String>();
//      用工厂类批量创建男人或者女人对象
        for (int i = 0; i < n; i++)
        {
            Human man = factoryHuman.creatHuman('m');
            manlist.put(man.name, man);
            manset.add(man.name);

            Human woman = factoryHuman.creatHuman('w');
            womanlist.put(woman.name, woman);
            womanset.add(woman.name);

        }
        
//      初始化男人心目中的女人队列，随机化女人数组，从高到低分数排列
        Iterator<Map.Entry<String, Human>> it = manlist.entrySet().iterator();

        while(it.hasNext())
        {
            Map.Entry<String, Human> entry = (Map.Entry<String, Human>)it.next();
            Human man = entry.getValue();      //返回对应的值
            Collections.shuffle(womanset);

            Map<String, Integer> expectlist = new LinkedHashMap<String, Integer>();
            for (int i = 0; i<n; i++)
            {
                expectlist.put(womanset.get(i),n-i);
            }
            
            man.addexpectlist(expectlist);


        }
//      初始化女人心目中的男人队列，随机化男人数组，从高到低分数排列
        Iterator<Map.Entry<String, Human>> womanit = womanlist.entrySet().iterator();

        while(womanit.hasNext())
        {
            Map.Entry<String, Human> entry = (Map.Entry<String, Human>)womanit.next();
            Human woman = entry.getValue();      //返回对应的值
            Collections.shuffle(manset);

            Map<String, Integer> womanexpectlist = new LinkedHashMap<String, Integer>();
            for (int i = 0; i<n; i++)
            {
                womanexpectlist.put(manset.get(i),n-i);
            }
            woman.addexpectlist(womanexpectlist);

        }
        stableMatch = new StableMatch(manlist, womanlist, n);
        long te = System.currentTimeMillis();
        long cost = te-ts;
        System.out.println("n="+n+"setup time="+cost);
        
	}

	@Test
	public void testExe() {
		System.out.println("n="+n);
		long ts,te,cost;
//		for (int i = 0; i< 10; i++){
			
            ts = System.currentTimeMillis();
            
            stableMatch.exe();
            te = System.currentTimeMillis();
            cost = te-ts;
            System.out.println("n="+n+"running time="+cost);
//		}
//        stableMatch.printResult();
        long testts = System.currentTimeMillis();
//      检查维度数目为n
        assertEquals(n, stableMatch.n);
//      检查结果列表中男人的数目为n        
        assertEquals(n, stableMatch.map_men.keySet().size());
//      检查结果列表中女人的数目为n         
        Set<String > woman_set = new HashSet<String>();
        woman_set = stableMatch.map_women.keySet();
        assertEquals(n, woman_set.size());
//      检查男人的伴侣不重复且数目为n
        Set<String > man_company_set =  new HashSet<String>();
        for (Human man:stableMatch.map_men.values()){
        	man_company_set.add(man.company);
        }
        assertEquals(n,	man_company_set.size());
//      检查男人伴侣集合等于 女人集合
        assertEquals(woman_set, man_company_set);
//      检查每一对是否都是稳定匹配
        for (Human man:stableMatch.map_men.values()){
        	for (Human otherman:stableMatch.map_men.values()){
        		Human otherwoman = stableMatch.map_women.get(otherman.company);
        		if ((man.map_expect.get(man.company) < man.map_expect.get(otherman.company)) &&
        		    (otherwoman.map_expect.get(man.name) > otherwoman.map_expect.get(otherwoman.company))){
        		    fail("Exist unstable match!");
        			
        		}
        	}
        }
        long testte = System.currentTimeMillis();
        long testcost = testte-testts;
        System.out.println("n="+n+"test time="+testcost);
          
	}

}
