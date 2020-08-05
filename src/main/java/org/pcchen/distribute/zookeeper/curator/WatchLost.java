package org.pcchen.distribute.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author ceek
 * @create 2020-07-24 10:37
 **/
public class WatchLost {
    private final static String ZK_CONNECTION_INFO = "10.10.32.61:2181,10.10.32.30:2181";

    static String path = "/distribute_lock";
    static CuratorFramework client = CuratorFrameworkFactory.builder().connectString(ZK_CONNECTION_INFO)
            .retryPolicy(new ExponentialBackoffRetry(3000, 3)).build();

    public static void main(String[] args) {
        try {
            int a = 1 / 0;
            System.out.println("abc");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("def");
        }
    }

    public static void main1(String[] args) {
        try {
            client.start();

            final NodeCache nodeCache = new NodeCache(client, path);
            nodeCache.start();

            if (client.checkExists().forPath(path) == null)
                client.create().forPath(path, "0".getBytes());

            nodeCache.getListenable().addListener(new NodeCacheListener() {
                public void nodeChanged() throws Exception {
                    if (nodeCache.getCurrentData() == null) {
                        System.out.println("节点被删除");
                    } else {
                        System.out.println("节点当前内容为：" + new String(nodeCache.getCurrentData().getData()));
                    }
                }
            });

            client.setData().forPath(path, "1".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "2".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "3".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "4".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "5".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "6".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "7".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "8".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "9".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "12".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "13".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "14".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "15".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "16".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "17".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "18".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "19".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a1".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a2".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a3".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a4".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a5".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a6".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a7".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a8".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a9".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a12".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a13".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a14".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a15".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a16".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a17".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a18".getBytes());
            TimeUnit.MILLISECONDS.sleep(100);
            client.setData().forPath(path, "a19".getBytes());
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
