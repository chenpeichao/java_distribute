package org.pcchen.distribute.zookeeper.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * zookeeper测试基础类
 *
 * @author ceek
 * @create 2020-07-14 17:10
 **/
public class BaseZookeeperDemo implements Watcher {
    private final static String ZK_CONNECTION_INFO = "10.10.32.61:2181,10.10.32.30:2181";

    private static Stat stat = new Stat();

    public static void main(String[] args) {
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(ZK_CONNECTION_INFO, 5000, new BaseZookeeperDemo());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("初始化zookeeper完成");
        try {
            zooKeeper.exists("/uar1", true);
//            zooKeeper.create("/uar1", "wohaishiwo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
//            System.out.println("创建临时节点/uar1完成");

//            zooKeeper.delete("/uar", -1);
//            zooKeeper.getData("/uar1", new BaseZookeeperDemo(), stat);
//            System.out.println("获取数据完成");

            zooKeeper.exists("/uar1/uar11", true);
//            zooKeeper.setData("/uar1", "wojiushiwo".getBytes(), -1);

            zooKeeper.create("/uar1/uar11", "1212".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            zooKeeper.delete("/uar1/uar11", -1);

            List<String> children = zooKeeper.getChildren("/", true);
            System.out.println(children);

            Thread.sleep(2000);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState().equals(Event.KeeperState.SyncConnected)) {
            System.out.println("SyncConnected");
        }

        if (watchedEvent.getType().equals(Event.EventType.NodeCreated)) {
            System.out.println("nodeCreate");
        }
        System.out.println("监听到了：" + watchedEvent.getState());
        System.out.println("type=" + watchedEvent.getType());
        System.out.println("path=" + watchedEvent.getPath());
    }
}