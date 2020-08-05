package org.pcchen.distribute.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 同步阻塞bio客户端实现
 * <p>
 * 此处注意当有BufferedReader时，需关闭BufferedReader流;
 * 当只有必须关闭OutputStream时，必须关闭OutputStream
 *
 * @author ceek
 * @create 2020-08-04 10:37
 **/
public class BIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);

        OutputStream outputStream = socket.getOutputStream();

        outputStream.write("我就是我".getBytes("UTF-8"));

//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream));
//        bw.write("我还是我");
//        bw.flush();
//        bw.close();
        outputStream.flush();
        socket.shutdownOutput();
        outputStream.close();
        socket.close();
    }
}
