package test;

/**
 * 用于测试引用其他bean
 */
public class Boss {
    private Car1 car1 = new Car1();
    private Secretary secretary;

    public Secretary getSecretary() {
        return secretary;
    }

    public void setSecretary(Secretary secretary) {
        this.secretary = secretary;
    }

    public Car1 getCar1() {
        return car1;
    }

    public void setCar1(Car1 car1) {
        this.car1 = car1;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car1=" + car1 +
                ", secretary=" + secretary +
                '}';
    }
}
