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
//Test to check whether the mage gnome drops 2-4 potions with probability 40%
//Refer to the code -- ItemGuardCreatureTest 
public class MageGnomeTest {
	
	//Set up the world, creature, and database so that the mage gnome will be setted up
	@Before
	public void setUp() {
		MockStendlRPWorld.get();
		CreatureTestHelper.generateRPClasses();
		Log4J.init();
		new DatabaseFactory().initializeDatabase();
	}
	
	//Reset the world when the test is finished 
	@After
	public void tearDown() {
		MockStendlRPWorld.reset();
	}
	
	//A boolean value to test whether the mage gnome drops 2-4 potions with probability 40%
	boolean result = false;

	@Test
	public void dropTest() {
		//Create a mage gnome instance
		//Same as the way rat is created in ItemGuardCreature 
		Creature mageGnome = SingletonRepository.getEntityManager().getCreature("mage gnome");
		
		//For each loop loop through all the items mage gnome drops 
		for(DropItem item : mageGnome.dropsItems)
		{
			//If the drop items is equal to potion, with probability 40%, min 2, and max 4
			if (item.name.equals("potion") && item.probability == 40 && item.min == 2 && item.max == 4)
			{
				//Then mage gnome DOES drop 2-4 potions with probability 40%
				result = true;
			}
		}
		//assertTrue to check if the bug is fixed 
		assertTrue(result);
	}

}