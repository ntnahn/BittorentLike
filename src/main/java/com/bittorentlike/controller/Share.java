package com.bittorentlike.controller;

import com.bittorentlike.broadcast.BroadcastSender;
import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLConstant;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Share {
	@FXML
	void onButtonShareClick(ActionEvent event) {
		BroadcastSender broadcastSender = new BroadcastSender(BTLConstant.SEND_PORT);
		BTLPackage btlPackage = new BTLPackage("Hi", null);
		broadcastSender.doBroadcast(btlPackage);
	}
}