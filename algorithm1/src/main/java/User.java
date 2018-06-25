import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

class User{

    public User() throws IOException {
        System.out.println("user 构造方法被调用");
    }
    private String name;
    private int age;
    private static String id="USER_ID";

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +'\'' +
                ", id=" + id +'\'' +
                '}';
    }


    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> x = User.class;

        Constructor[] constructor = x.getConstructors();
        User user = (User) constructor[0].newInstance();
        System.out.println(user);
        System.out.println(x.getGenericSuperclass());
        Type[] types = constructor[0].getGenericExceptionTypes();

        System.out.println(types.length);
        System.out.println(types[0].getTypeName());

        Field[] fields = x.getDeclaredFields();
        for (Field field : fields){
            System.out.println(field.getName() + "\t" + field.getType() + "\t" + field.get(user));
        }


        System.out.println(fields.length);


    }
}