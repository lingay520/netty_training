package com.huawei.it.nio;/*
 * @author 片尾solo
 * @Date  2018/11/5  22:42
 */

import java.nio.ByteBuffer;

public class Buffers {
    private ByteBuffer readBuffer;
    private ByteBuffer writeBuffer;
    public Buffers(int rsize ,int wsize) {
        readBuffer = ByteBuffer.allocate(rsize);
        writeBuffer = ByteBuffer.allocate(wsize);
    }
    public ByteBuffer getReadBuffer(){
        return readBuffer;
    }
    public ByteBuffer getWriteBuffer(){
        return writeBuffer;
    }
}
