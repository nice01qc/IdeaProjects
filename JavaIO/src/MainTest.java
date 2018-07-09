import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

public class MainTest {
    public static void main(String[] args) throws FileNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put(new Integer(0).byteValue());
        buffer.put(new Integer(0).byteValue());
        buffer.put(new Integer(0).byteValue());
        buffer.put(new Integer(11).byteValue());
        buffer.put(new Integer(0).byteValue());
        buffer.put(new Integer(0).byteValue());
        buffer.put(new Integer(11).byteValue());
        buffer.put(new Integer(11).byteValue());


        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getInt());

        System.out.println(Integer.toBinaryString(2827));
        System.out.println(Integer.toBinaryString(11));
        buffer.slice();
    }
}
