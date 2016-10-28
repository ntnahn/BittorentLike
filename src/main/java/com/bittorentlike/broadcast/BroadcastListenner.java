package com.bittorentlike.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLCommon;
import com.bittorentlike.common.BTLConstant;
import com.bittorentlike.common.ChunkTest;
import com.bittorentlike.udp.Sender;

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
					// Xử lý dữ liệu
					this.handlePackageType(dp);
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

	private void handlePackageType(DatagramPacket dp) {
		// Lấy data của packet nhận đc
		byte[] btlPackageReceive = dp.getData();
		System.out.println("----------------------------------------------");
		System.out.println("Server listen: getAddress " + dp.getAddress());
		System.out.println("Server listen: getSocketAddress " + dp.getSocketAddress());
		System.out.println("Server listen: Port " + dp.getPort());
		// Lấy gói tin đc gửi lên
		BTLPackage receiveBTLPackage = BTLCommon.deserializeBTLPackageBytes(btlPackageReceive);
		if (receiveBTLPackage != null) {
			// Nếu nhận đc gói dữ liệu thì tiến hành kiểm tra nó là loại gì để xử lý
			byte packageType = receiveBTLPackage.getType();
			byte[] receiveBTLPackageData = receiveBTLPackage.getData();
			System.out.println("Server listen: " + packageType);
			System.out.println("Server listen: " + new String(receiveBTLPackage.getOption()));
			switch (packageType) {
				case BTLConstant.TYPE_BROADCAST:
					// Kiểm tra nếu trên máy có chứa file mà máy kia cần tải về thì tiến hành tạo mới
					// một luồng xử lý để tiến hành gửi file cho máy kia
					// Gửi lại cho máy kia gói tin nói là máy này có tập tin đó
					// Khi bên máy kia nhận đc thì nó sẽ chọn máy nào để tiến hành nhận, tại vì có thể có nhiều máy có file đó
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
					Sender sender = new Sender();
					ChunkTest chunkTest = new ChunkTest("abc.chunk", "D:\\TKB HK3.png");
					byte[] data = BTLCommon.serializeObject(chunkTest);
					BTLPackage btlPackage = new BTLPackage("I have file you looking for! Come to get it", data);
					btlPackage.setType(BTLConstant.TYPE_HAVE_CHUNK);
					sender.sendData(BTLConstant.LISTEN_PORT, dp.getAddress().getHostAddress(), btlPackage);
					break;
				case BTLConstant.TYPE_HAVE_CHUNK:
					 System.out.println("Server listen: Have chunk");
					break;
				case BTLConstant.TYPE_CHUNK:
		
					break;
			}
		}
	}
}
