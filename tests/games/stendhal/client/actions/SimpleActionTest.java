package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.After;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class SimpleActionTest {
	
	@After
	public void tearDown() throws Exception {
		StendhalClient.resetClient();
	}

	/**
	 * Tests for execute.
	 */
	@Test
	public void testExecute() {
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				for (final String attrib : action) {
					assertEquals("type", attrib);
					assertEquals("who", (action.get(attrib)));
				}
			}
		};
		//test for Simple Who action execute
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		final SimpleAction WhoAct = new SimpleAction("who", map, null, 0, 0);
		assertTrue(WhoAct.execute(null, null));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		//test for Simple Who action getMaxParameters
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		final SimpleAction WhoAct = new SimpleAction("who", map, null, 0, 0);
		assertThat(WhoAct.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		//test for Simple Who action getMinParameters
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		final SimpleAction WhoAct = new SimpleAction("who", map, null, 0, 0);
		assertThat(WhoAct.getMinimumParameters(), is(0));
	}
	
}
