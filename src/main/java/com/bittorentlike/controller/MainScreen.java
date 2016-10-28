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
	@FXML
	void initialize() {
		Tab tab = new Tab();
		tab.setText("Download");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Download.fxml"));
			Parent root = loader.load();
			tab.setContent(root);
			mainTab.getTabs().add(tab);
			
			// Add tab Share
			tab = new Tab();
			tab.setText("Share");
			loader = new FXMLLoader(getClass().getResource("../fxml/Share.fxml"));
			root = loader.load();
			tab.setContent(root);
			mainTab.getTabs().add(tab);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}