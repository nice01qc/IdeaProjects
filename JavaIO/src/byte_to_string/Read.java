package byte_to_string;

import java.io.*;
import java.nio.charset.Charset;

public class Read {
    public static void main(String[] args) throws IOException {

        Charset charset = Charset.forName("UTF-8");
        FileReader fileReader = new FileReader("C:\\Users\\ljh\\Desktop\\toolbox\\IdeaProjects\\JavaIO\\src\\byte_to_string\\nice.txt");

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String tmp;
        while ((tmp = bufferedReader.readLine()) != null){
            String[] hexs = tmp.replaceAll("[ ]+"," ").split(" ");
            for (int i = 1; i < hexs.length; i++){

                System.out.print(Character.toString((char)Integer.parseInt(hexs[1],16)));
            }
        }


    }
}
