package container.engine;

import container.savedata.Aftale;

public class Beregner {
	// Beregner Objektet st�r for at opbevare f�lgende
	private static double sats;
	private static int kreditv�rdighedPctPoint;
	private static int udbetalingsbel�bPctPoint;
	private static int afbetalingsperiodePctPoint;
	private static double totalPctPoint;
	private static String kreditv�rdighedString;
	private static boolean henter;
	// Til dannelse af nyt Beregner Objekt
	public static Beregner beregner;
	
	// Kode til nyt Beregner objekt
	public static void initBeregner() {
		beregner = new Beregner();
	}

	// Beregn og kald de n�dvendige metoder herfra
	public void beregnAutoFeedback(double satsGet, String kreditv�rdighedStringGet, double udbetalingsbel�bGet, double bil_prisGet, int afbetalingsperiodeGet){
		
		// L�g ud med at finde bel�bet kunden skal betale, efter udbetalingsbel�bet er trukket fra bilens pris.
		beregnAfbetalingsbel�b(udbetalingsbel�bGet, bil_prisGet);
		
		// Beregn totale procent point
		beregnPctPoint(satsGet, kreditv�rdighedStringGet, udbetalingsbel�bGet, bil_prisGet, afbetalingsperiodeGet);
		
		// Gem totalPctPoint i �OP
		beregn�OP(sats, kreditv�rdighedPctPoint, udbetalingsbel�bPctPoint, afbetalingsperiodePctPoint);
		
		//Afbetalingsperioden er en positiv eller neutral v�rdi.
		if(Aftale.aftale.getAfbetalingsperiode()>=0) {
			//Bilens pris er en positiv.
			if(Aftale.aftale.getBil_pris()>=0) {
				// Hvis kreditv�rdigheden er A, B eller C.
				if(kreditv�rdighedPctPoint>=1) {
					// Hvis der n�es her til kan �OP etc. udregnes
					beregnAfbetalingsbel�b(totalPctPoint, Aftale.aftale.getAfbetalingsbel�b(), Aftale.aftale.getAfbetalingsperiode());
				}
			}
		}
	}
	
	public void beregnPctPoint(double satsGet, String kreditv�rdighedStringGet, double udbetalingsbel�bGet, double bil_prisGet, int afbetalingsperiodeGet) {
		// Hent satsen fra banken.
		sats = satsGet;
		
		// Hent kreditv�rdigheden fra kunde.
		kreditv�rdighedString = kreditv�rdighedStringGet;

		// Till�g procent point tilsvarende kundens kreditv�rdighed
		switch (kreditv�rdighedString) {
	        case "A":  kreditv�rdighedPctPoint = 1;
	        break;
	        case "B":  kreditv�rdighedPctPoint = 2;
	        break;
	        case "C":  kreditv�rdighedPctPoint = 3;
	        break;
	        // Hvis kundens kreditv�rdighed er for ringe
	        default:  kreditv�rdighedPctPoint = 0;
	        break;
		}

		// Hvis udbetalingsbel�bet er under 50% af bilens pris vil der till�gges �t procentpoint
		if(udbetalingsbel�bGet*2 < bil_prisGet) {
			udbetalingsbel�bPctPoint = 1;
		} else {
			udbetalingsbel�bPctPoint = 0;
		}
		
		// Hvis afbetalingsperioden er over 36 m�neder vil det till�ggegs �t procentpoint
		if(afbetalingsperiodeGet > 36) {
			afbetalingsperiodePctPoint = 1;
		} else {
			afbetalingsperiodePctPoint = 0;
		}
	}

	// Beregn �rlige omkostning i procent
	public void beregn�OP(double satsGet, int kreditv�rdighedPctPointGet, int udbetalingsbel�bPctPointGet, int afbetalingsperiodePctPointGet) {
		totalPctPoint = satsGet + kreditv�rdighedPctPointGet + udbetalingsbel�bPctPointGet + afbetalingsperiodePctPointGet;
		Aftale.aftale.set�rlige_omkostninger_i_procent(totalPctPoint);
	}
	
	// Beregn afbetalingsbel�bet
	public double beregnAfbetalingsbel�b(double udbetalingsbel�bGet, double bil_prisGet) {
		// Hvis udbetalingsbel�bet er h�jere end bilens pris givet det ikke rigtig mening...
		if(udbetalingsbel�bGet<bil_prisGet) {
			Aftale.aftale.setAfbetalingsbel�b(bil_prisGet - udbetalingsbel�bGet);
			return bil_prisGet - udbetalingsbel�bGet;
		}
		else {
			Aftale.aftale.setAfbetalingsbel�b(0);
			return 0;
		}
	}
	
	// Beregn afbetalingsbel�b - Fulde og m�nedlige
	public double beregnAfbetalingsbel�b(double totalPctPointGet, double afbetalingsbel�bGet, double afbetalingsperiodeGet) {
		// Afbetalingsbel�bet skal v�re mere end 0 kr., og perioden skal v�re mere end nul m�neder.
		if(afbetalingsbel�bGet > 0 && afbetalingsperiodeGet > 0) {
			double �OP = ( 1.0 + ( totalPctPointGet / 100.0 ) );
			double r = ( Math.pow ( �OP , ( 1.0 / 12.0 ) ) ) - 1.0;
			double double_m�nedligYdelse = ( ( r ) / ( 1 - ( Math.pow ( ( 1 + r ) , - afbetalingsperiodeGet ) ) ) ) * afbetalingsbel�bGet;
			Aftale.aftale.setM�nedlig_afbetaling(double_m�nedligYdelse);
			double double_fuldeAfbetalingsbel�b = ( double_m�nedligYdelse * afbetalingsperiodeGet );
			Aftale.aftale.setFuldeAfbetalingsbel�b(double_fuldeAfbetalingsbel�b);
			return double_fuldeAfbetalingsbel�b;
		}
		else {
			Aftale.aftale.setFuldeAfbetalingsbel�b(0);
			return 0;
		}
	}

	// Metode til at hente status, for at se om kreditv�rdighed er i f�rd med at blive hentet
	public static boolean getHenterKreditv�rdighed() {
		return henter;
	}

	// Metode til at s�tte status for, om kreditv�rdigheden er i f�rd med at blive hentet
	public static void setHenterKreditv�rdighed(boolean henterKreditv�rdighed) {
		henter = henterKreditv�rdighed;
	}
	
	// Hent v�rdier
	
	public double getSats() {
		return sats;
	}
	
	public double getKreditv�rdighedPctPoint() {
		return kreditv�rdighedPctPoint;
	}
	
	public double getUdbetalingsbel�bPctPoint() {
		return udbetalingsbel�bPctPoint;
	}
	
	public double getAfbetalingsperiodePctPoint() {
		return afbetalingsperiodePctPoint;
	}
	
	public double getTotalPctPoint() {
		return totalPctPoint;
	}
}