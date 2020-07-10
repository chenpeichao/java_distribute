package org.pcchen.distribute.webservice.client;

/**
 * 在org.pcchen.distribute.webservice.server这个文件夹目录下执行wsimport -keep http://localhost:8888/ceek/hello?wsdl;用于生成client端调用代码
 *
 * @author ceek
 * @create 2020-07-08 16:30
 **/
public class Demo {
    public static void main(String[] args) {
        ISayHelloServiceImplService service = new ISayHelloServiceImplService();
        ISayHelloServiceImpl serviceImplPort = service.getISayHelloServiceImplPort();

        System.out.println(serviceImplPort.sayHello("jack"));
    }
}
