package test;


/**
 * 非静态工厂方法
 */
public class CarFactory3 {
    //创建Car的非静态工厂方法
    public Car1 createCar(){
        Car1 car1 = new Car1();
        car1.setMaxSpeed("23344");
        car1.setBrand("aodi");
        return car1;
    }

    //创建Car的静态工厂方法
    public static Car1 createCar1(){
        Car1 car1 = new Car1();
        car1.setMaxSpeed("666");
        car1.setBrand("静态");


        return car1;

    }
}
