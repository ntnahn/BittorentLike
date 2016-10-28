package com.bittorentlike.broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.UUID;

import com.bittorentlike.chunks.InfoChunk;
import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.AppStaticVariable;
import com.bittorentlike.common.BTLCommon;
import com.bittorentlike.common.BTLConstant;
import com.bittorentlike.controller.Download;
import com.bittorentlike.udp.Sender;
import com.bittorentlike.udp.SenderThread;

public class BroadcastListenner {
	private boolean isRunning = true;
	int port;
	DatagramSocket ds;
	// Use to when broadcast, if it send to it self, then ignore
	public String serverID;
	// Lưu các thread
	private ArrayList<SenderThread> senderThreads;

	public BroadcastListenner(int port) {
		this.port = port;
		this.serverID = UUID.randomUUID().toString();
		Download.downloadController.addStatus("ServerID: " + this.serverID);
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
					this.handlePackage(dp);
				} catch (SocketTimeoutException ste) {
					break;
				}
			}
		} catch (IOException ex) {
			Download.downloadController.addStatus("UDP Port " + port + " is occupied.");
			System.exit(1);
		}
	}

	public void stop() {
		Download.downloadController.addStatus("Stop listen broadcast!");
		isRunning = false;
		ds.close();
	}
	
	private void handlePackage(DatagramPacket dp) {
//		Download.downloadController.addStatus("----------------------------------------------");
		this.serverHandlePackage(dp);
		this.clientHandlePackage(dp);
	}

	private void serverHandlePackage(DatagramPacket dp) {
		// Lắng nghe các yêu cầu từ phía client
		// Lấy data của packet nhận đc
		byte[] btlPackageReceive = dp.getData();
		// Lấy gói tin đc gửi lên
		BTLPackage receiveBTLPackage = BTLCommon.deserializeBTLPackageBytes(btlPackageReceive);
		if (receiveBTLPackage != null) {
			// Kiểm tra xem gói tin nhận đc có phải do chính mình gửi hay ko
			// Tại vì broadcast cũng gửi cho chính nó
			// Kiểm tra ở đây tránh trường hợp tự gửi tự nhận
//			if ( receiveBTLPackage.getOption().equals(this.serverID) ) {
//				Download.downloadController.addStatus("Server listen: serverID receive " + receiveBTLPackage.getOption());
//				Download.downloadController.addStatus("Server listen: serverID local   " + this.serverID);
//				return;
//			}
			// Nếu nhận đc gói dữ liệu thì tiến hành kiểm tra nó là loại gì để xử lý
			byte packageType = receiveBTLPackage.getType();
			byte[] receiveBTLPackageData = receiveBTLPackage.getData();
			Download.downloadController.addStatus("Server listen: " + packageType);
			Download.downloadController.addStatus("Server listen: " + new String(receiveBTLPackage.getOption()));
			switch (packageType) {
				// Yêu cầu xác nhận file chunk có hay ko?
				case BTLConstant.TYPE_BROADCAST:
					// Kiểm tra nếu trên máy có chứa file mà máy kia cần tải về thì
					// Gửi lại cho máy kia gói tin nói là máy này có tập tin đó
					// Khi bên máy kia nhận đc thì nó sẽ chọn máy nào để tiến hành nhận, tại vì có thể có nhiều máy có file đó
					if (receiveBTLPackageData != null) {
						InfoChunk infoChunk = BTLCommon.deserializeChunkTestBytes(receiveBTLPackageData);
						if (infoChunk != null) {
							if (BTLCommon.checkChunkFileExistsInLocal(infoChunk.getPath())) {
								Download.downloadController.addStatus("Server listen: Send package to tell to client it have file that client need");
								Sender sender = new Sender();
								byte[] data = BTLCommon.serializeObject(infoChunk);
								BTLPackage btlPackage = new BTLPackage(this.serverID, data);
								btlPackage.setType(BTLConstant.TYPE_HAVE_CHUNK);
								sender.sendData(BTLConstant.LISTEN_PORT, dp.getAddress().getHostAddress(), btlPackage);
							} else {
								Download.downloadController.addStatus("Server listen: filepath not exists " + new String(infoChunk.getPath()));
							}
						}
					}
					break;
				// Nếu trên máy có file và bên client xác nhận là muốn nhận file từ máy server này thì tiến hành tạo mới
				// một luồng xử lý để tiến hành gửi file cho máy client
				case BTLConstant.TYPE_ACCEPT_RECEIVE:
					// Create thread send file here
					SenderThread sendThread = new SenderThread(receiveBTLPackage);
					senderThreads.add(sendThread);
					sendThread.start();
					Download.downloadController.addStatus("Server listen: TYPE_ACCEPT_RECEIVE");
					break;
			}
		}
	}
	
	private void clientHandlePackage(DatagramPacket dp) {
		// Lắng nghe gói tin từ server
		// Lấy data của packet nhận đc
		byte[] btlPackageReceive = dp.getData();	
		// Lấy gói tin đc gửi lên
		BTLPackage receiveBTLPackage = BTLCommon.deserializeBTLPackageBytes(btlPackageReceive);
		if (receiveBTLPackage != null) {
			// Kiểm tra xem gói tin nhận đc có phải do chính mình gửi hay ko
			// Tại vì broadcast cũng gửi cho chính nó
			// Kiểm tra ở đây tránh trường hợp tự gửi tự nhận
//					if ( receiveBTLPackage.getOption().equals(this.serverID) ) {
//						Download.downloadController.addStatus("Server listen: serverID receive " + receiveBTLPackage.getOption());
//						Download.downloadController.addStatus("Server listen: serverID local   " + this.serverID);
//						return;
//					}
			// Nếu nhận đc gói dữ liệu thì tiến hành kiểm tra nó là loại gì để xử lý
			byte packageType = receiveBTLPackage.getType();
			byte[] receiveBTLPackageData = receiveBTLPackage.getData();
			Download.downloadController.addStatus("Server listen: " + packageType);
			Download.downloadController.addStatus("Server listen: " + new String(receiveBTLPackage.getOption()));
			switch (packageType) {
				case BTLConstant.TYPE_HAVE_CHUNK:
					// Kiểm tra xem đã download từ máy nào chưa
					// Nếu chưa thì nếu có máy có tập tin thì gửi yêu cầu cho máy đó gửi về cho mình
					// Để chặn lại chỉ cho download trên 1 máy
					if (!AppStaticVariable.isDownloading) {
						Sender sender = new Sender();
						InfoChunk infoChunk = BTLCommon.deserializeChunkTestBytes(receiveBTLPackageData);
						if ( infoChunk != null ) {
							byte[] data = BTLCommon.serializeObject(infoChunk);
							BTLPackage btlPackage = new BTLPackage(this.serverID, data);
							btlPackage.setType(BTLConstant.TYPE_ACCEPT_RECEIVE);
							sender.sendData(BTLConstant.LISTEN_PORT, dp.getAddress().getHostAddress(), btlPackage);
							Download.downloadController.addStatus("Client listen: receive server have file, do send accept request");
							AppStaticVariable.isDownloading = true;
						}
					}
					break;
				// Nhận file từ server
				case BTLConstant.TYPE_CHUNK:
					Download.downloadController.addStatus("Client listen: receive server chunk file");
					break;
			}
		}
	}
}
