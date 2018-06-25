package find;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Just {

    public static void main(String[] args) throws Exception {

        TreeMap<String,Integer> treeMap = new TreeMap<>();
        treeMap.put("2",2);
        treeMap.put("1",1);
        treeMap.put("3",3);

        for (Map.Entry x : treeMap.entrySet()){
            System.out.println(x.getKey());
        }


        System.out.println(Runtime.getRuntime().availableProcessors());


    }

    public static void filter(List<String> names, Predicate condition) {
        for (String name : names){
            if (condition.test(name)){{
                System.out.println(name);
            }}
        }

    }
}
