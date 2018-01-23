package test1;


import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Local1 {
    public static void main(String[] args) {
        Locale locale = new Locale("zh","CN");
        Locale locale1 = Locale.CHINA;
        Locale locale2 = Locale.CANADA;

        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale2);
        double x = 13.434224;
        System.out.println(numberFormat.format(x));

        DateFormat dataFormat = DateFormat.getDateInstance(DateFormat.FULL,locale2);
        Date date = new Date();
        System.out.println(dataFormat.format(date));

    }
}
