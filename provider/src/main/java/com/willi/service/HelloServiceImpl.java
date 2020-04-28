package com.willi.service;

/**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-04-28 15:21
 **/

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        System.out.println("收到conusmer消息=" + msg);
        if (msg != null){
            return "hello consumer, 我已经收到消息" + msg;
        }else {
            return "hello consumer, 我已经收到消息";
        }
    }
}
