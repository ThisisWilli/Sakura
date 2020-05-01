package com.willi.service; /**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-04-28 15:25
 **/

import com.willi.netty.NettyServer;
import lombok.extern.slf4j.Slf4j;


/**
 * 启动一个provicer
 */

@Slf4j
public class ProviderStart {

    public static final ProviderZK zk = new ProviderZK();

    public static void main(String[] args) {


        NettyServer.startServer("localhost", 7000, zk, 1);

    }
}
