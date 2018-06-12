package javaSock.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class TestUDP {
    public static void main(String[] args) throws IOException {

        // 服务器端，实现基于UDP的用户登录
        // 创建服务器端DatagramSocket，指定端口
        DatagramSocket datagramSocket = new DatagramSocket(10001);

        // 创建数据报，用于接受客户端发送的数据
        byte[] data = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(data,data.length);

        System.out.println("1-> " + datagramPacket.getAddress() + " \t " + datagramPacket.getPort());
        // 接收客户端发送的数据
        datagramSocket.receive(datagramPacket); // 接收后 封装了端口IP地址等信息在datagramPacket中
        System.out.println("2-> " + datagramPacket.getAddress() + " \t " + datagramPacket.getPort());
        // 读取数据
        String info = new String(data,0,data.length);
        System.out.println("客户端说：" + info);

        // 向客户端相应数据
        // 定义客户端的地址、端口号、数据
        InetAddress address = datagramPacket.getAddress();
        int port = datagramPacket.getPort();
        byte[] data2 = "欢迎你！".getBytes();
        //创建数据包，包含相应数据信息
        DatagramPacket datagramPacket1 = new DatagramPacket(data2,data2.length,address,port);

        // 响应客户端
        datagramSocket.send(datagramPacket1);
        // 关闭资源
        datagramSocket.close();

    }
}
