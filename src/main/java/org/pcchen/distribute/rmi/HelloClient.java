package org.pcchen.distribute.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * RMI客户端client
 *
 * @author ceek
 * @create 2020-07-07 14:44
 **/
public class HelloClient {
    public static void main(String[] args) {
        try {
            ISayHello iSayHello = (ISayHello) Naming.lookup("rmi://localhost:8888/sayHello");

            System.out.println(iSayHello.sayHello("小明"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
