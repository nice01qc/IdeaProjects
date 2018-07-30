package threedimethod;

public class BoyInstanceFactory {
    private Boy boy;

    public void setBoy(String name){
        this.boy = new Boy(name);
    }

    public Boy getBoy(){
        return boy;
    }
}
