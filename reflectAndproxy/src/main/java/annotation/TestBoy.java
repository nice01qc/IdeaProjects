package annotation;

@Boy(name = "TestBoy",age = 28)
public class TestBoy {
    @Boy(age = 20,name = "int a")
    int a=100;
    @Boy(name = "string str")
    String str="haoge";

    public TestBoy(int a, String str) {
        this.a = a;
        this.str = str;
    }

    @Boy(name = "method getA !")
    public int getA() {
        return a;
    }

    @Boy(name = "method setA !")
    public void setA(@Boy(name = "parameter int a of method setA !") int a) {
        this.a = a;
    }

    @Boy(name = "method getStr !")
    public String getStr() {
        return str;
    }

    @Boy(name = "method setStr !")
    public void setStr(@Boy(name = "parameter String str of method setStr !") String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "TestBoy{" +
                "a=" + a +
                ", str='" + str + '\'' +
                '}';
    }
}
