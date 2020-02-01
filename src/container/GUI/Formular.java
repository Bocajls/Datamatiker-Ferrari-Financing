package container.GUI;

import container.anmodning.ValiderAnmodning;
import container.engine.Forkorter;
import container.getDB.Bil;
import container.getDB.S�lger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import container.DB.DataLayerInstantiater;
import container.GUI.GUIController;

public class Formular extends GUIController{

	public void start(Stage stage) {
		Pane pane = new Pane();
		pane.setPrefSize(432, 760);

		
		// Tekstfelt over formularen
		Label headerTekstLabel = new Label("");
		headerTekstLabel.setTranslateX(15);
		headerTekstLabel.setTranslateY(20);
		headerTekstLabel.setPrefHeight(40);
		headerTekstLabel.setPrefWidth(392);
		headerTekstLabel.setId("headerTekst");
		pane.getChildren().add(headerTekstLabel);
		
		
		// CPR nr. elementer
		Label CPRnrLabel = new Label("CPR nr.");
		s�tParametreLabel(CPRnrLabel, 25, 80, 15, 80);
		Line CPRnrLine = new Line(15, 105, 420, 105);
		CPRnrLine.setStroke(Color.rgb(184,184,184));
		Label CPRnrColonLabel = new Label(":");
		s�tParametreLabel(CPRnrColonLabel, 25, 4, 95, 80);
		Label CPRnrExclamationMark = new Label("!");
		s�tParametreLabel(CPRnrExclamationMark, 25, 4, 425, 80);
		TextField CPRnrField = new TextField();
		CPRnrField.setPrefSize(320, 20);
		HBox CPRnrFieldBox = new HBox(CPRnrField);
		s�tParametreHBox(CPRnrFieldBox, 25, 101, 81);
		// S� snart der foretages en �ndring i CPrnrField 
		CPRnrField.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	tjekCPRnrMetode(CPRnrField, CPRnrExclamationMark, headerTekstLabel);
		    }
		});
		
		
		// Fornavn elementer
		Label fornavnLabel = new Label("Fornavn");
		s�tParametreLabel(fornavnLabel, 25, 80, 15, 105);
		Line fornavnLine = new Line(15, 130, 420, 130);
		fornavnLine.setStroke(Color.rgb(184,184,184));
		Label fornavnColonLabel = new Label(":");
		s�tParametreLabel(fornavnColonLabel, 25, 4, 95, 105);
		Label fornavnExclamationMark = new Label("!");
		s�tParametreLabel(fornavnExclamationMark, 25, 4, 425, 105);
		TextField fornavnField = new TextField();
		fornavnField.setPrefSize(320, 20);
		HBox fornavnFieldBox = new HBox(fornavnField);
		s�tParametreHBox(fornavnFieldBox, 25, 101, 106);
		// S� snart der foretages en �ndring i fornavnField 
		fornavnField.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	tjekFornavnMetode(fornavnField, fornavnExclamationMark);
		    }
		});

		
		// Efternavn elementer
		Label efternavnLabel = new Label("Efternavn");
		s�tParametreLabel(efternavnLabel, 25, 80, 15, 130);
		Line efternavnLine = new Line(15, 155, 420, 155);
		efternavnLine.setStroke(Color.rgb(184,184,184));
		Label efternavnColonLabel = new Label(":");
		s�tParametreLabel(efternavnColonLabel, 25, 4, 95, 130);
		Label efternavnExclamationMark = new Label("!");
		s�tParametreLabel(efternavnExclamationMark, 25, 4, 425, 130);
		TextField efternavnField = new TextField();
		efternavnField.setPrefSize(320, 20);
		HBox efternavnFieldBox = new HBox(efternavnField);
		s�tParametreHBox(efternavnFieldBox, 25, 101, 131);
		// S� snart der foretages en �ndring i efternavnField 
		efternavnField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tjekEfternavnMetode(efternavnField, efternavnExclamationMark);
			}
		});
		
		
		// Adresse elementer
		Label adresseLabel = new Label("Adresse");
		s�tParametreLabel(adresseLabel, 25, 80, 15, 155);
		Line adresseLine = new Line(15, 180, 420, 180);
		adresseLine.setStroke(Color.rgb(184,184,184));
		Label adresseColonLabel = new Label(":");
		s�tParametreLabel(adresseColonLabel, 25, 4, 95, 155);
		Label adresseExclamationMark = new Label("!");
		s�tParametreLabel(adresseExclamationMark, 25, 4, 425, 155);
		TextField adresseField = new TextField();
		adresseField.setPrefSize(320, 20);
		HBox adresseFieldBox = new HBox(adresseField);
		s�tParametreHBox(adresseFieldBox, 25, 101, 156);
		// S� snart der foretages en �ndring i adressseField
		adresseField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tjekAdresseMetode(adresseField, adresseExclamationMark);
			}
		});
		
		
		// Postnr elementer
		Label postnrLabel = new Label("Postnr");
		s�tParametreLabel(postnrLabel, 25, 80, 15, 180);
		Line postnrLine = new Line(15, 205, 420, 205);
		postnrLine.setStroke(Color.rgb(184,184,184));
		Label postnrColonLabel = new Label(":");
		s�tParametreLabel(postnrColonLabel, 25, 4, 95, 180);
		Label postnrExclamationMark = new Label("!");
		s�tParametreLabel(postnrExclamationMark, 25, 4, 425, 180);
		TextField postnrField = new TextField();
		postnrField.setPrefSize(320, 20);
		HBox postnrFieldBox = new HBox(postnrField);
		s�tParametreHBox(postnrFieldBox, 25, 101, 181);
		
		
		
		// By elementer
		Label byLabel = new Label("By");
		s�tParametreLabel(byLabel, 25, 80, 15, 205);
		Line byLine = new Line(15, 230, 420, 230);
		byLine.setStroke(Color.rgb(184,184,184));
		Label byColonLabel = new Label(":");
		s�tParametreLabel(byColonLabel, 25, 4, 95, 205);
		Label byExclamationMark = new Label("!");
		s�tParametreLabel(byExclamationMark, 25, 4, 425, 205);
		Label byField = new Label();
		s�tParametreLabel(byField, 23, 318, 102, 206);
		byField.setId("ikkeAEndre");
		// S� snart der foretages en �ndring i postnrField 	
		postnrField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tjekPostnrMetode(postnrField, postnrExclamationMark, byField, byExclamationMark);
			}
		});
		
		
		// Tlf. nr. elementer
		Label tlfnrLabel = new Label("Tlfnr");
		s�tParametreLabel(tlfnrLabel, 25, 80, 15, 230);
		Line tlfnrLine = new Line(15, 255, 420, 255);
		tlfnrLine.setStroke(Color.rgb(184,184,184));
		Label tlfnrColonLabel = new Label(":");
		s�tParametreLabel(tlfnrColonLabel, 25, 4, 95, 230);
		Label tlfnrExclamationMark = new Label("!");
		s�tParametreLabel(tlfnrExclamationMark, 25, 4, 425, 230);
		TextField tlfnrField = new TextField();
		tlfnrField.setPrefSize(320, 20);
		HBox tlfnrFieldBox = new HBox(tlfnrField);
		s�tParametreHBox(tlfnrFieldBox, 25, 101, 231);
		// S� snart der foretages en �ndring i tlfnrField 	
		tlfnrField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tjekTlfnrMetode(tlfnrField, tlfnrExclamationMark);
			}
		});
		
		
		// Email elementer
		Label emailLabel = new Label("Email");
		s�tParametreLabel(emailLabel, 25, 80, 15, 255);
		Line emailLine = new Line(15, 280, 420, 280);
		emailLine.setStroke(Color.rgb(184,184,184));
		Label emailColonLabel = new Label(":");
		s�tParametreLabel(emailColonLabel, 25, 4, 95, 255);
		Label emailExclamationMark = new Label("!");
		s�tParametreLabel(emailExclamationMark, 25, 4, 425, 255);
		TextField emailField = new TextField();
		emailField.setPrefSize(320, 20);
		HBox emailFieldBox = new HBox(emailField);
		s�tParametreHBox(emailFieldBox, 25, 101, 256);
		// S� snart der foretages en �ndring i emailField
		emailField.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	tjekEmailMetode(emailField, emailExclamationMark);
		    }
		});
		
		
		pane.getChildren().addAll(CPRnrLabel, 			fornavnLabel, 			efternavnLabel, 			adresseLabel, 			postnrLabel, 			byLabel, 			tlfnrLabel, 			emailLabel);
		pane.getChildren().addAll(CPRnrLine, 			fornavnLine, 			efternavnLine, 				adresseLine, 			postnrLine, 			byLine, 			tlfnrLine, 				emailLine);
		pane.getChildren().addAll(CPRnrColonLabel, 		fornavnColonLabel, 		efternavnColonLabel, 		adresseColonLabel, 		postnrColonLabel, 		byColonLabel, 		tlfnrColonLabel, 		emailColonLabel);
		pane.getChildren().addAll(CPRnrExclamationMark, fornavnExclamationMark, efternavnExclamationMark, 	adresseExclamationMark, postnrExclamationMark, 	byExclamationMark, 	tlfnrExclamationMark, 	emailExclamationMark);
		pane.getChildren().addAll(CPRnrFieldBox, 		fornavnFieldBox, 		efternavnFieldBox, 			adresseFieldBox, 		postnrFieldBox, 		byField,			tlfnrFieldBox, 			emailFieldBox);

		
		// Afbetalingsperiode elementer
		Label afbetalingsperiodeBeskrivelseLabel = new Label("(Ved afbetalingsperiode p� over 36 m�neder till�gges �t procentpoint)");
		s�tParametreLabel(afbetalingsperiodeBeskrivelseLabel, 25, 625, 15, 305);
		afbetalingsperiodeBeskrivelseLabel.setId("beskrivelseText");
		Label afbetalingsperiodeLabel = new Label("Afbetalingsperiode i m�neder");
		s�tParametreLabel(afbetalingsperiodeLabel, 25, 320, 15, 325);
		Line afbetalingsperiodeLine = new Line(15, 351, 420, 351);
		afbetalingsperiodeLine.setStroke(Color.rgb(184,184,184));
		Label afbetalingsperiodeColonLabel = new Label(":");
		s�tParametreLabel(afbetalingsperiodeColonLabel, 25, 4, 180, 325);
		Label afbetalingsperiodeExclamationMark = new Label("!");
		s�tParametreLabel(afbetalingsperiodeExclamationMark, 25, 4, 425, 325);
		TextField afbetalingsperiodeField = new TextField();
		afbetalingsperiodeField.setPrefSize(238, 20);
		HBox afbetalingsperiodeFieldBox = new HBox(afbetalingsperiodeField);
		s�tParametreHBox(afbetalingsperiodeFieldBox, 25, 183, 327);
		// S� snart der foretages en �ndring i afbetalingsperiodeField
		afbetalingsperiodeField.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	tjekAfbetalingsperiodeMetode(afbetalingsperiodeField, afbetalingsperiodeExclamationMark);
		    }
		});
		
		
		// Udbetalingsbel�b elementer
		Label udbetalingsbel�bBeskrivelseLabel = new Label("(Ved udbetalingsbel�b p� under 50% af bilens v�rdi till�gges �t procentpoint)");
		s�tParametreLabel(udbetalingsbel�bBeskrivelseLabel, 25, 625, 15, 377);
		udbetalingsbel�bBeskrivelseLabel.setId("beskrivelseText");
		Label udbetalingsbel�bLabel = new Label("Udbetalingsbel�b");
		s�tParametreLabel(udbetalingsbel�bLabel, 25, 320, 15, 397);
		Line udbetalingsbel�bLine = new Line(15, 423, 420, 423);
		udbetalingsbel�bLine.setStroke(Color.rgb(184,184,184));
		Label udbetalingsbel�bColonLabel = new Label(":");
		s�tParametreLabel(udbetalingsbel�bColonLabel, 25, 4, 120, 397);
		Label udbetalingsbel�bExclamationMark = new Label("!");
		s�tParametreLabel(udbetalingsbel�bExclamationMark, 25, 4, 425, 397);
		TextField udbetalingsbel�bField = new TextField();
		udbetalingsbel�bField.setPrefSize(300, 20);
		HBox udbetalingsbel�bFieldBox = new HBox(udbetalingsbel�bField);
		s�tParametreHBox(udbetalingsbel�bFieldBox, 25, 123, 399);
		// S� snart der foretages en �ndring i udbetalingsbel�bField
		udbetalingsbel�bField.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	tjekUdbetalingsbel�bMetode(udbetalingsbel�bField, udbetalingsbel�bExclamationMark);
		    }
		});
		
		
		pane.getChildren().addAll(afbetalingsperiodeBeskrivelseLabel,	afbetalingsperiodeLabel, 	afbetalingsperiodeLine,	afbetalingsperiodeColonLabel,	afbetalingsperiodeExclamationMark,	afbetalingsperiodeFieldBox);
		pane.getChildren().addAll(udbetalingsbel�bBeskrivelseLabel,		udbetalingsbel�bLabel, 		udbetalingsbel�bLine,	udbetalingsbel�bColonLabel,		udbetalingsbel�bExclamationMark,	udbetalingsbel�bFieldBox);
		

		// Udbetalingsbel�b elementer
		Label s�lgerGr�nseBeskrivelseLabel = new Label("Overg�r afbetalingsbel�bet (v�lg s�lger f�rst) skal anmodningen godkendes!");
		s�tParametreLabel(s�lgerGr�nseBeskrivelseLabel, 25, 625, 15, 449);
		s�lgerGr�nseBeskrivelseLabel.setId("beskrivelseText");
		pane.getChildren().addAll(s�lgerGr�nseBeskrivelseLabel);
		
		
		// Bil model elementer
		Label bilmodelLabel = new Label("Bilmodel");
		s�tParametreLabel(bilmodelLabel, 25, 320, 15, 469);
		Line bilmodelLine = new Line(15, 495, 420, 495);
		bilmodelLine.setStroke(Color.rgb(184,184,184));
		Label bilmodelColonLabel = new Label(":");
		s�tParametreLabel(bilmodelColonLabel, 25, 4, 95, 469);
		Label bilmodelExclamationMark = new Label("!");
		s�tParametreLabel(bilmodelExclamationMark, 25, 4, 425, 469);
		ChoiceBox<String> bilChoiceBox = new ChoiceBox<>();
		// For hvert element i ArrayList<S�lger> s�t det ind i ChoiceBox
		for(int i = 0; i < Bil.bilList.size(); i++) {
			bilChoiceBox.getItems().add(Bil.bilList.get(i).getBil_model_navn());
		}
		bilChoiceBox.setPrefSize(320, 25);
		bilChoiceBox.setTranslateX(101);
		bilChoiceBox.setTranslateY(471);
		bilChoiceBox.setId("dropdown");
		

		// Bil pris elementer
		Label bilprisLabel = new Label("Bilpris");
		s�tParametreLabel(bilprisLabel, 25, 320, 15, 494);
		Line bilprisLine = new Line(15, 520, 420, 520);
		bilprisLine.setStroke(Color.rgb(184,184,184));
		Label bilprisColonLabel = new Label(":");
		s�tParametreLabel(bilprisColonLabel, 25, 4, 95, 494);
		Label bilprisExclamationMark = new Label("!");
		s�tParametreLabel(bilprisExclamationMark, 25, 4, 425, 494);
		Label bilprisField = new Label();
		s�tParametreLabel(bilprisField, 23, 318, 102, 496);
		bilprisField.setId("ikkeAEndre");
		// Note denne angives l�ngere oppe, men listeneren er hernede, da den skal s�tte en v�rdi i bilprisField
		// Listener for ChoiceBox
		bilChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				tjekBilValgtMetode(bilChoiceBox, bilprisField, bilmodelExclamationMark, bilprisExclamationMark);
			}    
		});
	
		
		// S�lger elementer
		Label s�lgerLabel = new Label("S�lger");
		s�tParametreLabel(s�lgerLabel, 25, 320, 15, 519);
		Line s�lgerLine = new Line(15, 545, 420, 545);
		s�lgerLine.setStroke(Color.rgb(184,184,184));
		Label s�lgerColonLabel = new Label(":");
		s�tParametreLabel(s�lgerColonLabel, 25, 4, 95, 519);
		Label s�lgerExclamationMark = new Label("!");
		s�tParametreLabel(s�lgerExclamationMark, 25, 4, 425, 519);
		ChoiceBox<String> s�lgerChoiceBox = new ChoiceBox<>();
		// For hvert element i ArrayList<S�lger> s�t det ind i ChoiceBox
		for(int i = 0; i < S�lger.s�lgerList.size(); i++) {
			s�lgerChoiceBox.getItems().add(S�lger.s�lgerList.get(i).getS�lger_navn());
		}
		s�lgerChoiceBox.setPrefSize(320, 25);
		s�lgerChoiceBox.setTranslateX(101);
		s�lgerChoiceBox.setTranslateY(521);
		s�lgerChoiceBox.setId("dropdown");
		// Listener for ChoiceBox
		s�lgerChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				fyldS�lgerBoxMetode(s�lgerChoiceBox, s�lgerExclamationMark);
				s�lgerGr�nseBeskrivelseLabel.setText("Overg�r afbetalingsbel�bet " + Forkorter.Formater(DataLayerInstantiater.getS�lgerGr�nseFraDB(), "kr") + " kr. skal anmodningen godkendes!");
			}    
		});
		
			
		pane.getChildren().addAll(bilmodelLabel, 			bilprisLabel, 			s�lgerLabel);
		pane.getChildren().addAll(bilmodelLine, 			bilprisLine, 			s�lgerLine);
		pane.getChildren().addAll(bilmodelColonLabel, 		bilprisColonLabel, 		s�lgerColonLabel);
		pane.getChildren().addAll(bilmodelExclamationMark,	bilprisExclamationMark, s�lgerExclamationMark);
		pane.getChildren().addAll(bilprisField);
		pane.getChildren().addAll(bilChoiceBox, s�lgerChoiceBox);
		
		
		// �OP elementer
		Label �OPLabel = new Label("�OP");
		s�tParametreLabel(�OPLabel, 25, 136, 15, 570);
		Line �OPLine = new Line(15, 596, 420, 596);
		�OPLine.setStroke(Color.rgb(184,184,184));
		Label �OPColonLabel = new Label(":");
		s�tParametreLabel(�OPColonLabel, 25, 4, 145, 570);
		Label �OPField = new Label();
		�OPField.setPrefSize(275, 20);
		HBox �OPFieldBox = new HBox(�OPField);
		s�tParametreHBox(�OPFieldBox, 25, 158, 574);
		
		
		// Fulde afbetalingsbel�b elementer
		Label fuldeAfbetalingsbel�bLabel = new Label("Fulde afbetalingsbel�b");
		s�tParametreLabel(fuldeAfbetalingsbel�bLabel, 25, 136, 15, 595);
		Line fuldeAfbetalingsbel�bLine = new Line(15, 621, 420, 621);
		fuldeAfbetalingsbel�bLine.setStroke(Color.rgb(184,184,184));
		Label fuldeAfbetalingsbel�bColonLabel = new Label(":");
		s�tParametreLabel(fuldeAfbetalingsbel�bColonLabel, 25, 4, 145, 595);
		Label fuldeAfbetalingsbel�bField = new Label();
		fuldeAfbetalingsbel�bField.setPrefWidth(275);
		HBox fuldeAfbetalingsbel�bFieldBox = new HBox(fuldeAfbetalingsbel�bField);
		s�tParametreHBox(fuldeAfbetalingsbel�bFieldBox, 25, 158, 599);
		
		
		// Antal m�neder
		Label antalM�nederLabel = new Label("Antal m�neder");
		s�tParametreLabel(antalM�nederLabel, 25, 136, 15, 620);
		Line antalM�nederLine = new Line(15, 646, 420, 646);
		antalM�nederLine.setStroke(Color.rgb(184,184,184));
		Label antalM�nederColonLabel = new Label(":");
		s�tParametreLabel(antalM�nederColonLabel, 25, 4, 145, 620);
		Label antalM�nederField = new Label();
		antalM�nederField.setPrefSize(275, 20);
		HBox antalM�nederFieldBox = new HBox(antalM�nederField);
		s�tParametreHBox(antalM�nederFieldBox, 25, 158, 624);
		
		
		// M�nedlig afbetaling
		Label m�nedligAfbetalingLabel = new Label("M�nedlig afbetaling");
		s�tParametreLabel(m�nedligAfbetalingLabel, 25, 136, 15, 645);
		Line m�nedligAfbetalingLine = new Line(15, 671, 420, 671);
		m�nedligAfbetalingLine.setStroke(Color.rgb(184,184,184));
		Label m�nedligAfbetalingColonLabel = new Label(":");
		s�tParametreLabel(m�nedligAfbetalingColonLabel, 25, 4, 145, 645);
		Label m�nedligAfbetalingField = new Label();
		m�nedligAfbetalingField.setPrefSize(275, 20);
		HBox m�nedligAfbetalingFieldBox = new HBox(m�nedligAfbetalingField);
		s�tParametreHBox(m�nedligAfbetalingFieldBox, 25, 158, 649);
				
		
		pane.getChildren().addAll(�OPLabel, 		fuldeAfbetalingsbel�bLabel,			antalM�nederLabel,		m�nedligAfbetalingLabel);
		pane.getChildren().addAll(�OPLine, 			fuldeAfbetalingsbel�bLine,			antalM�nederLine,		m�nedligAfbetalingLine);
		pane.getChildren().addAll(�OPColonLabel, 	fuldeAfbetalingsbel�bColonLabel,	antalM�nederColonLabel,	m�nedligAfbetalingColonLabel);
		pane.getChildren().addAll(�OPFieldBox, 		fuldeAfbetalingsbel�bFieldBox,		antalM�nederFieldBox,	m�nedligAfbetalingFieldBox);
		
		// Til at indhente data fra DB hvis CPR nummer eksisterer
		Button hentKundeInfoButton = new Button("Hent info");
		s�tParametreButton(hentKundeInfoButton, 20, 80, 345, 80);
		hentKundeInfoButton.setVisible(false);
		pane.getChildren().addAll(hentKundeInfoButton);
		// N�r indsend anmodning knappen klikkes p�.
		hentKundeInfoButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	opdaterGUI(fornavnField, efternavnField, adresseField, postnrField, tlfnrField, emailField);
		    	hentKundeInfoButton.setVisible(false);
		    }
		});
		
		// Knappen til at indsende anmodningen
		Button indsendAnmodningButton = new Button("BEKR�FT OG INDSEND ANMODNING");
		s�tParametreButton(indsendAnmodningButton, 50, 408, 15, 697);
		indsendAnmodningButton.setId("indsendAnmodningButton");
		pane.getChildren().addAll(indsendAnmodningButton);
		
		
		// N�r indsend anmodning knappen klikkes p�.
		indsendAnmodningButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	// Hvis valideringen g�r igennem luk denne formular
		    	ValiderAnmodning.anmodning.valider();
		    	if(ValiderAnmodning.anmodning.getAccepteret() == 1) {
		    		stage.close();
		    	}
		     	
		    }
		});
		
		
		// Brug JavaFX Animation til at k�re asynkron kontrolmetode for �ndring i formular og Beregning.
		Timeline beregnValiderOgS�tline = new Timeline(new KeyFrame(Duration.millis(100), 
		new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				kontrolMetode(hentKundeInfoButton, m�nedligAfbetalingField, antalM�nederField, fuldeAfbetalingsbel�bField, CPRnrField, headerTekstLabel, �OPField, CPRnrExclamationMark, fornavnField, efternavnField, adresseField, postnrField, tlfnrField, emailField);
		    }
		}));
		beregnValiderOgS�tline.setCycleCount(Animation.INDEFINITE);
		beregnValiderOgS�tline.play();

		
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("container/styling/style.css");
		stage.setOnCloseRequest(e -> Platform.exit());
		stage.setResizable(false);
		stage.setScene(scene);
        stage.show();
	}
}
