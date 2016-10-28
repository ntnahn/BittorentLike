package com.bittorentlike.controller;

import java.io.File;

import com.bittorentlike.classes.Receiver;

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
		Receiver receiver = new Receiver();
		try {
//			receiver.listen();
			receiver.listenViaSocket();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Listen failed!");
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