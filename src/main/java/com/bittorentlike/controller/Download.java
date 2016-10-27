package com.bittorentlike.controller;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Download {
	@FXML
	void onButtonDownloadClick(ActionEvent event) {
		System.out.println("Hello, we will download this file");
	}
	@FXML
	void onButtonChooseChunkClick(ActionEvent event) {
		FileChooser fx = new FileChooser();
		fx.getExtensionFilters().addAll(new ExtensionFilter("Chunk File", "*.chunk"));
		File path = fx.showOpenDialog(null);
//		if (path != null) {
//			this.txtDuongDan.setText(path.getPath());
//		}
	}
}