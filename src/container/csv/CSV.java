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
	// Henter fornavn og efternavn p� kunden og gemmer filen som dette + ".csv"
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
		data_1D.add("S�lger:				" + Aftale.aftale.getS�lger() + ". ");
		data_1D.add("Afbetalingsperiode:		" + Aftale.aftale.getAfbetalingsperiode() + " m�neder. ");
		data_1D.add("Udbetalingsbel�b:		" + Forkorter.Formater(Aftale.aftale.getUdbetalingsbel�b(), "kr") + " kr.");
		data_1D.add("Bil model:				" + Aftale.aftale.getBil_model() + ". ");
		data_1D.add("�OP:					" + Forkorter.Formater(Aftale.aftale.get�rlige_omkostninger_i_procent(), "") + "%");
		data_1D.add("Bil pris:				" + Forkorter.Formater(Aftale.aftale.getBil_pris(), "kr") + " kr.");
		data_1D.add("Fulde afbetalingsbel�b:	" + Forkorter.Formater(Aftale.aftale.getFuldeAfbetalingsbel�b(), "kr") + " kr.");
		data_1D.add("M�nedlig afbetaling:		" + Forkorter.Formater(Aftale.aftale.getM�nedlig_afbetaling(), "kr") + " kr. \n");
		data_1D.add("					YYYY-MM-DD");
		data_1D.add("Dato for oprettelse:		" + Aftale.iDag);
		data_1D.add("Dato for f�rste betaling:	" + Aftale.aftale.getN�stBetaling());
		data_1D.add("Dato for sidste betaling:	" + Aftale.aftale.getSlutBetaling() + "\n");
		data_1D.add("YYYY-MM-DD");
		
		// Afbetalingsoversigt
		for(int i = 0; i <= Aftale.aftale.getAfbetalingsperiode(); i++) {
			data_1D.add(
				Aftale.aftale.getN�stBetaling().plusMonths(i).withDayOfMonth(1) + 
				"   Ydelse: " + 
				Forkorter.Formater(Aftale.aftale.getM�nedlig_afbetaling(), "kr") + 
				"   Resterende: " + 
				Forkorter.Formater(Aftale.aftale.getFuldeAfbetalingsbel�b()-(i*(Aftale.aftale.getM�nedlig_afbetaling())), "kr")
			);
		}
	}
		
	// Skriv .csv filen
	public static void CSVWriter() {
		// S�t elemetner der skal gemmes
		setCSV();
		try {
			BufferedWriter writeBuff = new BufferedWriter(new FileWriter(location));
			for (int i = 0; i < data_1D.size(); i++) {
				System.out.println(data_1D.get(i));
				writeBuff.write(data_1D.get(i) + seperator);
			}
			writeBuff.close();
			// I tilf�lde af, at s�lgeren klikker igen...
			data_1D.clear();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}