package games.stendhal.server.entity.item;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;
import marauroa.common.Log4J;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class InvisibilityRingTest {
	
	//Set up the fixture 
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//For ring 
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
		
		//For player 
		MockStendlRPWorld.get();

	}//setUPBeforeClass 
	
	
	//Test describe 
	@Test
	public void testDescribe() {
		final InvisibilityRing ring = new InvisibilityRing();
		assertThat(ring.describe(), is("'You see an invisibility ring that can make you invisible.'"));
		
	}
	
	
	/**
	 * Tests for ring is in bag and the player should not be invisible so that the creature can attack 
	 */
	@Test
	public void ringIsNotEquippedCreatureShouldAttack() {
		//Prepare the inputs
		//Create player 
		final Player player = PlayerTestHelper.createPlayer("player");
		//Create ring 
		final InvisibilityRing ring = new InvisibilityRing();
		
		//Check the result 
		//Test that the user is visible to creature 
		assertFalse(player.isInvisibleToCreatures());
		
		//Execute the test 
		//Player used the ring 
		ring.onEquipped(player, "bag");
		
		//Check the result 
		//Test that the user is visible to creature 
		assertFalse(player.isInvisibleToCreatures());
	}//testOnUsedIsInvisible 
	
	
	/**
	 * Tests for ring on used and the player is invisible so that the creature cannot attack 
	 */
	@Test
	public void ringIsEquippedCreatureShouldNotAttack() {
		//Prepare the inputs
		//Create player 
		final Player player = PlayerTestHelper.createPlayer("player");
		//Create ring 
		final InvisibilityRing ring = new InvisibilityRing();
		
		
		//Set the player to be visible
		//Test that the player is visible
		assertFalse(player.isInvisibleToCreatures());

		//Execute the test 
		//Player used the ring 
		ring.onEquipped(player, "finger");
		
		//Check the result 
		//Test that the user is invisible to creature when he/she has the ring 
		assertTrue(player.isInvisibleToCreatures());
	}//testOnUsedIsInvisible 
	
	
	/**
	 * Tests for ring is un-equipped from finger and the player should not be invisible so that the creature can attack 
	 */
	@Test
	public void ringIsUnequippedCreatureShouldAttack() {
		//Prepare the inputs
		//Create player 
		final Player player = PlayerTestHelper.createPlayer("player");
		//Create ring 
		final InvisibilityRing ring = new InvisibilityRing();
		
		//Equip player with ring on finger 
		ring.onEquipped(player, "finger");
		
		//Test that the user is invisible to creature when he/she has the ring 
		assertTrue(player.isInvisibleToCreatures());
		
		//Un-equipped the ring from finger 
		ring.onUnequipped();
		 
		//Player should not be visible to creature 
		assertFalse(player.isInvisibleToCreatures());
	}//testOnUsedIsInvisible 
	
	

}
