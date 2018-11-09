package com.huawei.it.bio;/*
 * @author 片尾solo
 * @Date  2018/11/9  11:11
 */

import java.io.*;
import java.net.Socket;

public class Handler implements  Runnable {

    private Socket socket;

    private PrintWriter out;
    private BufferedReader in;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //3.从客户端套接字获取输入流
        try {
            //4.从客户端套接字获取输出流
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);
            String str = in.readLine();
            //5.输入流按行读取字符串不为空时，往输出流写入数据，并刷新输出流
            if( null!= str){
                out.write(str);
                System.out.println(str);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //6.关闭相关流和socket
            ResourceUtil.close(in,out,socket);
        }

    }
}
