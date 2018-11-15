
package games.stendhal.server.entity.mapstuff.chest;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.Direction;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.Corpse;
import games.stendhal.server.entity.mapstuff.chest.WheelBarrow;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;

import utilities.PlayerTestHelper;
import utilities.RPClass.BlockTestHelper;


public class WheelBarrowsTest {


	@BeforeClass
	public static void beforeClass() {
		//For block 
		BlockTestHelper.generateRPClasses();
		//For player 
		PlayerTestHelper.generatePlayerRPClasses();
        MockStendlRPWorld.get();
	}//beforeClass 
	
	
	@Test
	public void testWheelBarrowExists()
	{
		//Create a wheel barrow 
		WheelBarrow wheelBarrow = new WheelBarrow();
		
		//Check the description 
		assertEquals("You have seen a wheel barrow, that allows you to push it to wherever, to unload the contents into a chest or to give to another player", wheelBarrow.getDescription());
	}//testWheelBarrowExists 


	@Test
	public void testPushingWheelBarrow() {
		//Create a wheel barrow 
		WheelBarrow wheelBarrow = new WheelBarrow();
		//Set up the position 
		wheelBarrow.setPosition(0, 0);
		//Create a player 
		Player player = PlayerTestHelper.createPlayer("player");
		//Create a zone 
		//StendhalRPZone zone = new StendhalRPZone("test", 10, 10);
		//zone.add(wheelBarrow);
		
		//Get the current position 
		assertThat(Integer.valueOf(wheelBarrow.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(wheelBarrow.getY()), is(Integer.valueOf(0)));
		
		//Push it to the right and test the position 
		wheelBarrow.push(player, Direction.RIGHT);
		assertThat(Integer.valueOf(wheelBarrow.getX()), is(Integer.valueOf(1)));
		assertThat(Integer.valueOf(wheelBarrow.getY()), is(Integer.valueOf(0)));
		
		//Push it to the left and test the position 
		wheelBarrow.push(player, Direction.LEFT);
		assertThat(Integer.valueOf(wheelBarrow.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(wheelBarrow.getY()), is(Integer.valueOf(0)));
		
		//Push it down and test the position 
		wheelBarrow.push(player, Direction.DOWN);
		assertThat(Integer.valueOf(wheelBarrow.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(wheelBarrow.getY()), is(Integer.valueOf(1)));
		
		//Push it up and test the position 
		wheelBarrow.push(player, Direction.UP);
		assertThat(Integer.valueOf(wheelBarrow.getX()), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(wheelBarrow.getY()), is(Integer.valueOf(0)));
	}//testPushingWheelBarrow


	@Test
	public void testWheelBarrowCoordinatesAfterPush() {
		//Create a wheel barrow 
		WheelBarrow wheelBarrow = new WheelBarrow();
		//Set up the position 
		wheelBarrow.setPosition(0, 0);
		
		//Push from up to down 
		assertThat(Integer.valueOf(wheelBarrow.getXAfterPush(Direction.UP)), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(wheelBarrow.getYAfterPush(Direction.UP)), is(Integer.valueOf(-1)));
		
		//Push from down to up 
		assertThat(Integer.valueOf(wheelBarrow.getXAfterPush(Direction.DOWN)), is(Integer.valueOf(0)));
		assertThat(Integer.valueOf(wheelBarrow.getYAfterPush(Direction.DOWN)), is(Integer.valueOf(1)));
		
		//Push from left to right 
		assertThat(Integer.valueOf(wheelBarrow.getXAfterPush(Direction.LEFT)), is(Integer.valueOf(-1)));
		assertThat(Integer.valueOf(wheelBarrow.getYAfterPush(Direction.LEFT)), is(Integer.valueOf(0)));
		
		//Push from right to left 
		assertThat(Integer.valueOf(wheelBarrow.getXAfterPush(Direction.RIGHT)), is(Integer.valueOf(1)));
		assertThat(Integer.valueOf(wheelBarrow.getYAfterPush(Direction.RIGHT)), is(Integer.valueOf(0)));
	}//testsWheelBarrowCoordinatesAfterPush 
	
	@Test 
	public final void testWheelBarrowOnUsed() {
		//Create a wheel barrow 
		WheelBarrow wheelBarrow = new WheelBarrow();
		//Assert false that wheel barrow is opened 
		assertFalse(wheelBarrow.isOpen());
		//Use wheel barrow 
		wheelBarrow.onUsed(new RPEntity() {
			
			@Override
			protected void dropItemsOn(final Corpse corpse) {
			}
			@Override
			public void logic() {
			}
		});
		
		//Assert true that the wheel barrow is opened 
		assertTrue(wheelBarrow.isOpen());
		//Use wheel barrow 
		wheelBarrow.onUsed(new RPEntity() {
			
			@Override
			protected void dropItemsOn(final Corpse corpse) {
			}
			@Override
			public void logic() {
			}
		});
		assertFalse(wheelBarrow.isOpen());
	}//testWheelBarrowOnUsed 

}
