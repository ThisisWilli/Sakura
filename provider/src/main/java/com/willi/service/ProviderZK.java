package com.willi.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;

/**
 * @program: sakura
 * @description: 当nettyserver启动成功时，在zk注册服务
 * @author: Hoodie_Willi
 * @create: 2020-05-01 15:32
 **/

@Slf4j
public class ProviderZK {

    private static final String PATH = "/sakura";

    public ProviderZK() {
    }

    /**
     * 在zookeeper端注册一个provider服务
     * */
    public synchronized void register(String hostName, int port, int providerNum){
        try {
            // mac上的配置localhost:2181,localhost:2182,localhost:2183
            // win上的配置node02:2181, node03:2181, node04:2181

            ZooKeeper zk = new ZooKeeper("localhost:2181, localhost:2182, localhost:2183", 3000, (watchedEvent)->{
                log.info("====已经触发了 " + watchedEvent.getType() + " 类型的事件====");
                log.info("====节点中的路径为：" + watchedEvent.getPath() + "====");
            });

            // 如果provider路径已经存在，将data读取，并加入host：port，相当于新增了一个服务节点
            if (zk.exists(PATH, true) != null){
                log.info("sakura已被创建");
                List<String> children = zk.getChildren(PATH, true);
                if (children.contains("provider")){
                    log.info("provider已被创建, 开始创建server");
                    List<String> providerChildren = zk.getChildren(PATH + "/provider", true);
                    if (!providerChildren.contains("server" + providerNum)){
                        zk.create(PATH + "/provider/server" + (providerNum), (hostName + ":"+ port).getBytes()
                                , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                        log.info("server" + providerNum + "注册成功");
                    }else {
                        zk.setData(PATH + "/provider/server" + (providerNum), (hostName + ":"+ port).getBytes()
                                , zk.exists(PATH + "/provider/server" + (providerNum), true).getVersion());
                    }
                }
                // provider已经被注册过，则不创建新的Znode
            }else {
                log.info("还没有server被创建过");
                // 创建总的目录
                zk.create(PATH, ("that's sakura dir").getBytes()
                        , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                zk.create(PATH + "/provider", ("that's servers dir").getBytes()
                        , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                zk.create(PATH + "/provider/server" + providerNum, (hostName + ":"+ port).getBytes()
                        , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                log.info("创建" + hostName + ":" + port + "服务");
            }
        } catch (IOException | KeeperException | InterruptedException e) {
            e.printStackTrace();
            log.error("创建" + hostName + ":" + port + "服务时出现异常");
        }
    }

    // TODO
    public synchronized void deleteZNode(){

    }
}
