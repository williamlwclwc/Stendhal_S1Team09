package games.stendhal.server.entity.item.timed;

import games.stendhal.common.Grammar;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.core.events.UseListener;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.item.Stackable;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.player.Player;

import java.lang.ref.WeakReference;
import java.util.Map;

import marauroa.common.Log4J;
import marauroa.common.Logger;
import marauroa.common.game.RPObject;

/**
 * Abstract base class for a stackable timed item. Extend this class and
 * implement methods useItem(Player) and itemFinished(Player).
 * 
 * @author johnnnny
 */
public abstract class TimedStackableItem extends StackableItem implements
		UseListener {

	private static Logger logger = Log4J.getLogger(TimedStackableItem.class);

	WeakReference<Player> player;

	/**
	 * Creates a TimedItem
	 * 
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	public TimedStackableItem(String name, String clazz, String subclass,
			Map<String, String> attributes) {
		super(name, clazz, subclass, attributes);
	}

	/**
	 * copy constructor
	 * 
	 * @param item
	 *            item to copy
	 */
	public TimedStackableItem(TimedStackableItem item) {
		super(item);
	}

	public boolean onUsed(RPEntity user) {
		RPObject base = this;
		boolean result = false;

		/* Find the top container */
		while (base.isContained()) {
			base = base.getContainer();
		}

		if (user.nextTo((Entity) base)) {
			if (useItem((Player) user)) {
				/* set the timer for the duration */
				TurnNotifier notifier = TurnNotifier.get();
				notifier.notifyInTurns(getAmount(), this);
				player = new WeakReference<Player>((Player) user);
				this.removeOne();
				user.notifyWorldAboutChanges();
			}
			result = true;
		} else {
			user.sendPrivateText(getTitle() + " is too far away");
			logger.debug(getTitle() + " is too far away");
		}

		return result;
	}

	@Override
	public void onTurnReached(int currentTurn) {
		itemFinished(player.get());
	}

	@Override
	public String describe() {
		String text = "You see " + Grammar.a_noun(getTitle()) + ".";
		if (hasDescription()) {
			text = getDescription();
		}

		String boundTo = getBoundTo();

		if (boundTo != null) {
			text = text + " It is a special quest reward for " + boundTo
					+ ", and cannot be used by others.";
		}

		return text;
	}

	@Override
	public boolean isStackable(Stackable other) {
		return (other.getClass() == this.getClass());
	}

	/**
	 * Get the length of the timed event in turns.
	 * 
	 * @return length in turns
	 */
	public int getAmount() {
		return getInt("amount");
	}

	/**
	 * Called when the player uses the item. Implement this in a subclass.
	 * 
	 * @param player
	 * @return true if the usage is successful
	 */
	public abstract boolean useItem(Player player);

	/**
	 * Called when the used item is finished. Implement this in a subclass.
	 * 
	 * @param player
	 */
	public abstract void itemFinished(Player player);
}
