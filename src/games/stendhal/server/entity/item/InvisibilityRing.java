package games.stendhal.server.entity.item;

import java.util.Map;

import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;


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
	 * Create a RingOfLife.
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
	

	@Override
	public boolean onEquipped(final RPEntity player, final String slot) {
		
		if (player instanceof Player)
		{
			if (slot.equals("finger") && (isActivated() == false))
			{
				((Player) player).setInvisible(true);
			}
		}

		return super.onEquipped(player, slot);
	}
	

}
