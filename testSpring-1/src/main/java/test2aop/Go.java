package test2aop;

public class Go implements G{
    public void go(){
        System.out.println("go go go go go");
    }

    public int get() {
        return 0;
    }

    public int div(int x, int y) {
        return x/y;
    }
}
