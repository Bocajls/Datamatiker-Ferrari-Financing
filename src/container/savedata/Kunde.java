package container.savedata;

public class Kunde {
	// Kunde Objektet står for at opbevare følgende
	private String cpr = "0";
	private String fornavn;
	private String efternavn;
	private String adresse;
	private String postnr = "0";
	private String by;
	private String tlfnr = "0";
	private String email = "0";
	private String kreditværdighed = "E";
	// Til dannelse af nyt Kunde Objekt
	public static Kunde kunde;

	// Metode til nyt Kunde objekt
	public static void initKunde() {
		kunde = new Kunde();
	}
	
	// Getters og setters
	
	public void setCPR(String cprGet) {
		cpr = cprGet;
	}
	
	public String getCPR() {
		return cpr;
	}
	
	public void setFornavn(String fornavnGet) {
		fornavn = fornavnGet;
	}
	
	public String getFornavn() {
		return fornavn;
	}
	
	public void setEfternavn(String efternavnGet) {
		efternavn = efternavnGet;
	}
	
	public String getEfternavn() {
		return efternavn;
	}
	
	public void setAdresse(String adresseGet) {
		adresse = adresseGet;
	}
	
	public String getAdresse() {
		return adresse;
	}
	
	public void setPostnr(String postnrGet) {
		postnr = postnrGet;
	}
	
	public String getPostnr() {
		return postnr;
	}
	
	public void setBy(String byGet) {
		by = byGet;
	}
	
	public String getBy() {
		return by;
	}
	
	public  void setTlfnr(String tlfnrGet) {
		tlfnr = tlfnrGet;
	}
	
	public String getTlfnr() {
		return tlfnr;
	}
	
	public void setEmail(String emailGet) {
		email = emailGet;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setKreditværdighed(String kreditværdighedGet) {
		kreditværdighed = kreditværdighedGet;
	}
	
	public String getKreditværdighed() {
		return kreditværdighed;
	}
	
}
