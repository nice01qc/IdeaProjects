import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] data = FileUtil.read("C:\\Users\\nice01qc\\Desktop\\toolbox\\IdeaProjects\\untitled2\\src\\surf.txt",null);
        List<String> list1 = new LinkedList<>();
        List<String> list2 = new LinkedList<>();
        List<String> list3 = new LinkedList<>();
        for (String x : data){
            String[] tmp = x.replaceAll("[ \t]+"," ").split(" ");
            list1.add(tmp[0]);
            list2.add(tmp[1]);
            list3.add(tmp[2]);
        }







        FileUtil.write("C:\\Users\\nice01qc\\Desktop\\toolbox\\IdeaProjects\\untitled2\\src\\surfice1.txt",list1.toArray(new String[list1.size()]),false);
        FileUtil.write("C:\\Users\\nice01qc\\Desktop\\toolbox\\IdeaProjects\\untitled2\\src\\surfice2.txt",list2.toArray(new String[list2.size()]),false);
        FileUtil.write("C:\\Users\\nice01qc\\Desktop\\toolbox\\IdeaProjects\\untitled2\\src\\sufice3.txt",list3.toArray(new String[list3.size()]),false);

    }
}
