package classloadProblem;

public class AboutClassForname {
    public static void main(String[] args) throws ClassNotFoundException {

// 默认为true ，加载类时进行静态初始化
        Class.forName("classloadProblem.Show",true,Thread.currentThread().getContextClassLoader());

// 默认为false ，加载类时不进行静态初始化
//        Class.forName("classloadProblem.Show",false,Thread.currentThread().getContextClassLoader());


    }
}
