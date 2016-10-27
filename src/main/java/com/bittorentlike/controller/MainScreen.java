package com.bittorentlike.controller;

import java.io.IOException;

import com.qlbh.controller.ManHinhChinhController;

import javafx.event.ActionEvent;
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
			// Add tab Download
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("../fxml/Download.fxml"));
			tab.setContent(root);
			mainTab.getTabs().add(tab);
			
			// Add tab Share
			tab = new Tab();
			tab.setText("Share");
			root = (Parent) FXMLLoader.load(getClass().getResource("../fxml/Share.fxml"));
			tab.setContent(root);
			mainTab.getTabs().add(tab);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}