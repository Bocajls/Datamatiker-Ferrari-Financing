package container.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import container.getDB.Sælger;
import container.savedata.Aftale;
import container.savedata.Kunde;
import container.anmodning.ValiderAnmodning;
import container.getDB.Bil;

public class DataLayer {
	private Connection connection;
	
	
	// Metode til at hente højeste tabel_id fra tabel i databasen. Meget vigtig metode.
	private static ArrayList<Integer> arrayMedHøjesteIDList = new ArrayList<Integer>();
	private static int eksisterendeBrugerID = 0;
	private static int eksisterendeBrugerAdresseID = 0;
	private static int eksisterendeBrugerKunde_kontaktID = 0;
	
	private PreparedStatement statement;
	private ResultSet resultSet;

	// Prøv at forbinde til databasen (printlines erstatter heri kommentarer)
	public void openConnection() {
		try {
			System.out.print("Loading JDBC Driver... ");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("JDBC Driver loaded!");
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load JDBC Driver!");
			System.exit(1);
		}

		String databaseName = "kredsloeb_dk0_db2";
		String dbName = "kredsloeb_dk0";
		String dbPass = "12357525";
		String connectionString = "jdbc:mysql://mysql69.unoeuro.com:3306/" + databaseName + "?autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8&verifyServerCertificate=false&useSSL=true";

		try {
			System.out.print("Connecting to database... ");
			connection = DriverManager.getConnection(connectionString, dbName, dbPass);
			if (connection != null)
				System.out.println("Connected to database");
			else
				System.out.println("Could not connect to database");
		} catch (SQLException e) {
			System.out.println("Failed to connect to database");
			System.exit(1);
		}
	}
	

	// Hent bil elementerne fra databasen. Gem dem som en ny Bil og tilføj dem i ArrayList<Bil>
	public void getBilListFraDB() {
		try {
			statement = connection.prepareStatement("SELECT * FROM Bil");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Bil.bilList.add(new Bil(resultSet.getString(3), resultSet.getDouble(2)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke hente biler fra databasen");
			System.exit(1);
		}
	}
	
	// Hent sælger elementerne fra databasen. Gem dem som en ny Sælger og tilføj dem i ArrayList<Sælger>
	public void getSælgerListFraDB() {
		try {
			statement = connection.prepareStatement("SELECT * FROM Sælger");
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Sælger.sælgerList.add(new Sælger(resultSet.getString(4)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke hente sælgere fra databasen");
			System.exit(1);
		}
	}
	
	// Hent by ud fra postnummer
	public String getByFraDB(int postnummer) {
		try {
			statement = connection.prepareStatement("SELECT * FROM Postcode WHERE postnummer=?");
			statement.setString(1, ""+postnummer);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString(2);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke hente by fra databasen");
			return "ERROR";
		}
		return null;
	}
	
	// Hent ID på den valgte sælger i formularen
	public int getSælgerIDFraDB() {
		try {
			statement = connection.prepareStatement("SELECT sælger_id FROM Sælger WHERE sælger_navn = '" + Sælger.sælgerList.get(Sælger.aktuelSælger).getSælger_navn()+ "'");
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke hente nuværende sælgers ID fra databasen");
			System.exit(1);
		}
		return 0;
	}
	
	// Hent den valgte sælgers maksimale grænse for afbetalingsbeløb
	public double getSælgerGrænseFraDB() {
		try {
			statement = connection.prepareStatement("SELECT * FROM Grænse WHERE grænse_id = " + getSælgerIDFraDB() +"");
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Sælger.setSælgerGrænse(resultSet.getDouble(2));
				return(resultSet.getDouble(2));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke hente nuværende sælgers grænse");
			System.exit(1);
		}
		return 0;
	}
	
	// Hent ID på den valgte bil i formularen
	public int getBilIDFraDB() {
		try {
			statement = connection.prepareStatement("SELECT bil_id FROM Bil WHERE bil_model_navn = '" + Bil.bilList.get(Bil.aktuelBil).getBil_model_navn()+ "'");
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke hente nuværende bil ID fra databasen");
			System.exit(1);
		}
		return 0;
	}
	
	
	// Hent den valgte sælgers maksimale grænse for afbetalingsbeløb
	public boolean tjekEksistererCPRIDB() {
		try {
			statement = connection.prepareStatement("SELECT * FROM Kunde WHERE kunde_CPR = '" + Kunde.kunde.getCPR() +"'");
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				System.out.println("Kunde findes allerede i databasen... ");
				eksisterendeBrugerID = resultSet.getInt(1);
				eksisterendeBrugerAdresseID = resultSet.getInt(5);
				eksisterendeBrugerKunde_kontaktID = resultSet.getInt(6);
				return true;
			} else {
				System.out.println("CPR kunne ikke findes i databasen. ");
				eksisterendeBrugerID = 0;
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke validere eksistens af kunde");
			System.exit(1);
		}
		return false;
	}
	
	// Metode til at skrive ny eller eksisterende bruger
	public void skrivKundeTilDB() {
		if(eksisterendeBrugerID != 0) {
			skrivEksisterendeBruger();
			System.out.println("Skriver eksisterende bruger...");
		} else {
			skrivNyBruger();
			System.out.println(eksisterendeBrugerID);
			System.out.println("Skriver ny bruger...");
		}
	}
	
	// Hent den valgte sælgers - hvis eksisterer - CPR nummer
	public int tjekEksisterendeCPRProblemIDB() {
		try {
			statement = connection.prepareStatement("SELECT kunde_problemer FROM Kunde WHERE kunde_CPR = " + Kunde.kunde.getCPR() +"");
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				System.out.println("Kunde CPR fundet! Ingen problemer!");
				if(resultSet.getInt(1) == 0) {
					return 0;
				} else {
					return 1;
				}
			} else {
				System.out.println("Kunde ikke fundet!");
				return 0;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke validere CPR i DB ...");
			System.exit(1);
		}
		return 1;
	}
	
	// Hent højste tabel_id fra tabel
	public void getHøjesteID(String tabel_id, String tabel) {
		try {
			statement = connection.prepareStatement("SELECT IFNULL(MAX(" + tabel_id + "),0) from " + tabel + "");
			resultSet = statement.executeQuery();
			if (resultSet.next()){
				arrayMedHøjesteIDList.add(resultSet.getInt(1)+1);
				autoIncrementTving(resultSet.getInt(1), tabel);
			}
		} catch (SQLException e) {
			System.exit(1);
		}
	}
	
	// Metode til at tvinge næste auto_increment til at være nuværende id + 1 - i tilfældee af, at noget er blevt slettet.
	public int autoIncrementTving(int tabel_id, String tabel) {
		try {
			statement = connection.prepareStatement("ALTER TABLE " + tabel + " AUTO_INCREMENT =" + tabel_id);
			statement.executeUpdate();
		} catch (SQLException e) {
			System.exit(1);
		}
		return 0;
	}
	
	// Skriv ny kunde til databasen - Alle metoder nødvendigt for dette. Her laves en ny kunde.
	public void skrivNyBruger() {
		try {
			connection.setAutoCommit(false);
			skrivKundeIDsFraDB();
			skrivNyKundeTilDB();
			skrivNyAdresseTilDB();
			skrivNyKunde_kontaktTilDB();
			skrivNyAftaleTilDB();
			skrivBetalingsoversigtTilDB(7);
			skrivBetalingsforløbTilDB(8);
			connection.commit();
			// For en sikkerheds skyld. Lukkedes også i metoden connectionClose()
			connection.close();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
			}
		}
	}
	
	// Skriv eksisterende kunde til databasen - Alle metoder nødvendigt for dette. Her laves kun en ny aftale
	public void skrivEksisterendeBruger() {
		try {
			connection.setAutoCommit(false);
			overwriteKundeDB();
			overwriteAdresseDB();
			overwriteKunde_kontaktDB();
			skrivKundeIDsFraDB();
			skrivEksisterendeAftaleTilDB();
			skrivBetalingsoversigtTilDB(7);
			skrivBetalingsforløbTilDB(8);
			connection.commit();
			// For en sikkerheds skyld. Lukkedes også i metoden connectionClose()
			connection.close();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
			}
		}
	}
	
	// Skriv ny eller eksisterende kunde til DB - Alle ID's nødvendigt for dette
	private void skrivKundeIDsFraDB() {
		getHøjesteID("kunde_id", "Kunde");
		getHøjesteID("adresse_id", "Adresse");
		getHøjesteID("kunde_kontakt_id", "Kunde_kontakt");
		getHøjesteID("aftale_id", "Aftale");
		getHøjesteID("kunde_id", "Kunde");
		arrayMedHøjesteIDList.add(getSælgerIDFraDB());
		arrayMedHøjesteIDList.add(getBilIDFraDB());
		getHøjesteID("betalingsoversigt_id", "Betalingsoversigt");
		getHøjesteID("betalingsforløb_id", "Betalingsforløb");
		arrayMedHøjesteIDList.add(ValiderAnmodning.anmodning.getValideret());
	}

	// Skriv selve kunden til DB for ny kunde
	private void skrivNyKundeTilDB() {
		try {
			statement = connection.prepareStatement("INSERT INTO Kunde(kunde_id, kunde_CPR, kunde_fornavn, kunde_efternavn, kunde_adresse_id, kunde_kontakt_id, kunde_problemer) VALUES (?,?,?,?,?,?,?)");
			statement.setString(1, ""+(arrayMedHøjesteIDList.get(0)));
			statement.setString(2, Kunde.kunde.getCPR());
			statement.setString(3, Kunde.kunde.getFornavn());
			statement.setString(4, Kunde.kunde.getEfternavn());
			statement.setString(5, ""+(arrayMedHøjesteIDList.get(1)));
			statement.setString(6, ""+(arrayMedHøjesteIDList.get(2)));
			statement.setString(7, "0");
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke skrive Kunde til databasen.");
			System.exit(1);
		}
	}
	
	// Skriv selve kundens adresse til DB for ny kunde
	private void skrivNyAdresseTilDB() {
		try {
			statement = connection.prepareStatement("INSERT INTO Adresse(adresse_id, postnr, adresse) VALUES (?,?,?)");
			statement.setString(1, ""+(arrayMedHøjesteIDList.get(1)));
			statement.setString(2, Kunde.kunde.getPostnr());
			statement.setString(3, Kunde.kunde.getAdresse());
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke skrive kundens adresse til databasen.");
			System.exit(1);
		}
	}
	
	// Skriv selve kundens kontaktoplysninger til DB for ny kunde
	private void skrivNyKunde_kontaktTilDB() {
		try {
			statement = connection.prepareStatement("INSERT INTO Kunde_kontakt(kunde_kontakt_id, kunde_tlfnr, kunde_email) VALUES (?,?,?)");
			statement.setString(1, ""+(arrayMedHøjesteIDList.get(2)));
			statement.setString(2, Kunde.kunde.getTlfnr());
			statement.setString(3, Kunde.kunde.getEmail());
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke skrive kundens kontaktoplysninger til databasen.");
			System.exit(1);
		}
	}
	
	// Skriv selve aftale til DB for ny kunde.
	private void skrivNyAftaleTilDB() {
		try {
			statement = connection.prepareStatement("INSERT INTO Aftale(aftale_id, kunde_id, sælger_id, bil_id, betalingsoversigt_id, betalingsforløb_id, validated) VALUES (?,?,?,?,?,?,?)");
			statement.setString(1, ""+(arrayMedHøjesteIDList.get(3)));
			statement.setString(2, ""+(arrayMedHøjesteIDList.get(4)));
			statement.setString(3, ""+(arrayMedHøjesteIDList.get(5)));
			statement.setString(4, ""+(arrayMedHøjesteIDList.get(6)));
			statement.setString(5, ""+(arrayMedHøjesteIDList.get(7)));
			statement.setString(6, ""+(arrayMedHøjesteIDList.get(8)));
			statement.setString(7, ""+(arrayMedHøjesteIDList.get(9)));
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke gemme aftalen til databasen");
			System.exit(1);
		}
	}
	
	
	// Skriv aftale til DB for eksisterende kunde
	private void skrivEksisterendeAftaleTilDB() {
		try {
			statement = connection.prepareStatement("INSERT INTO Aftale(aftale_id, kunde_id, sælger_id, bil_id, betalingsoversigt_id, betalingsforløb_id, validated) VALUES (?,?,?,?,?,?,?)");
			statement.setString(1, ""+(arrayMedHøjesteIDList.get(3)));
			statement.setString(2, ""+eksisterendeBrugerID);
			statement.setString(3, ""+(arrayMedHøjesteIDList.get(5)));
			statement.setString(4, ""+(arrayMedHøjesteIDList.get(6)));
			statement.setString(5, ""+(arrayMedHøjesteIDList.get(7)));
			statement.setString(6, ""+(arrayMedHøjesteIDList.get(8)));
			statement.setString(7, ""+(arrayMedHøjesteIDList.get(9)));
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke gemme aftalen for eksisterende kunde til databasen");
			System.exit(1);
		}
	}
	
	// Skriv selve aftalens betalingsoversigt til DB. NY ELLER EKSISTERENDE:
	private void skrivBetalingsoversigtTilDB(int id) {
		try {
			statement = connection.prepareStatement("INSERT INTO Betalingsoversigt(betalingsoversigt_id, udbetalingsbeløb, afbetalingsbeløb, afbetalingsperiode, månedlig_afbetaling, årlige_omkostninger_i_procent) VALUES (?,?,?,?,?,?)");
			statement.setString(1, ""+arrayMedHøjesteIDList.get(id));
			statement.setString(2, ""+Aftale.aftale.getUdbetalingsbeløb());
			statement.setString(3, ""+Aftale.aftale.getAfbetalingsbeløb());
			statement.setString(4, ""+Aftale.aftale.getAfbetalingsperiode());
			statement.setString(5, ""+Aftale.aftale.getMånedlig_afbetaling());
			statement.setString(6, ""+Aftale.aftale.getÅrlige_omkostninger_i_procent());
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke gemme betalingsoversigten til databasen");
			System.exit(1);
		}
	}
	
	// Skriv selve betalingsforløbets forløb til DB. NY ELLER EKSISTERENDE.
	private void skrivBetalingsforløbTilDB(int id) {
		try {
			statement = connection.prepareStatement("INSERT INTO Betalingsforløb(betalingsforløb_id, start_dato, slut_dato) VALUES (?,?,?)");
			statement.setString(1, ""+(arrayMedHøjesteIDList.get(id)));
			statement.setString(2, ""+Aftale.iDag);
			statement.setString(3, ""+Aftale.aftale.getNæstBetaling());
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke gemme betalingsbeløbet til databasen");
			System.exit(1);
		}
	}
	
	
	// Metoder til at overwrite eksisterende kundeinformation i databasen
	
	
	private void overwriteKundeDB() {
		try {
			statement = connection.prepareStatement("UPDATE Kunde SET kunde_fornavn=?,kunde_efternavn=? WHERE kunde_id=" + eksisterendeBrugerID);
			statement.setString(1, "" + Kunde.kunde.getFornavn());
			statement.setString(2, "" + Kunde.kunde.getEfternavn());
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke overwrite kundens fornavn og kundens efternavn");
			System.exit(1);
		}
	}
	
	private void overwriteAdresseDB() {
		try {
			statement = connection.prepareStatement("UPDATE Adresse SET postnr=?,adresse=? WHERE adresse_id=" + eksisterendeBrugerAdresseID);
			statement.setString(1, "" + Kunde.kunde.getPostnr());
			statement.setString(2, "" + Kunde.kunde.getAdresse());
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke overwrite kundens postnummer og adresse efternavn");
			System.exit(1);
		}
	}
	
	private void overwriteKunde_kontaktDB() {
		try {
			statement = connection.prepareStatement("UPDATE Kunde_kontakt SET kunde_tlfnr=?,kunde_email=? WHERE kunde_kontakt_id=" + eksisterendeBrugerKunde_kontaktID);
			statement.setString(1, "" + Kunde.kunde.getTlfnr());
			statement.setString(2, "" + Kunde.kunde.getEmail());
			statement.executeUpdate();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke overwrite kundens telefonnummer og email");
			System.exit(1);
		}
	}
	

	// Metoder til at hente information fra databasen baseret på CPR
	
	private ArrayList<Object> eksisterendeInfo;
	
	public ArrayList<Object> hentAltKundeInfoForCPR() {
		eksisterendeInfo = new ArrayList<Object>();
		getKundeInfoForCPRFraDB();
		getAdresseInfoForCPRFraDB();
		getKunde_kontaktInfoForCPRFraDB();
		return eksisterendeInfo;
	}
	
	// Hent kunde information fra Kunde tabel
	private void getKundeInfoForCPRFraDB() {
		try {
			statement = connection.prepareStatement("SELECT * FROM Kunde WHERE kunde_id=" + eksisterendeBrugerID);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				eksisterendeInfo.add(resultSet.getString(3));
				eksisterendeInfo.add(resultSet.getString(4));
				eksisterendeInfo.add(resultSet.getInt(5));
				eksisterendeInfo.add(resultSet.getInt(6));
			} else {
				System.out.println("Fejl ved getKundeInfoForCPRFraDB .. Ingen resultater!");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Fejl ved getKundeInfoForCPRFraDB");
			System.exit(1);
		}
	}
	
	// Hent kunde information fra Kunde tabel
	private void getAdresseInfoForCPRFraDB() {
		try {
			statement = connection.prepareStatement("SELECT * FROM Adresse WHERE adresse_id = " + eksisterendeInfo.get(2));
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				eksisterendeInfo.add(resultSet.getString(2));
				eksisterendeInfo.add(resultSet.getString(3));
			} else {
				System.out.println("Fejl ved getAdresseInfoForCPRFraDB");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Fejl ved getAdresseInfoForCPRFraDB");
			System.exit(1);
		}
	}
	
	// Hent kunde information fra Kunde tabel
	private void getKunde_kontaktInfoForCPRFraDB() {
		try {
			statement = connection.prepareStatement("SELECT * FROM Kunde_kontakt WHERE kunde_kontakt_id = " + eksisterendeInfo.get(3));
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				eksisterendeInfo.add(resultSet.getString(2));
				eksisterendeInfo.add(resultSet.getString(3));
			} else {
				System.out.println("Fejl ved getKunde_kontaktInfoForCPRFraDB");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Fejl ved getKunde_kontaktInfoForCPRFraDB");
			System.exit(1);
		}
	}
	
	public void closeConnection() {
		try {
			connection.close();
			statement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}