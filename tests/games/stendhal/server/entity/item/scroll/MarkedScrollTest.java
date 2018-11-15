package games.stendhal.server.entity.item.scroll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import games.stendhal.server.core.config.ZoneGroupsXMLLoader;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPWorld;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.rule.EntityManager;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.scroll.MarkedScroll;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;

public class MarkedScrollTest {

	private static EntityManager manager = null;
	private StendhalRPZone testzone = null;
	private Player player = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();

		manager = SingletonRepository.getEntityManager();
	}

	@Before
	public void setUp() {
		player = PlayerTestHelper.createPlayer("player");
		testzone = new StendhalRPZone("admin_test");
		testzone.add(player);
	}
	
	@Test
	public void testWofolScrollExists() {
		// Try to give the player a wofol city scroll and make sure it exists and has
		// the right description.
		Item scroll = manager.getItem("wofol city scroll");
		assertTrue(scroll instanceof MarkedScroll);
		assertEquals("You see a wofol city scroll, it will take you back to wofol city.",
				scroll.getDescription());
	}
		
	@Test
	public void testWofolScrollWorksCorrectly() {
		// Load zones because we are going to be testing teleportation.
		final Logger logger = Logger.getLogger(StendhalRPWorld.class);
		try {
			final ZoneGroupsXMLLoader loader = new ZoneGroupsXMLLoader(new URI(
					"/data/conf/zones.xml"));
			loader.load();
		} catch (final Exception e) {
			logger.error("Error loading zones into world.", e);
		}

		// Give player a scroll.
		Item scroll = manager.getItem("wofol city scroll");
		player.getSlot("bag").add(scroll);

		// Player must have visited the target zone before so make sure it doesn't work if they
		// haven't been there.
		assertFalse(scroll.onUsed(player));
		
		// Now make sure it works if the player has been to the target zone.
		player.setKeyedSlot("!visited", "-1_semos_mine_nw",
				Long.toString(System.currentTimeMillis()));

		// Use the scroll and make sure it teleports the player to the correct location.
		assertTrue(scroll.onUsed(player));
		
		assertEquals(50, player.getX());
		assertEquals(100, player.getY());
		assertEquals("-1_semos_mine_nw", player.getZone().getName());
	}

}