package org.pcchen.distribute.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * curator操作zookeeper实例
 *
 * @author ceek
 * @create 2020-07-20 13:36
 **/
public class CuratorSessionDemo {
    private final static String ZK_CONNECTION_INFO = "10.10.32.61:2181,10.10.32.30:2181";

    public static void main(String[] args) {
        CuratorFramework cf = CuratorFrameworkFactory.newClient(ZK_CONNECTION_INFO, 5000, 5000, new ExponentialBackoffRetry(1000, 3));
        cf.start();


        CuratorFramework cf2 = CuratorFrameworkFactory.builder().connectString(ZK_CONNECTION_INFO)
                .sessionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3))
                .build();

        cf2.start();
    }
}