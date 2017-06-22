/*
 * @(#)WriteDisk.java    Created on 2016年9月28日
 * Copyright (c) 2016 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package socket.disktest;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * @author Administrator
 * @version $Revision: 1.0 $, $Date: 2016年9月28日 下午5:21:58 $
 */
public class ReadDisk {
    private static String path;

    public static void main(String[] args) throws InterruptedException {
        path = args[0];
        if (path == null || path == "") {
            System.out.println("获取不到读路径");
        }

        long delayTime = 1000;
        if (args.length > 1) {
            delayTime = Long.parseLong(args[1]);
        }

        // TODO Auto-generated method stub
        MulticastSocket readSocket = null;
        MulticastSocket writeSocket = null;
        try {
            readSocket = new MulticastSocket(10001);
            InetAddress address1 = InetAddress.getByName("239.0.0.1");
            readSocket.joinGroup(address1);
            byte[] buf = new byte[1024];

            writeSocket = new MulticastSocket();
            InetAddress address2 = InetAddress.getByName("239.0.0.2"); // 必须使用D类地址
            writeSocket.joinGroup(address2); // 以D类地址为标识，加入同一个组才能实现广播

            while (true) {
                // 接收数据
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                readSocket.receive(datagramPacket); // 接收数据，同样会进入阻塞状态
                byte[] message = new byte[datagramPacket.getLength()]; // 从buffer中截取收到的数据
                System.arraycopy(buf, 0, message, 0, datagramPacket.getLength());
                long l1 = Long.parseLong(new String(message));

                Thread.sleep(delayTime);

                long l2 = new File(path).length();
                System.out.println("应该大小：" + l1 + ",读取大小：" + l2 + ",读取路径：" + path);
                if (l1 != l2) {
                    System.out.println("文件大小错误");

                    // 返回
                    byte[] writeBuf = "close".getBytes();
                    datagramPacket = new DatagramPacket(writeBuf, writeBuf.length);
                    datagramPacket.setAddress(address2); // 接收地址和group的标识相同
                    datagramPacket.setPort(10002); // 发送至的端口号
                    writeSocket.send(datagramPacket);
                    break;
                }
                System.out.println("文件大小正常");

                // 返回
                byte[] writeBuf = "continue".getBytes();
                datagramPacket = new DatagramPacket(writeBuf, writeBuf.length);
                datagramPacket.setAddress(address2); // 接收地址和group的标识相同
                datagramPacket.setPort(10002); // 发送至的端口号
                writeSocket.send(datagramPacket);
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
        } // 接收数据时需要指定监听的端口号
    }
}
