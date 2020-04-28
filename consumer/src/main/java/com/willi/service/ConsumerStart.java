package com.willi.service;

import com.willi.netty.NettyClient;

/**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-04-28 16:36
 **/

public class ConsumerStart {
    public static final String providerName = "HelloService#Hello#";

    public static void main(String[] args) {
        // 创建一个消费者
        NettyClient consumer = new NettyClient();

        // 创建代理对象
        HelloService service = (HelloService) consumer.getBean(HelloService.class, providerName);

        // 通过代理对象调用服务提供者的方法
        String res = service.hello("你好 dubbo");
        System.out.println("嗲用结果为" + res);
    }
}
