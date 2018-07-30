package com.elasticcloudservice.predict;

public enum TimeType {
    MINUTE(1), HOUR(1), DAY(1), DAY2(2),DAY3(3),DAY4(4),DAY5(5),DAY6(6), WEEK(7);

    private int index;
    TimeType(int i) {
        this.index = i;
    }

    public int getIndex() {
        return index;
    }

}
