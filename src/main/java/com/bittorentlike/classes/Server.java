package com.bittorentlike.classes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Server {
	private boolean isRunning = true;
	int port = 9797;
	DatagramSocket ds;
	public void start() throws IOException {
		ds = new DatagramSocket(port);
		try {
			byte[] receiveData = new byte[1024];
			int off = 0;
			FileOutputStream fos = new FileOutputStream(new File("D:\\TKB HK3 Download.png"));
			while (isRunning) {
				ds.setSoTimeout(0);
				try {
					receiveData = new byte[1024];

					DatagramPacket dp = new DatagramPacket(receiveData, receiveData.length);
					byte[] receivedData = dp.getData();
					System.out.println("Server listen: " + new String(receivedData));
					ds.receive(dp);
					byte[] b1 = new byte[dp.getLength()];
					fos.write(dp.getData(), 0, b1.length);
					off = off + b1.length;
				} catch (SocketTimeoutException ste) {
					break;
				}
			}
			fos.close();
		} catch (SocketException ex) {
			System.out.println("UDP Port "+port+" is occupied.");
			System.exit(1);
		}
	}
	public void stop() {
		isRunning = false;
		ds.close();
	}
}
