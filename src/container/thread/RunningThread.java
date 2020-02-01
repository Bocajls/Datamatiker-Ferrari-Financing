package container.thread;

import container.GUI.GUIController;
import container.async.Banksats;
import container.async.Kreditv�rdighed;
import container.engine.Beregner;
import container.savedata.Kunde;

public class RunningThread extends Thread implements Shutdownable{
	
	public boolean shutdown;
	
	public RunningThread() {
		shutdown = false;
	}
	
	public void run() {
		while ( !shutdown ) {
			// Hvis shutdown er falsk k�re to nedenst�ende metoder og k�r derefter shutdown metoden.
			startBanksats();
			startKreditv�rdighed();
			// K�r kun �n gang pr. tr�d.
			shutdown();
			// N�r tr�den er f�rdig frigiv timeslice
			Thread.yield();
		}
	}
	
	public void startBanksats() {
		// Hvis tr�dens navn er BT (Bank Thread) er dens form�l at finde banksatsen
		if(Thread.currentThread().getName()=="BT") {
			// K�r setRentesats() i Banksats klassen
			Banksats.setRentesats();
		}
	}
	
	public void startKreditv�rdighed() {
		// Hvis tr�dens navn er RT (RKI Thread) er dens form�l at finde en kreditv�rdighed gennem RKI
		if(Thread.currentThread().getName()=="RT") {
			// F� beregneren til at vise "Beregner" ved at s�tte forandring = true.
			GUIController.forandring = true;
			// Lav indikation p�, at kreditv�rdigheden er i f�rd med at blive hentet
			Beregner.setHenterKreditv�rdighed(true);
			// Opdater kreditv�rdigheden i Kreditv�rdighed med CPR nunmmeret fra Kunde.getCPR metoden. Denne v�rdi gemmes i kreditv�rdighed.
			Kreditv�rdighed.updateKreditv�rdighed(Kunde.kunde.getCPR());
			// Lav indikation p�, at kreditv�rdigheden er hentet
			Beregner.setHenterKreditv�rdighed(false);
			// Nu hvor kreditv�rdigheden er opdateret, beregn de nye v�rdier
			GUIController.forandring = true;
		}
	}
	
	public void shutdown() {
		shutdown = true;
	}
	
}
