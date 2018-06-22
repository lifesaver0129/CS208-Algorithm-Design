package cl01_stable_matching;

public class Single {

	private int id;
    private int chasedp=0;   //追求过几位女生
    private int[] rank;
    private boolean date=false;
    private int present=-1;

    public int getId(){
    	return id;
    }
    public void setId(int i){
    	this.id=i;
    }
    public int getChasedp() {
        return chasedp;
    }
    public void setChasedp(int chase) {
        this.chasedp = chase;
    }
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

class Man extends Single{
	
}

class Woman extends Single{
	
}