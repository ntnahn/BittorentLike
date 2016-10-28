package com.bittorentlike.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLCommon;

public class Sender {
	public boolean sendData(Integer port, String ipAddress, BTLPackage pack) {   
        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress ipServer = InetAddress.getByName(ipAddress);
            byte[] sendData = BTLCommon.serializeObject(pack);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipServer, port);
            socket.send(sendPacket);
            socket.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
