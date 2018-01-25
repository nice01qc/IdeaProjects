###Java类加载流程
Java 语言系统自带有三个类加载器：<br/>
1. **Bootstrap ClassLoader**：最顶层的加载类，主要加载核心类库<br/>
2. **Extention ClassLoader**:扩展加载类，主要加载ext目录jar包<br/>
3. **Appclass Loader/SystemAppClass**:加载当前应用的classPath的所有类<br/>

#####类加载通过双亲委托方案（具体见那篇博客）

上面已经详细介绍了加载过程，但具体为什么是这样加载，我们还需要了解几个个重要的方法
    loadClass()、findLoadedClass()、findClass()、defineClass()<br/>

#####元注解
元标签有 @Retention、@Documented、@Target、@Inherited、@Repeatable 5 种。<br/>
Retention：应用到一个注解上的时候，它解释说明了这个注解的的存活时间（RetentionPolicy.SOURCE）<br/>
Documented：它的作用是能够将注解中的元素包含到 Javadoc 中去。<br/>
Target：指定了注解运用的地方(ElementType.TYPE)<br/>
Inherited：父类被注解后，子类可以继承父类注解。<br/>
Repeatable：可以被反复使用，其变量可以取很多个值。<br/>


> Java类加载：http://blog.csdn.net/briblue/article/details/54973413<br/>
> 注解：http://blog.csdn.net/briblue/article/details/73824058