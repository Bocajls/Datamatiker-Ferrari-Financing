package container.DB;

import java.util.ArrayList;

public class DataLayerInstantiater {
	
	private static DataLayer conn = new DataLayer();
	
	// Metode der k�res direkte fra Main n�r programmet startes
	public static void startup() {
		// Forbind til database
		conn.openConnection();
		// Hent bil liste fra databasen
		conn.getBilListFraDB();
		// Hent s�lger liste fra databasen
		conn.getS�lgerListFraDB();
	}
	
	// Hent den valgte s�lgers gr�nse fra databasen
	public static double getS�lgerGr�nseFraDB() {
		return(conn.getS�lgerGr�nseFraDB());
	}
	
	// Tjek om kunden eksisterer
	public static boolean tjekEksistererCPRIDB() {
		return conn.tjekEksistererCPRIDB();
	}
	
	// Hent information on kunde, s� s�lgeren kan anvende "Hent info" knappen.
	public ArrayList<Object> hentAltKundeInfoForCPR() {
		return conn.hentAltKundeInfoForCPR();
	}
	
	// Gem kunden i databasen. Ny eller eksisterende.
	public static void skrivKundeTilDB() {
		conn.skrivKundeTilDB();
	}
	
	// Returnering af 0 eller 1 baseret p�, om kunden eksisterer i DB
	public static int tjekEksisterendeCPRProblemIDB() {
		return conn.tjekEksisterendeCPRProblemIDB();
	}
	
	// Til retunering af by baseret p� postnr
	public static String getByFraDB(int postnummer) {
		return conn.getByFraDB(postnummer);
	}
	
	// Luk forbindelsen til databasen
	public static void closeConnection() {
		conn.closeConnection();
	}
}
