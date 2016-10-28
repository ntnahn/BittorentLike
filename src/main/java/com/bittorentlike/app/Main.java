package com.bittorentlike.app;
	
import java.io.IOException;

import com.bittorentlike.broadcast.BroadcastListenner;
import com.bittorentlike.common.BTLConstant;
import com.bittorentlike.controller.Download;
import com.bittorentlike.controller.MainScreen;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Parent root;
		Scene scene;
		BroadcastListenner broadCastListenner = new BroadcastListenner(BTLConstant.LISTEN_PORT);
		new Thread(new Runnable() {
			public void run() {
				broadCastListenner.start();
			}
		}).start();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainScreen.fxml"));
			root = loader.load();
			scene = new Scene(root);
			MainScreen mainScreenController = 
				    loader.<MainScreen>getController();
			primaryStage.setTitle("Bittorent Like TNT");
			primaryStage.initStyle(StageStyle.UNIFIED);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../images/utorrent.png")));
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
//					mainScreenController.onClose();
					broadCastListenner.stop();
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
