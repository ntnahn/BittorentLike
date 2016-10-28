package com.bittorentlike.controller;

import java.io.File;
import java.io.IOException;

import com.bittorentlike.classes.Receiver;
import com.bittorentlike.classes.Sender;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Download {
	@FXML
	private TextField txtFilePath;
	@FXML
	void onButtonDownloadClick(ActionEvent event) {
		System.out.println("Hello, we will download this file");
//		Receiver receiver = new Receiver();
//		Sender sender = new Sender();
//		try {
////			receiver.listen();
//			sender.sendViaSocket();
//			receiver.listenViaSocket();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Listen failed!");
//		}
		new Thread() {
            public void run() {
                try {
                	Receiver.server();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        try {
			Receiver.client();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@FXML
	void onButtonChooseChunkClick(ActionEvent event) {
		FileChooser fx = new FileChooser();
		fx.getExtensionFilters().addAll(new ExtensionFilter("Chunk File", "*.chunk"));
		File path = fx.showOpenDialog(null);
		if (path != null) {
			this.txtFilePath.setText(path.getPath());
		}
	}
}