package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConfigActionTest {
	
	// Test for execution
//	@Test
//	public void testExecute() {
//		String[] params = new String[1];
//		params[0] = "test";
//		final ConfigAction config = new ConfigAction();
//		assertTrue(config.execute(params, null));
//		assertTrue(config.execute(params, "test"));
//	}
	
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
