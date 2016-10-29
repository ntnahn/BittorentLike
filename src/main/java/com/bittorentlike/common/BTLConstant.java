package com.bittorentlike.common;

public class BTLConstant {
	public static final Integer LISTEN_PORT = 100;
	public static final Integer SEND_PORT = 100;
	public static final String IP_ADDRESS = "localhost";
	public static final int MAX_PACKET_SIZE = 1024;
	public static final int BUFFER_SIZE = 512;
	// Broadcast gói tin để kiểm tra xem máy nào trong mạng đang giữ file
	public static final byte TYPE_BROADCAST = 1;
	// Gửi khi máy này có file chunk mà máy kia yêu cầu
	public static final byte TYPE_HAVE_CHUNK = 2;
	// Đồng ý nhận file từ máy nào đó
	public static final byte TYPE_ACCEPT_RECEIVE = 3;
	// Khi gửi thông tin từng file đã chia nhỏ của file cần tải
	public static final byte TYPE_CHUNK = 4;
	public static final byte TYPE_BEGIN_SEND_CHUNK = 5;
	public static final byte TYPE_END_SEND_CHUNK = 6;
}