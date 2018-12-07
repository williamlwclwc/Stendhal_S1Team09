package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QuitActionTest {
	
		// Cannot test for UI stuff here
		// Test for getting maximum parameters number
		@Test
		public void testGetMaximumParameters() {
			final QuitAction quit = new QuitAction();
			assertEquals(0, quit.getMaximumParameters());
		}
		
		// Test for getting minimum parameters number
		@Test
		public void testGetMinimumParameters() {
			final QuitAction quit = new QuitAction();
			assertEquals(0, quit.getMinimumParameters());
		}
}
