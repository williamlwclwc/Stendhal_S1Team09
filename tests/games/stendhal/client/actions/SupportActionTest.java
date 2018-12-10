package games.stendhal.client.actions;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class SupportActionTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	@Test
	public void testExecute() {
	
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("support", action.get("type"));
			}
		};
		final SupportAction action = new SupportAction();
		
		assertTrue(action.execute(new String []{null}, "haha"));
	}
	
	@Test
	public void testGetMaximumParameters() {
		final SupportAction action = new SupportAction();
		assertTrue(action.getMaximumParameters() == 0);
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final SupportAction action = new SupportAction();
		assertTrue(action.getMinimumParameters() == 0);
	}

}
