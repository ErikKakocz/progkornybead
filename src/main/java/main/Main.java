package main;

import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.AppController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import persistence.PersistenceManager;

public class Main extends Application {

	private PersistenceManager persistenceManager;
	
	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader=new FXMLLoader();
		Logger log=LoggerFactory.getLogger(Main.class);
		log.info("Initializing Application...");
		Parent root;
		
		try {
			persistenceManager=new PersistenceManager();
			log.info("Trying to Initialize ui...");
			URL url=getClass().getClassLoader().getResource("app.fxml");
			log.info("url found");
			loader.setLocation(url);
			root = loader.load();
			log.info("Resource found.");
			primaryStage.setTitle("ProgrammerFinder");
			log.info("Title set.");
			primaryStage.setScene(new Scene(root, 600, 400));
			log.info("Scene set!");
			AppController controller=loader.getController();
			controller.setPersistenceManager(persistenceManager);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		          public void handle(WindowEvent we) {
		              persistenceManager.disconnect();
		          }
		    });
			primaryStage.show();
	        log.info("Done!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.trace(e.getMessage());
		}
        
	}

	public static void main(String[] args) {
		launch(args);
	}
}
