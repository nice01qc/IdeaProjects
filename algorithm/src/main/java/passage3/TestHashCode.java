package passage3;

public class TestHashCode {
    public static int StringHashCode(String str) {
        int hash = 0;
        int R = 123;
        int M = 78979;
        for (int i = 0;i<str.length();i++) {
            hash = (R*hash + str.charAt(i)) % M;
        }
        return hash;
    }
}
