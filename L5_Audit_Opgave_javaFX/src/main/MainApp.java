package main;

import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.AfdelingenLijst;
import model.JuryLedenLijst;
import viewEnController.AuditRootViewController;

public class MainApp extends Application {
   
    private Stage primaryStage;
    private AfdelingenLijst al;
	private JuryLedenLijst jl;


    @Override
    public void start(Stage primaryStage) throws Exception {
    		al = new AfdelingenLijst();
    		jl = new JuryLedenLijst();
        this.primaryStage = primaryStage;  
        initRootLayout();
    }
    
    public void initRootLayout() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/viewEnController/AuditRootView.fxml"));
        Parent root = loader.load();
        AuditRootViewController controller = loader.getController();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Audit");
        primaryStage.setX(80);
        primaryStage.setY(28);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
        	e.consume();
        	afsluiten();
        });
        
        controller.setAfdelingenLijst(al);
        controller.setJuryLedenLijst(jl);
        controller.setStage(primaryStage);
   
        
        al.addObserver(controller);
        jl.addObserver(controller);
     
    }
    public void afsluiten(){
    	Alert confirmation = new Alert(AlertType.CONFIRMATION);
    	confirmation.setTitle("Afsluiten...");
    	confirmation.setHeaderText(null);
    	confirmation.setContentText("Audit-programma wordt afgesloten.");
    	Optional<ButtonType> result= confirmation.showAndWait();
    	if(result.get() == ButtonType.OK){
    		System.exit(0);
    	}
    	else {confirmation.close();}
    }
    public static void main(String[] args) {
        launch(args);
    }

}
