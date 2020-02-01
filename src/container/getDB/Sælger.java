package container.getDB;

import java.util.ArrayList;
import java.util.List;

public class S�lger {
	
	// ArrayListe til S�lger objektere
	public static List<S�lger> s�lgerList = new ArrayList<S�lger>();
	// Den aktuelle s�lgers navn
	private String s�lger_navn;
	// Den aktuelle s�lgers gr�nse f�r anmodningen skal accepteres af salgschef
	private static double s�lger_gr�nse;
	// Den aktuelle valgte s�lgers index
	public static int aktuelS�lger;

	// Getters og setters
	
	public String getS�lger_navn() {
		return s�lger_navn;
	}
	
	public void setS�lger_navn(String s�lger_navnGet) {
		s�lger_navn = s�lger_navnGet;
	}
	
	public static void setS�lgerGr�nse(double gr�nseGet) {
		s�lger_gr�nse = gr�nseGet;
	}
	
	public static double getS�lgerGr�nse() {
		return s�lger_gr�nse;
	}
	
	// Constructor
	public S�lger(String s�lger_navnGet) {
		this.s�lger_navn = s�lger_navnGet;
	}
	
}
