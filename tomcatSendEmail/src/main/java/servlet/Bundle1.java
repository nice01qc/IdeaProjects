package servlet;

import java.util.Locale;
import java.util.ResourceBundle;

public class Bundle1 {
    public static final String PROPERTIES_FILE_NAME = "nice";
    public static final String MY_NAME = "name";
    public static final String MY_VALUE = "value";


    private static String myName;
    private static String myValue;
    static {
        try{
            ResourceBundle bundle = ResourceBundle.getBundle(PROPERTIES_FILE_NAME);
            myName = bundle.getString(MY_NAME);
            myValue = bundle.getString(MY_VALUE);
        }catch(Exception ex){
            System.err.println("[Property]:Can't Load property.properties");
            myName = "default name";
            myValue = "default value";
            System.out.println(  "myName will use the default value: " + myName);
            System.out.println(  "myValue will use the default value: " + myValue);
        }
    }

    public static void main(String[] args) {
        System.out.println(Bundle1.myName+":"+Bundle1.myValue);

        Locale d = new Locale("https://www.baidu.com");
        System.out.println(d.getCountry());
        System.out.println(d.getDisplayName());

    }
}
