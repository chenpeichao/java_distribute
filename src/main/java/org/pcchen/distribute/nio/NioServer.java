package org.pcchen.distribute.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 同步非阻塞nio服务端
 *
 * @author ceek
 * @create 2020-08-05 13:35
 **/
public class NioServer {
    private Selector selector;
    private ServerSocketChannel serverSocketChannelSum;

    private ByteBuffer byteBuffer;

    public static void main(String[] args) {
        NioServer nioServer = new NioServer();
        nioServer.init();
        System.out.println("服务器端初始化完成！");
        nioServer.listener();
    }

    public void init() {
        byteBuffer = ByteBuffer.allocate(1024);
        try {
            serverSocketChannelSum = ServerSocketChannel.open();
            //将其设置为非阻塞
            serverSocketChannelSum.configureBlocking(false);

            //将通讯绑定在指定的端口上
            ServerSocket socket = serverSocketChannelSum.socket();
            socket.bind(new InetSocketAddress("localhost", 8888));

            //open一个多路复用器都
            selector = Selector.open();

            //将上面的serverSocketChannel注册到多路复用器上
            //对于服务来说，首先需要注册一个OP_ACCEPT时间来响应客户端的链接请求
            serverSocketChannelSum.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listener() {
        while (true) {
            //询问多路复用器，获取到所有事件
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历所有事件
            for (Iterator<SelectionKey> iterator = selectionKeys.iterator(); iterator.hasNext(); ) {
                SelectionKey selectionKey = iterator.next();

                //确保无重复selectionKey，并处理SelectionKey事件
                iterator.remove();
                handleSelectionKey(selectionKey);
            }
        }
    }

    public void handleSelectionKey(SelectionKey selectionKey) {
        SocketChannel socketChannel;
        try {
            if (selectionKey.isAcceptable()) {
                //获取总管道
//                ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();

                //接收链接请求,获取到具体时间的管道
//                socketChannel = channel.accept();
                socketChannel = serverSocketChannelSum.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
//                socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
                System.out.println("首先接收请求，什么也不操作！" + selectionKey);
            } else if (selectionKey.isReadable()) {
                socketChannel = (SocketChannel) selectionKey.channel();

                //清空缓冲区
                byteBuffer.clear();

                int read = socketChannel.read(byteBuffer);
                if (read > 0) {
                    byteBuffer.flip();

                    String readString = new String(byteBuffer.array());
                    System.out.println("服务器端接收的数据为---->" + readString);
                    socketChannel.register(selector, SelectionKey.OP_WRITE);
                }
            } else if (selectionKey.isWritable()) {
                socketChannel = (SocketChannel) selectionKey.channel();
                socketChannel.write(ByteBuffer.wrap("服务器端写数据".getBytes("UTF-8")));

                socketChannel.register(selector, SelectionKey.OP_READ);
                //write流注意关闭或者结束
                socketChannel.close();
//                socketChannel.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}