package com.bittorentlike.app;
	
import java.io.IOException;

import com.bittorentlike.broadcast.BroadcastListenner;
import com.bittorentlike.classes.BTLPackage;
import com.bittorentlike.common.BTLConstant;
import com.bittorentlike.udp.SenderThread;

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
	public static BroadcastListenner broadCastListenner;
	@Override
	public void start(Stage primaryStage) {
		Parent root;
		Scene scene;		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainScreen.fxml"));
			root = loader.load();
			scene = new Scene(root);
			primaryStage.setTitle("Bittorent Like TNT");
			primaryStage.initStyle(StageStyle.UNIFIED);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../images/utorrent.png")));
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					broadCastListenner.stop();
				}
			});
			
			broadCastListenner = new BroadcastListenner(BTLConstant.LISTEN_PORT);
			new Thread(new Runnable() {
				public void run() {
					broadCastListenner.start();
				}
			}).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void newSenderThread(BTLPackage receiveBTLPackage) {
		SenderThread sendThread = new SenderThread(receiveBTLPackage);
//		senderThreads.add(sendThread);
		sendThread.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
