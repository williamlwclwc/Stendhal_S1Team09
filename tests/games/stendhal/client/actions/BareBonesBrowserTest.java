package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BareBonesBrowserTest {
	// Test for getting maximum parameters number
	@Test
	public void testGetMaximumParameters() {
		final BareBonesBrowserLaunchCommand act = new BareBonesBrowserLaunchCommand(null);
		assertEquals(0, act.getMaximumParameters());
	}
	
	// Test for getting minimum parameters number
	@Test
	public void testGetMinimumParameters() {
		final BareBonesBrowserLaunchCommand act = new BareBonesBrowserLaunchCommand(null);
		assertEquals(0, act.getMinimumParameters());
	}
}
