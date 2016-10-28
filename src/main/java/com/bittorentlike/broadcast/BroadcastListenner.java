package com.bittorentlike.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLCommon;
import com.bittorentlike.common.BTLConstant;
import com.bittorentlike.common.ChunkTest;

public class BroadcastListenner {
	private boolean isRunning = true;
	int port;
	DatagramSocket ds;

	public BroadcastListenner(int port) {
		this.port = port;
	}

	public void start() {
		try {
			// Tạo kết nối UDP với port truyền vào và lắng nghe ở port đó
			ds = new DatagramSocket(port);
			// Tạo biến nhận dữ liệu sẽ nhận đc khi có máy khác gửi package tới
			byte[] buffer = new byte[BTLConstant.MAX_PACKET_SIZE];
			while (isRunning) {
				ds.setSoTimeout(0);
				try {
					// Tạo UDP packet để nhận dữ liêu
					DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
					// Nhận dữ liệu
					ds.receive(dp);
					// Lấy data của packet nhận đc
					byte[] btlPackage = dp.getData();
					System.out.println("----------------------------------------------");
					System.out.println("Server listen: getAddress " + dp.getAddress());
					System.out.println("Server listen: getSocketAddress " + dp.getSocketAddress());
					System.out.println("Server listen: Port " + dp.getPort());
					// Lấy gói tin đc gửi lên
					BTLPackage receiveBTLPackage = BTLCommon.deserializeBTLPackageBytes(btlPackage);
					if (receiveBTLPackage != null) {
						// Nếu nhận đc gói dữ liệu thì tiến hành kiểm tra nó là loại gì để xử lý
//						int packageType = receiveBTLPackage.getType();
						
						System.out.println("Server listen: " + new String(receiveBTLPackage.getOption()));
						byte[] receiveBTLPackageData = receiveBTLPackage.getData();
						if (receiveBTLPackageData != null) {
							ChunkTest chunk = BTLCommon.deserializeChunkTestBytes(receiveBTLPackageData);
							if (chunk != null) {
								if (BTLCommon.checkChunkFileExistsInLocal(chunk.getFilePath())) {
									System.out.println("Server listen: exists filepath " + new String(chunk.getFilePath()));
								} else {
									System.out.println("Server listen: filepath not exists " + new String(chunk.getFilePath()));
								}
							}
						}
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
