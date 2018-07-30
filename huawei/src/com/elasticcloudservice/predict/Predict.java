package com.elasticcloudservice.predict;

import java.util.*;

public class Predict {

    public static String[] predictVm(String[] ecsContent, String[] inputContent) {

        /** =========do your work here========== **/
        Mymethod.initParam(ecsContent, inputContent);

        List<String> results = new LinkedList<>();

        Mymethod.mainMethod(results, TimeType.DAY);


        return results.toArray(new String[results.size()]);

    }
}
