package com.huawei.it.bio;/*
 * @author 片尾solo
 * @Date  2018/11/9  11:11
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ResourceUtil {
    public static void close(BufferedReader in, PrintWriter out, Socket socket){
        try {
            if(in!=null){
                in.close();
            }
            if(out!=null){
                out.close();
            }
            if(socket!=null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
