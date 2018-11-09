package com.huawei.it.nio;/*
 * @author 片尾solo
 * @Date  2018/11/5  23:10
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOClient {
    private SocketChannel sc;
    private static String UTF8 = "UTF-8";
    private static String SERVER_IP = "127.0.0.1";
    private static int SERVER_PORT = 9999;
    private Selector selector;
    private static InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
    private Scanner scanner = new Scanner(System.in);
    private String name;
    private Charset charset = Charset.forName(UTF8);

    public NIOClient() throws IOException {
        selector = Selector.open();
        sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, new Buffers(256, 256));

    }

    public static void main(String[] args) {
        try {
            new NIOClient().listen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void listen() throws IOException, InterruptedException {
        sc.connect(inetSocketAddress);
        if (!sc.finishConnect()) {
        };
        this.setName();
        while (true) {
            int count = selector.select();
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
            SocketChannel socketChannel = (SocketChannel) key.channel();
            Buffers buffers = (Buffers) key.attachment();
            ByteBuffer readBuffer = buffers.getReadBuffer();
            ByteBuffer writeBuffer = buffers.getWriteBuffer();
            if (key.isReadable()) {
                socketChannel.read(readBuffer);
                readBuffer.flip();
                readBuffer.clear();
            }
            if (key.isWritable()) {
                StringBuilder sb = new StringBuilder();
                String msg =  scanner.nextLine();
                msg = sb.append(name).append(":").append(msg).toString();
                writeBuffer.put((msg).getBytes("UTF-8"));
                writeBuffer.flip();
                socketChannel.write(writeBuffer);
                writeBuffer.clear();
            }
        }
    }

    private void setName() {
        System.out.println("请输入你的昵称：");
        name = scanner.nextLine();
    }
}
