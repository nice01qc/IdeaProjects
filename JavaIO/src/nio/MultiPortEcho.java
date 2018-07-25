package nio;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.*;

public class MultiPortEcho
{
  static int sumNUM = 0;
  private int ports[];
  private ByteBuffer echoBuffer = ByteBuffer.allocate( 1024 );

  public MultiPortEcho( int ports[] ) throws IOException, InterruptedException {
    this.ports = ports;

    go();
  }

  private void go() throws IOException, InterruptedException {
    // Create a new selector
    Selector selector = Selector.open();

    // Open a listener on each port, and register each one
    // with the selector
    for (int i=0; i<ports.length; ++i) {
      ServerSocketChannel ssc = ServerSocketChannel.open();
      ssc.configureBlocking( false );
      InetSocketAddress address = new InetSocketAddress( ports[i] );

//      ServerSocket ss = ssc.socket();
////      ss.bind( address );
      ssc.bind(address);

      if (!ssc.isRegistered()){
        SelectionKey key = ssc.register( selector, SelectionKey.OP_ACCEPT );
      }

      System.out.println( "Going to listen on "+ports[i] );
    }

    while (true) {     // 通过轮询来查询状态

      int num = selector.select();
      System.out.println(num);
      Set selectedKeys = selector.selectedKeys();
      Iterator it = selectedKeys.iterator();

      while (it.hasNext()) {
        SelectionKey key = (SelectionKey)it.next();
        System.out.println("select ");
//        if (key.isConnectable()){
//          System.out.println("isConnectable...");
//        }
        if (key.isAcceptable()){
          ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
          SocketChannel sc = ssc.accept();
          sc.configureBlocking( false );
          SelectionKey newKey = sc.register( selector, SelectionKey.OP_READ );
          it.remove();
        }else if (key.isReadable()) {
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
//            echoBuffer.get(bytess);
//            message += byteToString(bytess);
            message += byteToString1(echoBuffer);

            echoBuffer.flip();
            sc.write( echoBuffer );
            bytesEchoed += r;
          }

          System.out.println(sumNUM + " \tbytesLength: "+bytesEchoed + "\tmessage: \"" + message + "\" from "+sc );
          sc.close();   // 接收完了消息，应当把socket关闭

          it.remove();

        }


      }
      Thread.sleep(2000);

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
