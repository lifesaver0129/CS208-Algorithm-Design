import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Edward on 2017/3/10.
 */
public class Person {
    private List<Person> preferenceList;
    private HashMap<Person,Integer> preferenceMap;
    private int preferancePointer = -1;
    private int currentMatchingIndex = -1;
    private boolean gender_female = false;
    private Person matching = null;
    private String id = "";
    private Map<Person, Integer> reverseList = null;

    public void setReverseList(Map<Person, Integer> reverseList) {
        this.reverseList = reverseList;
    }

    public Person(boolean gender, String id) {
        this.gender_female = gender;
        this.id = id;
    }

    public void setPreferanceList(List<Person> preferanceList) {
        this.preferenceList = preferanceList;
    }
    public void setPreferenceMap(HashMap<Person,Integer> map){
        this.preferenceMap=map;
    }

    public String getId() {
        return this.id;
    }

    public String outputMatching() {
        StringBuilder tmp = new StringBuilder(this.getId());
        if (!this.isfree()) {
            tmp.append("--");
            tmp.append(this.matching.id);
        } else {
            tmp.append("--none");
        }
        return tmp.toString();

    }

    public Person getFirstNotPropersol() {
        this.preferancePointer += 1;
        return this.preferenceList.get(this.preferancePointer);
    }

    public void setMatching(Person matching) {
        this.matching = matching;
        //System.out.println(this.outputMatching());;
    }

    public String outputPreferance() {
        StringBuilder list = new StringBuilder(this.getId() + "\t");
        if(isGender_female()){
            Iterator it = this.preferenceMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Person p =(Person) pair.getKey();
                list.append(p.getId()+":"+pair.getValue()+"\t");
            }
        }else{
            for (Person person : this.preferenceList) {
                list.append(person.getId() + " \t");
            }
        }
        list.append("\n");
        return list.toString();
    }

    public Person beProposaled(Person man) {
        //int newIndex = this.preferenceList.indexOf(man);
        int newIndex = getRank(man);
        if (this.isfree()) {
            this.matching = man;
            this.currentMatchingIndex = newIndex;
            man.setMatching(this);
            return null;
        } else if (newIndex < this.currentMatchingIndex) {
            Person breakUpMan = this.matching;
            this.currentMatchingIndex = newIndex;
            this.matching = man;
            man.setMatching(this);
            return breakUpMan;
        } else {
            return null;
        }
    }


    public boolean isfree() {
        return matching == null;
    }

    public int getRank(Person man) {
        return this.preferenceMap.get(man);
    }

    public boolean isGender_female() {
        return gender_female;
    }

}
