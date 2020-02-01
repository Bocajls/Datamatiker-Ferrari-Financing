package container.GUI;

import java.util.ArrayList;

import container.DB.DataLayerInstantiater;
import container.async.Banksats;
import container.async.Kreditværdighed;
import container.engine.Beregner;
import container.engine.Forkorter;
import container.getDB.Bil;
import container.getDB.Sælger;
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
    		// CPR nummeret er altid 10 cifre langt. Da skal der kun søges i RKI, hvis det CPR nummeret er 10 cifre - og udelukkende .
    		if(CPRnrField.getLength() == 10) {
	    		// Gem CPR nummeret for kunden.
	    		Kunde.kunde.setCPR(CPRnrField.getText());
	    		// Kør ny asynkron tråd til indhentning af kreditværdighed baseret på nye CPR nummer.
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
			// CPR indeholder fejl. Forhindrer sælgeren i at indsende anmodning
			Kunde.kunde.setCPR("0");
			Kunde.kunde.setKreditværdighed("E");
		// Hvis CPR nummerets længde IKKE er mere end 10 cifre. IKKE indeholder tegn.
    	} else if( CPRnrField.getText().length() < 10) {
	    	// CPR nummeret er endnu ikke skrevet færdigt.
		    CPRnrField.setId("neutral");
		    CPRnrExclamationMark.setId("neutral");
		    headerTekstLabel.setText("");
		    CPRnrExclamationMark.setText("!");
		    // CPR ikke komplet. Forhindrer sælgeren i at indsende anmodning
			Kunde.kunde.setCPR("0");
			Kunde.kunde.setKreditværdighed("E");
    	}
	}
	
	// Metode til at tjekke fornavn
	public void tjekFornavnMetode(TextField fornavnField, Label fornavnExclamationMark) {
		// Fornavne indeholder ikke tal
    	if(fornavnField.getText().matches("[a-zA-Z-æøåÆØÅ ]+")) {
    		// Hvis fornavn udelukkende er bogstaver er den godkendt
		    fornavnExclamationMark.setText("");
		    fornavnField.setId("correct");
		    // Gem fornavnet i kunde
		    Kunde.kunde.setFornavn(fornavnField.getText());
    	} else {
    		// Fornavnet indeholder tal! Det må det ikke.
    		fornavnExclamationMark.setText("!");
    		fornavnExclamationMark.setId("incorrect");
    		fornavnField.setId("incorrect");
    		// Fornavn indeholder fejl - undgå, at sælgeren kan indsende
    		Kunde.kunde.setFornavn(null);
    	}
    	// Hvis fornavnet er tomt
    	if(fornavnField.getText().isEmpty()) {
    		// Fornavnet er tomt. Da må fornavnet endnu ikke være skrevet færdigt.
    		fornavnExclamationMark.setText("!");
    		fornavnExclamationMark.setId("neutral");
		    fornavnField.setId("neutral");
		    // Fornavn er tomt. Sættes da til null
		    Kunde.kunde.setFornavn(null);
	    }
    	// Fornavn længde maks 32 karakterer
    	if(fornavnField.getLength() > 32) {
    		fornavnField.setText(fornavnField.getText().substring(0, fornavnField.getText().length()-1));
		}
	}
	
	// Metode til at tjekke efternavn
	public void tjekEfternavnMetode(TextField efternavnField, Label efternavnExclamationMark) {
		// Efternavne indeholder ikke tal
		if(efternavnField.getText().matches("[a-zA-Z-æøåÆØÅ ]+")) {
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
		    // Fejl i efternavn - undgå, at sælgeren kan indsende
		    Kunde.kunde.setEfternavn(null);
		 }
		// Hvis efternavnet er tomt.
		if(efternavnField.getText().isEmpty()) {
			// Efternavnet er tomt. Da må det endnu ikke være skrevet færdigt.
	    	efternavnExclamationMark.setText("!");
	    	efternavnExclamationMark.setId("neutral");
			efternavnField.setId("neutral");
			// Tomt efternavn. Sættes da til null
			Kunde.kunde.setEfternavn(null);
		}
    	// Efternavn længde maks 48 karakterer
    	if(efternavnField.getLength() > 48) {
    		efternavnField.setText(efternavnField.getText().substring(0, efternavnField.getText().length()-1));
		}
	}
	
	// Metode til tjekke adresse
	public void tjekAdresseMetode(TextField adresseField, Label adresseExclamationMark) {
		// Hvis adressen IKKE er tom og ikke indeholder speciale tegn.
		if(!adresseField.getText().isEmpty()) {
			if(adresseField.getText().matches("[a-zA-Z0123456789æøåÆØÅ ,.'_-]+")) {
				// Godkendt
				adresseExclamationMark.setText("");
				adresseField.setId("correct");
				// Gem adressen i kunde
				Kunde.kunde.setAdresse(adresseField.getText());
			} else {
				// Indeholder fejl!
				adresseExclamationMark.setText("!");
				adresseField.setId("incorrect");
				// Adresse er ikke gyldigt. Sæt da til null
				Kunde.kunde.setAdresse(null);
			}
		} else {
			// Ikke skrevet færdigt
			adresseExclamationMark.setText("!");
			adresseField.setId("neutral");
			// Adresse er tom, sæt da til null
			Kunde.kunde.setAdresse(null);
		}
    	// Adresse længde maks 48 karakterer
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
				// postnummeret er endnu ikke skrevet færdigt.
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
			// Advar da sælger om dette og sæt postnummer og by til null
			postnrExclamationMark.setText("!");
			byExclamationMark.setText("!");
			postnrExclamationMark.setId("incorrect");
			postnrField.setId("incorrect");
			Kunde.kunde.setPostnr("0");
			Kunde.kunde.setBy(null);
			byField.setText("");
		}
		if(postnrField.getText().isEmpty()) {
			// postnummeret er endnu ikke skrevet færdigt.
			// By og postnummer sættes da til null
			postnrField.setId("neutral");
			postnrExclamationMark.setId("neutral");
			Kunde.kunde.setPostnr("0");
			Kunde.kunde.setBy(null);
			byField.setText("");
		}
    	// Postnummer maks længde = 4 cifre
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
	    		// Style på telefonnummer-felterne ændres for at indikere, at værdien kan arbejdes med.
	    		tlfnrExclamationMark.setText("");
	    		tlfnrField.setId("correct");
	    		// Gem telefonnummeret for kunden.
	    		Kunde.kunde.setTlfnr(tlfnrField.getText());
	    	} else {
				// Telefonnummeret er tal, men ikke skrevet færdigt
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
			// tlfnr sættes da til null
			Kunde.kunde.setTlfnr(null);
    	}
    	// Telefonnummer maks længde = 8 cifre
    	if(tlfnrField.getLength() > 8) {
    		tlfnrField.setText(tlfnrField.getText().substring(0, tlfnrField.getText().length()-1));
		}
	}
	
	// Metode til at tjekke email
	public void tjekEmailMetode(TextField emailField, Label emailExclamationMark){
		// Valider Email
		if(emailField.getText().matches("^[a-zA-Z0123456789æøåÆØÅ@._-]*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$")) {
		    // Style på email-felterne ændres for at indikere, at værdien kan arbejdes med.
		    emailExclamationMark.setText("");
		    emailField.setId("correct");
		    // Gem emailen for kunden.
		    Kunde.kunde.setEmail(emailField.getText());
	    } else {
	    	// Emailen er endnu ikke skrevet færdigt.
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
		      // Style på afbetalingsperiode-felterne ændres for at indikere, at værdien kan arbejdes med.
		      afbetalingsperiodeExclamationMark.setText("");
		      afbetalingsperiodeField.setId("correct");
		      // Gem afbetalingsperioden for kunden.
		      Aftale.aftale.setAfbetalingsperiode(Integer.parseInt(afbetalingsperiodeField.getText()));
		      // Beregn nye værdier
		      forandring = true;
    	} else {
    		afbetalingsperiodeField.setId("incorrect");
    		afbetalingsperiodeExclamationMark.setId("incorrect");
    		afbetalingsperiodeExclamationMark.setText("!");
    		// Sæt afbetalingsperioden til 0 måneder, da perioden ellers ikke er gyldig
    		Aftale.aftale.setAfbetalingsperiode(0);
    		// Perioden er ikke gyldig. Vis ikke gamle værdi
		    forandring = true;
    	}
    	if(afbetalingsperiodeField.getText().isEmpty()) {
    		// Afbetalingsperioden er endnu ikke skrevet færdigt.
    		afbetalingsperiodeExclamationMark.setText("!");
    		afbetalingsperiodeField.setId("neutral");
    		afbetalingsperiodeExclamationMark.setId("neutral");
    		// Sæt afbetalingsperioden til 0 måneder, da perioden er tom
    		Aftale.aftale.setAfbetalingsperiode(0);
    		// Da perioden nu er tom skal gamle værdi ikke vises
		    forandring = true;
    	}
    	// Afbetalingsperiode maks 99 måneder
    	if(afbetalingsperiodeField.getLength() > 2) {
    		afbetalingsperiodeField.setText(afbetalingsperiodeField.getText().substring(0, afbetalingsperiodeField.getText().length()-1));
		}
	}
	
	// Metode til at tjekke udbetalingsbeløbet
	public void tjekUdbetalingsbeløbMetode(TextField udbetalingsbeløbField, Label udbetalingsbeløbExclamationMark) {
		if(udbetalingsbeløbField.getText().matches("[0123456789]+")) {
		      // Style på udbetalingsbeløb-felterne ændres for at indikere, at værdien kan arbejdes med.
		      udbetalingsbeløbExclamationMark.setText("");
		      udbetalingsbeløbField.setId("correct");
		      // Gem udbetalingsbeløb for kunden.
		      Aftale.aftale.setUdbetalingsbeløb(Double.parseDouble(udbetalingsbeløbField.getText()));
		      // Her må udbetalingsbeløbet være gyldigt. Skal beregnes
		      forandring = true;
		} else if(udbetalingsbeløbField.getText().isEmpty()){
			// Udbetalingsbeløbsfeltet er tomt.
			udbetalingsbeløbExclamationMark.setText("!");
			udbetalingsbeløbField.setId("neutral");
			Aftale.aftale.setUdbetalingsbeløb(0);
			// Nu hvor udbetalingsfeltet er tomt = 0 kr. nyt beløb (0 kr.) vises
		    forandring = true;
		} else {
			// Det indtastede udbetalingsbeløb indeholder fejl.
			udbetalingsbeløbExclamationMark.setText("!");
			udbetalingsbeløbExclamationMark.setId("incorrect");
			udbetalingsbeløbField.setId("incorrect");
			// Sæt udbetalingsbeløbet til 0 da udbetalingsbeløbet ikke er gyldigt
			Aftale.aftale.setUdbetalingsbeløb(0);
			// Hvis udbetalingsbeløbet ikke er gyldigt skal beregningen ikke vise ældre beregning
		    forandring = true;
		}
    	// Udbetalingsbeløb maksimalt 99.999.999
    	if(udbetalingsbeløbField.getLength() > 8) {
    		udbetalingsbeløbField.setText(udbetalingsbeløbField.getText().substring(0, udbetalingsbeløbField.getText().length()-1));
		}
	}
	
	// Metode til at tjekke den valgte bil
	public void tjekBilValgtMetode(ChoiceBox<String> bilChoiceBox, Label bilprisField, Label bilmodelExclamationMark, Label bilprisExclamationMark){
		for(int i = 0; i < Bil.bilList.size(); i++) {
			// Find objektet hvori navnet er lig med navnet sat i bilChoiceBox for at finde prisen på dette og sætte index
			if(bilChoiceBox.getValue() == Bil.bilList.get(i).getBil_model_navn()) {
				// Sæt aftale bil valgt
				Aftale.aftale.setBil_model(Bil.bilList.get(i).getBil_model_navn());
				// set prisen i bilprisField på den associerede bil
				bilprisField.setText(""+ (int) Bil.bilList.get(i).getBil_pris());
				// Set style på bilChoiceBox nu hvor bilen er valgt
				bilChoiceBox.setId("dropdown_correct");
				// Fjern udråbstegn nu hvor bil er valgt
				bilmodelExclamationMark.setText("");
				bilprisExclamationMark.setText("");
				// Sæt aktuelle bil
				Bil.aktuelBil = i;
				// Gem aktuelle bils pris
				Aftale.aftale.setBil_pris(Bil.bilList.get(i).getBil_pris());
				// Ny bil pris skal udregnes
				forandring = true;
			}
		}
	}
	
	
	// Metode til at udfylde sælger dropdown menuen
	public void fyldSælgerBoxMetode(ChoiceBox<String> sælgerChoiceBox, Label sælgerExclamationMark) {
		for(int i = 0; i < Sælger.sælgerList.size(); i++) {
			// Find objektet hvori navnet er lig med navnet sat i sælgerChoiceBox for at sætte index
			if(sælgerChoiceBox.getValue() == Sælger.sælgerList.get(i).getSælger_navn()) {
				// Sæt aftale sælger
				Aftale.aftale.setSælger(Sælger.sælgerList.get(i).getSælger_navn());
				// Set style på sælgerChoiceBox nu hvor sælgeren er valgt
				sælgerChoiceBox.setId("dropdown_correct");
				// Fjern udråbstegn nu hvor sælger er valgt
				sælgerExclamationMark.setText("");
				// sæt aktuelle sælger
				Sælger.aktuelSælger = i;
			}
		}
	}
	
	
	// kører 10 gange i sekundet for kontrol
	public void kontrolMetode(Button hentKundeInfoButton, Label månedligAfbetalingField, Label antalMånederField, Label fuldeAfbetalingsbeløbField, TextField CPRnrField, Label headerTekstLabel, Label ÅOPField, Label CPRnrExclamationMark, TextField fornavnField, TextField efternavnField, TextField adresseField, TextField postnrField, TextField tlfnrField, TextField emailField) {
		// Hvis der er foretaget en ændring
		if(forandring) {
			// Hvis kreditværdigheden ikke er i gang med at blive hentet, og cpr nummeret ikke er 0
	       	if(!Beregner.getHenterKreditværdighed() && Kunde.kunde.getCPR() != "0") {
	       		// Beregn ÅOP ... etc. hvis en værdi i formularen er ændret, eller hvis RKI har sat en ny værdi.
		       	Beregner.beregner.beregnAutoFeedback(Banksats.getRentesats(), Kreditværdighed.getKreditværdighed(), Aftale.aftale.getUdbetalingsbeløb(), Aftale.aftale.getBil_pris(), Aftale.aftale.getAfbetalingsperiode());
	       		// Opdater felterne i formularen (placeret nederst, eksisterer for at vise tilbuddet og oversigten over denne)
	       		månedligAfbetalingField.setText(Forkorter.Formater((Aftale.aftale.getMånedlig_afbetaling()),"kr") + " kr.");
	    		antalMånederField.setText(""+Aftale.aftale.getAfbetalingsperiode());
	    		fuldeAfbetalingsbeløbField.setText(Forkorter.Formater((Aftale.aftale.getFuldeAfbetalingsbeløb()),"kr") + " kr.");
	    		ÅOPField.setText(Forkorter.Formater((Aftale.aftale.getÅrlige_omkostninger_i_procent()), "") + " %");
	       	} else if(Kunde.kunde.getCPR().length() == 10){
	       		// Hvis CPR er ændret giver det ikke mening at beregne imens kreditværdigheden hentes
	       		månedligAfbetalingField.setText("Beregner...");
	    		antalMånederField.setText("Beregner...");
	    		fuldeAfbetalingsbeløbField.setText("Beregner...");
	    		ÅOPField.setText("Beregner...");
	       	}
	       	// Nu har beregneren beregnet færdigt
	       	forandring = false;
		}
       	// Hvis CPR nummeret er det CPR nummer der har kreditværdigheden på "D" og Beregneren er færdig med at hente kreditværdigheden.
       	if(CPRnrField.getText().equals(CPRisD) && !Beregner.getHenterKreditværdighed()) {
       		headerTekstLabel.setText("Kreditværdigheden " + Kunde.kunde.getKreditværdighed() + " er for lav!");
	   		CPRnrField.setId("incorrect");
	   		Kunde.kunde.setCPR(null);
       	}
       	// Hvis kreditværdigheden på CPR nummeret er "D" og Beregneren er færdig med at hente kreditværdigheden
	   	if(Kunde.kunde.getKreditværdighed() == "D" && !Beregner.getHenterKreditværdighed()) {
	   		// Her sættes CPRisD; CPR nummeret med kreditværdigheden på "D" gemmes. Skal bruges til ovenstående if sætning.
	   		CPRisD = CPRnrField.getText();
	   	}
	   	// Hvis kreditværdigheden er A, B eller C
	   	if(Kunde.kunde.getKreditværdighed().matches("A|B|C")) {
	   		// Da må kreditværdigheden være okay og vi behøver ikke advare ekspedienten.
	   		headerTekstLabel.setText("");
	   		CPRnrExclamationMark.setText("");
	   		// Hvis kreditværdigheden er 10 cifre langt, er et tal og Beregneren er færdig med at beregne kreditværdigheden.
	   		if(CPRnrField.getText().length() == 10 && CPRisTal && !Beregner.getHenterKreditværdighed()) {
	   			// Da må CPR nummeret være gyldigt. 
	   			CPRnrField.setId("correct");
	   			// Hvis det IKKE er samme CPR som før:
   				if(!CPRnrField.getText().equals(newCPR)) {
   	   				// Da må dette være nyt CPR
   	   				newCPR = Kunde.kunde.getCPR();
   	   				// Her må der da være en forandring der skal udregnes
   		   			forandring = true;
   		   			// Tjek om CPR eksisterer i Databasen
   			   		if(DataLayerInstantiater.tjekEksistererCPRIDB()) {
   			   			hentKundeInfoButton.setVisible(true);
   			   			// Undgå at overwrite en anden kundes information
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
	   	// Hvis sælgeren ændrer i CPR skal man ikke kunne hente info for tidligere CPR
	   	if(CPRnrField.getText().length() < 10) {
	   		hentKundeInfoButton.setVisible(false);
	   	}
	   	// Hvis beregneren er i gang med at beregne. For at undgå forkert returværdi fra kreditværdighedsberegningen.
	   	if(Beregner.getHenterKreditværdighed()) {
	   		CPRnrField.setEditable(false);
	   		CPRnrField.setStyle("-fx-background-color:rgb(235,235,235)");
	   	} else {
	   		CPRnrField.setEditable(true);
	   		CPRnrField.setStyle("-fx-background-color:transparent");
	   	}

	}
	
	// Hvis DataLayer har indhentet data fra databasen på kunden og sælgeren ønsker at indhente dette
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
	
	
	// Metode til at sætte parametre for Label
	public void sætParametreLabel(Label type, int height, int width, int x, int y) {
		type.setPrefSize(width, height);
		type.setTranslateX(x);
		type.setTranslateY(y);
	}
	
	
	
	// Metode til at sætte parametre for HBox
	public void sætParametreHBox(HBox type, int height, int x, int y) {
		type.setPrefHeight(height);
		type.setTranslateX(x);
		type.setTranslateY(y);
	}
	
	// Metode til at sætte parametre for Button
	public void sætParametreButton(Button type, int height, int width, int x, int y) {
		type.setPrefSize(width, height);
		type.setTranslateX(x);
		type.setTranslateY(y);
	}

	// Da extends Application
	@Override
	public void start(Stage Stage) throws Exception {
	}
}
