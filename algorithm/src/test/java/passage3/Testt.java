package passage3;

import org.junit.Test;

public class Testt {
    @Test
    public void test(){

        String str = "3fddd";
        int hash = TestHashCode.StringHashCode(str);
        System.out.println(hash+"\t"+str.hashCode());
    }
}
