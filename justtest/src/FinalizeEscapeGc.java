import java.sql.Savepoint;

public class FinalizeEscapeGc {
    public static FinalizeEscapeGc SAVE_HOOK = null;
    static {
        System.out.println("hello .....");
    }
    public void isAlive(){
        System.out.println("yes , I am still alive :) ");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed ! ");
        FinalizeEscapeGc.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGc();

        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(100);

        if (SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("I am dead :(");
        }


        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);

        if (SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("I am dead :(");
        }

    }
}
