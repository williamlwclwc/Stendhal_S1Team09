package games.stendhal.server.entity.creature;
import games.stendhal.server.entity.creature.impl.DropItem;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import marauroa.server.game.db.DatabaseFactory;
import utilities.RPClass.CreatureTestHelper;

public class MageGnomeTest {

	@Before
	public void setUp() {
		MockStendlRPWorld.get();
		CreatureTestHelper.generateRPClasses();
		Log4J.init();
		new DatabaseFactory().initializeDatabase();
	}

	@After
	public void tearDown() {
		MockStendlRPWorld.reset();
	}

	boolean result = false;

	@Test
	public void dropTest() {
		Creature mageGnome = SingletonRepository.getEntityManager().getCreature("mage gnome");
		for(DropItem item : mageGnome.dropsItems)
		{
			if (item.name.equals("potion") && item.probability == 40 && item.min == 2 && item.max == 4)
			{
				result = true;
			}
		}
		
		assertTrue(result);
	}

}