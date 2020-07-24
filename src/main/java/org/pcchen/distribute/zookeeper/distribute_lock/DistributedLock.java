package org.pcchen.distribute.zookeeper.distribute_lock;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 分布式共享锁
 * 实现步骤：
 * 客户端上线即向zookeeper注册，创建一把锁
 * 判断是否只有一个客户端工作，如只有一个，则客户端可以处理业务
 * 获取父节点下注册的所有锁，通过判断自己是否是号码最小的，若是则处理业务
 * 注意：在某一客户端获取到锁梳理完业务后，必须释放锁
 *
 * @author ceek
 * @create 2020-07-21 9:59
 **/
public class DistributedLock {
    private ZooKeeper zooKeeperCli = null;

    private static String ZK_CONNECTION_INFO = "10.10.32.61:2181,10.10.32.30:2181";

    //超时时间
    private static final int sessionTimeout = 5000;

    //父节点
    private static final String parentNode = "/distribute_lock";

    //记录自己创建子节点的路径
    private volatile String thisPath;

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 7; i++) {
            executorService.execute(new Runnable() {
                public void run() {
                    DistributedLock distributedLock = new DistributedLock();

                    distributedLock.getZKClient();

                    distributedLock.registLock();

                    try {
                        distributedLock.watchParent();
                        TimeUnit.SECONDS.sleep(3);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    public void registLock() {
        try {
            thisPath = zooKeeperCli.create(parentNode + "/lock", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取zk客户端
     */
    private void getZKClient() {
        try {
            zooKeeperCli = new ZooKeeper(ZK_CONNECTION_INFO, sessionTimeout, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getType().equals(Event.EventType.NodeChildrenChanged) && watchedEvent.getPath().equals(parentNode)) {
                        try {
                            List<String> childrenPath = zooKeeperCli.getChildren(parentNode, true);

                            //判断自己是否是最小的
                            String thisNode = thisPath.substring((parentNode + "/").length());
                            Collections.sort(childrenPath);
                            if (childrenPath.indexOf(thisNode) == 0) {
                                //处理业务逻辑
                                System.out.println("dosoming_2");
                                dosomething();
                                //重新注册一把新的锁
//                                thisPath = zooKeeperCli.create(parentNode + "/lock", null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                            }

                        } catch (KeeperException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //业务逻辑方法，注意：需要在最后释放锁
    private void dosomething() throws KeeperException, InterruptedException {
        System.out.println("得到锁:" + thisPath);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放锁:" + thisPath);
            zooKeeperCli.delete(thisPath, -1);
        }
    }

    //监听父节点，判断是否只有自己在线
    public void watchParent() throws Exception {
        TimeUnit.SECONDS.sleep(5);
        List<String> childrens = zooKeeperCli.getChildren(parentNode, true);
        if (childrens != null && childrens.size() == 1) {
            //只有自己在线，处理业务逻辑(处理完业务逻辑，必须删释放锁)
            System.out.println("dosoming_1");
            dosomething();
        } else {
            System.out.println("超过一个存在，等待10秒");
            //不是只有自己在线，说明别人已经获取到锁，等待
            TimeUnit.SECONDS.sleep(10);
        }
    }
}
