package org.pcchen.distribute.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI接口实现类
 *
 * @author ceek
 * @create 2020-07-07 14:39
 **/
public class ISayHelloImpl extends UnicastRemoteObject implements ISayHello {

    protected ISayHelloImpl() throws RemoteException {
    }

    public String sayHello(String name) {
        return "hello ->" + name;
    }
}
