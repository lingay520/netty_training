package com.huawei.it.nio;/*
 * @author 片尾solo
 * @Date  2018/11/5  22:36
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    private Selector selector;
    private static int PROT = 9999;
    private static String UTF8 = "UTF-8";
    private InetSocketAddress inetSocketAddress = new InetSocketAddress(PROT);
    private Charset charset = Charset.forName(UTF8);
    private ServerSocketChannel ssc;

    public NIOServer() throws IOException {
        selector = Selector.open();
        ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        ssc.bind(inetSocketAddress, 100);
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    public static void main(String[] args) {
        try {
            new NIOServer().listen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void listen() throws IOException, InterruptedException {
        this.header();
        while (true) {
            int count = 0;
            count = selector.select();
            if (count == 0) continue;
            this.handle();
            Thread.sleep(1000);
        }
    }

    private void handle() throws IOException {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            iterator.remove();
            if (key.isAcceptable()) {
                SocketChannel socketChannel = ssc.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ, new Buffers(256, 256));
            }
            if (key.isReadable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                Buffers buffers = (Buffers) key.attachment();
                ByteBuffer readBuffer = buffers.getReadBuffer();
                socketChannel.read(readBuffer);
                readBuffer.flip();
                ByteBuffer showBuffer = ByteBuffer.allocate(readBuffer.limit());
                showBuffer.put(readBuffer.array(),0,readBuffer.limit());
                System.out.println(new String(showBuffer.array()));
                readBuffer.rewind();
                readBuffer.clear();
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
            }
            if (key.isWritable()) {
                SocketChannel socketChannel = (SocketChannel) key.channel();
                Buffers buffers = (Buffers) key.attachment();
                ByteBuffer writeBuffer = buffers.getWriteBuffer();
                socketChannel.write(writeBuffer);
                writeBuffer.flip();
                int len = 0;
                if (writeBuffer.hasRemaining()) {
                    len = socketChannel.write(writeBuffer);
                    if (len == 0) break;
                }
                writeBuffer.compact();
                writeBuffer.clear();
                key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
            }

        }
    }

    private void header() {
        System.out.println("#################聊天服务器启动##################");
        System.out.println("##############欢迎进入爱聊不聊聊天室##############");
        System.out.println("##############################################");
        System.out.println("————————————————————————————————————————————————————————");
    }
}
