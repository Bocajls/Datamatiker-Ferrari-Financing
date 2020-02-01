package container.savedata;

import java.time.LocalDate;

public class Aftale {
	// Aftale Objektet står for at opbevare følgende
	private int afbetalingsperiode = 0;
	private double udbetalingsbeløb = 0;
	private double afbetalingsbeløb = 0;
	private double fuldeAfbetalingsbeløb = 0;
	private double månedlig_afbetaling = 0;
	private double årlige_omkostninger_i_procent = 0;
	public String bil_model;
	private double bil_pris = 0;
	public String sælger;

	// Dato for aftale
	public static LocalDate iDag = LocalDate.now();
	public LocalDate nMånede;
	public LocalDate slutMåned;
	
	// Til dannelse af nyt Aftale objekt
	public static Aftale aftale;
	
	// Metode til nyt Aftale objekt
	public static void initAftale() {
		aftale = new Aftale();
	}
	
	// Sæt næste betalingsdato
	public LocalDate getNæstBetaling() {
		if(iDag.getDayOfMonth() > 10) {
			nMånede = iDag.plusMonths(2).withDayOfMonth(1);
			return nMånede;
		} else {
			nMånede = iDag.plusMonths(1).withDayOfMonth(1);
			return nMånede;
		}
	}
	
	// Sæt slut betalingsdato
	public LocalDate getSlutBetaling() {
		if(iDag.getDayOfMonth() > 10) {
			slutMåned = iDag.plusMonths(afbetalingsperiode+2).withDayOfMonth(1);
			return slutMåned;
		} else {
			slutMåned = iDag.plusMonths(afbetalingsperiode+1).withDayOfMonth(1);
			return slutMåned;
		}
	}
	
	// Getters og setters
	
	public void setAfbetalingsperiode(int afbetalingsperiodeGet) {
		afbetalingsperiode = afbetalingsperiodeGet;
	}
	
	public int getAfbetalingsperiode() {
		return afbetalingsperiode;
	}
	
	public void setUdbetalingsbeløb(double udbetalingsbeløbGet) {
		udbetalingsbeløb = udbetalingsbeløbGet;
	}
	
	public  double getUdbetalingsbeløb() {
		return udbetalingsbeløb;
	}
	
	public void setAfbetalingsbeløb(double afbetalingsbeløbGet) {
		afbetalingsbeløb = afbetalingsbeløbGet;
	}
	
	public double getAfbetalingsbeløb() {
		return afbetalingsbeløb;
	}
	
	public void setFuldeAfbetalingsbeløb(double fuldeAfbetalingsBeløbGet) {
		fuldeAfbetalingsbeløb = fuldeAfbetalingsBeløbGet;
	}
	
	public double getFuldeAfbetalingsbeløb() {
		return fuldeAfbetalingsbeløb;
	}
	
	public void setMånedlig_afbetaling(double månedlig_afbetalingGet) {
		månedlig_afbetaling = månedlig_afbetalingGet;
	}
	
	public double getMånedlig_afbetaling() {
		return månedlig_afbetaling;
	}
	
	public void setÅrlige_omkostninger_i_procent(double årlige_omkostninger_i_procentGet) {
		årlige_omkostninger_i_procent = årlige_omkostninger_i_procentGet;
	}
	
	public double getÅrlige_omkostninger_i_procent() {
		return årlige_omkostninger_i_procent;
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
	
	public void setSælger(String sælgerGet) {
		sælger = sælgerGet;
	}
	
	public String getSælger() {
		return sælger;
	}
}
