###Java类加载流程
Java 语言系统自带有三个类加载器：<br/>
1. **Bootstrap ClassLoader**：最顶层的加载类，主要加载核心类库<br/>
2. **Extention ClassLoader**:扩展加载类，主要加载ext目录jar包<br/>
3. **Appclass Loader/SystemAppClass**:加载当前应用的classPath的所有类<br/>

####类加载通过双亲委托方案（具体见那篇博客||）####

上面已经详细介绍了加载过程，但具体为什么是这样加载，我们还需要了解几个个重要的方法
    loadClass()、findLoadedClass()、findClass()、defineClass()<br/>







> 出自：http://blog.csdn.net/briblue/article/details/54973413