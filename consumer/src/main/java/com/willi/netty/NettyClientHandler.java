package com.willi.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-04-28 15:51
 **/

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    private String paras;

    // 与服务器的连接创建后，就会被调用 first
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 在其他方法中会使用到ctx
        context = ctx;
    }

    // 收到服务起的数据后，调用方法  4
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        // 唤醒等待的线程
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    /**
     * 被代理对象调用，发送数据给服务器--> wait --> 等待被唤醒 --> 返回结果 3 5
     * @return
     * @throws Exception
     */
    public synchronized Object call() throws Exception {
        context.writeAndFlush(paras);
        // 进行wait()， 等待channelRead得到服务器结果之后，唤醒
        wait();
        // 服务方返回的结果
        System.out.println("收到消息" + result.toString());
        return result.toString();
    }

    // 2
    void setPara(String para){
        this.paras = para;
    }
}
