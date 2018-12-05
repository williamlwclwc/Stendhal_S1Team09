package games.stendhal.server.core.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.core.config.DefaultAction;

public class ActionXMLLoaderTest {
	
	@Before
	public void setUp() {
		MockStendlRPWorld.get();
	}

	@After
	public void tearDown() {
		MockStendlRPWorld.reset();
	}
	
	@Test
	public void testLoad() throws URISyntaxException {
		ActionsXMLLoader loader = new ActionsXMLLoader(new URI("testaction.xml"));
		List<DefaultAction> list = loader.load();
		assertThat(Boolean.valueOf(list.isEmpty()), is(Boolean.FALSE));
		DefaultAction action = list.get(0);
		
		//for "who"
		assertThat(action.getName(), is("whoTest"));
		assertThat(action.getImplementationClass(), notNullValue());
		assertThat(action.getImplementationClass().getName(), is("games.stendhal.client.actions.SlashAction"));
		assertThat(action.getRemainder(), null);
		assertThat(action.getTarget(), null);
		assertThat(action.getZone(), null);
		assertThat(action.getX(), null);
		assertThat(action.getY(), null);
		
		//for "where"
		assertThat(action.getName(), is("whereTest"));
		assertThat(action.getImplementationClass(), notNullValue());
		assertThat(action.getImplementationClass().getName(), is("games.stendhal.client.actions.SlashAction"));
		assertThat(action.getRemainder(), is("target"));
		assertThat(action.getTarget(), null);
		assertThat(action.getZone(), null);
		assertThat(action.getX(), null);
		assertThat(action.getY(), null);
		
		//for "teleport"
		assertThat(action.getName(), is("teleportTest"));
		assertThat(action.getImplementationClass(), notNullValue());
		assertThat(action.getImplementationClass().getName(), is("games.stendhal.client.actions.SlashAction"));
		assertThat(action.getRemainder(), null);
		assertThat(action.getTarget(), is(0));
		assertThat(action.getZone(), is(1));
		assertThat(action.getX(), is(2));
		assertThat(action.getY(), is(3));
		
	}


}
