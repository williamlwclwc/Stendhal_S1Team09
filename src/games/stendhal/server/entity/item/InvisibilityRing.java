package games.stendhal.server.entity.item;

import java.util.Map;

//import games.stendhal.common.constants.Testing;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;
//import marauroa.common.game.RPObject;
//import utilities.PlayerTestHelper;


public class InvisibilityRing extends SlotActivatedItem{
	public InvisibilityRing(final String name, final String clazz, final String subclass, final Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
	}

	/**
	 * Copy constructor.
	 *
	 * @param item copied item
	 */
	public InvisibilityRing(final InvisibilityRing item) {
		super(item);
	}

	
	/**
	 * Create an invisibility ring 
	 */
	public InvisibilityRing() {
		super("invisibility ring", "ring", "engagement_ring", null);
		put("amount", 1);
	}
	
	/**
	 * Gets the description.
	 *
	 * The description of the invisibility ring.
	 *
	 * @return The description text.
	 */
	@Override
	public String describe() {
		String text;
		text = "'You see an invisibility ring that can make you invisible.'";
		return text;
	}//describe
	

	/**
	 * Equip the ring 
	 *
	 * If on the finger then player becomes invisible 
	 *
	 * @return true or false 
	 */
	
	//Variables to store information about player and slot
	Player player;
	String fingerSlot;
	@Override
	public boolean onEquipped(final RPEntity owner, final String slot) {
		//If it is a player 
		if (owner instanceof Player)
		{
			//If the slot is the finger 
			if (slot.equals("finger"))
			{
				//Store the variable 
				player = (Player)owner;
				
				//Store the slot 
				fingerSlot = slot;
				
				//Set the player to be invisible 
				((Player) owner).setInvisible(true);
				return true;	
			}//if 
			else 
				return false;
		}//if

		else 
			return false;
	}//onEquipped
	
	
	
	/**
	 * Un-equip the ring 
	 *
	 * If un-equip from the finger then player becomes visible 
	 *
	 * @return true or false 
	 */
	@Override
	public boolean onUnequipped() {
		
		//If there is ring on the finger 
		if (this.onEquipped(player, fingerSlot) == true)
		{	
			//Player should be visible to creatures 
			player.setInvisible(false);
			return true;
		}//if 
		else 
		{
			return false;
		}
		
	}//onUnequipped

	

}
