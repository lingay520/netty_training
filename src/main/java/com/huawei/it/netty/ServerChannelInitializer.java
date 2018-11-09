package com.huawei.it.netty;/*
 * @author 片尾solo
 * @Date  2018/11/9  12:31
 */

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ServerChannelInitializer extends ChannelInitializer<NioSocketChannel> {
    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        nioSocketChannel.pipeline().addLast(new StringDecoder())
                .addLast(new StringEncoder())
                .addLast(new ServerHandler());
    }
}
