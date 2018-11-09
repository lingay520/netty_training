package com.huawei.it.netty;/*
 * @author 片尾solo
 * @Date  2018/11/9  12:44
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.Scanner;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private String name ;
    private Scanner scanner = new Scanner(System.in);


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.print("请输入你的昵称:");
        name = scanner.nextLine();
        while(true){
            StringBuffer sb = new StringBuffer();
            String msg = scanner.nextLine();
            msg = sb.append(name).append(":").append(msg).toString();
            ByteBuf byteBuf= Unpooled.copiedBuffer(msg.getBytes());
            ctx.writeAndFlush(byteBuf);
            msg = null;
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
