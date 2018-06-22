import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by Edward on 2017/3/10.
 */
public class GSAlgorithm {
    public static boolean match(Set menset, Set womenset) {
        // check numbers
        if (menset.size() != womenset.size() || menset.size() == 0 || menset == null)
            return false;

        // start to Propose
        Queue<Person> to_proposal = new LinkedList<Person>();
        to_proposal.addAll(menset);
        while (!to_proposal.isEmpty()) {
            Person man = to_proposal.poll();
            Person first_woman = man.getFirstNotPropersol();
            // code of next line is very important
            Person result = first_woman.beProposaled(man);
            if (result != null) to_proposal.add(result);
            if (man.isfree()) to_proposal.add(man);
        }
        return true;
    }
}

