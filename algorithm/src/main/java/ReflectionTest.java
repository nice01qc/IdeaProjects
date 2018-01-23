import java.lang.reflect.*;

public class ReflectionTest {

    public static void showClass(Class cl){
        Class supercl = cl.getSuperclass();
        String modifiers = Modifier.toString(cl.getModifiers());
        if (modifiers.length()>0){
            System.out.println(modifiers+" ");
        }
        System.out.print("class "+cl.getSimpleName());

        if (supercl !=null&&supercl!=Object.class){
            System.out.print(" extends "+supercl.getSimpleName());
        }
        System.out.print("\n{\n");
        printConstructors(cl);
        System.out.println();
        printMethods(cl);
        printFields(cl);
        System.out.println();
        System.out.println("  }");
    }


    /**
     * prints all contructors of a class
     *
     * @param cl
     */
    public static void printConstructors(Class cl) {
        Constructor[] constructors = cl.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            String name = constructor.getName().substring(constructor.getName().lastIndexOf(".")+1);
            String modifiers = Modifier.toString(constructor.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print("\t"+modifiers + " ");
            }
            System.out.print(name + "(");

            //print parameter types
            Class[] paramTypes = constructor.getParameterTypes();
            for (int j = 0; j < paramTypes.length; j++) {
                if (j > 0) {
                    System.out.println(", ");
                }
                System.out.print(paramTypes[j].getName());
            }
            System.out.print(");");
        }
    }

    /**
     * prints all methods of a class
     *
     * @param cl
     */
    public static void printMethods(Class cl) {
        Method[] methods = cl.getDeclaredMethods();
        for (Method method : methods) {
            Class retType = method.getReturnType();
            String name = method.getName();

            System.out.print("    ");
            //print modifiers,return type.and method name
            String modifiers = Modifier.toString(method.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print(modifiers + "  ");
            }
            System.out.print(retType.getName() + " " + name + "(");
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters){
                String paraName = parameter.getType().getName();
                System.out.print(paraName+" ");
            }
            System.out.print(")");
            System.out.println();
        }
    }

    /**
     * Prints all fields of a class
     *
     * @param cl
     */
    public static void printFields(Class cl) {
        Field[] fields = cl.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();
            String name = field.getName();
            System.out.println("   ");
            String modifiers = Modifier.toString(field.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print(modifiers + "  ");
                System.out.print(type.getName() + " " + name + ";");
            }
        }
    }

    public static void main(String[] args) {
//        printConstructors(ReflectionTest.class);
//        printMethods(ReflectionTest.class);
        showClass(ReflectionTest.class);
    }
}
