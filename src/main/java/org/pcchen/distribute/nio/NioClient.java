package org.pcchen.distribute.nio;

import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 同步非阻塞nio客户端
 *
 * @author ceek
 * @create 2020-08-05 13:36
 **/
public class NioClient {
    private SocketChannel socketChannel;

    public static void main(String[] args) {
        NioClient nioClient = new NioClient();
        nioClient.send();
    }

    public void send() {
        try {
//            SocketChannel socketChannel = SocketChannel.open();
            socketChannel = SocketChannel.open();
            //TODO:pcchen 此处注意设置非阻塞要放在连接之前
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8888));


            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                //注意要去主动询问轮询器
                selector.select();

                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (Iterator<SelectionKey> itera = selectionKeys.iterator(); itera.hasNext(); ) {
                    SelectionKey selectionKey = itera.next();

                    itera.remove();
                    handleSelectkey(selectionKey);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSelectkey(SelectionKey selectionKey) {
        SocketChannel channel = (SocketChannel) selectionKey.channel();

        if (selectionKey.isConnectable()) {
            if (channel.isConnectionPending()) {
                try {
                    if (channel.finishConnect()) {
                        //只有当连接成功后才能注册OP_READ事件
                        selectionKey.interestOps(SelectionKey.OP_READ);
                        System.out.println("客户端连接成功");
//                        channel.register(selectionKey.selector(), SelectionKey.OP_READ);
                        socketChannel.write(ByteBuffer.wrap("客户端连接成功，发送请求！".getBytes("UTF-8")));
                        socketChannel.write(ByteBuffer.wrap("客户端连接成功1212，发送请求！".getBytes("UTF-8")));
                        TimeUnit.SECONDS.sleep(new Random().nextInt(3));
                    } else {
                        selectionKey.cancel();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (selectionKey.isReadable()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(128);
            try {
                int read = channel.read(byteBuffer);
                if (read > 0) {
                    byteBuffer.flip();

                    System.out.println(new String(byteBuffer.array()));
                    try {
                        TimeUnit.SECONDS.sleep(new Random().nextInt(3));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    byteBuffer.clear();

                    try {
//                        byteBuffer = ByteBuffer.wrap("客户端又写数据".getBytes("utf-8"));
//                        byteBuffer.flip();
                        //将数据写入
                        ByteBuffer wrap = ByteBuffer.wrap("客户端又写数据".getBytes("utf-8"));
//                        byteBuffer = ByteBuffer.wrap("客户端又写数据".getBytes("utf-8"));
                        wrap.flip();
                        socketChannel.write(wrap);

                        socketChannel.register(selectionKey.selector(), SelectionKey.OP_WRITE);
//                channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
//                if(read > 0) {
//                    byteBuffer.flip();
//
//                    System.out.println(new String(byteBuffer.array()));
//                    try {
//                        TimeUnit.SECONDS.sleep(new Random().nextInt(3));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    byteBuffer.clear();
//
//                    try {
//                        byteBuffer.wrap("客户端又写数据".getBytes("utf-8"));
//                        byteBuffer.flip();
//                        //将数据写入
//                        channel.write(byteBuffer);
////                channel.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (selectionKey.isWritable()) {
            try {
                ByteBuffer wrap = ByteBuffer.wrap("客户端还写数据".getBytes("utf-8"));
//                        byteBuffer = ByteBuffer.wrap("客户端又写数据".getBytes("utf-8"));
                wrap.flip();
                socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);

                socketChannel.write(wrap);
                socketChannel.close();
                TimeUnit.SECONDS.sleep(new Random().nextInt(3));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}