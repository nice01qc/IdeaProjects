public class Testt1 {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Testt1 testt1 = new Testt1();
        Testt1 testt2 = Testt1.class.newInstance();
        Testt1 testt3 = Testt1.class.newInstance();

        System.out.println(testt1.hashCode());
        System.out.println(testt2.hashCode());
        System.out.println(testt3.hashCode());
    }
}
