package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AtlasBrowserTest {
	// Test for getting maximum parameters number
	@Test
	public void testGetMaximumParameters() {
		final AtlasBrowserLaunchCommand act = new AtlasBrowserLaunchCommand();
		assertEquals(0, act.getMaximumParameters());
	}
	
	// Test for getting minimum parameters number
	@Test
	public void testGetMinimumParameters() {
		final AtlasBrowserLaunchCommand act = new AtlasBrowserLaunchCommand();
		assertEquals(0, act.getMinimumParameters());
	}
}
