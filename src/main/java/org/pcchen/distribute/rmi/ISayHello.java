package org.pcchen.distribute.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI中接口方法类
 *
 * @author ceek
 * @create 2020-07-07 14:38
 **/
public interface ISayHello extends Remote {
    public String sayHello(String name) throws RemoteException;
}
