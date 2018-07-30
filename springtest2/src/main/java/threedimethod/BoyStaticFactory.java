package threedimethod;

public class BoyStaticFactory {

    public static Boy getBoy(String name){
        return new Boy(name);
    }
}
