package hash;

public class HashTest {


    public static void main(String[] args) {
        for (int i = 0; i < 60; i++){
            double tmp = Math.random()*10e8;
            System.out.print(hashget((int)tmp) + "\t");
            if (i%20==0) System.out.println();
        }

    }



    public static int hashget(int x){
        return (int) ((Math.pow(x,2))/100 % 20);
    }
}
