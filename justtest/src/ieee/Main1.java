package ieee;

import java.util.Scanner;

public class Main1 {
    public static void main(String[] args) {
        double result = 0.0;
        Scanner cin = new Scanner(System.in);
        int num = Integer.valueOf(cin.nextLine());
        for (int i = 0; i < num; i++){
            String tmp = cin.nextLine();
            String[] two = tmp.split(" ");
            result += Math.sin(Math.PI*Double.valueOf(two[0])/180.0)*Double.valueOf(two[1]);
        }
        System.out.println((double)Math.round(result*100)/100);
    }
}
