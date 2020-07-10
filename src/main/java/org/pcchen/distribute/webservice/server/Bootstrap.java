package org.pcchen.distribute.webservice.server;

import javax.xml.ws.Endpoint;

/**
 * @author ceek
 * @create 2020-07-08 16:17
 **/
public class Bootstrap {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/ceek/hello", new ISayHelloServiceImpl());

        System.out.println("publish success!");
    }
}