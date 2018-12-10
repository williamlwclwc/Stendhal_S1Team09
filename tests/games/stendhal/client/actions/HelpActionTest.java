package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.gui.MockUserInterface;
import marauroa.common.game.RPAction;

public class HelpActionTest {
	
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
				assertEquals("help", action.get("type"));
			}
		};
		
		final HelpAction action = new HelpAction();
		
		MockUserInterface userInterface = new MockUserInterface();
		
		ClientSingletonRepository.setUserInterface(userInterface);
		
		assertTrue(action.execute(new String []{null}, null));
		
		action.execute(new String []{null}, null);
		assertEquals(userInterface.getLastEventLine(), "- /help \tShow help information.");
	
	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final HelpAction action = new HelpAction();
		assertThat(action.getMaximumParameters(), is(0));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final HelpAction action = new HelpAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
