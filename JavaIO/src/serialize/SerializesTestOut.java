package serialize;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

// 序列化
public class SerializesTestOut {

    public static void main(String[] args) throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("E:/objectfile.obj"));
//        out.writeObject("Hello");
        out.writeObject(new Date());
        out.writeObject(new Student(33,"Jiange"));


    }


}
