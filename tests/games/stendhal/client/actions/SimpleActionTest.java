package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.After;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
//import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
//import marauroa.common.game.RPAction;
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
		String[] params = new String[4];
		params[0] = "test1";
		params[1] = "test2";
		params[2] = "test3";
		params[3] = "test4";
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		//test for Simple actions execute
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("who", action.get("type"));
			}
		};
		final SimpleAction whoAct = new SimpleAction("who", map, null, 0, 0);
		assertTrue(!whoAct.execute(params, null));
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("where", action.get("type"));
			}
		};
		final SimpleAction whereAct = new SimpleAction("where", map, "test", 0, 0);
		assertTrue(whereAct.execute(null, "test"));
		new MockStendhalClient() {
			@Override
			public void send(final RPAction action) {
				assertEquals("teleport", action.get("type"));
			}
		};
		map.put("target", 0);
		map.put("zone", 1);
		map.put("x", 2);
		map.put("y", 3);
		final SimpleAction teleport = new SimpleAction("teleport", map, null, 4, 4);
		assertTrue(teleport.execute(params, null));
	}

	/**
	 * Tests for getMaximumParameters().
	 */
	@Test
	public void testGetMaximumParameters() {
		//test for Simple Who action getMaxParameters
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		final SimpleAction whoAct = new SimpleAction("who", map, null, 0, 0);
		assertThat(whoAct.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		//test for Simple Who action getMinParameters
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		final SimpleAction whoAct = new SimpleAction("who", map, null, 0, 0);
		assertThat(whoAct.getMinimumParameters(), is(0));
	}
	
}
