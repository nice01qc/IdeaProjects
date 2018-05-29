import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MainTest {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("C:\\Users\\ljh\\Desktop\\toolbox\\IdeaProjects\\JavaIO\\src\\readme.txt");
        FileChannel fc = fin.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(8);
        fc.read(buffer);
        buffer.flip();
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
    }
}
