package com.bittorentlike.controller;

import java.io.File;
import java.io.IOException;

import com.bittorentlike.classes.Server;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Download {
	@FXML
	private TextField txtFilePath;
	final Server server = new Server();
	@FXML
	void onButtonDownloadClick(ActionEvent event) {
		System.out.println("Hello, we will download this file");
		new Thread(new Runnable() {
			public void run() {
				try {
					server.start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
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
	
	public void onClose() {
		// Stop listen when application close to free port
		server.stop();
		System.out.println("Main screen close");
	}
}