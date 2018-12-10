package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import marauroa.common.game.RPAction;

public class IgnoreActionTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

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
				assertEquals("ignore", action.get("type"));
			}
		};
		final IgnoreAction ignoreAction = new IgnoreAction();
		
		assertTrue(ignoreAction.execute(new String []{null}, null));
		assertFalse(ignoreAction.execute(new String []{"hi", "lol"}, null));
		assertFalse(ignoreAction.execute(new String []{"hi", "*"}, null));
    	assertTrue(ignoreAction.execute(new String []{"hi", null}, "haha"));
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final IgnoreAction action = new IgnoreAction();
		assertThat(action.getMaximumParameters(), is(2));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final IgnoreAction action = new IgnoreAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
