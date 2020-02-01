package container.GUI;

import container.DB.DataLayerInstantiater;
import container.csv.CSV;
import container.engine.Forkorter;
import container.getDB.Bil;
import container.getDB.Sælger;
import container.savedata.Aftale;
import container.savedata.Kunde;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class AnmodningAccepteret extends GUIController{
	
	private String grundlag;
	private String headerType;
	Stage stage = new Stage();
	
	// Start stage og gem hvorfor anmodning blev accepteret
	public AnmodningAccepteret(String grundlagGet, String headerTypeGet) {
		// Grundlag ( Om anmodningen er accepteret direkte eller ej )
		grundlag = grundlagGet;
		// Font typen på teksten i header
		headerType = headerTypeGet;
		start(stage);
	}
	
	public void start(Stage stage) {
		Pane pane = new Pane();
		pane.setPrefSize(432, 760);
		
		
		// Tekstfelt over formularen
		Label headerTekstLabel = new Label(grundlag);
		sætParametreLabel(headerTekstLabel, 40, 392, 15, 20);
		headerTekstLabel.setId(headerType);
		
		
		// Fornavn elementer
		Label fornavnLabel = new Label("Fornavn:");
		sætParametreLabel(fornavnLabel, 25, 160, 15, 105);
		Label fornavnField = new Label(Kunde.kunde.getFornavn());
		sætParametreLabel(fornavnField, 25, 320, 100, 105);
		Line fornavnLine = new Line(15, 130, 420, 130);
		fornavnLine.setStroke(Color.rgb(184,184,184));
		
		
		// Efternavn elementer
		Label efternavnLabel = new Label("Efternavn:");
		sætParametreLabel(efternavnLabel, 25, 120, 15, 130);
		Label efternavnField = new Label(Kunde.kunde.getEfternavn());
		sætParametreLabel(efternavnField, 25, 320, 100, 130);
		Line efternavnLine = new Line(15, 155, 420, 155);
		efternavnLine.setStroke(Color.rgb(184,184,184));
		
		
		// Adresse elementer
		Label adresseLabel = new Label("Adresse:");
		sætParametreLabel(adresseLabel, 25, 160, 15, 155);
		Label adresseField = new Label(Kunde.kunde.getAdresse());
		sætParametreLabel(adresseField, 25, 320, 100, 155);
		Line adresseLine = new Line(15, 180, 420, 180);
		adresseLine.setStroke(Color.rgb(184,184,184));
		
		
		// Postnr elementer
		Label postnrLabel = new Label("Post nr.:");
		sætParametreLabel(postnrLabel, 25, 160, 15, 180);
		Label postnrField = new Label(""+Kunde.kunde.getPostnr());
		sætParametreLabel(postnrField, 25, 320, 100, 180);
		Line postnrLine = new Line(15, 205, 420, 205);
		postnrLine.setStroke(Color.rgb(184,184,184));
		
		
		// By elementer
		Label byLabel = new Label("By");
		sætParametreLabel(byLabel, 25, 160, 15, 205);
		Label byField = new Label(DataLayerInstantiater.getByFraDB(Integer.parseInt(Kunde.kunde.getPostnr())));
		sætParametreLabel(byField, 25, 320, 100, 205);
		Line byLine = new Line(15, 230, 420, 230);
		byLine.setStroke(Color.rgb(184,184,184));
		
		
		// Tlf. nr. elementer
		Label tlfnrLabel  = new Label("Tlf. nr.:");
		sætParametreLabel(tlfnrLabel, 25, 160, 15, 230);
		Label tlfnrField = new Label(Kunde.kunde.getTlfnr());
		sætParametreLabel(tlfnrField, 25, 320, 100, 230);
		Line tlfnrLine = new Line(15, 255, 420, 255);
		tlfnrLine.setStroke(Color.rgb(184,184,184));
		
		
		// Email elementer
		Label emailLabel = new Label("Email:");
		sætParametreLabel(emailLabel, 25, 160, 15, 255);
		Label emailField = new Label(Kunde.kunde.getEmail());
		sætParametreLabel(emailField, 25, 320, 100, 255);
		Line emailLine = new Line(15, 280, 420, 280);
		emailLine.setStroke(Color.rgb(184,184,184));

		
		// Fornavn elementer
		Label sælgerLabel = new Label("Sælger:");
		sætParametreLabel(sælgerLabel, 25, 160, 15, 305);
		Label sælgerField = new Label(Sælger.sælgerList.get(Sælger.aktuelSælger).getSælger_navn());
		sætParametreLabel(sælgerField, 25, 320, 200, 305);
		Line sælgerLine = new Line(15, 330, 420, 330);
		sælgerLine.setStroke(Color.rgb(184,184,184));

		
		// Efternavn elementer
		Label afbetalingsperiodeLabel = new Label("Afbetalingsperiode i måneder:");
		sætParametreLabel(afbetalingsperiodeLabel, 25, 160, 15, 330);
		Label afbetalingsperiodeField = new Label(""+Aftale.aftale.getAfbetalingsperiode());
		sætParametreLabel(afbetalingsperiodeField, 25, 320, 200, 330);
		Line afbetalingsperiodeLine = new Line(15, 355, 420, 355);
		afbetalingsperiodeLine.setStroke(Color.rgb(184,184,184));

		
		// Fornavn elementer
		Label udbetalingsbeløbLabel = new Label("Udbetalingsbeløb:");
		sætParametreLabel(udbetalingsbeløbLabel, 25, 160, 15, 355);
		Label udbetalingsbeløbField = new Label("" + Forkorter.Formater(Aftale.aftale.getUdbetalingsbeløb(), "kr") + " kr.");
		sætParametreLabel(udbetalingsbeløbField, 25, 320, 200, 355);
		Line udbetalingsbeløbLine = new Line(15, 380, 420, 380);
		udbetalingsbeløbLine.setStroke(Color.rgb(184,184,184));
		
		
		// Fornavn elementer
		Label bil_modelLabel = new Label("Bilmodel " + Bil.bilList.get(Bil.aktuelBil).getBil_model_navn() + ":");
		sætParametreLabel(bil_modelLabel, 25, 160, 15, 380);
		Label bil_modelField = new Label("" + Forkorter.Formater(Aftale.aftale.getBil_pris(), "kr") + " kr.");
		sætParametreLabel(bil_modelField, 25, 320, 200, 380);
		Line bil_modelLine = new Line(15, 405, 420, 405);
		bil_modelLine.setStroke(Color.rgb(184,184,184));
		
		
		// Fornavn elementer
		Label fuldeAfbetalingsbeløbLabel = new Label("Fulde afbetalingsbeløb:");
		sætParametreLabel(fuldeAfbetalingsbeløbLabel, 25, 160, 15, 405);
		Label fuldeAfbetalingsbeløbField = new Label("" + Forkorter.Formater(Aftale.aftale.getFuldeAfbetalingsbeløb(), "kr") + " kr.");
		sætParametreLabel(fuldeAfbetalingsbeløbField, 25, 160, 200, 405);
		Line fuldeAfbetalingsbeløbsLine = new Line(15, 430, 420, 430);
		fuldeAfbetalingsbeløbsLine.setStroke(Color.rgb(184,184,184));
		
		
		// Fornavn elementer
		Label månedligAfbetalingLabel = new Label("Månedlig afbetaling:");
		sætParametreLabel(månedligAfbetalingLabel, 25, 160, 15, 430);
		Label månedligAfbetalingField = new Label("" + Forkorter.Formater(Aftale.aftale.getMånedlig_afbetaling(), "kr") + " kr.");
		sætParametreLabel(månedligAfbetalingField, 25, 160, 200, 430);
		Line månedligAfbetalingLine = new Line(15, 455, 420, 455);
		månedligAfbetalingLine.setStroke(Color.rgb(184,184,184));
		
		
		// Fornavn elementer
		Label nuværendeDatoLabel = new Label("Nuværende dato:");
		sætParametreLabel(nuværendeDatoLabel, 25, 160, 15, 480);
		Label nuværendeDatoField = new Label("" + Aftale.iDag);
		sætParametreLabel(nuværendeDatoField, 25, 160, 200, 480);
		Line nuværendeDatoLine = new Line(15, 505, 420, 505);
		nuværendeDatoLine.setStroke(Color.rgb(184,184,184));
		
		
		// Fornavn elementer
		Label førsteBetalingsDatoLabel = new Label("Dato for første betaling:");
		sætParametreLabel(førsteBetalingsDatoLabel, 25, 160, 15, 505);
		Label førsteBetalingsDatoField = new Label("" + Aftale.aftale.getNæstBetaling());
		sætParametreLabel(førsteBetalingsDatoField, 25, 160, 200, 505);
		Line førsteBetalingsDatoLine = new Line(15, 530, 420, 530);
		førsteBetalingsDatoLine.setStroke(Color.rgb(184,184,184));

		
		Button eksporterLåneanmodningButton = new Button("EKSPORTER LÅNEANMODNING");
		sætParametreButton(eksporterLåneanmodningButton, 50, 408, 15, 697);
		eksporterLåneanmodningButton.setId("indsendAnmodningButton");
				
		pane.getChildren().addAll(
				headerTekstLabel, fornavnLabel, fornavnField, efternavnLabel, efternavnField, adresseLabel, adresseField, postnrLabel, postnrField, byLabel, 
				byField, tlfnrLabel, tlfnrField, emailLabel, emailField, sælgerLabel, sælgerField, afbetalingsperiodeLabel, afbetalingsperiodeField, 
				udbetalingsbeløbLabel, udbetalingsbeløbField, bil_modelLabel, bil_modelField, fuldeAfbetalingsbeløbLabel, fuldeAfbetalingsbeløbField, 
				månedligAfbetalingLabel, månedligAfbetalingField, nuværendeDatoLabel, nuværendeDatoField, førsteBetalingsDatoLabel, førsteBetalingsDatoField, 
				eksporterLåneanmodningButton);
		
		pane.getChildren().addAll(fornavnLine, efternavnLine, adresseLine, postnrLine, byLine, tlfnrLine, emailLine, sælgerLine, afbetalingsperiodeLine, 
				udbetalingsbeløbLine, bil_modelLine, fuldeAfbetalingsbeløbsLine, månedligAfbetalingLine, nuværendeDatoLine, førsteBetalingsDatoLine);
		
		// Når indsend anmodning knappen klikkes på.
		eksporterLåneanmodningButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
	    		CSV.CSVWriter();
	    		headerTekstLabel.setId("headerGodtTekst");
	    		headerTekstLabel.setText("Anmodning eksporteret!");
			}
		});

		
		
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("container/styling/style.css");
		stage.setResizable(false);
		stage.setOnCloseRequest(e -> Platform.exit());
		stage.setScene(scene);
        stage.show();
        
	}

}