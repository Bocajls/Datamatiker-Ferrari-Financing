package container.engine;

import container.savedata.Aftale;

public class Beregner {
	// Beregner Objektet står for at opbevare følgende
	private static double sats;
	private static int kreditværdighedPctPoint;
	private static int udbetalingsbeløbPctPoint;
	private static int afbetalingsperiodePctPoint;
	private static double totalPctPoint;
	private static String kreditværdighedString;
	private static boolean henter;
	// Til dannelse af nyt Beregner Objekt
	public static Beregner beregner;
	
	// Kode til nyt Beregner objekt
	public static void initBeregner() {
		beregner = new Beregner();
	}

	// Beregn og kald de nødvendige metoder herfra
	public void beregnAutoFeedback(double satsGet, String kreditværdighedStringGet, double udbetalingsbeløbGet, double bil_prisGet, int afbetalingsperiodeGet){
		
		// Læg ud med at finde beløbet kunden skal betale, efter udbetalingsbeløbet er trukket fra bilens pris.
		beregnAfbetalingsbeløb(udbetalingsbeløbGet, bil_prisGet);
		
		// Beregn totale procent point
		beregnPctPoint(satsGet, kreditværdighedStringGet, udbetalingsbeløbGet, bil_prisGet, afbetalingsperiodeGet);
		
		// Gem totalPctPoint i ÅOP
		beregnÅOP(sats, kreditværdighedPctPoint, udbetalingsbeløbPctPoint, afbetalingsperiodePctPoint);
		
		//Afbetalingsperioden er en positiv eller neutral værdi.
		if(Aftale.aftale.getAfbetalingsperiode()>=0) {
			//Bilens pris er en positiv.
			if(Aftale.aftale.getBil_pris()>=0) {
				// Hvis kreditværdigheden er A, B eller C.
				if(kreditværdighedPctPoint>=1) {
					// Hvis der nåes her til kan ÅOP etc. udregnes
					beregnAfbetalingsbeløb(totalPctPoint, Aftale.aftale.getAfbetalingsbeløb(), Aftale.aftale.getAfbetalingsperiode());
				}
			}
		}
	}
	
	public void beregnPctPoint(double satsGet, String kreditværdighedStringGet, double udbetalingsbeløbGet, double bil_prisGet, int afbetalingsperiodeGet) {
		// Hent satsen fra banken.
		sats = satsGet;
		
		// Hent kreditværdigheden fra kunde.
		kreditværdighedString = kreditværdighedStringGet;

		// Tillæg procent point tilsvarende kundens kreditværdighed
		switch (kreditværdighedString) {
	        case "A":  kreditværdighedPctPoint = 1;
	        break;
	        case "B":  kreditværdighedPctPoint = 2;
	        break;
	        case "C":  kreditværdighedPctPoint = 3;
	        break;
	        // Hvis kundens kreditværdighed er for ringe
	        default:  kreditværdighedPctPoint = 0;
	        break;
		}

		// Hvis udbetalingsbeløbet er under 50% af bilens pris vil der tillægges ét procentpoint
		if(udbetalingsbeløbGet*2 < bil_prisGet) {
			udbetalingsbeløbPctPoint = 1;
		} else {
			udbetalingsbeløbPctPoint = 0;
		}
		
		// Hvis afbetalingsperioden er over 36 måneder vil det tillæggegs ét procentpoint
		if(afbetalingsperiodeGet > 36) {
			afbetalingsperiodePctPoint = 1;
		} else {
			afbetalingsperiodePctPoint = 0;
		}
	}

	// Beregn årlige omkostning i procent
	public void beregnÅOP(double satsGet, int kreditværdighedPctPointGet, int udbetalingsbeløbPctPointGet, int afbetalingsperiodePctPointGet) {
		totalPctPoint = satsGet + kreditværdighedPctPointGet + udbetalingsbeløbPctPointGet + afbetalingsperiodePctPointGet;
		Aftale.aftale.setÅrlige_omkostninger_i_procent(totalPctPoint);
	}
	
	// Beregn afbetalingsbeløbet
	public double beregnAfbetalingsbeløb(double udbetalingsbeløbGet, double bil_prisGet) {
		// Hvis udbetalingsbeløbet er højere end bilens pris givet det ikke rigtig mening...
		if(udbetalingsbeløbGet<bil_prisGet) {
			Aftale.aftale.setAfbetalingsbeløb(bil_prisGet - udbetalingsbeløbGet);
			return bil_prisGet - udbetalingsbeløbGet;
		}
		else {
			Aftale.aftale.setAfbetalingsbeløb(0);
			return 0;
		}
	}
	
	// Beregn afbetalingsbeløb - Fulde og månedlige
	public double beregnAfbetalingsbeløb(double totalPctPointGet, double afbetalingsbeløbGet, double afbetalingsperiodeGet) {
		// Afbetalingsbeløbet skal være mere end 0 kr., og perioden skal være mere end nul måneder.
		if(afbetalingsbeløbGet > 0 && afbetalingsperiodeGet > 0) {
			double ÅOP = ( 1.0 + ( totalPctPointGet / 100.0 ) );
			double r = ( Math.pow ( ÅOP , ( 1.0 / 12.0 ) ) ) - 1.0;
			double double_månedligYdelse = ( ( r ) / ( 1 - ( Math.pow ( ( 1 + r ) , - afbetalingsperiodeGet ) ) ) ) * afbetalingsbeløbGet;
			Aftale.aftale.setMånedlig_afbetaling(double_månedligYdelse);
			double double_fuldeAfbetalingsbeløb = ( double_månedligYdelse * afbetalingsperiodeGet );
			Aftale.aftale.setFuldeAfbetalingsbeløb(double_fuldeAfbetalingsbeløb);
			return double_fuldeAfbetalingsbeløb;
		}
		else {
			Aftale.aftale.setFuldeAfbetalingsbeløb(0);
			return 0;
		}
	}

	// Metode til at hente status, for at se om kreditværdighed er i færd med at blive hentet
	public static boolean getHenterKreditværdighed() {
		return henter;
	}

	// Metode til at sætte status for, om kreditværdigheden er i færd med at blive hentet
	public static void setHenterKreditværdighed(boolean henterKreditværdighed) {
		henter = henterKreditværdighed;
	}
	
	// Hent værdier
	
	public double getSats() {
		return sats;
	}
	
	public double getKreditværdighedPctPoint() {
		return kreditværdighedPctPoint;
	}
	
	public double getUdbetalingsbeløbPctPoint() {
		return udbetalingsbeløbPctPoint;
	}
	
	public double getAfbetalingsperiodePctPoint() {
		return afbetalingsperiodePctPoint;
	}
	
	public double getTotalPctPoint() {
		return totalPctPoint;
	}
}