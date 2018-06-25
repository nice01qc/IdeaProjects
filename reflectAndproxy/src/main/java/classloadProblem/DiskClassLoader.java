package classloadProblem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


// 自行创建的一个类加载器,默认先从自己的路径下寻找，没找到再抛给父类
public class DiskClassLoader extends ClassLoader {

    private String mLibPath;

    public DiskClassLoader(String path){
        mLibPath = path;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(mLibPath,fileName);
        try{
            FileInputStream is = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len = 0;    // 此处应该有问题,其实没问题，请见byte与int区别，只是范围大小而已，这样就相当于一个一个地读取
            try{
                while ((len = is.read()) != -1){
                    bos.write(len);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            byte data[] = bos.toByteArray();

            is.close();
            bos.close();
            System.out.println("see it ,which load from my direct");

            return defineClass(name,data,0,data.length);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    //  获取要加载的class文件名
    private String getFileName(String name){
        int index = name.lastIndexOf('.');
        if (index == -1){
            return name + ".class";
        }else{
            return name.substring(index+1)+".class";
        }
    }
}
