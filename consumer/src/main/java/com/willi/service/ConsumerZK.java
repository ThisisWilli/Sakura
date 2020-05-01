package com.willi.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
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
    private static final String PATH = "/sakura/provider";

    public synchronized void subscribe(String serverPath){
        try {
            ZooKeeper zk = new ZooKeeper("localhost:2181,localhost:2182,localhost:2183", 3000, (watchedEvent)->{
                log.info("====已经触发了 " + watchedEvent.getType() + " 类型的事件====");
                log.info("====节点中的路径为：" + watchedEvent.getPath() + "====");
            });
            List<String> children = zk.getChildren(PATH, true);
            System.out.println(children.get(0));
            byte[] data = zk.getData(serverPath + "/" + children.get(0), null, zk.exists(serverPath + "/" + children.get(0), true));
            log.info("data = " + new String(data));

        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }
}
