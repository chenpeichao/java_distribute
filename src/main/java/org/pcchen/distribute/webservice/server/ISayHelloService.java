package org.pcchen.distribute.webservice.server;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * @author ceek
 * @create 2020-07-08 15:58
 **/
@WebService
public interface ISayHelloService {
    @WebMethod
    public String sayHello(String name);
}