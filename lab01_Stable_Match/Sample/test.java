/**
 * @author zhaoyao
 */

package algorithm;

import java.util.*;


public class test {
    public static void main(String[] args)
    {
        System.out.println(args);
        int n = 2;
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

//      创建稳定匹配，开始运算
        StableMatch stableMatch = new StableMatch(manlist, womanlist, n);
        long ts = System.currentTimeMillis();
        stableMatch.exe();
        long te = System.currentTimeMillis();
        long cost = te-ts;
        System.out.println("n="+n+"cost time="+cost);
        stableMatch.printResult();

    }
}
