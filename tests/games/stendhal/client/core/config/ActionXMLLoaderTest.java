package games.stendhal.client.core.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		assertThat(action.getImplementationClass().getName(), is("games.stendhal.client.actions.SlashAction"));
		assertThat(action.getRemainder(), is(""));
		assertTrue(action.getParameters().isEmpty());
		
		//for "where"
		DefaultAction action2 = iterator.next();
		assertThat(action2.getName(), is("whereTest"));
		assertThat(action2.getImplementationClass(), notNullValue());
		assertThat(action2.getImplementationClass().getName(), is("games.stendhal.client.actions.SlashAction"));
		assertThat(action2.getRemainder(), is("target"));
		assertTrue(action.getParameters().isEmpty());
		
		//for "teleport"
		DefaultAction action3 = iterator.next();
		assertThat(action3.getName(), is("teleportTest"));
		assertThat(action3.getImplementationClass(), notNullValue());
		assertThat(action3.getImplementationClass().getName(), is("games.stendhal.client.actions.SlashAction"));
		assertThat(action.getRemainder(), is(""));
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
	

		
	}


}
