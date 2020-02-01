package container.engine;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Forkorter {
	
	public static DecimalFormat talProcent;
	public static NumberFormat talValuta;
	
	public static String Formater(double v�rdiGet, String type) {
		// S�t v�rdiGet til dansk format
		talValuta = NumberFormat.getInstance(new Locale("da", "dk"));
		talValuta.setMaximumFractionDigits(2);
		
		// S�t maksimale antal af decimaler i Procent til nedenst�ende
		talProcent = new DecimalFormat("0.000000");
		
		// Formater tal baseret p� hvilken type
		if(type == "kr") {
			// Hvis typen er af dansk valuta
			return(talValuta.format(v�rdiGet));
		} else {
			// Ellers m� det (her) v�re procent
			return(talProcent.format(v�rdiGet));
		}
	}
}
