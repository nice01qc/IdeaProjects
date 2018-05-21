package ieee;

import java.util.ArrayList;
import java.util.Scanner;

public class TimeEssence {
    public static void main(String[] args) {
        String result = "";
        Scanner cin = new Scanner(System.in);
        String data1 = cin.nextLine();
        int num = Integer.valueOf(cin.nextLine());
        String[] datas = data1.split(" ");

        ArrayList<String> unit = new ArrayList<>();
        ArrayList<Integer> unitNum = new ArrayList<>();
        for (int i = 1; i < datas.length; i++){
            if (i % 2 == 0){
                unitNum.add(Integer.valueOf(datas[i]));
            }else {
                unit.add(datas[i]);
            }
        }

        double big = num+0.0;
        for (int i = unitNum.size()-1; i >= 0; i--){
            big /= unitNum.get(i);
        }
        result += (int)Math.round(big) + " " + unit.get(0) + "\n";
        result += (int)big +  " " + unit.get(0) + " ";

        big = big - (double) ((int)big);
        big = Math.round(big*unitNum.get(0));

        result += (int)big + " " + unit.get(1);

        System.out.println(result);

    }
}
