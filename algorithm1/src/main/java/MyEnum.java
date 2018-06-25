import java.util.EnumMap;

public enum MyEnum {
    YELLOW,BLUE,BLACK;


    public static void main(String[] args) {
        MyEnum yellow = MyEnum.YELLOW;

        EnumMap<MyEnum,Integer> enumMap = new EnumMap<MyEnum, Integer>(MyEnum.YELLOW.getDeclaringClass());

        enumMap.put(MyEnum.BLACK,33);

        System.out.println(enumMap.get(MyEnum.BLACK));

    }
}
