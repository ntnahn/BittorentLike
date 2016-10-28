package com.bittorentlike.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
	public void start() throws IOException {
		int port = 9797;
		DatagramSocket ds = new DatagramSocket();
		try {
			File file = new File("D:\\TKB HK3.png");
			FileInputStream f = new FileInputStream(file);
			byte[] b = new byte[1024];
			int remainLength = (int) file.length();
			int off = 0;
			while (remainLength >= 1024) {
				b = new byte[1024];

				f.read(b, 0, 1024);
				/*
				 * public int read(byte[] b, int off, int len) throws
				 * IOException b - the buffer into which the data is read. off -
				 * the start offset in the destination array b //I'm sorry. I
				 * missed off argu when I introduced the read function len - the
				 * maximum number of bytes read.
				 */
				off = off + 1024;
				remainLength = remainLength - 1024;
				//
				DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), port);
				System.out.println("Sending data to " + b.length + " bytes to server, port " + port);
				ds.send(dp);

			}
			if (remainLength > 0) {
				/*
				 * -- Wrong b = new byte[1024]; int read = f.read(b, 0, 1024);
				 * //read value will be smaller 1024
				 */
				b = new byte[remainLength];
				int read = f.read(b, 0, remainLength);
				System.out.println("The number of bytes will be read: " + read);

				DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), port);
				System.out.println("Sending data to " + b.length + " bytes to server.");
				ds.send(dp);
			}

			f.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File not found :P");
		}
	}
}
