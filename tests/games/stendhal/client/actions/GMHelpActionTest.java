package games.stendhal.client.actions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.MockStendhalClient;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.gui.MockUserInterface;
import marauroa.common.game.RPAction;
import games.stendhal.client.ClientSingletonRepository;

public class GMHelpActionTest {
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
				assertEquals("gmhelp", action.get("type"));
			}
		};
		
		final GMHelpAction action = new GMHelpAction();
		
		MockUserInterface userInterface = new MockUserInterface();
		
		ClientSingletonRepository.setUserInterface(userInterface);
		
		assertTrue(action.execute(new String []{null}, ""));
		assertTrue(action.execute(new String []{"alter"}, null));
		assertTrue(action.execute(new String []{"script"}, null));
		assertTrue(action.execute(new String []{"support"}, null));
		
		action.execute(new String []{null}, "");
		assertEquals(userInterface.getLastEventLine(), "\t\tList the jailed players and their sentences.");
		
		action.execute(new String []{"alter"}, null);
		assertEquals(userInterface.getLastEventLine(), "\t\t  This will make <testplayer> look like danter");
		
		action.execute(new String []{"script"}, null);
		assertEquals(userInterface.getLastEventLine(), "#/script #ResetSlot.class #player #slot : Resets the named slot such as !kills or !quests. Useful for debugging.");
		

	}

	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final GMHelpAction action = new GMHelpAction();
		assertThat(action.getMaximumParameters(), is(1));
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final GMHelpAction action = new GMHelpAction();
		assertThat(action.getMinimumParameters(), is(0));
	}

}
