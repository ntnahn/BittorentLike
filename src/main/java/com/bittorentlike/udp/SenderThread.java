package com.bittorentlike.udp;

import java.util.ArrayList;

import com.bittorentlike.app.Main;
import com.bittorentlike.chunks.InfoChunk;
import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLCommon;
import com.bittorentlike.common.BTLConstant;
import com.bittorentlike.common.SplitFile;

public class SenderThread extends Thread {
	private BTLPackage btlPackage;
	public SenderThread(BTLPackage btlPackage) {
		this.btlPackage = btlPackage;
	}
	public void run() {
		byte[] receiveBTLPackageData = btlPackage.getData();
		if (receiveBTLPackageData != null) {
			InfoChunk infoChunk = BTLCommon.deserializeChunkTestBytes(receiveBTLPackageData);
			if (infoChunk != null) {
				Sender sender = new Sender();
				System.out.println("Read file chunk: " + infoChunk.getPath());
				ArrayList<byte[]> fileData = SplitFile.getByteListFromPath(infoChunk.getPath());
				System.out.println("File split lenght: " + fileData.size());
				
				BTLPackage btlPackage = new BTLPackage(Main.broadCastListenner.serverID, null);
				btlPackage.setType(BTLConstant.TYPE_BEGIN_SEND_CHUNK);
				sender.sendData(BTLConstant.LISTEN_PORT, BTLConstant.IP_ADDRESS, btlPackage);
				for ( byte[] subData : fileData ) {
					try {
						Thread.sleep(10);
						System.out.println("Send file-----");
						btlPackage = new BTLPackage(Main.broadCastListenner.serverID, subData);
						btlPackage.setType(BTLConstant.TYPE_CHUNK);
						sender.sendData(BTLConstant.LISTEN_PORT, BTLConstant.IP_ADDRESS, btlPackage);
					} catch (InterruptedException ex) {
					}
				}
				btlPackage = new BTLPackage(Main.broadCastListenner.serverID, null);
				btlPackage.setType(BTLConstant.TYPE_END_SEND_CHUNK);
				sender.sendData(BTLConstant.LISTEN_PORT, BTLConstant.IP_ADDRESS, btlPackage);
				
				sender.close();
			}
		}
	}
}
