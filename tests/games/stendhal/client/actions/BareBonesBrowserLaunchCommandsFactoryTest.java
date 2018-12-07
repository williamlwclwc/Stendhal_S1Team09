package games.stendhal.client.actions;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BareBonesBrowserLaunchCommandsFactoryTest {
	
	@Test
	public void testBareBonesHashmap() {
		Map<String, SlashAction> commandsMap = new HashMap<String, SlashAction>();
		commandsMap.putAll(BareBonesBrowserLaunchCommandsFactory.createBrowserCommands());
		// cannot get LaunchCommands' url, but can test whether the action is created
		assertFalse(commandsMap.get("beginnersguide") == null);
		assertFalse(commandsMap.get("faq") == null);
		assertFalse(commandsMap.get("manual") == null);
		assertFalse(commandsMap.get("rules") == null);
		assertFalse(commandsMap.get("changepassword") == null);
		assertFalse(commandsMap.get("loginhistory") == null);
		assertFalse(commandsMap.get("merge") == null);
		assertFalse(commandsMap.get("halloffame") == null);
	}
	
}
