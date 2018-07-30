package com.elasticcloudservice.predict;

/**
 * 存储服务器相关信息的类
 */
class Flavor {
    public int kind;
    public int cpu;
    public int mem;
    public int memG;

    public Flavor(int kind, int cpu, int mem) {
        this.kind = kind;
        this.cpu = cpu;
        this.mem = mem;
        this.memG = mem / 1024;
    }

    public String getFlavorName() {
        return "flavor" + kind;
    }

    @Override
    public String toString() {
        return "flavor" + kind + " " + cpu + " " + mem;
    }
}