package container.GUI;

import java.util.ArrayList;

import container.DB.DataLayerInstantiater;
import container.async.Banksats;
import container.async.Kreditv�rdighed;
import container.engine.Beregner;
import container.engine.Forkorter;
import container.getDB.Bil;
import container.getDB.S�lger;
import container.savedata.Aftale;
import container.savedata.Kunde;
import container.thread.RunningThread;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUIController extends Application{
	
	private String CPRisD;
	private boolean CPRisTal;
	public static GUIController controller;
	public static boolean forandring;
	private String newCPR;

	// Nyt FormularController Objekt
	public static void initFormularController() {
		controller = new GUIController();
	}
	
	// Metode til at tjekke CPR nummer
	public void tjekCPRnrMetode(TextField CPRnrField, Label CPRnrExclamationMark, Label headerTekstLabel) {
		// Indeholder CPR nummeret udelukkende tal?
    	if(CPRnrField.getText().matches("[0123456789]+")) {
    		CPRisTal = true;
    		// CPR nummeret er altid 10 cifre langt. Da skal der kun s�ges i RKI, hvis det CPR nummeret er 10 cifre - og udelukkende .
    		if(CPRnrField.getLength() == 10) {
	    		// Gem CPR nummeret for kunden.
	    		Kunde.kunde.setCPR(CPRnrField.getText());
	    		// K�r ny asynkron tr�d til indhentning af kreditv�rdighed baseret p� nye CPR nummer.
	    		RunningThread RKIThread = new RunningThread();
	    		RKIThread.setName("RT");
	    		RKIThread.start();
    		}
    	}
    	if(CPRnrField.getLength() > 10) {
			CPRnrField.setText(CPRnrField.getText().substring(0, CPRnrField.getText().length()-1));
		}
    	// Hvis CPR nummeret IKKE er et tal, og hvis CPR nummeret IKKE er 0
    	if(!CPRnrField.getText().matches("[0123456789]+") && CPRnrField.getText().length() > 0){
    		// Det indtastede CPR nummer indeholder fejl.
			CPRnrExclamationMark.setText("!");
			CPRnrExclamationMark.setId("incorrect");
			CPRnrField.setId("incorrect");
			// CPR indeholder fejl. Forhindrer s�lgeren i at indsende anmodning
			Kunde.kunde.setCPR("0");
			Kunde.kunde.setKreditv�rdighed("E");
		// Hvis CPR nummerets l�ngde IKKE er mere end 10 cifre. IKKE indeholder tegn.
    	} else if( CPRnrField.getText().length() < 10) {
	    	// CPR nummeret er endnu ikke skrevet f�rdigt.
		    CPRnrField.setId("neutral");
		    CPRnrExclamationMark.setId("neutral");
		    headerTekstLabel.setText("");
		    CPRnrExclamationMark.setText("!");
		    // CPR ikke komplet. Forhindrer s�lgeren i at indsende anmodning
			Kunde.kunde.setCPR("0");
			Kunde.kunde.setKreditv�rdighed("E");
    	}
	}
	
	// Metode til at tjekke fornavn
	public void tjekFornavnMetode(TextField fornavnField, Label fornavnExclamationMark) {
		// Fornavne indeholder ikke tal
    	if(fornavnField.getText().matches("[a-zA-Z-������ ]+")) {
    		// Hvis fornavn udelukkende er bogstaver er den godkendt
		    fornavnExclamationMark.setText("");
		    fornavnField.setId("correct");
		    // Gem fornavnet i kunde
		    Kunde.kunde.setFornavn(fornavnField.getText());
    	} else {
    		// Fornavnet indeholder tal! Det m� det ikke.
    		fornavnExclamationMark.setText("!");
    		fornavnExclamationMark.setId("incorrect");
    		fornavnField.setId("incorrect");
    		// Fornavn indeholder fejl - undg�, at s�lgeren kan indsende
    		Kunde.kunde.setFornavn(null);
    	}
    	// Hvis fornavnet er tomt
    	if(fornavnField.getText().isEmpty()) {
    		// Fornavnet er tomt. Da m� fornavnet endnu ikke v�re skrevet f�rdigt.
    		fornavnExclamationMark.setText("!");
    		fornavnExclamationMark.setId("neutral");
		    fornavnField.setId("neutral");
		    // Fornavn er tomt. S�ttes da til null
		    Kunde.kunde.setFornavn(null);
	    }
    	// Fornavn l�ngde maks 32 karakterer
    	if(fornavnField.getLength() > 32) {
    		fornavnField.setText(fornavnField.getText().substring(0, fornavnField.getText().length()-1));
		}
	}
	
	// Metode til at tjekke efternavn
	public void tjekEfternavnMetode(TextField efternavnField, Label efternavnExclamationMark) {
		// Efternavne indeholder ikke tal
		if(efternavnField.getText().matches("[a-zA-Z-������ ]+")) {
			// Hvis efternavnet udelukkende er bogstaver er den godkendt
			efternavnExclamationMark.setText("");
			efternavnField.setId("correct");
			// Gem efternavnet i kunde
			Kunde.kunde.setEfternavn(efternavnField.getText());
		} else {
			// Efternavnet indeholder tal!
		    efternavnExclamationMark.setText("!");
		    efternavnExclamationMark.setId("incorrect");
		    efternavnField.setId("incorrect");
		    // Fejl i efternavn - undg�, at s�lgeren kan indsende
		    Kunde.kunde.setEfternavn(null);
		 }
		// Hvis efternavnet er tomt.
		if(efternavnField.getText().isEmpty()) {
			// Efternavnet er tomt. Da m� det endnu ikke v�re skrevet f�rdigt.
	    	efternavnExclamationMark.setText("!");
	    	efternavnExclamationMark.setId("neutral");
			efternavnField.setId("neutral");
			// Tomt efternavn. S�ttes da til null
			Kunde.kunde.setEfternavn(null);
		}
    	// Efternavn l�ngde maks 48 karakterer
    	if(efternavnField.getLength() > 48) {
    		efternavnField.setText(efternavnField.getText().substring(0, efternavnField.getText().length()-1));
		}
	}
	
	// Metode til tjekke adresse
	public void tjekAdresseMetode(TextField adresseField, Label adresseExclamationMark) {
		// Hvis adressen IKKE er tom og ikke indeholder speciale tegn.
		if(!adresseField.getText().isEmpty()) {
			if(adresseField.getText().matches("[a-zA-Z0123456789������ ,.'_-]+")) {
				// Godkendt
				adresseExclamationMark.setText("");
				adresseField.setId("correct");
				// Gem adressen i kunde
				Kunde.kunde.setAdresse(adresseField.getText());
			} else {
				// Indeholder fejl!
				adresseExclamationMark.setText("!");
				adresseField.setId("incorrect");
				// Adresse er ikke gyldigt. S�t da til null
				Kunde.kunde.setAdresse(null);
			}
		} else {
			// Ikke skrevet f�rdigt
			adresseExclamationMark.setText("!");
			adresseField.setId("neutral");
			// Adresse er tom, s�t da til null
			Kunde.kunde.setAdresse(null);
		}
    	// Adresse l�ngde maks 48 karakterer
    	if(adresseField.getLength() > 48) {
    		adresseField.setText(adresseField.getText().substring(0, adresseField.getText().length()-1));
		}
	}
	
	// Metode til at tjekke postnr
	public void tjekPostnrMetode(TextField postnrField, Label postnrExclamationMark,  Label byField, Label byExclamationMark) {
		// Hvis postnummeret ikke er tomt og kun indeholder tal
		if(!postnrField.getText().isEmpty() && postnrField.getText().matches("[0123456789]+")) {
			// Et postnummer er altid 4 cifre langt (0800)
			if(postnrField.getText().length() == 4) {
				// Godkendt
				postnrExclamationMark.setText("");
				postnrField.setId("correct");
				// Gem postnr i kunde
				Kunde.kunde.setPostnr(postnrField.getText());
				Kunde.kunde.setBy(DataLayerInstantiater.getByFraDB(Integer.parseInt(postnrField.getText())));
				byField.setText(DataLayerInstantiater.getByFraDB(Integer.parseInt(postnrField.getText())));
				byExclamationMark.setText("");
			} else if(postnrField.getText().length() < 4) {
				// postnummeret er endnu ikke skrevet f�rdigt.
				postnrExclamationMark.setText("!");
				byExclamationMark.setText("!");
				postnrField.setId("neutral");
				postnrExclamationMark.setId("neutral");
				Kunde.kunde.setPostnr("0");
				Kunde.kunde.setBy(null);
				byField.setText("");
			}
		} else {
			// Det indtastede postnummer indeholder fejl.
			// Advar da s�lger om dette og s�t postnummer og by til null
			postnrExclamationMark.setText("!");
			byExclamationMark.setText("!");
			postnrExclamationMark.setId("incorrect");
			postnrField.setId("incorrect");
			Kunde.kunde.setPostnr("0");
			Kunde.kunde.setBy(null);
			byField.setText("");
		}
		if(postnrField.getText().isEmpty()) {
			// postnummeret er endnu ikke skrevet f�rdigt.
			// By og postnummer s�ttes da til null
			postnrField.setId("neutral");
			postnrExclamationMark.setId("neutral");
			Kunde.kunde.setPostnr("0");
			Kunde.kunde.setBy(null);
			byField.setText("");
		}
    	// Postnummer maks l�ngde = 4 cifre
    	if(postnrField.getLength() > 4) {
    		postnrField.setText(postnrField.getText().substring(0, postnrField.getText().length()-1));
		}
	}

	// Metode til at tjekke telefonnummer
	public void tjekTlfnrMetode(TextField tlfnrField, Label tlfnrExclamationMark) {
		// Indeholder telefonnummeret udelukkende tal?
		if(tlfnrField.getText().matches("[0123456789]+")) {
	 		//  telefonnummeret er altid 8 cifre langt. 
	    	if(tlfnrField.getLength() == 8) {
	    		// Style p� telefonnummer-felterne �ndres for at indikere, at v�rdien kan arbejdes med.
	    		tlfnrExclamationMark.setText("");
	    		tlfnrField.setId("correct");
	    		// Gem telefonnummeret for kunden.
	    		Kunde.kunde.setTlfnr(tlfnrField.getText());
	    	} else {
				// Telefonnummeret er tal, men ikke skrevet f�rdigt
				tlfnrField.setId("neutral");
				tlfnrExclamationMark.setId("neutral");
				Kunde.kunde.setTlfnr(null);
	    	}
		} else {
		    // Det indtastede telefonnummer indeholder fejl.
			tlfnrExclamationMark.setText("!");
			tlfnrExclamationMark.setId("incorrect");
			tlfnrField.setId("incorrect");
			Kunde.kunde.setTlfnr(null);
		}
		// Hvis telefonnummeret er tomt
    	if(tlfnrField.getText().isEmpty()) {
			tlfnrField.setId("neutral");
			tlfnrExclamationMark.setId("neutral");
			// tlfnr s�ttes da til null
			Kunde.kunde.setTlfnr(null);
    	}
    	// Telefonnummer maks l�ngde = 8 cifre
    	if(tlfnrField.getLength() > 8) {
    		tlfnrField.setText(tlfnrField.getText().substring(0, tlfnrField.getText().length()-1));
		}
	}
	
	// Metode til at tjekke email
	public void tjekEmailMetode(TextField emailField, Label emailExclamationMark){
		// Valider Email
		if(emailField.getText().matches("^[a-zA-Z0123456789������@._-]*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$")) {
		    // Style p� email-felterne �ndres for at indikere, at v�rdien kan arbejdes med.
		    emailExclamationMark.setText("");
		    emailField.setId("correct");
		    // Gem emailen for kunden.
		    Kunde.kunde.setEmail(emailField.getText());
	    } else {
	    	// Emailen er endnu ikke skrevet f�rdigt.
	    	emailField.setId("neutral");
	    	Kunde.kunde.setEmail(null);
	    }
    	// Email maks 64 karakterer
    	if(emailField.getLength() > 64) {
    		emailField.setText(emailField.getText().substring(0, emailField.getText().length()-1));
		}
	}
	
	// Metode til at tjekke afbetalingsperioden
	public void tjekAfbetalingsperiodeMetode(TextField afbetalingsperiodeField, Label afbetalingsperiodeExclamationMark) {
		// afbetalingsperioden er 2 eller et cifre lang(t).
		if(afbetalingsperiodeField.getText().matches("[0-9]+") && Integer.parseInt(afbetalingsperiodeField.getText()) > 0) {
		      // Style p� afbetalingsperiode-felterne �ndres for at indikere, at v�rdien kan arbejdes med.
		      afbetalingsperiodeExclamationMark.setText("");
		      afbetalingsperiodeField.setId("correct");
		      // Gem afbetalingsperioden for kunden.
		      Aftale.aftale.setAfbetalingsperiode(Integer.parseInt(afbetalingsperiodeField.getText()));
		      // Beregn nye v�rdier
		      forandring = true;
    	} else {
    		afbetalingsperiodeField.setId("incorrect");
    		afbetalingsperiodeExclamationMark.setId("incorrect");
    		afbetalingsperiodeExclamationMark.setText("!");
    		// S�t afbetalingsperioden til 0 m�neder, da perioden ellers ikke er gyldig
    		Aftale.aftale.setAfbetalingsperiode(0);
    		// Perioden er ikke gyldig. Vis ikke gamle v�rdi
		    forandring = true;
    	}
    	if(afbetalingsperiodeField.getText().isEmpty()) {
    		// Afbetalingsperioden er endnu ikke skrevet f�rdigt.
    		afbetalingsperiodeExclamationMark.setText("!");
    		afbetalingsperiodeField.setId("neutral");
    		afbetalingsperiodeExclamationMark.setId("neutral");
    		// S�t afbetalingsperioden til 0 m�neder, da perioden er tom
    		Aftale.aftale.setAfbetalingsperiode(0);
    		// Da perioden nu er tom skal gamle v�rdi ikke vises
		    forandring = true;
    	}
    	// Afbetalingsperiode maks 99 m�neder
    	if(afbetalingsperiodeField.getLength() > 2) {
    		afbetalingsperiodeField.setText(afbetalingsperiodeField.getText().substring(0, afbetalingsperiodeField.getText().length()-1));
		}
	}
	
	// Metode til at tjekke udbetalingsbel�bet
	public void tjekUdbetalingsbel�bMetode(TextField udbetalingsbel�bField, Label udbetalingsbel�bExclamationMark) {
		if(udbetalingsbel�bField.getText().matches("[0123456789]+")) {
		      // Style p� udbetalingsbel�b-felterne �ndres for at indikere, at v�rdien kan arbejdes med.
		      udbetalingsbel�bExclamationMark.setText("");
		      udbetalingsbel�bField.setId("correct");
		      // Gem udbetalingsbel�b for kunden.
		      Aftale.aftale.setUdbetalingsbel�b(Double.parseDouble(udbetalingsbel�bField.getText()));
		      // Her m� udbetalingsbel�bet v�re gyldigt. Skal beregnes
		      forandring = true;
		} else if(udbetalingsbel�bField.getText().isEmpty()){
			// Udbetalingsbel�bsfeltet er tomt.
			udbetalingsbel�bExclamationMark.setText("!");
			udbetalingsbel�bField.setId("neutral");
			Aftale.aftale.setUdbetalingsbel�b(0);
			// Nu hvor udbetalingsfeltet er tomt = 0 kr. nyt bel�b (0 kr.) vises
		    forandring = true;
		} else {
			// Det indtastede udbetalingsbel�b indeholder fejl.
			udbetalingsbel�bExclamationMark.setText("!");
			udbetalingsbel�bExclamationMark.setId("incorrect");
			udbetalingsbel�bField.setId("incorrect");
			// S�t udbetalingsbel�bet til 0 da udbetalingsbel�bet ikke er gyldigt
			Aftale.aftale.setUdbetalingsbel�b(0);
			// Hvis udbetalingsbel�bet ikke er gyldigt skal beregningen ikke vise �ldre beregning
		    forandring = true;
		}
    	// Udbetalingsbel�b maksimalt 99.999.999
    	if(udbetalingsbel�bField.getLength() > 8) {
    		udbetalingsbel�bField.setText(udbetalingsbel�bField.getText().substring(0, udbetalingsbel�bField.getText().length()-1));
		}
	}
	
	// Metode til at tjekke den valgte bil
	public void tjekBilValgtMetode(ChoiceBox<String> bilChoiceBox, Label bilprisField, Label bilmodelExclamationMark, Label bilprisExclamationMark){
		for(int i = 0; i < Bil.bilList.size(); i++) {
			// Find objektet hvori navnet er lig med navnet sat i bilChoiceBox for at finde prisen p� dette og s�tte index
			if(bilChoiceBox.getValue() == Bil.bilList.get(i).getBil_model_navn()) {
				// S�t aftale bil valgt
				Aftale.aftale.setBil_model(Bil.bilList.get(i).getBil_model_navn());
				// set prisen i bilprisField p� den associerede bil
				bilprisField.setText(""+ (int) Bil.bilList.get(i).getBil_pris());
				// Set style p� bilChoiceBox nu hvor bilen er valgt
				bilChoiceBox.setId("dropdown_correct");
				// Fjern udr�bstegn nu hvor bil er valgt
				bilmodelExclamationMark.setText("");
				bilprisExclamationMark.setText("");
				// S�t aktuelle bil
				Bil.aktuelBil = i;
				// Gem aktuelle bils pris
				Aftale.aftale.setBil_pris(Bil.bilList.get(i).getBil_pris());
				// Ny bil pris skal udregnes
				forandring = true;
			}
		}
	}
	
	
	// Metode til at udfylde s�lger dropdown menuen
	public void fyldS�lgerBoxMetode(ChoiceBox<String> s�lgerChoiceBox, Label s�lgerExclamationMark) {
		for(int i = 0; i < S�lger.s�lgerList.size(); i++) {
			// Find objektet hvori navnet er lig med navnet sat i s�lgerChoiceBox for at s�tte index
			if(s�lgerChoiceBox.getValue() == S�lger.s�lgerList.get(i).getS�lger_navn()) {
				// S�t aftale s�lger
				Aftale.aftale.setS�lger(S�lger.s�lgerList.get(i).getS�lger_navn());
				// Set style p� s�lgerChoiceBox nu hvor s�lgeren er valgt
				s�lgerChoiceBox.setId("dropdown_correct");
				// Fjern udr�bstegn nu hvor s�lger er valgt
				s�lgerExclamationMark.setText("");
				// s�t aktuelle s�lger
				S�lger.aktuelS�lger = i;
			}
		}
	}
	
	
	// k�rer 10 gange i sekundet for kontrol
	public void kontrolMetode(Button hentKundeInfoButton, Label m�nedligAfbetalingField, Label antalM�nederField, Label fuldeAfbetalingsbel�bField, TextField CPRnrField, Label headerTekstLabel, Label �OPField, Label CPRnrExclamationMark, TextField fornavnField, TextField efternavnField, TextField adresseField, TextField postnrField, TextField tlfnrField, TextField emailField) {
		// Hvis der er foretaget en �ndring
		if(forandring) {
			// Hvis kreditv�rdigheden ikke er i gang med at blive hentet, og cpr nummeret ikke er 0
	       	if(!Beregner.getHenterKreditv�rdighed() && Kunde.kunde.getCPR() != "0") {
	       		// Beregn �OP ... etc. hvis en v�rdi i formularen er �ndret, eller hvis RKI har sat en ny v�rdi.
		       	Beregner.beregner.beregnAutoFeedback(Banksats.getRentesats(), Kreditv�rdighed.getKreditv�rdighed(), Aftale.aftale.getUdbetalingsbel�b(), Aftale.aftale.getBil_pris(), Aftale.aftale.getAfbetalingsperiode());
	       		// Opdater felterne i formularen (placeret nederst, eksisterer for at vise tilbuddet og oversigten over denne)
	       		m�nedligAfbetalingField.setText(Forkorter.Formater((Aftale.aftale.getM�nedlig_afbetaling()),"kr") + " kr.");
	    		antalM�nederField.setText(""+Aftale.aftale.getAfbetalingsperiode());
	    		fuldeAfbetalingsbel�bField.setText(Forkorter.Formater((Aftale.aftale.getFuldeAfbetalingsbel�b()),"kr") + " kr.");
	    		�OPField.setText(Forkorter.Formater((Aftale.aftale.get�rlige_omkostninger_i_procent()), "") + " %");
	       	} else if(Kunde.kunde.getCPR().length() == 10){
	       		// Hvis CPR er �ndret giver det ikke mening at beregne imens kreditv�rdigheden hentes
	       		m�nedligAfbetalingField.setText("Beregner...");
	    		antalM�nederField.setText("Beregner...");
	    		fuldeAfbetalingsbel�bField.setText("Beregner...");
	    		�OPField.setText("Beregner...");
	       	}
	       	// Nu har beregneren beregnet f�rdigt
	       	forandring = false;
		}
       	// Hvis CPR nummeret er det CPR nummer der har kreditv�rdigheden p� "D" og Beregneren er f�rdig med at hente kreditv�rdigheden.
       	if(CPRnrField.getText().equals(CPRisD) && !Beregner.getHenterKreditv�rdighed()) {
       		headerTekstLabel.setText("Kreditv�rdigheden " + Kunde.kunde.getKreditv�rdighed() + " er for lav!");
	   		CPRnrField.setId("incorrect");
	   		Kunde.kunde.setCPR(null);
       	}
       	// Hvis kreditv�rdigheden p� CPR nummeret er "D" og Beregneren er f�rdig med at hente kreditv�rdigheden
	   	if(Kunde.kunde.getKreditv�rdighed() == "D" && !Beregner.getHenterKreditv�rdighed()) {
	   		// Her s�ttes CPRisD; CPR nummeret med kreditv�rdigheden p� "D" gemmes. Skal bruges til ovenst�ende if s�tning.
	   		CPRisD = CPRnrField.getText();
	   	}
	   	// Hvis kreditv�rdigheden er A, B eller C
	   	if(Kunde.kunde.getKreditv�rdighed().matches("A|B|C")) {
	   		// Da m� kreditv�rdigheden v�re okay og vi beh�ver ikke advare ekspedienten.
	   		headerTekstLabel.setText("");
	   		CPRnrExclamationMark.setText("");
	   		// Hvis kreditv�rdigheden er 10 cifre langt, er et tal og Beregneren er f�rdig med at beregne kreditv�rdigheden.
	   		if(CPRnrField.getText().length() == 10 && CPRisTal && !Beregner.getHenterKreditv�rdighed()) {
	   			// Da m� CPR nummeret v�re gyldigt. 
	   			CPRnrField.setId("correct");
	   			// Hvis det IKKE er samme CPR som f�r:
   				if(!CPRnrField.getText().equals(newCPR)) {
   	   				// Da m� dette v�re nyt CPR
   	   				newCPR = Kunde.kunde.getCPR();
   	   				// Her m� der da v�re en forandring der skal udregnes
   		   			forandring = true;
   		   			// Tjek om CPR eksisterer i Databasen
   			   		if(DataLayerInstantiater.tjekEksistererCPRIDB()) {
   			   			hentKundeInfoButton.setVisible(true);
   			   			// Undg� at overwrite en anden kundes information
				   		fornavnField.setText("");
						efternavnField.setText("");
						postnrField.setText("");
						adresseField.setText("");
						tlfnrField.setText("");
						emailField.setText("");
   			   		} else {
   			   			hentKundeInfoButton.setVisible(false);
   			   		}
   				}
	   		}
	   	}
	   	// Hvis s�lgeren �ndrer i CPR skal man ikke kunne hente info for tidligere CPR
	   	if(CPRnrField.getText().length() < 10) {
	   		hentKundeInfoButton.setVisible(false);
	   	}
	   	// Hvis beregneren er i gang med at beregne. For at undg� forkert returv�rdi fra kreditv�rdighedsberegningen.
	   	if(Beregner.getHenterKreditv�rdighed()) {
	   		CPRnrField.setEditable(false);
	   		CPRnrField.setStyle("-fx-background-color:rgb(235,235,235)");
	   	} else {
	   		CPRnrField.setEditable(true);
	   		CPRnrField.setStyle("-fx-background-color:transparent");
	   	}

	}
	
	// Hvis DataLayer har indhentet data fra databasen p� kunden og s�lgeren �nsker at indhente dette
	public static void opdaterGUI(TextField fornavnField, TextField efternavnField, TextField adresseField, TextField postnrField, TextField tlfnrField, TextField emailField) {
		ArrayList<Object> hentedeInfo = new DataLayerInstantiater().hentAltKundeInfoForCPR();
		fornavnField.setText(hentedeInfo.get(0).toString());
		efternavnField.setText(hentedeInfo.get(1).toString());
		postnrField.setText(hentedeInfo.get(4).toString());
		adresseField.setText(hentedeInfo.get(5).toString());
		tlfnrField.setText(hentedeInfo.get(6).toString());
		emailField.setText(hentedeInfo.get(7).toString());
	}
	
	//Grundlag for accept: Valideret eller ej.
	public String anmodningAccepteretGrundlag(int i) {
		if(i == 0) {
			return "Anmodning afventer svar fra salgschef!";
		} else if(i == 1) {
			return "Anmodning godkendt!";
		}
		return "ERROR!";
	}
	
	
	// Metode til at s�tte parametre for Label
	public void s�tParametreLabel(Label type, int height, int width, int x, int y) {
		type.setPrefSize(width, height);
		type.setTranslateX(x);
		type.setTranslateY(y);
	}
	
	
	
	// Metode til at s�tte parametre for HBox
	public void s�tParametreHBox(HBox type, int height, int x, int y) {
		type.setPrefHeight(height);
		type.setTranslateX(x);
		type.setTranslateY(y);
	}
	
	// Metode til at s�tte parametre for Button
	public void s�tParametreButton(Button type, int height, int width, int x, int y) {
		type.setPrefSize(width, height);
		type.setTranslateX(x);
		type.setTranslateY(y);
	}

	// Da extends Application
	@Override
	public void start(Stage Stage) throws Exception {
	}
}
