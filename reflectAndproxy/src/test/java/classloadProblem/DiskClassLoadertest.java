package classloadProblem;

import org.junit.Test;
public class DiskClassLoadertest {
    @Test
    // 测试DisClassLoader 是否可用
    public void test1() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        DiskClassLoader diskLoader = new DiskClassLoader("E:\\lib");
        // 在D:\lib放入Show.class便可进入测试,测试时，由于target/classes在appClassLoader默认
        // 路径里面，因此需要保证没有此类，才会调用自己设计的路径里面的类
        Class c = diskLoader.loadClass("classloadProblem.Show");

        if (c != null){
            Object obj = c.newInstance();
            System.out.println("this is succeed !");
        }
    }
}
