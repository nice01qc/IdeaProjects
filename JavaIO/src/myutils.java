public class myutils {

    public static void printTack(){
        StackTraceElement[] st = Thread.currentThread().getStackTrace();
        if (st == null){
            return;
        }
        StringBuilder sbf = new StringBuilder();
        for (StackTraceElement e : st){
            if (sbf.length() > 0){
                sbf.append("<-").append(System.getProperty("line.separator"));
            }
            sbf.append(java.text.MessageFormat.format("{0}.{1}() {2}",e.getClassName()
            ,e.getMethodName(),e.getLineNumber()));
        }

        System.out.println(sbf.toString());
    }


    public static synchronized  void jiasuo(){
        int i = 1;
        i++;
    }

    public static  void nojiasuo(){
        int i = 1;
        i++;
    }

    public static void main(String[] args) {
        int num = 111111111;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < num; i++){
            jiasuo();
        }
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);

        long t3 = System.currentTimeMillis();
        for (int i = 0; i < num; i++){
            nojiasuo();
        }
        long t4 = System.currentTimeMillis();
        System.out.println(t4 - t3);

    }

}
