package container.engine;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Forkorter {
	
	public static DecimalFormat talProcent;
	public static NumberFormat talValuta;
	
	public static String Formater(double værdiGet, String type) {
		// Sæt værdiGet til dansk format
		talValuta = NumberFormat.getInstance(new Locale("da", "dk"));
		talValuta.setMaximumFractionDigits(2);
		
		// Sæt maksimale antal af decimaler i Procent til nedenstående
		talProcent = new DecimalFormat("0.000000");
		
		// Formater tal baseret på hvilken type
		if(type == "kr") {
			// Hvis typen er af dansk valuta
			return(talValuta.format(værdiGet));
		} else {
			// Ellers må det (her) være procent
			return(talProcent.format(værdiGet));
		}
	}
}
