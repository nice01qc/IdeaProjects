public class XianCheng implements Runnable {
    @Override
    public void run() {

        while (true){

        }
    }


    public static void main(String[] args) {
        Thread thread8 = new Thread(new XianCheng(),"qiangge");

        thread8.start();


    }
}
