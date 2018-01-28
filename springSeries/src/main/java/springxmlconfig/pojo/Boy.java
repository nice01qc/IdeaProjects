package springxmlconfig.pojo;

public class Boy {
    int a;

    public void boysay(){
        System.out.println("I am a boy !");
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    @Override
    public String toString() {
        return "Boy{" +
                "a=" + a +
                '}';
    }
}
