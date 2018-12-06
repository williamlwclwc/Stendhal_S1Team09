package games.stendhal.client.core.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import games.stendhal.client.actions.SlashAction;
import games.stendhal.client.core.config.ActionsXMLLoader;
import games.stendhal.client.core.rule.defaultruleset.DefaultAction;
import games.stendhal.server.maps.MockStendlRPWorld;
import org.xml.sax.SAXException;

public class ActionXMLLoaderTest {
	
	@Before
	public void setUp() throws Exception {
		MockStendlRPWorld.get();
	}

	@After
	public void tearDown() {
		MockStendlRPWorld.reset();
	}
	
	@Test
	public void testLoad() throws URISyntaxException, SAXException {
		ActionsXMLLoader loader = new ActionsXMLLoader(new URI("testaction.xml"));
		List<DefaultAction> list = loader.load();
		assertThat(Boolean.valueOf(list.isEmpty()), is(Boolean.FALSE));
		ListIterator<DefaultAction> iterator = list.listIterator();
		
		//for "who"
		DefaultAction action = iterator.next();
		assertThat(action.getName(), is("whoTest"));
		assertThat(action.getImplementationClass(), notNullValue());
		assertThat(action.getImplementationClass().getName(), is("games.stendhal.client.actions.SimpleAction"));
		assertThat(action.getMinimumParameters(), is(Integer.valueOf(0)));
		assertThat(action.getMaximumParameters(), is(Integer.valueOf(0)));
		assertThat(action.getRemainder(), is(""));
		assertTrue(action.getParameters().isEmpty());
		// Test SlashAction object creation.
		SlashAction slashAction = action.getAction();
		assertEquals(slashAction.getMinimumParameters(), action.getMinimumParameters());
		assertEquals(slashAction.getMaximumParameters(), action.getMaximumParameters());
		
		//for "where"
		DefaultAction action2 = iterator.next();
		assertThat(action2.getName(), is("whereTest"));
		assertThat(action2.getImplementationClass(), notNullValue());
		assertThat(action2.getImplementationClass().getName(), is("games.stendhal.client.actions.SimpleAction"));
		assertThat(action2.getMinimumParameters(), is(Integer.valueOf(0)));
		assertThat(action2.getMaximumParameters(), is(Integer.valueOf(0)));
		assertThat(action2.getRemainder(), is("target"));
		assertTrue(action2.getParameters().isEmpty());
		// Test SlashAction object creation.
		SlashAction slashAction2 = action2.getAction();
		assertEquals(slashAction2.getMinimumParameters(), action2.getMinimumParameters());
		assertEquals(slashAction2.getMaximumParameters(), action2.getMaximumParameters());
		
		//for "teleport"
		DefaultAction action3 = iterator.next();
		assertThat(action3.getName(), is("teleportTest"));
		assertThat(action3.getImplementationClass(), notNullValue());
		assertThat(action3.getImplementationClass().getName(), is("games.stendhal.client.actions.SimpleAction"));
		assertThat(action3.getMinimumParameters(), is(Integer.valueOf(4)));
		assertThat(action3.getMaximumParameters(), is(Integer.valueOf(4)));
		assertThat(action3.getRemainder(), is(""));
		Map<String, Integer> param;
		param = action3.getParameters();
		assertThat(param, notNullValue());
		assertTrue(param.containsKey("target"));
		assertTrue(param.containsKey("zone"));
		assertTrue(param.containsKey("x"));
		assertTrue(param.containsKey("y"));
		assertThat(param.get("target"), is(Integer.valueOf(0)));
		assertThat(param.get("zone"), is(Integer.valueOf(1)));
		assertThat(param.get("x"), is(Integer.valueOf(2)));
		assertThat(param.get("y"), is(Integer.valueOf(3)));
		// Test SlashAction object creation.
		SlashAction slashAction3 = action3.getAction();
		assertEquals(slashAction3.getMinimumParameters(), action3.getMinimumParameters());
		assertEquals(slashAction3.getMaximumParameters(), action3.getMaximumParameters());
	

		
	}


}
