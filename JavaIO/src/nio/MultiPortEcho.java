package nio;// $Id$

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.*;

public class MultiPortEcho
{
  static int sumNUM = 0;
  private int ports[];
  private ByteBuffer echoBuffer = ByteBuffer.allocate( 1024 );

  public MultiPortEcho( int ports[] ) throws IOException {
    this.ports = ports;

    go();
  }

  private void go() throws IOException {
    // Create a new selector
    Selector selector = Selector.open();

    // Open a listener on each port, and register each one
    // with the selector
    for (int i=0; i<ports.length; ++i) {
      ServerSocketChannel ssc = ServerSocketChannel.open();
      ssc.configureBlocking( false );
      ServerSocket ss = ssc.socket();
      InetSocketAddress address = new InetSocketAddress( ports[i] );
      ss.bind( address );

      if (!ssc.isRegistered()){
        SelectionKey key = ssc.register( selector, SelectionKey.OP_ACCEPT );
      }

      System.out.println( "Going to listen on "+ports[i] );
    }

    while (true) {      // 通过轮询来查询状态

      int num = selector.select();

      Set selectedKeys = selector.selectedKeys();
      Iterator it = selectedKeys.iterator();

      while (it.hasNext()) {
        SelectionKey key = (SelectionKey)it.next();
/**
 * 以下有两个if，用于处理两个阶段，已连接阶段，可读数据阶段。也可以直接一波（就第一个阶段后接着读取数据）
 */
        if ((key.readyOps() & SelectionKey.OP_ACCEPT)   // 用于判断是否与SelectionKey.OP_ACCEPT
          == SelectionKey.OP_ACCEPT) {
          // Accept the new connection
          ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
          SocketChannel sc = ssc.accept();
          sc.configureBlocking( false );

          // Add the new connection to the selector
          System.out.println(sc.isRegistered());
          SelectionKey newKey = sc.register( selector, SelectionKey.OP_READ );
          it.remove();

          System.out.println( "Got connection from "+sc );
        } else if ((key.readyOps() & SelectionKey.OP_READ)
          == SelectionKey.OP_READ) {
          // Read the data
          SocketChannel sc = (SocketChannel)key.channel();

          // Echo data
          int bytesEchoed = 0;
          String message = "";
          while (true) {
            sumNUM++;
            echoBuffer.clear();

            int r = sc.read( echoBuffer );

            if (r<=0) {
              sumNUM--;
              break;
            }

            echoBuffer.flip();


//            byte[] bytess = new byte[echoBuffer.limit()];
//
//            echoBuffer.get(bytess);
//            message += byteToString(bytess);
            message += byteToString1(echoBuffer);

            echoBuffer.flip();
            sc.write( echoBuffer );
            bytesEchoed += r;
          }

          System.out.println(sumNUM + " \tbytesLength: "+bytesEchoed + "\tmessage: \"" + message + "\" from "+sc );
          sc.close();   // 接收完了消息，应当把socket关闭



        }


      }

//System.out.println( "going to clear" );
//      selectedKeys.clear();
//System.out.println( "cleared" );
    }
  }
  private String byteToString(byte[] bytes){
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < bytes.length; i++) stringBuffer.append((char)bytes[i]);
    return stringBuffer.toString();
  }

  private String byteToString1(ByteBuffer byteBuffer) throws CharacterCodingException {
    Charset latin1 = Charset.forName( "UTF-8" );
    CharsetDecoder decoder = latin1.newDecoder();
    CharBuffer charBuffer = decoder.decode(byteBuffer);
    return charBuffer.toString();
  }

  static public void main( String args[] ) throws Exception {


    int ports[] = {1234,8080,8081};

    new MultiPortEcho( ports );
  }
}
