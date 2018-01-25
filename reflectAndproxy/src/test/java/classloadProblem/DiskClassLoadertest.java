package classloadProblem;

import org.junit.Test;
public class DiskClassLoadertest {
    @Test
    // 测试DisClassLoader 是否可用
    public void test1() throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        DiskClassLoader diskLoader = new DiskClassLoader("D:");
        // 在D:放入Show.class便可进入测试
        Class c = diskLoader.loadClass("classloadProblem.Show");

        if (c != null){
            Object obj = c.newInstance();
            Show show = (Show) obj;
            System.out.println("this is succeed !");
        }
    }
}
