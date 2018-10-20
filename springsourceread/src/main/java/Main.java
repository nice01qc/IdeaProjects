import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Main {

    public static void main(String[] args) throws IOException {

//        JarFile jarFile = new JarFile("");
//        InputStream inputStream = Thread.currentThread().getClass().getResourceAsStream("/META-INF/notice.txt");
//        Scanner in = new Scanner(inputStream);
//
//        while (in.hasNext()){
//            System.out.println(in.nextLine());
//        }
//
//        inputStream.close();
//        in.close();
//
//        System.out.println(Thread.currentThread().getClass().getResource("/META-INF/notice.txt").getPath());


        Properties properties = System.getProperties();
//        Enumeration enumeration = properties.propertyNames();
//        while (enumeration.hasMoreElements()){
//            String s = enumeration.nextElement().toString();
//            String ss  = properties.getProperty(s);
//            System.out.println(s + "\t" + ss);
//        }

        String classPath = properties.getProperty("java.class.path");
        String[] classpaths = classPath.split(";");
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < classpaths.length; i++) {
            if (classpaths[i].contains("spring") && classpaths[i].endsWith(".jar"))
//                System.out.println(classpaths[i]);
                list.add(classpaths[i]);
        }
        for (String x : list){
            if (x.contains("aop")){
                System.out.println(x);
                soutJar(x);
                System.out.println();
            }
        }


    }

    public static void soutJar(String path) throws IOException {
        JarFile jarFile = new JarFile(path);
        Enumeration<JarEntry> entryEnumeration = jarFile.entries();
        while (entryEnumeration.hasMoreElements()) {
            JarEntry jarEntry = entryEnumeration.nextElement();
//            jarFile.getInputStream(jarEntry);
            if (!jarEntry.toString().endsWith(".schemas")) continue;
            InputStream inputStream = jarFile.getInputStream(jarEntry);
            Scanner in = new Scanner(inputStream);
            while (in.hasNext()){
                System.out.println(in.nextLine());
            }
        }
    }

}
