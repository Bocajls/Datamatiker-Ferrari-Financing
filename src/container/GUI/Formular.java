package container.GUI;

import container.anmodning.ValiderAnmodning;
import container.engine.Forkorter;
import container.getDB.Bil;
import container.getDB.Sælger;
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
		sætParametreLabel(CPRnrLabel, 25, 80, 15, 80);
		Line CPRnrLine = new Line(15, 105, 420, 105);
		CPRnrLine.setStroke(Color.rgb(184,184,184));
		Label CPRnrColonLabel = new Label(":");
		sætParametreLabel(CPRnrColonLabel, 25, 4, 95, 80);
		Label CPRnrExclamationMark = new Label("!");
		sætParametreLabel(CPRnrExclamationMark, 25, 4, 425, 80);
		TextField CPRnrField = new TextField();
		CPRnrField.setPrefSize(320, 20);
		HBox CPRnrFieldBox = new HBox(CPRnrField);
		sætParametreHBox(CPRnrFieldBox, 25, 101, 81);
		// Så snart der foretages en ændring i CPrnrField 
		CPRnrField.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	tjekCPRnrMetode(CPRnrField, CPRnrExclamationMark, headerTekstLabel);
		    }
		});
		
		
		// Fornavn elementer
		Label fornavnLabel = new Label("Fornavn");
		sætParametreLabel(fornavnLabel, 25, 80, 15, 105);
		Line fornavnLine = new Line(15, 130, 420, 130);
		fornavnLine.setStroke(Color.rgb(184,184,184));
		Label fornavnColonLabel = new Label(":");
		sætParametreLabel(fornavnColonLabel, 25, 4, 95, 105);
		Label fornavnExclamationMark = new Label("!");
		sætParametreLabel(fornavnExclamationMark, 25, 4, 425, 105);
		TextField fornavnField = new TextField();
		fornavnField.setPrefSize(320, 20);
		HBox fornavnFieldBox = new HBox(fornavnField);
		sætParametreHBox(fornavnFieldBox, 25, 101, 106);
		// Så snart der foretages en ændring i fornavnField 
		fornavnField.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	tjekFornavnMetode(fornavnField, fornavnExclamationMark);
		    }
		});

		
		// Efternavn elementer
		Label efternavnLabel = new Label("Efternavn");
		sætParametreLabel(efternavnLabel, 25, 80, 15, 130);
		Line efternavnLine = new Line(15, 155, 420, 155);
		efternavnLine.setStroke(Color.rgb(184,184,184));
		Label efternavnColonLabel = new Label(":");
		sætParametreLabel(efternavnColonLabel, 25, 4, 95, 130);
		Label efternavnExclamationMark = new Label("!");
		sætParametreLabel(efternavnExclamationMark, 25, 4, 425, 130);
		TextField efternavnField = new TextField();
		efternavnField.setPrefSize(320, 20);
		HBox efternavnFieldBox = new HBox(efternavnField);
		sætParametreHBox(efternavnFieldBox, 25, 101, 131);
		// Så snart der foretages en ændring i efternavnField 
		efternavnField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tjekEfternavnMetode(efternavnField, efternavnExclamationMark);
			}
		});
		
		
		// Adresse elementer
		Label adresseLabel = new Label("Adresse");
		sætParametreLabel(adresseLabel, 25, 80, 15, 155);
		Line adresseLine = new Line(15, 180, 420, 180);
		adresseLine.setStroke(Color.rgb(184,184,184));
		Label adresseColonLabel = new Label(":");
		sætParametreLabel(adresseColonLabel, 25, 4, 95, 155);
		Label adresseExclamationMark = new Label("!");
		sætParametreLabel(adresseExclamationMark, 25, 4, 425, 155);
		TextField adresseField = new TextField();
		adresseField.setPrefSize(320, 20);
		HBox adresseFieldBox = new HBox(adresseField);
		sætParametreHBox(adresseFieldBox, 25, 101, 156);
		// Så snart der foretages en ændring i adressseField
		adresseField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tjekAdresseMetode(adresseField, adresseExclamationMark);
			}
		});
		
		
		// Postnr elementer
		Label postnrLabel = new Label("Postnr");
		sætParametreLabel(postnrLabel, 25, 80, 15, 180);
		Line postnrLine = new Line(15, 205, 420, 205);
		postnrLine.setStroke(Color.rgb(184,184,184));
		Label postnrColonLabel = new Label(":");
		sætParametreLabel(postnrColonLabel, 25, 4, 95, 180);
		Label postnrExclamationMark = new Label("!");
		sætParametreLabel(postnrExclamationMark, 25, 4, 425, 180);
		TextField postnrField = new TextField();
		postnrField.setPrefSize(320, 20);
		HBox postnrFieldBox = new HBox(postnrField);
		sætParametreHBox(postnrFieldBox, 25, 101, 181);
		
		
		
		// By elementer
		Label byLabel = new Label("By");
		sætParametreLabel(byLabel, 25, 80, 15, 205);
		Line byLine = new Line(15, 230, 420, 230);
		byLine.setStroke(Color.rgb(184,184,184));
		Label byColonLabel = new Label(":");
		sætParametreLabel(byColonLabel, 25, 4, 95, 205);
		Label byExclamationMark = new Label("!");
		sætParametreLabel(byExclamationMark, 25, 4, 425, 205);
		Label byField = new Label();
		sætParametreLabel(byField, 23, 318, 102, 206);
		byField.setId("ikkeAEndre");
		// Så snart der foretages en ændring i postnrField 	
		postnrField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tjekPostnrMetode(postnrField, postnrExclamationMark, byField, byExclamationMark);
			}
		});
		
		
		// Tlf. nr. elementer
		Label tlfnrLabel = new Label("Tlfnr");
		sætParametreLabel(tlfnrLabel, 25, 80, 15, 230);
		Line tlfnrLine = new Line(15, 255, 420, 255);
		tlfnrLine.setStroke(Color.rgb(184,184,184));
		Label tlfnrColonLabel = new Label(":");
		sætParametreLabel(tlfnrColonLabel, 25, 4, 95, 230);
		Label tlfnrExclamationMark = new Label("!");
		sætParametreLabel(tlfnrExclamationMark, 25, 4, 425, 230);
		TextField tlfnrField = new TextField();
		tlfnrField.setPrefSize(320, 20);
		HBox tlfnrFieldBox = new HBox(tlfnrField);
		sætParametreHBox(tlfnrFieldBox, 25, 101, 231);
		// Så snart der foretages en ændring i tlfnrField 	
		tlfnrField.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				tjekTlfnrMetode(tlfnrField, tlfnrExclamationMark);
			}
		});
		
		
		// Email elementer
		Label emailLabel = new Label("Email");
		sætParametreLabel(emailLabel, 25, 80, 15, 255);
		Line emailLine = new Line(15, 280, 420, 280);
		emailLine.setStroke(Color.rgb(184,184,184));
		Label emailColonLabel = new Label(":");
		sætParametreLabel(emailColonLabel, 25, 4, 95, 255);
		Label emailExclamationMark = new Label("!");
		sætParametreLabel(emailExclamationMark, 25, 4, 425, 255);
		TextField emailField = new TextField();
		emailField.setPrefSize(320, 20);
		HBox emailFieldBox = new HBox(emailField);
		sætParametreHBox(emailFieldBox, 25, 101, 256);
		// Så snart der foretages en ændring i emailField
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
		Label afbetalingsperiodeBeskrivelseLabel = new Label("(Ved afbetalingsperiode på over 36 måneder tillægges ét procentpoint)");
		sætParametreLabel(afbetalingsperiodeBeskrivelseLabel, 25, 625, 15, 305);
		afbetalingsperiodeBeskrivelseLabel.setId("beskrivelseText");
		Label afbetalingsperiodeLabel = new Label("Afbetalingsperiode i måneder");
		sætParametreLabel(afbetalingsperiodeLabel, 25, 320, 15, 325);
		Line afbetalingsperiodeLine = new Line(15, 351, 420, 351);
		afbetalingsperiodeLine.setStroke(Color.rgb(184,184,184));
		Label afbetalingsperiodeColonLabel = new Label(":");
		sætParametreLabel(afbetalingsperiodeColonLabel, 25, 4, 180, 325);
		Label afbetalingsperiodeExclamationMark = new Label("!");
		sætParametreLabel(afbetalingsperiodeExclamationMark, 25, 4, 425, 325);
		TextField afbetalingsperiodeField = new TextField();
		afbetalingsperiodeField.setPrefSize(238, 20);
		HBox afbetalingsperiodeFieldBox = new HBox(afbetalingsperiodeField);
		sætParametreHBox(afbetalingsperiodeFieldBox, 25, 183, 327);
		// Så snart der foretages en ændring i afbetalingsperiodeField
		afbetalingsperiodeField.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	tjekAfbetalingsperiodeMetode(afbetalingsperiodeField, afbetalingsperiodeExclamationMark);
		    }
		});
		
		
		// Udbetalingsbeløb elementer
		Label udbetalingsbeløbBeskrivelseLabel = new Label("(Ved udbetalingsbeløb på under 50% af bilens værdi tillægges ét procentpoint)");
		sætParametreLabel(udbetalingsbeløbBeskrivelseLabel, 25, 625, 15, 377);
		udbetalingsbeløbBeskrivelseLabel.setId("beskrivelseText");
		Label udbetalingsbeløbLabel = new Label("Udbetalingsbeløb");
		sætParametreLabel(udbetalingsbeløbLabel, 25, 320, 15, 397);
		Line udbetalingsbeløbLine = new Line(15, 423, 420, 423);
		udbetalingsbeløbLine.setStroke(Color.rgb(184,184,184));
		Label udbetalingsbeløbColonLabel = new Label(":");
		sætParametreLabel(udbetalingsbeløbColonLabel, 25, 4, 120, 397);
		Label udbetalingsbeløbExclamationMark = new Label("!");
		sætParametreLabel(udbetalingsbeløbExclamationMark, 25, 4, 425, 397);
		TextField udbetalingsbeløbField = new TextField();
		udbetalingsbeløbField.setPrefSize(300, 20);
		HBox udbetalingsbeløbFieldBox = new HBox(udbetalingsbeløbField);
		sætParametreHBox(udbetalingsbeløbFieldBox, 25, 123, 399);
		// Så snart der foretages en ændring i udbetalingsbeløbField
		udbetalingsbeløbField.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	tjekUdbetalingsbeløbMetode(udbetalingsbeløbField, udbetalingsbeløbExclamationMark);
		    }
		});
		
		
		pane.getChildren().addAll(afbetalingsperiodeBeskrivelseLabel,	afbetalingsperiodeLabel, 	afbetalingsperiodeLine,	afbetalingsperiodeColonLabel,	afbetalingsperiodeExclamationMark,	afbetalingsperiodeFieldBox);
		pane.getChildren().addAll(udbetalingsbeløbBeskrivelseLabel,		udbetalingsbeløbLabel, 		udbetalingsbeløbLine,	udbetalingsbeløbColonLabel,		udbetalingsbeløbExclamationMark,	udbetalingsbeløbFieldBox);
		

		// Udbetalingsbeløb elementer
		Label sælgerGrænseBeskrivelseLabel = new Label("Overgår afbetalingsbeløbet (vælg sælger først) skal anmodningen godkendes!");
		sætParametreLabel(sælgerGrænseBeskrivelseLabel, 25, 625, 15, 449);
		sælgerGrænseBeskrivelseLabel.setId("beskrivelseText");
		pane.getChildren().addAll(sælgerGrænseBeskrivelseLabel);
		
		
		// Bil model elementer
		Label bilmodelLabel = new Label("Bilmodel");
		sætParametreLabel(bilmodelLabel, 25, 320, 15, 469);
		Line bilmodelLine = new Line(15, 495, 420, 495);
		bilmodelLine.setStroke(Color.rgb(184,184,184));
		Label bilmodelColonLabel = new Label(":");
		sætParametreLabel(bilmodelColonLabel, 25, 4, 95, 469);
		Label bilmodelExclamationMark = new Label("!");
		sætParametreLabel(bilmodelExclamationMark, 25, 4, 425, 469);
		ChoiceBox<String> bilChoiceBox = new ChoiceBox<>();
		// For hvert element i ArrayList<Sælger> sæt det ind i ChoiceBox
		for(int i = 0; i < Bil.bilList.size(); i++) {
			bilChoiceBox.getItems().add(Bil.bilList.get(i).getBil_model_navn());
		}
		bilChoiceBox.setPrefSize(320, 25);
		bilChoiceBox.setTranslateX(101);
		bilChoiceBox.setTranslateY(471);
		bilChoiceBox.setId("dropdown");
		

		// Bil pris elementer
		Label bilprisLabel = new Label("Bilpris");
		sætParametreLabel(bilprisLabel, 25, 320, 15, 494);
		Line bilprisLine = new Line(15, 520, 420, 520);
		bilprisLine.setStroke(Color.rgb(184,184,184));
		Label bilprisColonLabel = new Label(":");
		sætParametreLabel(bilprisColonLabel, 25, 4, 95, 494);
		Label bilprisExclamationMark = new Label("!");
		sætParametreLabel(bilprisExclamationMark, 25, 4, 425, 494);
		Label bilprisField = new Label();
		sætParametreLabel(bilprisField, 23, 318, 102, 496);
		bilprisField.setId("ikkeAEndre");
		// Note denne angives længere oppe, men listeneren er hernede, da den skal sætte en værdi i bilprisField
		// Listener for ChoiceBox
		bilChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				tjekBilValgtMetode(bilChoiceBox, bilprisField, bilmodelExclamationMark, bilprisExclamationMark);
			}    
		});
	
		
		// Sælger elementer
		Label sælgerLabel = new Label("Sælger");
		sætParametreLabel(sælgerLabel, 25, 320, 15, 519);
		Line sælgerLine = new Line(15, 545, 420, 545);
		sælgerLine.setStroke(Color.rgb(184,184,184));
		Label sælgerColonLabel = new Label(":");
		sætParametreLabel(sælgerColonLabel, 25, 4, 95, 519);
		Label sælgerExclamationMark = new Label("!");
		sætParametreLabel(sælgerExclamationMark, 25, 4, 425, 519);
		ChoiceBox<String> sælgerChoiceBox = new ChoiceBox<>();
		// For hvert element i ArrayList<Sælger> sæt det ind i ChoiceBox
		for(int i = 0; i < Sælger.sælgerList.size(); i++) {
			sælgerChoiceBox.getItems().add(Sælger.sælgerList.get(i).getSælger_navn());
		}
		sælgerChoiceBox.setPrefSize(320, 25);
		sælgerChoiceBox.setTranslateX(101);
		sælgerChoiceBox.setTranslateY(521);
		sælgerChoiceBox.setId("dropdown");
		// Listener for ChoiceBox
		sælgerChoiceBox.valueProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> ov, String t, String t1) {
				fyldSælgerBoxMetode(sælgerChoiceBox, sælgerExclamationMark);
				sælgerGrænseBeskrivelseLabel.setText("Overgår afbetalingsbeløbet " + Forkorter.Formater(DataLayerInstantiater.getSælgerGrænseFraDB(), "kr") + " kr. skal anmodningen godkendes!");
			}    
		});
		
			
		pane.getChildren().addAll(bilmodelLabel, 			bilprisLabel, 			sælgerLabel);
		pane.getChildren().addAll(bilmodelLine, 			bilprisLine, 			sælgerLine);
		pane.getChildren().addAll(bilmodelColonLabel, 		bilprisColonLabel, 		sælgerColonLabel);
		pane.getChildren().addAll(bilmodelExclamationMark,	bilprisExclamationMark, sælgerExclamationMark);
		pane.getChildren().addAll(bilprisField);
		pane.getChildren().addAll(bilChoiceBox, sælgerChoiceBox);
		
		
		// ÅOP elementer
		Label ÅOPLabel = new Label("ÅOP");
		sætParametreLabel(ÅOPLabel, 25, 136, 15, 570);
		Line ÅOPLine = new Line(15, 596, 420, 596);
		ÅOPLine.setStroke(Color.rgb(184,184,184));
		Label ÅOPColonLabel = new Label(":");
		sætParametreLabel(ÅOPColonLabel, 25, 4, 145, 570);
		Label ÅOPField = new Label();
		ÅOPField.setPrefSize(275, 20);
		HBox ÅOPFieldBox = new HBox(ÅOPField);
		sætParametreHBox(ÅOPFieldBox, 25, 158, 574);
		
		
		// Fulde afbetalingsbeløb elementer
		Label fuldeAfbetalingsbeløbLabel = new Label("Fulde afbetalingsbeløb");
		sætParametreLabel(fuldeAfbetalingsbeløbLabel, 25, 136, 15, 595);
		Line fuldeAfbetalingsbeløbLine = new Line(15, 621, 420, 621);
		fuldeAfbetalingsbeløbLine.setStroke(Color.rgb(184,184,184));
		Label fuldeAfbetalingsbeløbColonLabel = new Label(":");
		sætParametreLabel(fuldeAfbetalingsbeløbColonLabel, 25, 4, 145, 595);
		Label fuldeAfbetalingsbeløbField = new Label();
		fuldeAfbetalingsbeløbField.setPrefWidth(275);
		HBox fuldeAfbetalingsbeløbFieldBox = new HBox(fuldeAfbetalingsbeløbField);
		sætParametreHBox(fuldeAfbetalingsbeløbFieldBox, 25, 158, 599);
		
		
		// Antal måneder
		Label antalMånederLabel = new Label("Antal måneder");
		sætParametreLabel(antalMånederLabel, 25, 136, 15, 620);
		Line antalMånederLine = new Line(15, 646, 420, 646);
		antalMånederLine.setStroke(Color.rgb(184,184,184));
		Label antalMånederColonLabel = new Label(":");
		sætParametreLabel(antalMånederColonLabel, 25, 4, 145, 620);
		Label antalMånederField = new Label();
		antalMånederField.setPrefSize(275, 20);
		HBox antalMånederFieldBox = new HBox(antalMånederField);
		sætParametreHBox(antalMånederFieldBox, 25, 158, 624);
		
		
		// Månedlig afbetaling
		Label månedligAfbetalingLabel = new Label("Månedlig afbetaling");
		sætParametreLabel(månedligAfbetalingLabel, 25, 136, 15, 645);
		Line månedligAfbetalingLine = new Line(15, 671, 420, 671);
		månedligAfbetalingLine.setStroke(Color.rgb(184,184,184));
		Label månedligAfbetalingColonLabel = new Label(":");
		sætParametreLabel(månedligAfbetalingColonLabel, 25, 4, 145, 645);
		Label månedligAfbetalingField = new Label();
		månedligAfbetalingField.setPrefSize(275, 20);
		HBox månedligAfbetalingFieldBox = new HBox(månedligAfbetalingField);
		sætParametreHBox(månedligAfbetalingFieldBox, 25, 158, 649);
				
		
		pane.getChildren().addAll(ÅOPLabel, 		fuldeAfbetalingsbeløbLabel,			antalMånederLabel,		månedligAfbetalingLabel);
		pane.getChildren().addAll(ÅOPLine, 			fuldeAfbetalingsbeløbLine,			antalMånederLine,		månedligAfbetalingLine);
		pane.getChildren().addAll(ÅOPColonLabel, 	fuldeAfbetalingsbeløbColonLabel,	antalMånederColonLabel,	månedligAfbetalingColonLabel);
		pane.getChildren().addAll(ÅOPFieldBox, 		fuldeAfbetalingsbeløbFieldBox,		antalMånederFieldBox,	månedligAfbetalingFieldBox);
		
		// Til at indhente data fra DB hvis CPR nummer eksisterer
		Button hentKundeInfoButton = new Button("Hent info");
		sætParametreButton(hentKundeInfoButton, 20, 80, 345, 80);
		hentKundeInfoButton.setVisible(false);
		pane.getChildren().addAll(hentKundeInfoButton);
		// Når indsend anmodning knappen klikkes på.
		hentKundeInfoButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	opdaterGUI(fornavnField, efternavnField, adresseField, postnrField, tlfnrField, emailField);
		    	hentKundeInfoButton.setVisible(false);
		    }
		});
		
		// Knappen til at indsende anmodningen
		Button indsendAnmodningButton = new Button("BEKRÆFT OG INDSEND ANMODNING");
		sætParametreButton(indsendAnmodningButton, 50, 408, 15, 697);
		indsendAnmodningButton.setId("indsendAnmodningButton");
		pane.getChildren().addAll(indsendAnmodningButton);
		
		
		// Når indsend anmodning knappen klikkes på.
		indsendAnmodningButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	// Hvis valideringen går igennem luk denne formular
		    	ValiderAnmodning.anmodning.valider();
		    	if(ValiderAnmodning.anmodning.getAccepteret() == 1) {
		    		stage.close();
		    	}
		     	
		    }
		});
		
		
		// Brug JavaFX Animation til at køre asynkron kontrolmetode for ændring i formular og Beregning.
		Timeline beregnValiderOgSætline = new Timeline(new KeyFrame(Duration.millis(100), 
		new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				kontrolMetode(hentKundeInfoButton, månedligAfbetalingField, antalMånederField, fuldeAfbetalingsbeløbField, CPRnrField, headerTekstLabel, ÅOPField, CPRnrExclamationMark, fornavnField, efternavnField, adresseField, postnrField, tlfnrField, emailField);
		    }
		}));
		beregnValiderOgSætline.setCycleCount(Animation.INDEFINITE);
		beregnValiderOgSætline.play();

		
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("container/styling/style.css");
		stage.setOnCloseRequest(e -> Platform.exit());
		stage.setResizable(false);
		stage.setScene(scene);
        stage.show();
	}
}
