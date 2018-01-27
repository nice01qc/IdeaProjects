package proxy;
// 被代理对象
public class MyCglibObject {
    public void say(){
        System.out.println("hello world ! --> cglib");
    }
}
