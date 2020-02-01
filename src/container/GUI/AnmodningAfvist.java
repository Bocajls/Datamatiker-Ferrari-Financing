package container.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;
import container.GUI.GUIController;
import javafx.stage.Stage;

public class AnmodningAfvist extends GUIController{
	
	private static String grundlag;
	Stage stage = new Stage();
	
	public AnmodningAfvist(String grundlagGet) {
		grundlag = grundlagGet;
		start(stage);
	}

	public void start(Stage stage) {
		Pane pane = new Pane();
		pane.setPrefSize(432, 120);
		
		// Label der indeholder grundlaget for afvisningen
		Label afvistBesked = new Label(grundlag);
		sætParametreLabel(afvistBesked, 80, 398, 20, 20);
		afvistBesked.setTextAlignment(TextAlignment.CENTER);
		afvistBesked.setId("headerTekst");
		
		pane.getChildren().addAll(afvistBesked);
		
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("container/styling/style.css");
		stage.setResizable(false);
		stage.setScene(scene);
        stage.show();
        
	}
}