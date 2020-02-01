package container.savedata;

import java.time.LocalDate;

public class Aftale {
	// Aftale Objektet st�r for at opbevare f�lgende
	private int afbetalingsperiode = 0;
	private double udbetalingsbel�b = 0;
	private double afbetalingsbel�b = 0;
	private double fuldeAfbetalingsbel�b = 0;
	private double m�nedlig_afbetaling = 0;
	private double �rlige_omkostninger_i_procent = 0;
	public String bil_model;
	private double bil_pris = 0;
	public String s�lger;

	// Dato for aftale
	public static LocalDate iDag = LocalDate.now();
	public LocalDate nM�nede;
	public LocalDate slutM�ned;
	
	// Til dannelse af nyt Aftale objekt
	public static Aftale aftale;
	
	// Metode til nyt Aftale objekt
	public static void initAftale() {
		aftale = new Aftale();
	}
	
	// S�t n�ste betalingsdato
	public LocalDate getN�stBetaling() {
		if(iDag.getDayOfMonth() > 10) {
			nM�nede = iDag.plusMonths(2).withDayOfMonth(1);
			return nM�nede;
		} else {
			nM�nede = iDag.plusMonths(1).withDayOfMonth(1);
			return nM�nede;
		}
	}
	
	// S�t slut betalingsdato
	public LocalDate getSlutBetaling() {
		if(iDag.getDayOfMonth() > 10) {
			slutM�ned = iDag.plusMonths(afbetalingsperiode+2).withDayOfMonth(1);
			return slutM�ned;
		} else {
			slutM�ned = iDag.plusMonths(afbetalingsperiode+1).withDayOfMonth(1);
			return slutM�ned;
		}
	}
	
	// Getters og setters
	
	public void setAfbetalingsperiode(int afbetalingsperiodeGet) {
		afbetalingsperiode = afbetalingsperiodeGet;
	}
	
	public int getAfbetalingsperiode() {
		return afbetalingsperiode;
	}
	
	public void setUdbetalingsbel�b(double udbetalingsbel�bGet) {
		udbetalingsbel�b = udbetalingsbel�bGet;
	}
	
	public  double getUdbetalingsbel�b() {
		return udbetalingsbel�b;
	}
	
	public void setAfbetalingsbel�b(double afbetalingsbel�bGet) {
		afbetalingsbel�b = afbetalingsbel�bGet;
	}
	
	public double getAfbetalingsbel�b() {
		return afbetalingsbel�b;
	}
	
	public void setFuldeAfbetalingsbel�b(double fuldeAfbetalingsBel�bGet) {
		fuldeAfbetalingsbel�b = fuldeAfbetalingsBel�bGet;
	}
	
	public double getFuldeAfbetalingsbel�b() {
		return fuldeAfbetalingsbel�b;
	}
	
	public void setM�nedlig_afbetaling(double m�nedlig_afbetalingGet) {
		m�nedlig_afbetaling = m�nedlig_afbetalingGet;
	}
	
	public double getM�nedlig_afbetaling() {
		return m�nedlig_afbetaling;
	}
	
	public void set�rlige_omkostninger_i_procent(double �rlige_omkostninger_i_procentGet) {
		�rlige_omkostninger_i_procent = �rlige_omkostninger_i_procentGet;
	}
	
	public double get�rlige_omkostninger_i_procent() {
		return �rlige_omkostninger_i_procent;
	}
	
	public void setBil_model(String bil_modelGet) {
		bil_model = bil_modelGet;
	}
	
	public String getBil_model() {
		return bil_model;
	}
	
	public void setBil_pris(double bil_prisGet) {
		bil_pris = bil_prisGet;
	}
	
	public double getBil_pris() {
		return bil_pris;
	}
	
	public void setS�lger(String s�lgerGet) {
		s�lger = s�lgerGet;
	}
	
	public String getS�lger() {
		return s�lger;
	}
}
