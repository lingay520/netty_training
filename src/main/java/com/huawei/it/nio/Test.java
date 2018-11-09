package com.huawei.it.nio;/*
 * @author 片尾solo
 * @Date  2018/11/6  2:30
 */

import java.lang.reflect.Array;
import java.nio.ByteBuffer;

public class Test {
    public static void main(String[] args) {
        new Test().start();
    }
    private void start(){
        String str = "abcdefg";
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes());
        System.out.println("原来 ： "+buffer);
        buffer.flip();
        System.out.println("flip :"+buffer);
        ByteBuffer buffer1 =  ByteBuffer.allocate(buffer.limit());
        buffer1.put(buffer.array(),0,buffer.limit());

        System.out.println(new String(buffer1.array()));


        buffer.rewind();
        System.out.println("rewind :"+buffer);




    }

}
