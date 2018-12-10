package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClearChatLogActionTest {
	
	// Cannot test for UI stuff here
	
	// Test for getting maximum parameters number
	@Test
	public void testGetMaximumParameters() {
		final ClearChatLogAction clear = new ClearChatLogAction();
		assertEquals(0, clear.getMaximumParameters());
	}
	
	// Test for getting minimum parameters number
	@Test
	public void testGetMinimumParameters() {
		final ClearChatLogAction clear = new ClearChatLogAction();
		assertEquals(0, clear.getMinimumParameters());
	}
}
