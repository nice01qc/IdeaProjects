package proxy;

public class ProxyObject implements ProxyInaterface{
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "ProxyObject{" +
                "age=" + age +
                '}';
    }

    @Override
    public void nice() {
        System.out.println("this is nice()....");
    }
}
