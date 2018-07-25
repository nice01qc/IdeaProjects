package utils;

import one.ImageServlet;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GetAllMethod {

    public static void printHttpServletRequestAllMethodValue(Object request) throws InvocationTargetException, IllegalAccessException {
        Class<?> requestClass = request.getClass();

        Method[] methods = requestClass.getDeclaredMethods();

        for (Method method : methods){
            method.setAccessible(true);
            try{
                if (!method.isVarArgs() && method.getName().startsWith("get")){
                    Object object = method.invoke(request,null);
                    System.out.println(method.getName() + "(): " + object);
                }
            }catch (Exception e){

            }
        }

    }


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        printHttpServletRequestAllMethodValue(new Boy(23));
    }


}
