package javaSock.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class ReplicateThree {
    public static void main(String[] args) throws Exception {
        FileWriter fileWriterreplicate = new FileWriter("D:\\mysoft\\redis-cluster-test\\redis\\replicate.bat");
        BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\mysoft\\redis-cluster-test\\redis\\nodes-6379.conf"));

        fileWriterreplicate.write("echo three step assign slots ...\n");
        HashMap<String,String> hashMap = new HashMap<>();
        String tmp = "";
        while ((tmp = bufferedReader.readLine()) != null){
            if (tmp.contains("63")){
                String[] strings = tmp.split(" ");
                if (strings.length < 2) throw new RuntimeException("nodes-6379.conf is note fit my require ");
                hashMap.put(strings[1].replaceAll("127.0.0.1:",""),strings[0]);
            }
        }

        String replication = "redis-cli -h 127.0.0.1 -p";
        for (int i = 6382; i <= 6384; i++){
            fileWriterreplicate.write(replication + " " + i + " cluster replicate" + " " + hashMap.get(String.valueOf(i-3)) + "\n");
        }
        fileWriterreplicate.write("pause");

        fileWriterreplicate.flush();
        fileWriterreplicate.close();
    }
}
