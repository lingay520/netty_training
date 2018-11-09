package com.huawei.it.netty;/*
 * @author 片尾solo
 * @Date  2018/11/9  12:35
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public class NettyClient {
    private static int PORT = 9999;
    private static String IP = "127.0.0.1";
    private static int WORK_THREAD_COUNT = Runtime.getRuntime().availableProcessors();
    private EventLoopGroup work = new NioEventLoopGroup(WORK_THREAD_COUNT);
    Bootstrap client = new Bootstrap();
    private void bind(){
        client.group(work).option(ChannelOption.SO_SNDBUF,1024)
                .option(ChannelOption.SO_TIMEOUT,3000)
                .channel(NioSocketChannel.class)
                .handler(new ClientHandler());
        try {
            ChannelFuture fu = client.connect(IP, PORT).sync();
            fu.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            work.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new NettyClient().bind();
    }
}
