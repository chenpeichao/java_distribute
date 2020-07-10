package org.pcchen.distribute.webservice.server;

import javax.jws.WebService;

/**
 * @author ceek
 * @create 2020-07-08 16:15
 **/
@WebService
public class ISayHelloServiceImpl implements ISayHelloService {

    public String sayHello(String name) {
        System.out.println("call sayHello method");
        return "hello i am " + name;
    }
}
