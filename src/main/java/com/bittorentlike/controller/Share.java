package com.bittorentlike.controller;

import com.bittorentlike.broadcast.BroadcastSender;
import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLConstant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Share {
	@FXML
	void onButtonShareClick(ActionEvent event) {
//		Client client = new Client();
//		try {
//			client.start();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Send failed!");
//		}
//		System.out.println("Hello, we will download this file");
		BroadcastSender broadcastSender = new BroadcastSender(BTLConstant.SEND_PORT);
		BTLPackage btlPackage = new BTLPackage("Hi", null);
		broadcastSender.doBroadcast(btlPackage);
	}
	public void onClose() {
		// Todo: do your code when main screen close
	}
}