package com.bittorentlike.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLCommon;
import com.bittorentlike.common.BTLConstant;

public class BroadcastListenner {
	private boolean isRunning = true;
	int port;
	DatagramSocket ds;
	public BroadcastListenner(int port) {
		this.port = port;
	}

	public void start() {
		try {
			ds = new DatagramSocket(port);
			byte[] buffer = new byte[BTLConstant.MAX_PACKET_SIZE];
			while (isRunning) {
				ds.setSoTimeout(0);
				try {					
					DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
					ds.receive(dp);
					byte[] btlPackage = dp.getData();
					BTLPackage receiveBTLPackage = BTLCommon.deserializeBytes(btlPackage);
					if ( receiveBTLPackage != null ) {
						System.out.println("Server listen: " + new String(receiveBTLPackage.getOption()));
					}
				} catch (SocketTimeoutException ste) {
					break;
				}
			}
		} catch (IOException ex) {
			System.out.println("UDP Port " + port + " is occupied.");
			System.exit(1);
		}
	}

	public void stop() {
		System.out.println("Stop listen broadcast!");
		isRunning = false;
		ds.close();
	}
}
