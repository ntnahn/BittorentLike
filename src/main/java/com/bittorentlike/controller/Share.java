package com.bittorentlike.controller;

import com.bittorentlike.classes.Sender;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Share {
	@FXML
	void onButtonShareClick(ActionEvent event) {
		Sender sender = new Sender();
		try {
//			sender.send();
			sender.sendViaSocket();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Send failed!");
		}
		System.out.println("Hello, we will download this file");
	}
}