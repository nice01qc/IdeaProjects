package threedimethod;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Boy {
    String name;

    public Boy(String name) {this.name = name;}

    public Boy() {}

    public String getName() {return name;}

    public void setName(String name) { this.name = name;}
}
