package games.stendhal.client.actions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SoundActionTest {
	
		// Cannot test for UI stuff here
	
		// Test for getting maximum parameters number
		@Test
		public void testGetMaximumParameters() {
			final SoundAction sound = new SoundAction();
			assertEquals(5, sound.getMaximumParameters());
		}
		
		// Test for getting minimum parameters number
		@Test
		public void testGetMinimumParameters() {
			final SoundAction sound = new SoundAction();
			assertEquals(0, sound.getMinimumParameters());
		}
}
