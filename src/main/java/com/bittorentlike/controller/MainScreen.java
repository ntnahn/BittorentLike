package com.bittorentlike.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class MainScreen {
	@FXML
	private TabPane mainTab;
	private Download downloadController;
	private Share shareController;
	@FXML
	void initialize() {
		Tab tab = new Tab();
		tab.setText("Download");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Download.fxml"));
			Parent root = loader.load();
			downloadController = 
				    loader.<Download>getController();
			tab.setContent(root);
			mainTab.getTabs().add(tab);
			
			// Add tab Share
			tab = new Tab();
			tab.setText("Share");
			loader = new FXMLLoader(getClass().getResource("../fxml/Share.fxml"));
			root = loader.load();
			shareController = 
				    loader.<Share>getController();
			tab.setContent(root);
			mainTab.getTabs().add(tab);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onClose() {
		if ( downloadController != null ) {
			downloadController.onClose();
		}
		if ( shareController != null ) {
			shareController.onClose();
		}
	}
}