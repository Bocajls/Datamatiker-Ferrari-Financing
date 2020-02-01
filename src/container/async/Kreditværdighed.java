package container.async;

import com.ferrari.finances.dk.rki.CreditRator;

import container.savedata.Kunde;

public class Kreditv�rdighed {
	private static String kreditv�rdighed = "E";
	
	// Hent og gem kreditv�rdigheden fra CreditRator - RKI
	public static void updateKreditv�rdighed(String cprGet) {
		// tjek kreditv�rdigheden p� Kunde.getCPR metoden (som set i RunningThread klassen)
		kreditv�rdighed = (CreditRator.i().rate(cprGet)).toString();
		// S�t kundens kreditv�rdighed til den nye kreditv�rdighed
		Kunde.kunde.setKreditv�rdighed(kreditv�rdighed);
	}
	// Metode til at sp�rge m� kreditv�rdigheden p� kunden.
	public static String getKreditv�rdighed() {
		return kreditv�rdighed;
	}
}
