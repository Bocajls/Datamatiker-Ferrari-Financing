package container.GUI;

import container.DB.DataLayerInstantiater;
import container.csv.CSV;
import container.engine.Forkorter;
import container.getDB.Bil;
import container.getDB.S�lger;
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
		// Font typen p� teksten i header
		headerType = headerTypeGet;
		start(stage);
	}
	
	public void start(Stage stage) {
		Pane pane = new Pane();
		pane.setPrefSize(432, 760);
		
		
		// Tekstfelt over formularen
		Label headerTekstLabel = new Label(grundlag);
		s�tParametreLabel(headerTekstLabel, 40, 392, 15, 20);
		headerTekstLabel.setId(headerType);
		
		
		// Fornavn elementer
		Label fornavnLabel = new Label("Fornavn:");
		s�tParametreLabel(fornavnLabel, 25, 160, 15, 105);
		Label fornavnField = new Label(Kunde.kunde.getFornavn());
		s�tParametreLabel(fornavnField, 25, 320, 100, 105);
		Line fornavnLine = new Line(15, 130, 420, 130);
		fornavnLine.setStroke(Color.rgb(184,184,184));
		
		
		// Efternavn elementer
		Label efternavnLabel = new Label("Efternavn:");
		s�tParametreLabel(efternavnLabel, 25, 120, 15, 130);
		Label efternavnField = new Label(Kunde.kunde.getEfternavn());
		s�tParametreLabel(efternavnField, 25, 320, 100, 130);
		Line efternavnLine = new Line(15, 155, 420, 155);
		efternavnLine.setStroke(Color.rgb(184,184,184));
		
		
		// Adresse elementer
		Label adresseLabel = new Label("Adresse:");
		s�tParametreLabel(adresseLabel, 25, 160, 15, 155);
		Label adresseField = new Label(Kunde.kunde.getAdresse());
		s�tParametreLabel(adresseField, 25, 320, 100, 155);
		Line adresseLine = new Line(15, 180, 420, 180);
		adresseLine.setStroke(Color.rgb(184,184,184));
		
		
		// Postnr elementer
		Label postnrLabel = new Label("Post nr.:");
		s�tParametreLabel(postnrLabel, 25, 160, 15, 180);
		Label postnrField = new Label(""+Kunde.kunde.getPostnr());
		s�tParametreLabel(postnrField, 25, 320, 100, 180);
		Line postnrLine = new Line(15, 205, 420, 205);
		postnrLine.setStroke(Color.rgb(184,184,184));
		
		
		// By elementer
		Label byLabel = new Label("By");
		s�tParametreLabel(byLabel, 25, 160, 15, 205);
		Label byField = new Label(DataLayerInstantiater.getByFraDB(Integer.parseInt(Kunde.kunde.getPostnr())));
		s�tParametreLabel(byField, 25, 320, 100, 205);
		Line byLine = new Line(15, 230, 420, 230);
		byLine.setStroke(Color.rgb(184,184,184));
		
		
		// Tlf. nr. elementer
		Label tlfnrLabel  = new Label("Tlf. nr.:");
		s�tParametreLabel(tlfnrLabel, 25, 160, 15, 230);
		Label tlfnrField = new Label(Kunde.kunde.getTlfnr());
		s�tParametreLabel(tlfnrField, 25, 320, 100, 230);
		Line tlfnrLine = new Line(15, 255, 420, 255);
		tlfnrLine.setStroke(Color.rgb(184,184,184));
		
		
		// Email elementer
		Label emailLabel = new Label("Email:");
		s�tParametreLabel(emailLabel, 25, 160, 15, 255);
		Label emailField = new Label(Kunde.kunde.getEmail());
		s�tParametreLabel(emailField, 25, 320, 100, 255);
		Line emailLine = new Line(15, 280, 420, 280);
		emailLine.setStroke(Color.rgb(184,184,184));

		
		// Fornavn elementer
		Label s�lgerLabel = new Label("S�lger:");
		s�tParametreLabel(s�lgerLabel, 25, 160, 15, 305);
		Label s�lgerField = new Label(S�lger.s�lgerList.get(S�lger.aktuelS�lger).getS�lger_navn());
		s�tParametreLabel(s�lgerField, 25, 320, 200, 305);
		Line s�lgerLine = new Line(15, 330, 420, 330);
		s�lgerLine.setStroke(Color.rgb(184,184,184));

		
		// Efternavn elementer
		Label afbetalingsperiodeLabel = new Label("Afbetalingsperiode i m�neder:");
		s�tParametreLabel(afbetalingsperiodeLabel, 25, 160, 15, 330);
		Label afbetalingsperiodeField = new Label(""+Aftale.aftale.getAfbetalingsperiode());
		s�tParametreLabel(afbetalingsperiodeField, 25, 320, 200, 330);
		Line afbetalingsperiodeLine = new Line(15, 355, 420, 355);
		afbetalingsperiodeLine.setStroke(Color.rgb(184,184,184));

		
		// Fornavn elementer
		Label udbetalingsbel�bLabel = new Label("Udbetalingsbel�b:");
		s�tParametreLabel(udbetalingsbel�bLabel, 25, 160, 15, 355);
		Label udbetalingsbel�bField = new Label("" + Forkorter.Formater(Aftale.aftale.getUdbetalingsbel�b(), "kr") + " kr.");
		s�tParametreLabel(udbetalingsbel�bField, 25, 320, 200, 355);
		Line udbetalingsbel�bLine = new Line(15, 380, 420, 380);
		udbetalingsbel�bLine.setStroke(Color.rgb(184,184,184));
		
		
		// Fornavn elementer
		Label bil_modelLabel = new Label("Bilmodel " + Bil.bilList.get(Bil.aktuelBil).getBil_model_navn() + ":");
		s�tParametreLabel(bil_modelLabel, 25, 160, 15, 380);
		Label bil_modelField = new Label("" + Forkorter.Formater(Aftale.aftale.getBil_pris(), "kr") + " kr.");
		s�tParametreLabel(bil_modelField, 25, 320, 200, 380);
		Line bil_modelLine = new Line(15, 405, 420, 405);
		bil_modelLine.setStroke(Color.rgb(184,184,184));
		
		
		// Fornavn elementer
		Label fuldeAfbetalingsbel�bLabel = new Label("Fulde afbetalingsbel�b:");
		s�tParametreLabel(fuldeAfbetalingsbel�bLabel, 25, 160, 15, 405);
		Label fuldeAfbetalingsbel�bField = new Label("" + Forkorter.Formater(Aftale.aftale.getFuldeAfbetalingsbel�b(), "kr") + " kr.");
		s�tParametreLabel(fuldeAfbetalingsbel�bField, 25, 160, 200, 405);
		Line fuldeAfbetalingsbel�bsLine = new Line(15, 430, 420, 430);
		fuldeAfbetalingsbel�bsLine.setStroke(Color.rgb(184,184,184));
		
		
		// Fornavn elementer
		Label m�nedligAfbetalingLabel = new Label("M�nedlig afbetaling:");
		s�tParametreLabel(m�nedligAfbetalingLabel, 25, 160, 15, 430);
		Label m�nedligAfbetalingField = new Label("" + Forkorter.Formater(Aftale.aftale.getM�nedlig_afbetaling(), "kr") + " kr.");
		s�tParametreLabel(m�nedligAfbetalingField, 25, 160, 200, 430);
		Line m�nedligAfbetalingLine = new Line(15, 455, 420, 455);
		m�nedligAfbetalingLine.setStroke(Color.rgb(184,184,184));
		
		
		// Fornavn elementer
		Label nuv�rendeDatoLabel = new Label("Nuv�rende dato:");
		s�tParametreLabel(nuv�rendeDatoLabel, 25, 160, 15, 480);
		Label nuv�rendeDatoField = new Label("" + Aftale.iDag);
		s�tParametreLabel(nuv�rendeDatoField, 25, 160, 200, 480);
		Line nuv�rendeDatoLine = new Line(15, 505, 420, 505);
		nuv�rendeDatoLine.setStroke(Color.rgb(184,184,184));
		
		
		// Fornavn elementer
		Label f�rsteBetalingsDatoLabel = new Label("Dato for f�rste betaling:");
		s�tParametreLabel(f�rsteBetalingsDatoLabel, 25, 160, 15, 505);
		Label f�rsteBetalingsDatoField = new Label("" + Aftale.aftale.getN�stBetaling());
		s�tParametreLabel(f�rsteBetalingsDatoField, 25, 160, 200, 505);
		Line f�rsteBetalingsDatoLine = new Line(15, 530, 420, 530);
		f�rsteBetalingsDatoLine.setStroke(Color.rgb(184,184,184));

		
		Button eksporterL�neanmodningButton = new Button("EKSPORTER L�NEANMODNING");
		s�tParametreButton(eksporterL�neanmodningButton, 50, 408, 15, 697);
		eksporterL�neanmodningButton.setId("indsendAnmodningButton");
				
		pane.getChildren().addAll(
				headerTekstLabel, fornavnLabel, fornavnField, efternavnLabel, efternavnField, adresseLabel, adresseField, postnrLabel, postnrField, byLabel, 
				byField, tlfnrLabel, tlfnrField, emailLabel, emailField, s�lgerLabel, s�lgerField, afbetalingsperiodeLabel, afbetalingsperiodeField, 
				udbetalingsbel�bLabel, udbetalingsbel�bField, bil_modelLabel, bil_modelField, fuldeAfbetalingsbel�bLabel, fuldeAfbetalingsbel�bField, 
				m�nedligAfbetalingLabel, m�nedligAfbetalingField, nuv�rendeDatoLabel, nuv�rendeDatoField, f�rsteBetalingsDatoLabel, f�rsteBetalingsDatoField, 
				eksporterL�neanmodningButton);
		
		pane.getChildren().addAll(fornavnLine, efternavnLine, adresseLine, postnrLine, byLine, tlfnrLine, emailLine, s�lgerLine, afbetalingsperiodeLine, 
				udbetalingsbel�bLine, bil_modelLine, fuldeAfbetalingsbel�bsLine, m�nedligAfbetalingLine, nuv�rendeDatoLine, f�rsteBetalingsDatoLine);
		
		// N�r indsend anmodning knappen klikkes p�.
		eksporterL�neanmodningButton.setOnAction(new EventHandler<ActionEvent>() {
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