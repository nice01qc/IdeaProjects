package springxmlconfig.pojo1;

import org.springframework.stereotype.Component;

@Component("car1")  // 可以选择合理的名字，默认首字母小写
public class Car {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                '}';
    }
    public void carsay(){
        System.out.println("this is my car, ....");
    }
}
