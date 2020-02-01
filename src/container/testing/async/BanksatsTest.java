package container.testing.async;

import static org.junit.Assert.*;

import org.junit.Test;

import container.async.Banksats;

public class BanksatsTest {

	@Test
	public void test() {
		// Tjek, at satsen fra banken bliver sat korrekt
		Banksats.setRentesats();
		double outputBanksatsTest = Banksats.getRentesats();
		assertTrue(3 <= outputBanksatsTest && outputBanksatsTest <= 9);
	}

}
