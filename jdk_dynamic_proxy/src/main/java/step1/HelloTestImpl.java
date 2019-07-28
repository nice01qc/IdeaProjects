package step1;

public class HelloTestImpl implements HelloTest {
    public void say(String name) {
        System.out.println("hello: " + name);
    }

    public void smile() {
        System.out.println("hello: simle...");
    }
}
