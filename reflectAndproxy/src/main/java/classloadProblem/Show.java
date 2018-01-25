package classloadProblem;

import sun.misc.Launcher;

public class Show {
    public static void main(String[] args) throws ClassNotFoundException {
        // Bootstrap 目录有
        String[] boot = System.getProperty("sun.boot.class.path").split(";");
        for (int i = 0; i < boot.length; i++){ System.out.println(boot[i]); }

        // Extension 目录有
        System.out.println();
        String[] extend = System.getProperty("java.ext.dirs").split(";");
        for (int i = 0; i < extend.length; i++){ System.out.println(extend[i]); }

        // AppClassLoader目录有
        System.out.println();
        String[] app = System.getProperty("java.class.path").split(";");
        for (int i = 0; i < app.length; i++){ System.out.println(app[i]); }

        // 查看用的是哪个加载器,
        ClassLoader c1 = Show.class.getClassLoader();
        System.out.println("-------> Cl ClassLoader is: " + c1.toString());
        System.out.println("-------> Cl ClassLoader'parent is: " + c1.getParent().toString());
//        System.out.println("-------> Cl ClassLoader'parent'parent is: " + c1.getParent().getParent().toString());



    }
}
