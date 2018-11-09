package com.huawei.it.bio;/*
 * @author 片尾solo
 * @Date  2018/11/9  11:10
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {
    private ServerSocket serverSocket;
    private static int PORT = 9999;

    public BIOServer() throws IOException {
        //1.创建服务端套接字
        serverSocket = new ServerSocket(PORT);
    }
    /*
     服务器
     1.创建服务端套接字。
     2.通过客户端套接字循环监听服务器套接字 accept事件
     3.从客户端套接字获取输入流
     4.从客户端套接字获取输出流
     5.输入流按行读取字符串不为空时，往输出流写入数据，并刷新输出流
     6.关闭相关流和socket
     */
    public static void main(String[] args) {
        try {
            new BIOServer().listen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void listen() throws IOException, InterruptedException {
        this.header();
        while(true){
            //2.通过客户端套接字循环监听服务器套接字 accept事件
            Socket socket = serverSocket.accept();
            new Thread(new Handler(socket)).start();
            Thread.sleep(1000);
        }

    }

    private void header(){
        System.out.println("#################聊天服务器启动##################");
        System.out.println("##############欢迎进入爱聊不聊聊天室##############");
        System.out.println("##############################################");
        System.out.println("————————————————————————————————————————————————————————");
    }

}
