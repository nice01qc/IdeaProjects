package javaSock.test;

import java.io.*;

public class WriteAssignSlot {
    public static void main(String[] args) throws IOException {

        FileWriter fileWriter = new FileWriter("D:\\mysoft\\redis-cluster-test\\redis\\assign-slot.bat");

        String zero = "redis-cli -h 127.0.0.1 -p 6379 cluster meet 127.0.0.1";
        String one = "redis-cli -h 127.0.0.1 -p 6379 cluster addslots";
        String two = "redis-cli -h 127.0.0.1 -p 6380 cluster addslots";
        String three = "redis-cli -h 127.0.0.1 -p 6381 cluster addslots";
        String[] begin = {one,two,three};

        fileWriter.write("echo one step meet ...\n");
        for (int i = 6380; i <= 6384; i++){
            fileWriter.write(zero + " " + i);
            fileWriter.write("\n");
        }

        fileWriter.write("echo two step assign slots ...\n");
        int j=0;
        for (int i = 0; i<begin.length; i++){
            StringBuilder stringBuilder = new StringBuilder(begin[i]);
            for (j = 16384/begin.length*i; j <16384/begin.length*(i+1); j++){
                stringBuilder.append(" " + j);
                if (j % 100 == 0 || j == 16384/begin.length*(i+1)-1){
                    fileWriter.write(stringBuilder.toString());
                    fileWriter.write('\n');
                    stringBuilder = new StringBuilder(begin[i]);
                }
            }

            fileWriter.write('\n');
        }
        while (j-1 < 16383){
            fileWriter.write(begin[begin.length-1] + " " + j);
            fileWriter.write('\n');
            j++;
        }
        fileWriter.write("pause");
        fileWriter.flush();
        fileWriter.close();

    }
}
