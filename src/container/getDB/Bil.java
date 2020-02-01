package container.getDB;

import java.util.ArrayList;
import java.util.List;

public class Bil {
	
	// ArrayListe til Bil objektere
	public static List<Bil> bilList = new ArrayList<Bil>();
	// Den aktuelle bils pris
	private double bil_pris;
	// Den aktuelle bils model navn
	private String bil_model_navn;
	// Den aktuelle valgte bils index
	public static int aktuelBil;

	// Getters og setters
	
	public double getBil_pris() {
		return bil_pris;
	}

	public String getBil_model_navn() {
		return bil_model_navn;
	}
	
	public void setBil_model_navn(String bil_model_navnGet) {
		bil_model_navn = bil_model_navnGet;
	}
	
	// Constructor
	public Bil(String bil_model_navnGet, double bil_prisGet) {
		this.bil_pris = bil_prisGet;
		this.bil_model_navn = bil_model_navnGet;
	}
}
