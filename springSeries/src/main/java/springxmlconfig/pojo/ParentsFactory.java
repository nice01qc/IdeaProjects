package springxmlconfig.pojo;

public class ParentsFactory {

    public Boy getBoy(){
        return new Boy();
    }

    public Girl getGirl(){
        return new Girl();
    }

    public static Boy getStaticBoy(){
        return new Boy();
    }

    public static Girl GEtStaticGirl(){
        return new Girl();
    }

}
