package springxmlconfig.pojo;

public class Girl {
    String name;
    public void girlSay(){
        System.out.println("girl say..........");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Girl{" +
                "name='" + name + '\'' +
                '}';
    }
}
