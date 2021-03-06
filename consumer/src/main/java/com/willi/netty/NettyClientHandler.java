package com.willi.netty;

import com.willi.service.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @program: sakura
 * @description: 继承了ChannelInboundHandlerAdapter可以对出站和入站的数据进行处理
 * @author: Hoodie_Willi
 * @create: 2020-04-28 15:51
 **/
@Slf4j
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    // 保存channel的所有上下文信息
    private ChannelHandlerContext context;
    private String result;
    private RpcProtocol paras;

    // 通道就绪事件与服务器的连接创建后，就会被调用 first
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 在其他方法中会使用到ctx
        log.info("channelActive被调用");
        context = ctx;
    }

    // 通道数据读取收到服务起的数据后，调用方法  4
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("channelRead被调用");
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
        log.info("call被调用并即将进入阻塞");
        context.writeAndFlush(paras);
        // 进行wait()， 等待channelRead得到服务器结果之后，唤醒
        wait();
        // 服务方返回的结果
        log.info("channelRead方法读完数据并唤醒");
        System.out.println("收到消息" + result);
        return result;
    }

    // 2
    void setPara(RpcProtocol para){
        this.paras = para;
    }
}
