package org.pcchen.distribute.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 同步阻塞bio服务端实现
 *
 * @author ceek
 * @create 2020-08-04 10:37
 **/
public class BIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);

        while (true) {
            Socket socket = serverSocket.accept();

            InputStream inputStream = socket.getInputStream();

//            byte[] buf = new byte[1024];
//
//            StringBuffer sb = new StringBuffer();
//            int len = inputStream.read(buf);
//            while(len > 0) {
//                //注意指定编码格式，发送方和接收方一定要统一，建议使用UTF-8
//                sb.append(new String(buf, 0, len,"UTF-8"));
//                len = inputStream.read(buf);
//            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println("输入字符为：" + sb.toString());
        }
    }
}