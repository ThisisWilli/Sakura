package com.willi.service;

import com.willi.netty.NettyClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-04-28 16:36
 **/
@Slf4j
public class ConsumerStart {
    public static final String providerName = "HelloService#Hello#";
    public static final ConsumerZK zk = new ConsumerZK();
    public static void main(String[] args) {
        // 创建一个消费者
        NettyClient consumer = new NettyClient();

        String subscribe = zk.subscribe("/sakura/provider");
        if (subscribe != null){
            zk.register("/sakura/consumer", subscribe.split(":")[0], Integer.parseInt(subscribe.split(":")[1]));
            // 如果有这个服务 创建代理对象
            HelloService service =
                    (HelloService) consumer.getBean(HelloService.class, providerName, subscribe.split(":")[0], Integer.parseInt(subscribe.split(":")[1]));

            // 通过代理对象调用服务提供者的方法git
            String res = service.hello("你好 dubbo");
            System.out.println("嗲用结果为" + res);
        }else {
            log.error("没有可用的服务");
        }

        NettyClient.getExecutor().shutdownNow();
        log.info(String.valueOf(NettyClient.getExecutor().isTerminated()));
    }
}
