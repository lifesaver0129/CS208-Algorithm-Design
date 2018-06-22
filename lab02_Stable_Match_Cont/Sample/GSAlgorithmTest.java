/**
 * Created by Edward on 2017/3/10.
 */

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

class GSAlgorithmTest {
    static int n = 0;

    @Test
    public void testGS() {
        n += 500;
        // For output
        // StringBuilder out = new StringBuilder();
        // Generate persons and a list off random numbers
        Set<Person> womenset = new HashSet<Person>();
        Set<Person> menset = new HashSet<Person>();
        List<Integer> numList = new ArrayList<Integer>();
        for (int i = 1; i <= n; i++) {
            menset.add(new Person(false, "M" + Integer.toString(i)));
            womenset.add(new Person(true, "W" + Integer.toString(i)));
            numList.add(i);
        }
        // Generate men preference lists
        List<Person> womenlist = new LinkedList<Person>(womenset);
        Iterator<Person> it = menset.iterator();
        Person p = null;
        while (it.hasNext()) {
            p = it.next();
            Collections.shuffle(womenlist);
            p.setPreferanceList(womenlist);
            //out.append(p.outputPreferance());
        }
        // Generate Reverse Preference List for women
        it = womenset.iterator();
        while (it.hasNext()) {
            p = it.next();
            Collections.shuffle(numList);
            HashMap<Person, Integer> preMap = new HashMap<Person, Integer>();
            Iterator<Person> it_tmp1 = menset.iterator();
            Iterator<Integer> it_tmp2 = numList.iterator();
            while (it_tmp1.hasNext() && it_tmp2.hasNext()) {
                preMap.put(it_tmp1.next(), it_tmp2.next());
            }
            p.setPreferenceMap(preMap);
            //out.append(p.outputPreferance());
        }
        // Main Algorithm
        long startTime = System.currentTimeMillis();
        GSAlgorithm.match(menset, womenset);
        long endTime = System.currentTimeMillis();
        System.out.println("Time： " + (endTime - startTime) + "ms\nN = " + n);

//			Output the result
/*        it = menset.iterator();
          out.append("Result: ");
          while (it.hasNext()) {
              p = it.next();
              out.append(p.outputMatching());
              out.append("\t");
          }
          System.out.print(out);
*/
    }

}