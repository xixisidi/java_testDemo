package lan_monitor;

import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class JpcapTest2 implements PacketReceiver {

    public static void main(String[] args) throws IOException, InterruptedException {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        for (NetworkInterface device : devices) {
            printDevice(device);
            System.out.println("=================================");
        }

    }

    private static void printDevice(NetworkInterface device) {
        System.out.println(device.datalink_description);
        System.out.println(device.datalink_name);
        System.out.println(device.description);
        System.out.println(device.mac_address);
        System.out.println(device.name);
        System.out.println(device.addresses[1].address.getHostAddress());

    }

    @Override
    public void receivePacket(Packet arg0) {
        // TODO Auto-generated method stub

    }

}
