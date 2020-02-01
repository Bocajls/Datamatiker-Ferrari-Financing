package container.getDB;

import java.util.ArrayList;
import java.util.List;

public class Sælger {
	
	// ArrayListe til Sælger objektere
	public static List<Sælger> sælgerList = new ArrayList<Sælger>();
	// Den aktuelle sælgers navn
	private String sælger_navn;
	// Den aktuelle sælgers grænse før anmodningen skal accepteres af salgschef
	private static double sælger_grænse;
	// Den aktuelle valgte sælgers index
	public static int aktuelSælger;

	// Getters og setters
	
	public String getSælger_navn() {
		return sælger_navn;
	}
	
	public void setSælger_navn(String sælger_navnGet) {
		sælger_navn = sælger_navnGet;
	}
	
	public static void setSælgerGrænse(double grænseGet) {
		sælger_grænse = grænseGet;
	}
	
	public static double getSælgerGrænse() {
		return sælger_grænse;
	}
	
	// Constructor
	public Sælger(String sælger_navnGet) {
		this.sælger_navn = sælger_navnGet;
	}
	
}
