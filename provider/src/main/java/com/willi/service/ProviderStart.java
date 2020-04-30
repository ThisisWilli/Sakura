package com.willi.service; /**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-04-28 15:25
 **/

import com.willi.netty.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;


/**
 * 启动一个provicer
 */

@Slf4j
public class ProviderStart {

    public static void main(String[] args) {

        log.debug("输出DEBUG级别日志");
        log.info("输出INFO级别日志");
        log.warn("输出WARN级别日志");
        log.error("输出ERROR级别日志");
        NettyServer.startServer("localhost", 7000);

    }
}
