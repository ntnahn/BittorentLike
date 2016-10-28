package com.bittorentlike.common;

public class BTLConstant {
	public static final Integer LISTEN_PORT = 9797;
	public static final Integer SEND_PORT = 9797;
	public static final String IP_ADDRESS = "localhost";
	public static final int MAX_PACKET_SIZE = 1024;
	// Broadcast gói tin để kiểm tra xem máy nào trong mạng đang giữ file
	public static final int TYPE_BROADCAST = 0;
	// Gửi khi máy này có file chunk mà máy kia yêu cầu
	public static final int TYPE_HAVE_CHUNK = 1;
	// Khi gửi thông tin từng file đã chia nhỏ của file cần tải
	public static  int TYPE_CHUNK = 2;
}