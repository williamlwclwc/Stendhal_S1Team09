package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;

import org.junit.Test;

//import games.stendhal.client.MockStendhalClient;
//import marauroa.common.game.RPAction;

public class ClientInfoActionTest {
	
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
