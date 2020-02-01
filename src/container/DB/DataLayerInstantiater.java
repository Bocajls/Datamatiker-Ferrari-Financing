package container.DB;

import java.util.ArrayList;

public class DataLayerInstantiater {
	
	private static DataLayer conn = new DataLayer();
	
	// Metode der køres direkte fra Main når programmet startes
	public static void startup() {
		// Forbind til database
		conn.openConnection();
		// Hent bil liste fra databasen
		conn.getBilListFraDB();
		// Hent sælger liste fra databasen
		conn.getSælgerListFraDB();
	}
	
	// Hent den valgte sælgers grænse fra databasen
	public static double getSælgerGrænseFraDB() {
		return(conn.getSælgerGrænseFraDB());
	}
	
	// Tjek om kunden eksisterer
	public static boolean tjekEksistererCPRIDB() {
		return conn.tjekEksistererCPRIDB();
	}
	
	// Hent information on kunde, så sælgeren kan anvende "Hent info" knappen.
	public ArrayList<Object> hentAltKundeInfoForCPR() {
		return conn.hentAltKundeInfoForCPR();
	}
	
	// Gem kunden i databasen. Ny eller eksisterende.
	public static void skrivKundeTilDB() {
		conn.skrivKundeTilDB();
	}
	
	// Returnering af 0 eller 1 baseret på, om kunden eksisterer i DB
	public static int tjekEksisterendeCPRProblemIDB() {
		return conn.tjekEksisterendeCPRProblemIDB();
	}
	
	// Til retunering af by baseret på postnr
	public static String getByFraDB(int postnummer) {
		return conn.getByFraDB(postnummer);
	}
	
	// Luk forbindelsen til databasen
	public static void closeConnection() {
		conn.closeConnection();
	}
}
