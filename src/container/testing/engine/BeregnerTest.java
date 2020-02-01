package container.testing.engine;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import container.engine.Beregner;
import container.savedata.Aftale;

public class BeregnerTest {

	@Test
	public void test() {
		Aftale.initAftale();
		Beregner.initBeregner();
		assertTotalProcentPoint();
		assertAfbetalingsbel�b();
		assertFuldeAfbetalingsbel�b();
		assertM�nedligAfbetaling();
	}
	
	// Beregn procentpoint
	public void assertTotalProcentPoint() {
		Beregner.beregner.beregnPctPoint(5, "A", 500000, 1000000, 50);
		Beregner.beregner.beregn�OP(Beregner.beregner.getSats(), (int)Beregner.beregner.getKreditv�rdighedPctPoint(), (int)Beregner.beregner.getUdbetalingsbel�bPctPoint(), (int)Beregner.beregner.getAfbetalingsperiodePctPoint());
		double outputBeregnPctPoint = Beregner.beregner.getTotalPctPoint();
		assertTrue(outputBeregnPctPoint == 7.0);
	}
	
	// Tjek �OP
	public void assert�OP() {
		Beregner.beregner.beregn�OP(5, 1, 1, 0);
		double output�OP = Aftale.aftale.get�rlige_omkostninger_i_procent();
		assertTrue(output�OP == 7.0);
	}
	
	// Tjek afbetalingsbel�bet
	public void assertAfbetalingsbel�b() {
		double outputAfbetalingsbel�b = Beregner.beregner.beregnAfbetalingsbel�b(50000, 500000);
		assertTrue(outputAfbetalingsbel�b == 450000.0);
	}
	

	// Tjek fulde afbetalingsbel�b
	public void assertFuldeAfbetalingsbel�b() {
		Beregner.beregner.beregnAfbetalingsbel�b(8.168866, 500000, 24);
		double outputFulde = Aftale.aftale.getFuldeAfbetalingsbel�b();
		assertTrue(outputFulde == 542060.5125396585);
	}
	
	// Tjek m�nedlig afbetaling
	public void assertM�nedligAfbetaling() {
		Beregner.beregner.beregnAfbetalingsbel�b(8.168866, 500000, 24);
		double outputM�nedligAfbetaling = Aftale.aftale.getM�nedlig_afbetaling();
		System.out.println(outputM�nedligAfbetaling);
		assertTrue(outputM�nedligAfbetaling == 22585.85468915244
);
	}
	
}
