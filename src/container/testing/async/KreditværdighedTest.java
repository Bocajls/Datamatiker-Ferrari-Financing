package container.testing.async;

import static org.junit.Assert.*;
import org.junit.Test;
import com.ferrari.finances.dk.rki.CreditRator;


public class KreditværdighedTest {

	@Test
	public void test() throws InterruptedException {
		
		// Tjek at CreditRator ved CPR 0000000001 har af returværdi "B"
		String kreditTest0 = CreditRator.i().rate("0000000000").toString();
		assertEquals("A", kreditTest0);
		
		// Tjek at CreditRator ved CPR 0000000001 har af returværdi "B"
		String kreditTest1 = CreditRator.i().rate("0000000001").toString();
		assertEquals("B", kreditTest1);
		
		// Tjek at CreditRator ved CPR 0000000001 har af returværdi "B"
		String kreditTest2 = CreditRator.i().rate("0000000002").toString();
		assertEquals("C", kreditTest2);
				
		// Tjek at CreditRator ved CPR 0000000001 har af returværdi "B"
		String kreditTest3 = CreditRator.i().rate("0000000003").toString();
		assertEquals("D", kreditTest3);
		
	}

}
