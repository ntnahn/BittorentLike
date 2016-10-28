package com.bittorentlike.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Sender {
	public void send() throws Exception {
		// TODO code application logic here
		DatagramSocket ds = new DatagramSocket();
		try {
			File file = new File("D:\\testSenderFunction.chunk");
			FileInputStream f = new FileInputStream(file);
			byte[] b = new byte[1024];
			int remainLength = (int) file.length();
			int off = 0;
			while (remainLength >= 1024) {
				b = new byte[1024];
				f.read(b, 0, 1024);
				off = off + 1024;
				remainLength = remainLength - 1024;
				DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), 4567);
				System.out.println("Sending data to " + b.length + " bytes to server, port 4567");
				ds.send(dp);

			}
			if (remainLength > 0) {
				b = new byte[remainLength];
				int read = f.read(b, 0, remainLength);
				System.out.println("The number of bytes will be read: " + read);
				DatagramPacket dp = new DatagramPacket(b, b.length, InetAddress.getLocalHost(), 4567);
				System.out.println("Sending data to " + b.length + " bytes to server.");
				ds.send(dp);
			}

			f.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File not found :P");
		} finally {
//			ds.close();
		}
	}
	
	public void sendViaSocket() throws IOException {
		Socket socket = null;
        String host = "localhost";

        socket = new Socket(host, 4567);

        File file = new File("D:\\testReceiver001.chunk");
        // Get the size of the file
//        long length = file.length();
        byte[] bytes = new byte[16 * 1024];
        InputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }

        out.close();
        in.close();
        socket.close();
    }
}
