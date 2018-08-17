package provider;

import java.util.ArrayList;
import java.util.List;

public class DemoServiceImp implements DemoService {
    public String sayHello(String name) {
        return "Hello " + name;
    }

    public List getUsers() {

        List list = new ArrayList();
        User u1 = new User();
        u1.setAge(12);
        u1.setName("Jake");
        u1.setSex("men");

        User u2 = new User();
        u2.setAge(22);
        u2.setName("Kangkang");
        u2.setSex("girl");

        list.add(u1);
        list.add(u2);

        return list;
    }
}
