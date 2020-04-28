package com.willi.service; /**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-04-28 15:25
 **/

import com.willi.netty.NettyServer;

/**
 * 启动一个provicer
 */
public class ProviderStart {
    public static void main(String[] args) {
        NettyServer.startServer("localhost", 7000);
    }
}
