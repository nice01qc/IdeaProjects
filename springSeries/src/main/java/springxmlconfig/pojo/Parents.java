package springxmlconfig.pojo;

public class Parents {
    Boy boy;
    Girl girl;

    public Boy getBoy() {
        return boy;
    }

    public void setBoy(Boy boy) {
        this.boy = boy;
    }

    public Girl getGirl() {
        return girl;
    }

    public void setGirl(Girl girl) {
        this.girl = girl;
    }

    @Override
    public String toString() {
        return "Parents{" +
                "boy=" + boy +
                ", girl=" + girl +
                '}';
    }
}
