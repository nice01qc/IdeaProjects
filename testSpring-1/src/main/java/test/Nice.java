package test;

import java.awt.*;

public enum Nice {
    RED(225,0,0),BLUE(0,0,255),BLACK(0,0,0),YELLOW(255,255,0),GREEN(0,255,0);
    private int redValue;
    private int greenValue;
    private int blueValue;

    private Nice(int rv,int gv,int bv){
        this.redValue = rv;
        this.greenValue = gv;
        this.blueValue = bv;
    }
    public String toString(){
        return super.toString()+"("+redValue+"," + greenValue + "," + blueValue + ")";
    }

    public static void main(String[] args) {
        for (Nice nice : Nice.values()){
            System.out.println(nice.toString());
        }
    }
}
