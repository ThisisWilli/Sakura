package com.willi.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-05-01 20:16
 **/

@Slf4j
public class ConsumerZK {

    private static final String ROOT_PATH = "/sakura";

    private static final String PROVIDER_PATH = "/sakura/provider";
    private static final String CONSUMER_PATH = "/sakura/consumer";

    public synchronized String subscribe(String serverPath){
        byte[] data = null;
        try {
            ZooKeeper zk = new ZooKeeper("localhost:2181,localhost:2182,localhost:2183", 3000, (watchedEvent)->{
                log.info("====已经触发了 " + watchedEvent.getType() + " 类型的事件====");
                log.info("====节点中的路径为：" + watchedEvent.getPath() + "====");
            });
            List<String> children = zk.getChildren(PROVIDER_PATH, true);
            System.out.println(children.get(0));
            data = zk.getData(serverPath + "/" + children.get(0), null, zk.exists(serverPath + "/" + children.get(0), true));
        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
        assert data != null;
        return new String(data);
    }

    public synchronized void register(String servicePath, String serverPath, int port){
        try {
            ZooKeeper zk = new ZooKeeper("localhost:2181,localhost:2182,localhost:2183", 3000, (watchedEvent)->{
                log.info("====已经触发了 " + watchedEvent.getType() + " 类型的事件====");
                log.info("====节点中的路径为：" + watchedEvent.getPath() + "====");
            });
            zk.create(CONSUMER_PATH + "consumer" + serverPath + String.valueOf(port), "consumer".getBytes()
                    , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            log.info("创建临时节点成功");
        } catch (IOException | KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
