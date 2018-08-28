package com.nice01qc.serial;

import java.io.Serializable;

public class PerSon implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private long userid;
    private String name;
    private int age;
    private String addr;
    private String eMail;
    private String userRole;

    @Override
    public String toString() {
        return "PerSon{" +
                "userid=" + userid +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", addr='" + addr + '\'' +
                ", eMail='" + eMail + '\'' +
                ", userRole='" + userRole + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
