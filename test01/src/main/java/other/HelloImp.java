package other;

public class HelloImp implements Hello {

    @Override
    public void say(String name) {
        System.out.println(name);
    }


    public void say1() {
        System.out.println("say1....");
    }
}
