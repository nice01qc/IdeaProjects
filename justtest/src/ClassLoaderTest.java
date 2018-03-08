import java.io.IOException;
import java.io.InputStream;

public class ClassLoaderTest {

    public static void main(String[] args) throws Exception{

        ClassLoader myLoader = new ClassLoader(){


            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                String fileName = name.substring(name.indexOf(".")+1) + ".class";
                InputStream is = getClass().getResourceAsStream(fileName);

                if (is == null){
                    return super.loadClass(name);
                }

                try {
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    System.out.println("my classload....");
                    return defineClass(name,b,0,b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ClassNotFoundException(fileName);
                }
            }

            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                return super.findClass(name);
            }
        };


        Object obj = myLoader.loadClass("ClassLoaderTest").newInstance();

        System.out.println(obj.getClass());

        System.out.println(obj instanceof ClassLoaderTest);

    }



}
