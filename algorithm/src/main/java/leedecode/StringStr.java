package leedecode;

public class StringStr {
    public static void main(String[] args) {
        String str = "12.3 12.44 14.85 23.523 2.3 12.44 14.85 23.523 2.3 12.44 14.85 23.523";
        String[] strings = str.split(" ");
        for (int i = 0;i<strings.length;i++){
            System.out.print(strings[i]+"\t");
        }
        for (int i =0;i<strings.length;i++){
            strings[i] = strings[i].substring(strings[i].lastIndexOf(".")+1);
        }
        System.out.println();
        for (int i = 0;i<strings.length;i++){
            System.out.print(strings[i]+"\t");
        }
    }
}
