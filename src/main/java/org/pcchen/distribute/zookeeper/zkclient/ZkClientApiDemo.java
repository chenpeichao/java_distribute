package org.pcchen.distribute.zookeeper.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

/**
 * zkClient连接zookeeper
 *
 * @author ceek
 * @create 2020-07-15 15:16
 **/
public class ZkClientApiDemo {
    private static String ZK_CONNECTION_INFO = "10.10.32.61:2181,10.10.32.30:2181";


    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(ZK_CONNECTION_INFO, 4000);

        System.out.println(zkClient + "==》success");

        boolean exists = zkClient.exists("/pcchen");

        if (!exists) {
            System.out.println("/pcchen节点不存在，创建！");
            zkClient.create("/pcchen", "wojiushiwo", CreateMode.PERSISTENT);
//            zkClient.createPersistent("/pcchen", "wohaishiwo".getBytes());

//            zkClient.createPersistent("/pcchen/cpc", true);

            zkClient.subscribeDataChanges("/pcchen", new IZkDataListener() {
                public void handleDataChange(String s, Object o) throws Exception {
                    System.out.println("1dataChange:" + s + "=>" + o);
                }

                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("1dataDeleted:" + s);
                }
            });
        } else {
//            zkClient.delete("/pcchen", -1);
            zkClient.subscribeDataChanges("/pcchen", new IZkDataListener() {
                public void handleDataChange(String s, Object o) throws Exception {
                    System.out.println("2dataChange:" + s + "=>" + o);
                }

                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("2dataDeleted:" + s);
                }
            });
        }

        //订阅事件长期有效，只要下面数据改变，并且等待返回，则所有的change方法都会执行
        zkClient.writeData("/pcchen", "wojiushiwo3");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        zkClient.writeData("/pcchen", "wojiushiwo4");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        zkClient.writeData("/pcchen", "wojiushiwo5");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        zkClient.writeData("/pcchen", "wojiushiwo6");
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("/pcchen节点的数据为：" + zkClient.readData("/pcchen"));
    }
}