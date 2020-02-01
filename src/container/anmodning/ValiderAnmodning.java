package container.anmodning;

import container.savedata.Aftale;
import container.savedata.Kunde;
import container.GUI.AnmodningAfvist;
import container.DB.DataLayerInstantiater;
import container.GUI.AnmodningAccepteret;

public class ValiderAnmodning{
	
	public static String anmodningAfvistGrundlag;
	public static ValiderAnmodning anmodning;
	private static int accepteret = 0;
	private static int valideret = 0;
	
	
	// Metode til nyt Kunde objekt
	public static void initAnmodning() {
		anmodning = new ValiderAnmodning();
	}

	// Hvis anmodningen afvises lav en ny AnmodningAfvist med grundlaget for at afvise anmodningen
	private void AnmodningAfvist(String grundlag) {
    	new AnmodningAfvist(grundlag);
	}
	
	public void valider() {
		// Start valideringsprocessen
		kundeCPRDataValid();
	}
	
	// Valider angivede CPR nummeret
	private void kundeCPRDataValid() {
		if(Kunde.kunde.getCPR() != "0" && !Kunde.kunde.getKreditv�rdighed().matches("D|E")) {
			kundeFornavnDataValid();
		} else {
			AnmodningAfvist("Problemer med CPR nummer!");
		}
	}
	
	// Valider angivede fornavn
	private void kundeFornavnDataValid() {
		if(Kunde.kunde.getFornavn() != null) {
			kundeEfternavnDataValid();
		} else {
			AnmodningAfvist("Fornavn ikke korrekt udfyldt!");
		}
	}
	
	// Valider angivede efternavn
	private void kundeEfternavnDataValid() {
		if(Kunde.kunde.getEfternavn() != null) {
			kundeAdresseDataValid();
		} else {
			AnmodningAfvist("Efternavn ikke korrekt udfyldt!");
		}
	}
	
	// Valider angivede adresse
	private void kundeAdresseDataValid() {
		if(Kunde.kunde.getAdresse() != null) {
			kundePostnrDataValid();
		} else {
			AnmodningAfvist("Adresse ikke korrekt udfyldt!");
		}
	}

	// Validerangivede postnummer
	private void kundePostnrDataValid() {
		if(Kunde.kunde.getPostnr() != "0") {
			kundeByDataValid();
		} else {
			AnmodningAfvist("Post nummer ikke korrekt udfyldt!");
		}
	}
	
	// Valider angivede 'by'
	private void kundeByDataValid() {
		if(Kunde.kunde.getBy() != null) {
			kundeTlfnrDataValid();
		} else {
			AnmodningAfvist("Post nummer ikke korrekt udfyldt!");
		}
	}
	
	// Valider angivede telefonnummer
	private void kundeTlfnrDataValid() {
		if(Kunde.kunde.getTlfnr() != null) {
			kundeEmailDataValid();
		} else {
			AnmodningAfvist("Telefonnummer ikke korrekt udfyldt!");
		}
	}
	
	// Valider angivede email
	private void kundeEmailDataValid() {
		if(Kunde.kunde.getEmail() != null) {
				aftaleAfbetalingsperiodeDataValid();
			} else {
				AnmodningAfvist("Email ikke korrekt udfyldt!");
		}
	}
	
	// Valider angivede afbetalingsperiode
	private void aftaleAfbetalingsperiodeDataValid() {
		if(Aftale.aftale.getAfbetalingsperiode() != 0) {
			aftaleUdbetalingsbel�bDataValid();
		} else {
			AnmodningAfvist("Afbetalingsperiode ikke korrekt udfyldt!");
		}
	}
	
	// Valider angivede udbetalingsbel�b
	private void aftaleUdbetalingsbel�bDataValid() {
		if(Aftale.aftale.getUdbetalingsbel�b() >= 0) {
			if(Aftale.aftale.getUdbetalingsbel�b() <= Aftale.aftale.getBil_pris()) {
				aftaleBil_modelDataValid();
			} else {
				AnmodningAfvist("Udbetalingsbel�bet er h�jere end bilens pris!");
			}
				
		} else {
			AnmodningAfvist("Udbetalingsbel�b ikke korrekt udfyldt!");
		}
	}
	
	// Valider angivede bil model
	private void aftaleBil_modelDataValid() {
		if(Aftale.aftale.getBil_model() != null) {
			aftaleBil_prisDataValid();
		} else {
			AnmodningAfvist("Ingen bil valgt!");
		}
	}
	
	// Valider angivede bil pris
	private void aftaleBil_prisDataValid() {
		if(Aftale.aftale.getBil_pris() != 0) {
			aftaleS�lgerDataValid();
		} else {
			AnmodningAfvist("Bilens pris er for lav!");
		}
	}
	
	// Valider angivede s�lger
	private void aftaleS�lgerDataValid() {
		if(Aftale.aftale.getS�lger() != null) {
			tidligereProblemDataValid();
		} else {
			AnmodningAfvist("Ingen s�lger valgt!");
		}
	}
	
	// Valider, om der tidligere har v�ret problemer med kunde
	private void tidligereProblemDataValid() {
		if(DataLayerInstantiater.tjekEksisterendeCPRProblemIDB() == 0) {
			anmodningValideretF�rdigt();
		} else {
			AnmodningAfvist("Problem associeret med CPR!");
		}
	}
	
	
	// Anmodningen er accepteret hvis S�lger accepteres.
	private void anmodningValideretF�rdigt() {
		accepteret = 1;
		// Er afbetalingsbel�bet h�jere end s�lgerens fastsatte gr�nse skal den f�rst godkendes!
		if(DataLayerInstantiater.getS�lgerGr�nseFraDB() > Aftale.aftale.getAfbetalingsbel�b()) {
			valideret = 1;
			new AnmodningAccepteret("Anmodning godkendt!", "headerGodtTekst");
			DataLayerInstantiater.skrivKundeTilDB();
			System.out.println("Closing connection...");
			DataLayerInstantiater.closeConnection();
		} else {
			valideret = 0;
			new AnmodningAccepteret("Anmodning afventer svar fra en salgschef!", "headerTekst");
			DataLayerInstantiater.skrivKundeTilDB();
			System.out.println("Closing connection...");
			DataLayerInstantiater.closeConnection();
		}
		
	}

	public int getAccepteret() {
		return accepteret;
	}
	
	public int getValideret() {
		return valideret;
	}
}