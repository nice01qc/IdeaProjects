package one;

public class JustTEst {
    static int i = 1;
    public static void fun(){
        System.out.println(i++);
        fun();
    }


    public static void main(String[] args) {
        fun();
    }
}
