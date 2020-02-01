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
		assertAfbetalingsbeløb();
		assertFuldeAfbetalingsbeløb();
		assertMånedligAfbetaling();
	}
	
	// Beregn procentpoint
	public void assertTotalProcentPoint() {
		Beregner.beregner.beregnPctPoint(5, "A", 500000, 1000000, 50);
		Beregner.beregner.beregnÅOP(Beregner.beregner.getSats(), (int)Beregner.beregner.getKreditværdighedPctPoint(), (int)Beregner.beregner.getUdbetalingsbeløbPctPoint(), (int)Beregner.beregner.getAfbetalingsperiodePctPoint());
		double outputBeregnPctPoint = Beregner.beregner.getTotalPctPoint();
		assertTrue(outputBeregnPctPoint == 7.0);
	}
	
	// Tjek ÅOP
	public void assertÅOP() {
		Beregner.beregner.beregnÅOP(5, 1, 1, 0);
		double outputÅOP = Aftale.aftale.getÅrlige_omkostninger_i_procent();
		assertTrue(outputÅOP == 7.0);
	}
	
	// Tjek afbetalingsbeløbet
	public void assertAfbetalingsbeløb() {
		double outputAfbetalingsbeløb = Beregner.beregner.beregnAfbetalingsbeløb(50000, 500000);
		assertTrue(outputAfbetalingsbeløb == 450000.0);
	}
	

	// Tjek fulde afbetalingsbeløb
	public void assertFuldeAfbetalingsbeløb() {
		Beregner.beregner.beregnAfbetalingsbeløb(8.168866, 500000, 24);
		double outputFulde = Aftale.aftale.getFuldeAfbetalingsbeløb();
		assertTrue(outputFulde == 542060.5125396585);
	}
	
	// Tjek månedlig afbetaling
	public void assertMånedligAfbetaling() {
		Beregner.beregner.beregnAfbetalingsbeløb(8.168866, 500000, 24);
		double outputMånedligAfbetaling = Aftale.aftale.getMånedlig_afbetaling();
		System.out.println(outputMånedligAfbetaling);
		assertTrue(outputMånedligAfbetaling == 22585.85468915244
);
	}
	
}
