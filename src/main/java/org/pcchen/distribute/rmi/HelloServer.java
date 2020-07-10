package org.pcchen.distribute.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * rmi服务端server
 *
 * @author ceek
 * @create 2020-07-07 14:37
 **/
public class HelloServer {
    public static void main(String[] args) {
        try {
            ISayHello iSayHello = new ISayHelloImpl();

            LocateRegistry.createRegistry(8888);

            Naming.bind("rmi://localhost:8888/sayHello", iSayHello);

            System.out.println("server start success");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

    }
}
