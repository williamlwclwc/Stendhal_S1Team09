package games.stendhal.server.entity.mapstuff.chest;

import java.awt.geom.Rectangle2D;

import games.stendhal.common.Direction;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.events.MovementListener;
import games.stendhal.server.core.events.ZoneEnterExitListener;
import games.stendhal.server.entity.ActiveEntity;
import games.stendhal.server.entity.mapstuff.chest.Chest;
import games.stendhal.server.entity.player.Player;
import marauroa.common.game.RPObject;

public class WheelBarrow extends Chest implements MovementListener, ZoneEnterExitListener{

	private static final String CHEST_RPCLASS_NAME = "wheelbarrow";

	private int startX;
	private int startY;
	private boolean wasMoved = false;
	WheelBarrow(){
		super();
		put("type", CHEST_RPCLASS_NAME);
		setDescription(this.getDescription());
	}
	/**
	 * Push this Block into a given direction
	 *
	 * @param p
	 * @param d
	 *            the direction, this block is pushed into
	 */
	public void push(Player p, Direction d) {
		// after push
		int x = getXAfterPush(d);
		int y = getYAfterPush(d);
		this.setPosition(x, y);
		
		wasMoved = true;
		this.notifyWorldAboutChanges();
	}
	public int getYAfterPush(Direction d) {
		return this.getY() + d.getdy();
	}

	public int getXAfterPush(Direction d) {
		return this.getX() + d.getdx();
	}
	@Override
	public void onAdded(StendhalRPZone zone) {
		super.onAdded(zone);
		this.startX = getX();
		this.startY = getY();
		zone.addMovementListener(this);
		zone.addZoneEnterExitListener(this);
	}
	@Override
	public void onEntered(ActiveEntity entity, StendhalRPZone zone, int newX, int newY) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onExited(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void beforeMove(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY, int newX, int newY) {
		if (entity instanceof Player) {
			Rectangle2D oldA = new Rectangle2D.Double(oldX, oldY, entity.getWidth(), entity.getHeight());
			Rectangle2D newA = new Rectangle2D.Double(newX, newY, entity.getWidth(), entity.getHeight());
			Direction d = Direction.getAreaDirectionTowardsArea(oldA, newA);
			this.push((Player) entity, d);
		}
	}
	@Override
	public void onMoved(ActiveEntity entity, StendhalRPZone zone, int oldX, int oldY, int newX, int newY) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onEntered(RPObject object, StendhalRPZone zone) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onExited(RPObject object, StendhalRPZone zone) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String getDescription() {
		String text = "You have seen a wheel barrow, that allows you to push it to wherever, to unload the contents into a chest or to give to another player";

		return (text);
	}
}
