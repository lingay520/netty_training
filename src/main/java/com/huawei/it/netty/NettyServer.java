package com.huawei.it.netty;/*
 * @author 片尾solo
 * @Date  2018/11/9  12:24
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    private static int PORT = 9999;
    private static int BOSS_THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    private static int WORK_THREAD_COUNT = 100;
    private ServerBootstrap server = new ServerBootstrap();
    private EventLoopGroup boss = new NioEventLoopGroup(BOSS_THREAD_COUNT);
    private EventLoopGroup work = new NioEventLoopGroup(WORK_THREAD_COUNT);


    private void listen(){
        this.header();
        server.group(boss,work);
        server.option(ChannelOption.SO_BACKLOG,1024)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_NODELAY,true)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerChannelInitializer());
        try {
            ChannelFuture fu = server.bind(PORT).sync();
            fu.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyServer().listen();
    }
    private void header(){
        System.out.println("#################聊天服务器启动##################");
        System.out.println("##############欢迎进入爱聊不聊聊天室##############");
        System.out.println("##############################################");
        System.out.println("————————————————————————————————————————————————————————");
    }
}
