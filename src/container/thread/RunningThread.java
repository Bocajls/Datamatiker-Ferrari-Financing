package container.thread;

import container.GUI.GUIController;
import container.async.Banksats;
import container.async.Kreditværdighed;
import container.engine.Beregner;
import container.savedata.Kunde;

public class RunningThread extends Thread implements Shutdownable{
	
	public boolean shutdown;
	
	public RunningThread() {
		shutdown = false;
	}
	
	public void run() {
		while ( !shutdown ) {
			// Hvis shutdown er falsk køre to nedenstående metoder og kør derefter shutdown metoden.
			startBanksats();
			startKreditværdighed();
			// Kør kun én gang pr. tråd.
			shutdown();
			// Når tråden er færdig frigiv timeslice
			Thread.yield();
		}
	}
	
	public void startBanksats() {
		// Hvis trådens navn er BT (Bank Thread) er dens formål at finde banksatsen
		if(Thread.currentThread().getName()=="BT") {
			// Kør setRentesats() i Banksats klassen
			Banksats.setRentesats();
		}
	}
	
	public void startKreditværdighed() {
		// Hvis trådens navn er RT (RKI Thread) er dens formål at finde en kreditværdighed gennem RKI
		if(Thread.currentThread().getName()=="RT") {
			// Få beregneren til at vise "Beregner" ved at sætte forandring = true.
			GUIController.forandring = true;
			// Lav indikation på, at kreditværdigheden er i færd med at blive hentet
			Beregner.setHenterKreditværdighed(true);
			// Opdater kreditværdigheden i Kreditværdighed med CPR nunmmeret fra Kunde.getCPR metoden. Denne værdi gemmes i kreditværdighed.
			Kreditværdighed.updateKreditværdighed(Kunde.kunde.getCPR());
			// Lav indikation på, at kreditværdigheden er hentet
			Beregner.setHenterKreditværdighed(false);
			// Nu hvor kreditværdigheden er opdateret, beregn de nye værdier
			GUIController.forandring = true;
		}
	}
	
	public void shutdown() {
		shutdown = true;
	}
	
}
