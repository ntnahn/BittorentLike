package com.bittorentlike.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLCommon;

public class Sender {
	DatagramSocket socket;
	public Sender() {
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean sendData(Integer port, String ipAddress, BTLPackage pack) {   
        try {
            InetAddress ipServer = InetAddress.getByName(ipAddress);
            byte[] sendData = BTLCommon.serializeObject(pack);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getLocalHost(), port);
            socket.send(sendPacket);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	public void close() {
		socket.close();
	}
}
