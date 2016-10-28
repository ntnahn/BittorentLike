package com.bittorentlike.controller;

import java.io.File;

import com.bittorentlike.broadcast.BroadcastSender;
import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLCommon;
import com.bittorentlike.common.BTLConstant;
import com.bittorentlike.common.ChunkTest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Download {
	@FXML
	private TextField txtFilePath;
	@FXML
	void onButtonChooseChunkClick(ActionEvent event) {
		FileChooser fx = new FileChooser();
		fx.getExtensionFilters().addAll(new ExtensionFilter("Chunk File", "*.chunk"));
		File path = fx.showOpenDialog(null);
		if (path != null) {
			this.txtFilePath.setText(path.getPath());
		}
	}
	@FXML
	void onButtonDownloadClick(ActionEvent event) {
		BroadcastSender broadcastSender = new BroadcastSender(BTLConstant.SEND_PORT);
		ChunkTest chunkTest = new ChunkTest("abc.chunk", "D:\\TKB HK3.png");
		byte[] data = BTLCommon.serializeObject(chunkTest);
		BTLPackage btlPackage = new BTLPackage("Hi", data);
		btlPackage.setType(BTLConstant.TYPE_BROADCAST);
		broadcastSender.doBroadcast(btlPackage);
	}
}
