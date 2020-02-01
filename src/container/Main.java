package container;

import container.DB.DataLayerInstantiater;
import container.GUI.Formular;
import container.GUI.GUIController;
import container.anmodning.ValiderAnmodning;
import container.engine.Beregner;
import container.savedata.Aftale;
import container.savedata.Kunde;
import container.thread.RunningThread;
import javafx.application.Application;

public class Main {

	public static void main(String[] args) {
		
		// Start up database elementer
		DataLayerInstantiater.startup();
		
		// Ny kunde
		Kunde.initKunde();
		
		// Ny aftale
		Aftale.initAftale();
		
		// Ny beregner
		Beregner.initBeregner();
		
		// Ny valider anmodning
		ValiderAnmodning.initAnmodning();
		
		// New thread til at indhente satsen fra bank - køres én gang, hver gang programmet eksekveres.
		RunningThread bankThread = new RunningThread();
		bankThread.setName("BT");
		bankThread.start();
		
		// Ny controller til formular
		GUIController.initFormularController();

		// Ny formular
		Application.launch(Formular.class);
		
	}
}
