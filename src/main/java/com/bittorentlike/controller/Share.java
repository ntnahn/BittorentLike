package com.bittorentlike.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class Share {

	public static final String specitor = "@#&";

	@FXML
	private TextField txtFile;

	@FXML
	private TextField txtPath;

	@FXML
	void btnChooseFileOnClick(ActionEvent event) {
		FileChooser fx = new FileChooser();
		fx.getExtensionFilters().addAll(new ExtensionFilter("Files", "*.mp3"));
		File path = fx.showOpenDialog(null);
		if (path != null) {
			this.txtFile.setText(path.getPath());
		}
	}

	@FXML
	void btnChoosePathOnClick(ActionEvent event) {
		DirectoryChooser fx = new DirectoryChooser();
		File path = fx.showDialog(null);
		if (path != null) {
			this.txtPath.setText(path.getPath());
		}
	}

	@FXML
	void btnCreateOnClick(ActionEvent event) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA1");
			String sourceFile = txtFile.getText();
			File srcFile = new File(sourceFile);
			FileInputStream inputStream = new FileInputStream(sourceFile);
			String desPath = txtPath.getText();
			desPath += "/" + srcFile.getName() + ".chunk";
			File desFile = new File(desPath);
			if (desFile.createNewFile()) {
				//get SHA1
				 byte[] data = new byte[(int) srcFile.length()];
				 int count = inputStream.read(data);  
				 md.update(data, 0, count);
                 byte[] mdbytes = md.digest();
                 StringBuffer sb = new StringBuffer("");
                 for (int j = 0; j < mdbytes.length; j++) {
                     sb.append(Integer.toString((mdbytes[j] & 0xff) + 0x100, 16).substring(1));
                 }
                 String sha1 = sb.toString();
                 //write into file
                 String content = "0" + specitor + srcFile.getName() + specitor + sha1 + specitor + srcFile.getAbsolutePath();
                FileWriter fw = new FileWriter(desFile.getAbsoluteFile());
     			BufferedWriter bw = new BufferedWriter(fw);
     			bw.write(content);
     			bw.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}