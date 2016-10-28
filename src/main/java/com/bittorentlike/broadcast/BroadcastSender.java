package com.bittorentlike.broadcast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLCommon;
import com.bittorentlike.common.BTLConstant;

public class BroadcastSender {
	int port;
	private DatagramSocket senderSocket;

	public BroadcastSender(int port) {
		this.port = port;
	}

	public void doBroadcast(BTLPackage btlPackage) {
		try {
			System.out.println("Begin send Broadcast!");
			byte[] buffer = BTLCommon.serializeObject(btlPackage);
			// For this example, just send it to the local host
			InetAddress addr = InetAddress.getByName(BTLConstant.IP_ADDRESS);
			// Create the packet of information
			DatagramPacket infoPacket = new DatagramPacket(buffer, buffer.length, addr, this.port);
			senderSocket = new DatagramSocket();
			// Send the packet and clean up the resources
			senderSocket.send(infoPacket);
			senderSocket.close();
		} catch(Exception ex) {
			System.out.println("Do send Broadcast failed!");
		}
	}
}
