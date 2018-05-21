import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner cin = new Scanner(System.in);
        int num = Integer.valueOf(cin.nextLine());
        String dataStr = cin.nextLine().trim();
        String datas[] = dataStr.split(" ");
        if (datas.length != num) return;
        String pan = cin.nextLine().trim();

        int mixscore = 0;
        HashMap<String,Integer> result = new HashMap<>();
        String currentdui = "";
        for (int i = 0; i < pan.length(); i++){
            if (pan.charAt(i) == 'W'){
                currentdui = datas[0] + " " + datas[2];
                exch(datas,0,2);
                exch(datas,1,3);
                exch(datas,1,4);
                exchfinal(datas);
            }else {
                currentdui = datas[1] + " " + datas[3];
                exch(datas,1,3);
                exch(datas,0,2);
                exch(datas,0,4);
                exchfinal(datas);
            }
            if (result.containsKey(currentdui)){
                result.put(currentdui,result.get(currentdui)+1);
            }else {
                result.put(currentdui,1);
            }

            if (result.get(currentdui) > mixscore){
                mixscore = result.get(currentdui);
            }
        }

        for (String x : result.keySet()){
           System.out.println(x + " " + result.get(x));
            if (result.get(x) >= mixscore){
                System.out.println(x);
            }
        }


    }

    private static void exch(String[] datas, int a , int b){
        String tmp = datas[a];
        datas[a] = datas[b];
        datas[b] = tmp;
    }
    private  static void exchfinal(String[] datas){
        for (int i = 4; i < datas.length-1; i++){
            exch(datas,i,i+1);
        }
    }
}
