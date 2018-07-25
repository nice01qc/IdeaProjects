package nio;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class ClientSock {
    static private final int firstHeaderLength = 2;
    static private final int secondHeaderLength = 4;
    static private final int bodyLength = 6;
    public static void main(String[] args) throws IOException, InterruptedException {

        //1、创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("localhost", 1234);

        socket.getOutputStream().write("nice 8080".getBytes());

        byte[] message = new byte[1024];
        int length = socket.getInputStream().read(message);
        for (int i =0; i < length; i++) System.out.print((char)message[i]);
        System.out.println();

        socket.close();
    }
}
