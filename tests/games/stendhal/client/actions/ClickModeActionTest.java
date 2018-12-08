package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ClickModeActionTest {
	
	// Test for execution
//	@Test
//	public void testExecute() {
//		ClickModeAction click = new ClickModeAction();
//		assertTrue(click.execute(null, null));
//	}
	// Test for getting maximum parameters number
	@Test
	public void testGetMaximumParameters() {
		final ClickModeAction click = new ClickModeAction();
		assertEquals(0, click.getMaximumParameters());
	}
	
	// Test for getting minimum parameters number
	@Test
	public void testGetMinimumParameters() {
		final ClickModeAction click = new ClickModeAction();
		assertEquals(0, click.getMinimumParameters());
	}
}
