import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class Piped {
    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);

        Thread printThread = new Thread(new Print(in), "PrintWrite");
        printThread.start();
        int receive = 0;
        try {
            while ((receive = System.in.read()) != 10) {
                out.write(receive);
                System.out.println("receive: " + receive);
            }
        } finally {
            out.close();
        }
    }


    static class Print implements Runnable {
        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive = 0;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
                System.out.println();
            } catch (IOException e) {

            }
        }
    }
}
