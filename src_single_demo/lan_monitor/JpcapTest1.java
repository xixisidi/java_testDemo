package lan_monitor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class JpcapTest1 implements PacketReceiver {

    public static void main(String[] args) throws IOException, InterruptedException {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        for (NetworkInterface device : devices) {
            printDevice(device);
            System.out.println("=================================");
        }

        printIMData(devices[0]);

    }

    public static void printIMData(NetworkInterface device) throws IOException {
        JpcapCaptor captor = JpcapCaptor.openDevice(device, 65535, false, 20);
        captor.loopPacket(-1, new PacketReceiver() {
            @Override
            public void receivePacket(Packet packet) {
                IPPacket ipPacket = (IPPacket) packet;
                String target_ip = String.valueOf(ipPacket.dst_ip);
                String src_ip = String.valueOf(ipPacket.src_ip);

                if (packet.getClass().equals(TCPPacket.class)) {
                    if (src_ip.equals("/192.168.60.108") && target_ip.equals("/106.3.210.9")) {
                        try {
                            System.out.println("tcp：>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                            System.out.println("  target_ip:" + target_ip + "||src_ip:" + src_ip);
                            String data = new String(packet.data, "utf-8");
                            System.out.println("  data：>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                            System.out.println("         data1:" + new String(packet.data));
                            System.out.println("         data2:" + data);
                            System.out.println("         data3:" + packet.data);
                            System.out.println("        utl-8:" + URLDecoder.decode(data, "utf-8"));
                            System.out.println("          gbk:" + URLDecoder.decode(data, "gbk"));
                        }
                        catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    public static void printData(NetworkInterface device) throws IOException {
        // printDevuice(device);// 打印device信息
        JpcapCaptor captor = JpcapCaptor.openDevice(device, 65535, false, 20);
        captor.loopPacket(-1, new PacketReceiver() {
            @Override
            public void receivePacket(Packet packet) {
                IPPacket ipPacket = (IPPacket) packet;
                String target_ip = String.valueOf(ipPacket.dst_ip);
                String src_ip = String.valueOf(ipPacket.src_ip);

                if (packet.getClass().equals(UDPPacket.class)) {
                    if (src_ip.equals("/192.167.1.107")) {
                        // System.out.println("target_ip:" + target_ip + "||src_ip:"+src_ip+"||data:"+ new
                        // String(packet.data));
                    }
                }
                else if (packet.getClass().equals(TCPPacket.class)) {
                    if (src_ip.equals("/192.167.1.107")) {
                        try {
                            System.out.println("target_ip:" + target_ip + "||src_ip:" + src_ip);
                            String data = new String(packet.data, "utf-8");
                            System.out.println("data===========================================");
                            System.out.println(URLDecoder.decode(data, "utf-8"));
                        }
                        catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @SuppressWarnings("unused")
    private static void printDevice(NetworkInterface device) {
        System.out.println(device.datalink_description);
        System.out.println(device.datalink_name);
        System.out.println(device.description);
        System.out.println(device.mac_address);
        System.out.println(device.name);
        System.out.println(device.addresses[1].address.getHostAddress());

    }

    @Override
    public void receivePacket(Packet packet) {
        if (packet.getClass().equals(ARPPacket.class)) {
            System.out.println("协议类型 ：ARP协议");
            try {
                ARPPacket arpPacket = (ARPPacket) packet;
                String target_ip = String.valueOf(arpPacket.getTargetProtocolAddress());
                String target_mac = String.valueOf(arpPacket.getTargetHardwareAddress());
                String src_ip = String.valueOf(arpPacket.getSenderProtocolAddress());
                String src_mac = String.valueOf(arpPacket.getSenderHardwareAddress());

                DataPacket d = new DataPacket();
                // d.setArpid(arpid);
                d.setTarget_ip(target_ip);
                d.setTarget_mac(target_mac);
                d.setSrc_ip(src_ip);
                d.setSrc_mac(src_mac);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (packet.getClass().equals(UDPPacket.class)) {
            System.out.println("协议类型 ：UDP协议");
            try {
                UDPPacket udpPacket = (UDPPacket) packet;
                String target_ip = String.valueOf(udpPacket.dst_ip);
                int target_port = udpPacket.dst_port;
                String src_ip = String.valueOf(udpPacket.src_ip);
                int src_port = udpPacket.src_port;

                DataPacket d = new DataPacket();
                // d.setUdpid(udpid);
                d.setTarget_ip(target_ip);
                d.setTarget_port(target_port);
                d.setSrc_ip(src_ip);
                d.setSrc_port(src_port);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (packet.getClass().equals(TCPPacket.class)) {
            System.out.println("协议类型 ：TCP协议");
            try {
                TCPPacket tcpPacket = (TCPPacket) packet;

                String target_ip = String.valueOf(tcpPacket.dst_ip);
                int target_port = tcpPacket.dst_port;
                String src_ip = String.valueOf(tcpPacket.src_ip);
                int src_port = tcpPacket.src_port;

                DataPacket d = new DataPacket();
                // d.setTcpid(tcpid);
                d.setTarget_ip(target_ip);
                d.setTarget_port(target_port);
                d.setSrc_ip(src_ip);
                d.setSrc_port(src_port);

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (packet.getClass().equals(ICMPPacket.class)) {
            System.out.println("协议类型 ：ICMP协议");
        }
        else {
            System.out.println("协议类型 ：GGP、EGP、JGP协议或OSPF协议或ISO的第4类运输协议TP4");
        }

    }

    public static class DataPacket {
        private String arpid;
        private String udpid;
        private String tcpid;
        private String target_ip;
        private int target_port;
        private String src_ip;
        private int src_port;
        private String target_mac;
        private String src_mac;

        public String getArpid() {
            return arpid;
        }

        public void setArpid(String arpid) {
            this.arpid = arpid;
        }

        public String getUdpid() {
            return udpid;
        }

        public void setUdpid(String udpid) {
            this.udpid = udpid;
        }

        public String getTcpid() {
            return tcpid;
        }

        public void setTcpid(String tcpid) {
            this.tcpid = tcpid;
        }

        public String getTarget_ip() {
            return target_ip;
        }

        public void setTarget_ip(String target_ip) {
            this.target_ip = target_ip;
        }

        public String getSrc_ip() {
            return src_ip;
        }

        public void setSrc_ip(String src_ip) {
            this.src_ip = src_ip;
        }

        public int getTarget_port() {
            return target_port;
        }

        public void setTarget_port(int target_port) {
            this.target_port = target_port;
        }

        public int getSrc_port() {
            return src_port;
        }

        public void setSrc_port(int src_port) {
            this.src_port = src_port;
        }

        public String getTarget_mac() {
            return target_mac;
        }

        public void setTarget_mac(String target_mac) {
            this.target_mac = target_mac;
        }

        public String getSrc_mac() {
            return src_mac;
        }

        public void setSrc_mac(String src_mac) {
            this.src_mac = src_mac;
        }
    }
}
