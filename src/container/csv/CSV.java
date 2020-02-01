package container.csv;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import container.engine.Forkorter;
import container.savedata.Aftale;
import container.savedata.Kunde;

public class CSV {
	
	// Bruges til at lave new lines
	private static String seperator = "\n";
	// Henter fornavn og efternavn på kunden og gemmer filen som dette + ".csv"
	private static String location = Kunde.kunde.getFornavn()+"_"+ Kunde.kunde.getEfternavn()+".csv";
	// Et array til strings der skal gemmes i .csv filen
	private static ArrayList<String> data_1D = new ArrayList<String>();
	// Hvad CSV filen skal indeholde
	public static void setCSV() {
		data_1D.add("Navn:		" + Kunde.kunde.getFornavn() + " " + Kunde.kunde.getEfternavn() + ". ");
		data_1D.add("Adresse:	" + Kunde.kunde.getAdresse() + ". ");
		data_1D.add("Postnr:	" + Kunde.kunde.getPostnr() + ". ");
		data_1D.add("Tlfnr:	" + Kunde.kunde.getTlfnr() + ". ");
		data_1D.add("Email:	" + Kunde.kunde.getEmail() + ". ");
		data_1D.add("By:		" + Kunde.kunde.getBy() + ". \n");
		data_1D.add("Sælger:				" + Aftale.aftale.getSælger() + ". ");
		data_1D.add("Afbetalingsperiode:		" + Aftale.aftale.getAfbetalingsperiode() + " måneder. ");
		data_1D.add("Udbetalingsbeløb:		" + Forkorter.Formater(Aftale.aftale.getUdbetalingsbeløb(), "kr") + " kr.");
		data_1D.add("Bil model:				" + Aftale.aftale.getBil_model() + ". ");
		data_1D.add("ÅOP:					" + Forkorter.Formater(Aftale.aftale.getÅrlige_omkostninger_i_procent(), "") + "%");
		data_1D.add("Bil pris:				" + Forkorter.Formater(Aftale.aftale.getBil_pris(), "kr") + " kr.");
		data_1D.add("Fulde afbetalingsbeløb:	" + Forkorter.Formater(Aftale.aftale.getFuldeAfbetalingsbeløb(), "kr") + " kr.");
		data_1D.add("Månedlig afbetaling:		" + Forkorter.Formater(Aftale.aftale.getMånedlig_afbetaling(), "kr") + " kr. \n");
		data_1D.add("					YYYY-MM-DD");
		data_1D.add("Dato for oprettelse:		" + Aftale.iDag);
		data_1D.add("Dato for første betaling:	" + Aftale.aftale.getNæstBetaling());
		data_1D.add("Dato for sidste betaling:	" + Aftale.aftale.getSlutBetaling() + "\n");
		data_1D.add("YYYY-MM-DD");
		
		// Afbetalingsoversigt
		for(int i = 0; i <= Aftale.aftale.getAfbetalingsperiode(); i++) {
			data_1D.add(
				Aftale.aftale.getNæstBetaling().plusMonths(i).withDayOfMonth(1) + 
				"   Ydelse: " + 
				Forkorter.Formater(Aftale.aftale.getMånedlig_afbetaling(), "kr") + 
				"   Resterende: " + 
				Forkorter.Formater(Aftale.aftale.getFuldeAfbetalingsbeløb()-(i*(Aftale.aftale.getMånedlig_afbetaling())), "kr")
			);
		}
	}
		
	// Skriv .csv filen
	public static void CSVWriter() {
		// Sæt elemetner der skal gemmes
		setCSV();
		try {
			BufferedWriter writeBuff = new BufferedWriter(new FileWriter(location));
			for (int i = 0; i < data_1D.size(); i++) {
				System.out.println(data_1D.get(i));
				writeBuff.write(data_1D.get(i) + seperator);
			}
			writeBuff.close();
			// I tilfælde af, at sælgeren klikker igen...
			data_1D.clear();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}