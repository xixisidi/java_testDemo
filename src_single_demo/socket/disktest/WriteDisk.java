/*
 * @(#)WriteDisk.java    Created on 2016年9月28日
 * Copyright (c) 2016 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package socket.disktest;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2016年9月28日 下午5:21:58 $
 */
public class WriteDisk {
    private static String path;

    public static void main(String[] args) {
        path = args[0];
        if (path == null || path == "") {
            System.out.println("获取不到写路径");
        }

        // TODO Auto-generated method stub
        MulticastSocket readSocket = null;
        MulticastSocket writeSocket = null;
        try {
            readSocket = new MulticastSocket(10002);
            InetAddress address1 = InetAddress.getByName("239.0.0.2");
            readSocket.joinGroup(address1);
            byte[] readBuf = new byte[1024];

            writeSocket = new MulticastSocket();
            InetAddress address = InetAddress.getByName("239.0.0.1"); // 必须使用D类地址
            writeSocket.joinGroup(address); // 以D类地址为标识，加入同一个组才能实现广播

            while (true) {
                // 写文件,获取文件长度
                byte[] buf = (write() + "").getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                datagramPacket.setAddress(address); // 接收地址和group的标识相同
                datagramPacket.setPort(10001); // 发送至的端口号

                // 发送者文件的长度
                writeSocket.send(datagramPacket);

                // 接收响应
                datagramPacket = new DatagramPacket(readBuf, readBuf.length);
                readSocket.receive(datagramPacket); // 接收数据，同样会进入阻塞状态
                byte[] message = new byte[datagramPacket.getLength()]; // 从buffer中截取收到的数据
                System.arraycopy(readBuf, 0, message, 0, datagramPacket.getLength());
                System.out.println(new String(message));
                if (new String(message).equals("close")) {
                    System.out.println("关闭");
                    break;
                }
            }

            readSocket.close();
            writeSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            try {
                readSocket.close();
                writeSocket.close();
            }
            catch (Exception e2) {
                // TODO: handle exception
            }
        }
    }

    private static long write() throws IOException {
        File file = new File(path).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }

        RandomAccessFile fs = new RandomAccessFile(path, "rw");
        fs.seek(fs.length());// 指定位置开始写入
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + "\r\n";
        fs.write(time.getBytes());

        long l = fs.length();

        System.out.println("写入内容：" + time.substring(0, time.length() - 2) + ",写入长度：" + l + ",写入路径：" + path);
        fs.close();
        return l;
    }
}
