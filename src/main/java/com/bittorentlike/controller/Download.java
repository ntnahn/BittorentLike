package com.bittorentlike.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.bittorentlike.app.Main;
import com.bittorentlike.chunks.InfoChunk;
import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLCommon;
import com.bittorentlike.common.BTLConstant;
import com.bittorentlike.udp.Sender;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Download {
	public static Download downloadController;
	@FXML
	private TextField txtFilePath, txtPath;
	@FXML
	private Text txtPercent, txtWarning;
	@FXML
	private TextArea txtStatus;
	@FXML
	private ProgressBar progressBarDownload;
//	private ArrayList<String> status = new ArrayList<>();
	@FXML
	void initialize() {
		downloadController = this;
	}
	public void addStatus(String stt) {
//		status.add(stt);
//		txtStatus.setText(txtStatus.getText() + stt + "\n");
		System.out.println(stt);
	}
	public void clearStatus() {
//		status.clear();
//		txtStatus.setText("");
	}
	@FXML
	void onButtonChooseChunkClick(ActionEvent event) {
		FileChooser fx = new FileChooser();
		fx.setTitle("Chọn file chunk");
		fx.getExtensionFilters().addAll(new ExtensionFilter("Chunk File", "*.chunk"));
		File path = fx.showOpenDialog(null);
		if (path != null) {
			this.txtFilePath.setText(path.getPath());
		}
	}
	@FXML
	void onButtonChooseFolderClick(ActionEvent event) {
		DirectoryChooser fx = new DirectoryChooser();
		fx.setTitle("Chọn đường dẫn để lưu file tải xuống");
		File path = fx.showDialog(null);
		if (path != null) {
			this.txtPath.setText(path.getPath());
		}
	}
	@FXML
	void onButtonDownloadClick(ActionEvent event) {
		if ( BTLCommon.isEmpty(txtFilePath.getText())) {
			txtWarning.setText("Hãy chọn file chunk.");
			return;
		}
		if ( BTLCommon.isEmpty(txtPath.getText()) ) {
			txtWarning.setText("Hãy chọn thư mục để lưu file tải xuống.");
			return;
		}
		Path path = Paths.get(txtPath.getText());
		if (!Files.exists(path)) {
			txtWarning.setText("Đường dẫn đến thư mục lưu không đúng.");
			return;
		}
		if ( BTLCommon.checkChunkFileExistsInLocal(this.txtFilePath.getText()) ) {
			txtWarning.setText("");
			InfoChunk infoChunk = InfoChunk.getInfoChunkByPath(this.txtFilePath.getText());
			Sender sender = new Sender();
			byte[] data = BTLCommon.serializeObject(infoChunk);
			BTLPackage btlPackage = new BTLPackage(Main.broadCastListenner.serverID, data);
			btlPackage.setType(BTLConstant.TYPE_BROADCAST);
			sender.sendData(BTLConstant.SEND_PORT, BTLConstant.IP_ADDRESS, btlPackage);
		} else {
			txtWarning.setText("Đường dẫn đến file chunk không đúng.");
		}
	}
}
