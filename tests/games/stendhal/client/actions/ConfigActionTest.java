package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConfigActionTest {
	
	// Test for getting maximum parameters number
	@Test
	public void testGetMaximumParameters() {
		final ConfigAction config = new ConfigAction();
		assertEquals(1, config.getMaximumParameters());
	}
	
	// Test for getting minimum parameters number
	@Test
	public void testGetMinimumParameters() {
		final ConfigAction config = new ConfigAction();
		assertEquals(1, config.getMinimumParameters());
	}
}
