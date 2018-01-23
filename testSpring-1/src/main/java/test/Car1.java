package test;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyDescriptor;
public class Car1 {
    private String brand;
    private String color;
    private String maxSpeed;


    private BeanFactory beanFactory;
    private String beanName;
    public Car1() {
    }

    public Car1(String brand, String color, String maxSpeed) {
        this.brand = brand;
        this.color = color;
        this.maxSpeed = maxSpeed;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "Car1{" +
                "brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", maxSpeed='" + maxSpeed + '\'' +
                '}';
    }

    /**
     * 以下是实现Bean生命周期的接口，通过他们可以控制其中任何一个阶段
     *
     */
//    public Object postProcessBeforeInstantiation(Class<?> aClass, String s) throws BeansException {
//
////        System.out.println("postProcessBeforeInstantiation-->"+aClass.getName()+";----"+s.charAt(3)+"++");
//        return null;
//    }
//
//    public boolean postProcessAfterInstantiation(Object o, String s) throws BeansException {
////        System.out.println("postProcessAfterInstantiation-->"+o.equals(null)+";----?"+s);
//        return false;
//    }
//
//    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, PropertyDescriptor[] propertyDescriptors, Object o, String s) throws BeansException {
////        System.out.println("postProcessPropertyValues-->"+propertyValues.toString()+";"+s);
//        return null;
//    }
//
//    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
////        System.out.println("postProcessBeforeInitialization-->"+o.equals(null)+";"+s);
//        return o;
//    }
//
//    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
////        System.out.println("postProcessAfterInitialization-->"+o.equals(null)+";"+s);
//        return o;
//    }
//    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
////        System.out.println("调用BeanFactory.setBeanFactory()");
//        this.beanFactory = beanFactory;
//    }
//
//    public void setBeanName(String s) {
////        System.out.println("调用BeanNameAware.setBeanName().");
//        this.beanName = s;
//    }
//
//    public void destroy() throws Exception {
////        System.out.println("destroy ....");
//    }
//
//    public void afterPropertiesSet() throws Exception {
////        System.out.println("调用initializingBean.afterPropertiesSet().");
//    }



}
