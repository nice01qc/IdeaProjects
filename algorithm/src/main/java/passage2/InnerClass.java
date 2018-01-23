package passage2;

import java.util.Arrays;

public class InnerClass {
    int a =11;
    int b =22;
    int[] nice = new int[]{3,3,3,366        ,3,3,3};
    public Good getGood(){
        return new Good();
    }
    class Good{
        int a = InnerClass.this.a;
        int b = InnerClass.this.b;
        int[] nice = InnerClass.this.nice;
        public void show(){
            System.out.println(Arrays.asList(nice));
        }
    }

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();

    }
}
