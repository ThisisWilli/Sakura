package com.willi.netty;

import com.willi.service.ProviderZK;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: sakura
 * @description:
 * @author: Hoodie_Willi
 * @create: 2020-04-28 15:26
 **/
@Slf4j
public class NettyServer {
    // 编写一个方法，完成对NettyServer的初始化和启动

    public static void startServer(String hostName, int port, ProviderZK zk, int providerNum){
        startServerMethod01(hostName, port, providerNum, zk);

//        log.error("provider" + hostName + ":" + port + "出错");
    }

    private static void startServerMethod01(String hostName, int port, int providerNum, ProviderZK zk){
//        log.info("启动成功：" + "http://" + hostname + ":" + port + "/");
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 使用主从Reactor多线程模型
            serverBootstrap.group(bossGroup, workerGroup)
                    // 采用nio的传输方式
                    .channel(NioServerSocketChannel.class)
                    // 添加我们自己的handler处理流程
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 获取pipeline并定义流程
                            ChannelPipeline pipeline = ch.pipeline();
                            // 字符串解码和编码
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            // 自己逻辑的handler
                            pipeline.addLast("handler", new NettyServerHandler());
                        }
                    });
            zk.register(hostName, port, providerNum);
            log.info("provider" + "http://" + hostName + ":" + port + "/" + "开始提供服务");
            // server绑定端口监听
            ChannelFuture channelFuture = serverBootstrap.bind(hostName, port).sync();
            channelFuture.channel().closeFuture().sync();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();

        }
    }
}
