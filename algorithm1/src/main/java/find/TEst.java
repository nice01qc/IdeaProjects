package find;

public class TEst {
    public static void main(String[] args) {
        Boy boy1 = new Boy();
        Boy boy11 = new Boy();
        Boy boy2 = new Boy();
        Boy boy22 = new Boy();
        int num = 111111012;

        long t1 = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                Boy tmp = boy1;
                boy1 = boy11;
                boy11 = tmp;
            }
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
        long t3 = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                int tmp = boy1.age;
                boy1.age = boy11.age;
                boy11.age = tmp;
            }
        }

        long t4 = System.currentTimeMillis();
        System.out.println(t4 - t3);

    }
}
