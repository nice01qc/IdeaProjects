package byte_to_string;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Read {
    public static void main(String[] args) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\ljh\\Desktop\\toolbox\\IdeaProjects\\JavaIO\\src\\byte_to_string\\nice.txt");
        byte[] bytes = new byte[fileInputStream.available()];
        fileInputStream.read(bytes);

        System.out.println("length: " + bytes.length);
        System.out.println(new String(bytes));


    }
}
