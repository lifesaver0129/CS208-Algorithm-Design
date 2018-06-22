package cl01_stable_matching;

public class Woman {
    private int[] rank;
    private boolean date=false;
    private int present=100;
 
   
    public int[] getRank() {
        return rank;
    }
    public void setRank(int[] rank) {
        this.rank = rank;
    }
    public boolean isDate() {
        return date;
    }
    public void setDate(boolean date) {
        this.date = date;
    }
    public int getPresent() {
        return present;
    }
    public void setPresent(int present) {
        this.present = present;
    } 
}