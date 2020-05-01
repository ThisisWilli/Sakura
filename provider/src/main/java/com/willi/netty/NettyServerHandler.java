package com.willi.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import com.willi.service.HelloServiceImpl;

/**
 * @program: sakura
 * @description: server端的handler处理逻辑
 * @author: Hoodie_Willi
 * @create: 2020-04-28 15:37
 **/

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 在channel中数据如果被读到，则触发channelRead方法
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取客户端发送的消息，并调用服务
        System.out.println("msg=" + msg);
        // 客户端在调用服务器的api时，我们需要定义一个协议
        // 比如我们要求每次发消息时都必须以某个字符串开头"com.willi.service.HelloService#hello#
        if (msg.toString().startsWith("HelloService#Hello#")){
            // 服务器准备返回的结果
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
