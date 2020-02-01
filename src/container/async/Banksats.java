package container.async;

import com.ferrari.finances.dk.bank.InterestRate;

public class Banksats {
	private static double sats = 0;
	
	// Method til at sætte sats fra banken til senere brug.
	public static void setRentesats() {
		// Hent den nye bank sats for denne dag og gem satsen i sats
		sats = InterestRate.i().todaysRate();
	}
	// Method til at hente rentesats fra Banksats
	public static double getRentesats() {
		return sats;
	}
}
