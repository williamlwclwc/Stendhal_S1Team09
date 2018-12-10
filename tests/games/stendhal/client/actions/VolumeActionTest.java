package games.stendhal.client.actions;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.client.StendhalClient;
import games.stendhal.client.gui.MockUserInterface;

public class VolumeActionTest {
	
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
	
		MockUserInterface userInterface = new MockUserInterface();
		
		ClientSingletonRepository.setUserInterface(userInterface);
		
		
		final VolumeAction action = new VolumeAction();
		
		
		assertTrue(action.execute(new String []{null, "1"}, null));
		assertTrue(action.execute(new String []{"master", "20"}, null));
		assertTrue(action.execute(new String []{"123", null}, null));
		assertEquals(userInterface.getLastEventLine(), "Please use /volume for help.");
	
	}

	@Test
	public void testChangeVolumeMethod() {
		MockUserInterface userInterface = new MockUserInterface();
		
		ClientSingletonRepository.setUserInterface(userInterface);
		
		
		final VolumeAction action = new VolumeAction();
		
		assertTrue(action.execute(new String []{"123", "98"}, null));
		assertTrue(action.execute(new String []{"creature", null}, null));
		assertTrue(action.execute(new String []{"creature", "32"}, null));
		
	}
	
	/**
	 * Tests for getMaximumParameters.
	 */
	@Test
	public void testGetMaximumParameters() {
		final VolumeAction action = new VolumeAction();
		assertTrue(action.getMaximumParameters() == 2);
	}

	/**
	 * Tests for getMinimumParameters.
	 */
	@Test
	public void testGetMinimumParameters() {
		final VolumeAction action = new VolumeAction();
		assertTrue(action.getMinimumParameters() == 0);
	}

}
