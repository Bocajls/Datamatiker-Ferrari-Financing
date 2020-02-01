package container.async;

import com.ferrari.finances.dk.rki.CreditRator;

import container.savedata.Kunde;

public class Kreditværdighed {
	private static String kreditværdighed = "E";
	
	// Hent og gem kreditværdigheden fra CreditRator - RKI
	public static void updateKreditværdighed(String cprGet) {
		// tjek kreditværdigheden på Kunde.getCPR metoden (som set i RunningThread klassen)
		kreditværdighed = (CreditRator.i().rate(cprGet)).toString();
		// Sæt kundens kreditværdighed til den nye kreditværdighed
		Kunde.kunde.setKreditværdighed(kreditværdighed);
	}
	// Metode til at spørge må kreditværdigheden på kunden.
	public static String getKreditværdighed() {
		return kreditværdighed;
	}
}
