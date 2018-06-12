package serialize;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

// 反序列化
public class SerializesTestRead {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        ObjectInputStream in = new ObjectInputStream(new FileInputStream("E:/objectfile.obj"));

        Date obj1 = (Date)in.readObject();
        Student student = (Student)in.readObject();

        System.out.println(obj1);
        System.out.println(student);

    }
}
