package filter;

public class Test {

    public static void main(String[] args) {
        System.out.println(getall("0123"));
    }



    public static int getall(String s){
        int result = 0;
        for (int i = 1; i < s.length();i++){
            result += find(s.substring(0,i),s.substring(i,s.length()));
        }
        return result;
    }

    private static int find(String a, String b){
        System.out.println(a + "  " + b );
        int anum = 0;
        int bnum = 0;
        if (a.length() < 1  || b.length() < 1) return 0;

        if (a.charAt(0) == '0' && a.length() != 1) return 0;
        if (b.charAt(0) == '0' && b.length() != 1) return 0;

        if (a.charAt(0) == '0' || a.length() == 1) anum = 1;
        else if (a.charAt(a.length()-1) == '0') anum = 1;
        else anum = a.length();

        if (b.charAt(0) == '0' || b.length() == 1) bnum = 1;
        else if (b.charAt(b.length()-1) == '0') bnum = 1;
        else bnum = b.length();

        return anum*bnum;



    }


}
