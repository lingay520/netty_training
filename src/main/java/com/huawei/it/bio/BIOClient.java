package com.huawei.it.bio;/*
 * @author 片尾solo
 * @Date  2018/11/9  11:12
 */

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BIOClient {
    private Socket socket;
    private static int SERVER_PORT = 9999;
    private static String SERVER_IP = "127.0.0.1";
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner = new Scanner(System.in);

    public BIOClient() throws IOException {
        //1.通过IP和端口创建客户端套接字
        socket = new Socket(SERVER_IP,SERVER_PORT);
    }
    /*
     客户端
     1.通过IP和端口创建客户端套接字
     2.从客户端套接字中获取输入流
     3.从客户端套接字中获取输入流
     4.往输出流写入客户端字符串数据，并刷新输出流
     5.关闭流和socket
     */
    public static void main(String[] args) {
        try {
            new BIOClient().setMsg();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void listen(String msg,Socket socket) throws IOException {
        //2.从客户端套接字中获取输入流
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //3.从客户端套接字中获取输出流
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
        //4.往输出流写入客户端字符串数据，并刷新输出流
        out.write(msg);
        out.flush();
        //5.关闭流和socket
        ResourceUtil.close(in,out,socket);
    }
    private void setMsg(){
        System.out.print("请输入你的昵称:");
        String name = scanner.nextLine();
        try {
            while(true){
                try {
                    StringBuilder sb = new StringBuilder();
                    String msg =  scanner.nextLine();
                    msg = sb.append(name).append(":").append(msg).toString();
                    Socket socket = new Socket(SERVER_IP,SERVER_PORT);
                    if(socket!=null){
                        this.listen(msg,socket);
                        msg =null;
                    }
                    Thread.sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
