package hash;

public class Test {


    public static void main(String[] args) {
        Test test = new Test();
        System.out.println(test.digui2(1000) + " nodigui");;
        System.out.println(test.digui22(1000) + " yesdigui");;
    }


    int digui2(int x){
        long time1 = System.currentTimeMillis();

        int result = 0;
        for (int i = 0; i <= x; i++){
            result += i*i;
        }

        long time2 = System.currentTimeMillis();

        System.out.println(result);
        return (int) (time2-time1);
    }

    int digui22(int x){
        long time1 = System.currentTimeMillis();

        int result = digui1(x);

        long time2 = System.currentTimeMillis();

        System.out.println(result);
        return (int) (time2-time1);
    }

    int digui1(int x){
        if (x <= 0) return 0;
        return digui1(x-1) +x*x;

    }
}
