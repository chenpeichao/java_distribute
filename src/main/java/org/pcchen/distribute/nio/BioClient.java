package org.pcchen.distribute.nio;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * 同步阻塞Bio客户端实现
 *
 * @author ceek
 * @create 2020-08-05 15:31
 **/
public class BioClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8888);


//            OutputStream outputStream = socket.getOutputStream();
//            outputStream.write("".getBytes("UTF-8"));
//            outputStream.close();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.write("客户端发送请求1");
            writer.flush();
            socket.shutdownOutput();

            TimeUnit.SECONDS.sleep(3);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            System.out.println("客户端接收的数据为---》" + sb.toString());

            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        try {
//            Socket sc = new Socket("localhost", 8888);
//            OutputStream os = sc.getOutputStream();
//            InputStream in = sc.getInputStream();
//            os.write("我就是我".getBytes());
//            os.flush();
//            byte[] b = new byte[1024];
//            int read = in.read(b, 0, 1024);
//            System.out.println("from server:" + read + " bytes-->" + new String(b));
//
//            sc.shutdownOutput();
//            sc.shutdownInput();
//            sc.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
