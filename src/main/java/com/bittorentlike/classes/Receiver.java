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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Receiver {
	public void listen() throws Exception {
		DatagramSocket ds = new DatagramSocket(4567);
		try {
			byte[] receiveData = new byte[1024];
			int off = 0;
			FileOutputStream fos = new FileOutputStream(new File("D:\\testReceiver001.chunk"));
			while (true) {
				ds.setSoTimeout(1000);

				try {
					receiveData = new byte[1024];

					DatagramPacket dp = new DatagramPacket(receiveData, receiveData.length);

					ds.receive(dp);
					byte[] b1 = new byte[dp.getLength()];
					fos.write(receiveData, 0, b1.length);
					off = off + b1.length;
				} catch (SocketTimeoutException ste) {
					break;
				}
			}
			fos.close();
		} catch (SocketException ex) {
			System.out.println("UDP Port 4567 is occupied.");
			System.exit(1);
		} finally {
//			ds.close();
		}
	}
	
	public void listenViaSocket() throws UnknownHostException, IOException {
		ServerSocket serverSocket = null;

        try {
        	serverSocket = new ServerSocket(4567);
        } catch (IOException ex) {
            System.out.println("Can't setup server on this port number. ");
        }

        Socket socket = null;
        InputStream in = null;
        OutputStream out = null;

        try {
            socket = serverSocket.accept();
        } catch (IOException ex) {
            System.out.println("Can't accept client connection. ");
        }

        try {
            in = socket.getInputStream();
        } catch (IOException ex) {
            System.out.println("Can't get socket input stream. ");
        }

        try {
            out = new FileOutputStream("D:\\testReceiver001.chunk");
        } catch (FileNotFoundException ex) {
            System.out.println("File not found. ");
        }

        byte[] bytes = new byte[16*1024];

        int count;
        while ((count = in.read(bytes)) > 0) {
            out.write(bytes, 0, count);
        }

        out.close();
        in.close();
        socket.close();
        serverSocket.close();
	}
	
	public static void server() throws IOException {
        ServerSocket ss = new ServerSocket(3434);
        Socket socket = ss.accept();
        InputStream in = new FileInputStream("D:\\testSenderFunction.chunk");
        OutputStream out = socket.getOutputStream();
        copy(in, out);
        out.close();
        in.close();
    }

    public static void client() throws IOException {
        Socket socket = new Socket("localhost", 3434);
        InputStream in = socket.getInputStream();
        OutputStream out = new FileOutputStream("D:\\testReceiver001.chunk");
        copy(in, out);
        out.close();
        in.close();
    }

    static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            out.write(buf, 0, len);
        }
    }
}
