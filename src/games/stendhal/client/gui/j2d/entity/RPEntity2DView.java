/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.gui.j2d.entity;


import games.stendhal.client.IGameScreen;
import games.stendhal.client.entity.ActionType;
import games.stendhal.client.entity.EntityChangeListener;
import games.stendhal.client.entity.IEntity;
import games.stendhal.client.entity.Player;
import games.stendhal.client.entity.RPEntity;
import games.stendhal.client.entity.User;
import games.stendhal.client.gui.j2d.entity.helpers.HorizontalAlignment;
import games.stendhal.client.gui.j2d.entity.helpers.VerticalAlignment;
import games.stendhal.client.sprite.AnimatedSprite;
import games.stendhal.client.sprite.Sprite;
import games.stendhal.client.sprite.SpriteStore;
import games.stendhal.client.sprite.TextSprite;
import games.stendhal.common.Debug;
import games.stendhal.common.Direction;
import games.stendhal.common.constants.Nature;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import marauroa.common.game.RPAction;

/**
 * The 2D view of an RP entity.
 * 
 * @param <T> type of RPEntity
 */
abstract class RPEntity2DView<T extends RPEntity> extends ActiveEntity2DView<T> {
	private static final int ICON_OFFSET = 8;
	
	/** Number of frames in attack sprites */
	private static final int NUM_ATTACK_FRAMES = 3;
	private static final Stroke ARROW_STROKE = new BasicStroke(2);
	private static final Map<Nature, Color> arrowColor;
	
	static {
		arrowColor = new EnumMap<Nature, Color>(Nature.class);
		arrowColor.put(Nature.CUT, Color.LIGHT_GRAY);
		arrowColor.put(Nature.DARK, Color.DARK_GRAY);
		arrowColor.put(Nature.LIGHT, new Color(255, 240, 140)); // light yellow
		arrowColor.put(Nature.FIRE, new Color(255, 100, 0)); // reddish orange
		arrowColor.put(Nature.ICE, new Color(140, 140, 255)); // light blue
	}

	/**
	 * The attack sprites. The top level map contains all the
	 * strike sprites sorted by damage type. Those in turn are
	 * retrievable by the attack direction.
	 */
	private static final Map<Nature, Map<Direction, Sprite[]>> bladeStrikeSprites;

	private static final Sprite eatingSprite;
	private static final Sprite poisonedSprite;
	private static final Sprite chokingSprite;
	private static final Sprite hitSprite;
	private static final Sprite blockedSprite;
	private static final Sprite missedSprite;
	
	/** Temporary text sprites, like HP and XP changes. */
	private Map<RPEntity.TextIndicator, Sprite> floaters = new HashMap<RPEntity.TextIndicator, Sprite>();

	/**
	 * Model attributes effecting the title changed.
	 */
	private boolean titleChanged;

	/**
	 * The title image sprite.
	 */
	private Sprite titleSprite;

	/*
	 * The drawn height.
	 */
	protected int height;

	/*
	 * The drawn width.
	 */
	protected int width;
	/** Status icon managers */
	private final List<StatusIconManager> iconManagers = new ArrayList<StatusIconManager>();

	/**
	 * Flag for checking if the entity is attacking. Can be modified by both
	 * the EDT and the game loop.
	 */
	volatile boolean isAttacking;
	/** <code>true</code> if the current attack is ranged. */
	boolean rangedAttack;
	/** Attack sprites to be used for the current attack. */
	Sprite[] attackSprite;
	/** Blade strike frame. */
	volatile int bladeStrikeFrame;
	
	private final AttackListener attackListener = new AttackListener();

	static {
		final SpriteStore st = SpriteStore.get();
		
		final int twidth = NUM_ATTACK_FRAMES * IGameScreen.SIZE_UNIT_PIXELS;
		final int theight = 4 * IGameScreen.SIZE_UNIT_PIXELS;

		bladeStrikeSprites = new EnumMap<Nature, Map<Direction, Sprite[]>>(Nature.class);
		
		// Load all attack sprites
		for (Nature damageType : Nature.values()) {
			final Sprite tiles = st.getSprite("data/sprites/combat/blade_strike_" 
					+ damageType.toString().toLowerCase(Locale.US) + ".png");

			Map<Direction, Sprite[]> map = new EnumMap<Direction, Sprite[]>(Direction.class);
			bladeStrikeSprites.put(damageType, map);
			
			int y = 0;
			map.put(Direction.UP, st.getTiles(tiles, 0, y, 3, twidth, theight));

			y += theight;
			map.put(Direction.RIGHT, st.getTiles(tiles, 0, y, 3, twidth, theight));

			y += theight;
			map.put(Direction.DOWN, st.getTiles(tiles, 0, y, 3, twidth, theight));

			y += theight;
			map.put(Direction.LEFT, st.getTiles(tiles, 0, y, 3, twidth, theight));
		}

		hitSprite = st.getSprite("data/sprites/combat/hitted.png");
		blockedSprite = st.getSprite("data/sprites/combat/blocked.png");
		missedSprite = st.getSprite("data/sprites/combat/missed.png");
		eatingSprite = st.getSprite("data/sprites/ideas/eat.png");
		poisonedSprite = st.getSprite("data/sprites/ideas/poisoned.png");
		chokingSprite = st.getSprite("data/sprites/ideas/choking.png");
	}

	/**
	 * Create a new RPEntity2DView
	 */
	public RPEntity2DView() {
		addIconManager(new StatusIconManager(Player.PROP_EATING, eatingSprite,
				HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM, 0, 0) {
					@Override
					boolean show(T rpentity) {
						return rpentity.isEating() && !rpentity.isChoking();
					}} );
		addIconManager(new StatusIconManager(Player.PROP_EATING, chokingSprite,
				HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM, 0, 0) {
					@Override
					boolean show(T rpentity) {
						return rpentity.isChoking();
					}} );
		addIconManager(new StatusIconManager(Player.PROP_POISONED, poisonedSprite,
				HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM, -poisonedSprite.getWidth(), 0) {
					@Override
					boolean show(T rpentity) {
						return rpentity.isPoisoned();
					}} );
	}

	@Override
	public void initialize(final T entity) {
		if (this.entity != null) {
			entity.removeChangeListener(attackListener);
		}
		super.initialize(entity);
		titleSprite = createTitleSprite();
		titleChanged = false;
		entity.addChangeListener(attackListener);
	}
	//
	// RPEntity2DView
	//

	/**
	 * Populate keyed state sprites.
	 * 
	 * @param map
	 *            The map to populate.
	 * @param tiles
	 *            The master sprite.
	 * @param width
	 *            The tile width (in pixels).
	 * @param height
	 *            The tile height (in pixels).
	 */
	protected void buildSprites(final Map<Object, Sprite> map,
			final Sprite tiles, final int width, final int height) {
		int y = 0;
		map.put(Direction.UP, createWalkSprite(tiles, y, width, height));

		y += height;
		map.put(Direction.RIGHT, createWalkSprite(tiles, y, width, height));

		y += height;
		map.put(Direction.DOWN, createWalkSprite(tiles, y, width, height));

		y += height;
		map.put(Direction.LEFT, createWalkSprite(tiles, y, width, height));
	}

	/**
	 * Calculate sprite image offset. Sub-classes may override this to change
	 * alignment.
	 * 
	 * @param spriteWidth
	 *            The sprite width (in pixels).
	 * @param spriteHeight
	 *            The sprite height (in pixels).
	 * @param entityWidth
	 *            The entity width (in pixels).
	 * @param entityHeight
	 *            The entity height (in pixels).
	 */
	@Override
	protected void calculateOffset(final int spriteWidth,
			final int spriteHeight, final int entityWidth,
			final int entityHeight) {
		/*
		 * X alignment centered, Y alignment bottom
		 */
		xoffset = (entityWidth - spriteWidth) / 2;
		yoffset = entityHeight - spriteHeight;
	}

	/**
	 * Create the title sprite.
	 * 
	 * @return The title sprite.
	 */
	protected Sprite createTitleSprite() {
		final String titleType = entity.getTitleType();
		final int adminlevel = entity.getAdminLevel();
		Color nameColor = null;

		if (titleType != null) {
			if (titleType.equals("npc")) {
				nameColor = new Color(200, 200, 255);
			} else if (titleType.equals("enemy")) {
				nameColor = new Color(255, 200, 200);
			}
		}

		if (nameColor == null) {
			if (adminlevel >= 800) {
				nameColor = new Color(200, 200, 0);
			} else if (adminlevel >= 400) {
				nameColor = new Color(255, 255, 0);
			} else if (adminlevel > 0) {
				nameColor = new Color(255, 255, 172);
			} else {
				nameColor = Color.white;
			}
		}

		return TextSprite.createTextSprite(entity.getTitle(), nameColor);
	}

	/**
	 * Extract a walking animation for a specific row. The source sprite
	 * contains 3 animation tiles, but this is converted to 4 frames.
	 * 
	 * @param tiles
	 *            The tile image.
	 * @param y
	 *            The base Y coordinate.
	 * @param width
	 *            The frame width.
	 * @param height
	 *            The frame height.
	 * 
	 * @return A sprite.
	 */
	protected Sprite createWalkSprite(final Sprite tiles, final int y,
			final int width, final int height) {
		final SpriteStore store = SpriteStore.get();

		final Sprite[] frames = new Sprite[4];

		int x = 0;
		frames[0] = store.getTile(tiles, x, y, width, height);

		x += width;
		frames[1] = store.getTile(tiles, x, y, width, height);

		x += width;
		frames[2] = store.getTile(tiles, x, y, width, height);

		frames[3] = frames[1];

		return new AnimatedSprite(frames, 100, false);
	}
	
	/**
	 * Add a new status icon manager.
	 * 
	 * @param manager
	 */
	void addIconManager(StatusIconManager manager) {
		iconManagers.add(manager);
	}

	/**
	 * Draw the floating text indicators (floaters).
	 * 
	 * @param g2d
	 *            The graphics context.
	 * @param x
	 *            The drawn X coordinate.
	 * @param y
	 *            The drawn Y coordinate.
	 * @param width
	 *            The drawn width.
	 */
	protected void drawFloaters(final Graphics2D g2d, final int x, final int y,
			final int width) {
		for (Map.Entry<RPEntity.TextIndicator, Sprite> floater : floaters.entrySet()) {
			final RPEntity.TextIndicator indicator = floater.getKey();
			final Sprite sprite = floater.getValue();
			final int age = indicator.getAge();
			
			final int tx = x + (width - sprite.getWidth()) / 2;
			final int ty = y - (int) (age * 5L / 300L);
			sprite.draw(g2d, tx, ty);
		}
	}

	/**
	 * Draw the entity HP bar.
	 * 
	 * @param g2d
	 *            The graphics context.
	 * @param x
	 *            The drawn X coordinate.
	 * @param y
	 *            The drawn Y coordinate.
	 * @param width
	 *            The drawn width.
	 */
	protected void drawHPbar(final Graphics2D g2d, final int x, final int y,
			final int width) {
		final int barWidth = Math.max(width * 2 / 3, IGameScreen.SIZE_UNIT_PIXELS);

		final int bx = x + ((width - barWidth) / 2);
		final int by = y - 3;
		
		final float hpRatio = entity.getHpRatio();

		final float r = Math.min((1.0f - hpRatio) * 2.0f, 1.0f);
		final float g = Math.min(hpRatio * 2.0f, 1.0f);

		g2d.setColor(Color.gray);
		g2d.fillRect(bx, by, barWidth, 3);

		g2d.setColor(new Color(r, g, 0.0f));
		g2d.fillRect(bx, by, (int) (hpRatio * barWidth), 3);

		g2d.setColor(Color.black);
		g2d.drawRect(bx, by, barWidth, 3);
	}

	/**
	 * Draw the entity status bar. The status bar show the title and HP bar.
	 * 
	 * @param g2d
	 *            The graphics context.
	 * @param x
	 *            The drawn X coordinate.
	 * @param y
	 *            The drawn Y coordinate.
	 * @param width
	 *            The drawn width.
	 */
	protected void drawStatusBar(final Graphics2D g2d, final int x,
			final int y, final int width) {
		drawTitle(g2d, x, y, width);
		drawHPbar(g2d, x, y, width);
	}

	/**
	 * Draw the entity title.
	 * 
	 * @param g2d
	 *            The graphics context.
	 * @param x
	 *            The drawn X coordinate.
	 * @param y
	 *            The drawn Y coordinate.
	 * @param width
	 *            The drawn width.
	 */
	protected void drawTitle(final Graphics2D g2d, final int x, final int y, final int width) {
		if (titleSprite != null) {
			final int tx = x + ((width - titleSprite.getWidth()) / 2);
			final int ty = y - 3 - titleSprite.getHeight();

			titleSprite.draw(g2d, tx, ty);
		}
	}

	/**
	 * Draw the combat indicators. 
	 * 
	 * @param g2d
	 *            The graphics context.
	 * @param x
	 *            The drawn X coordinate.
	 * @param y
	 *            The drawn Y coordinate.
	 * @param width
	 *            The drawn entity width.
	 * @param height
	 *            The drawn entity height.
	 */
	protected void drawCombat(final Graphics2D g2d, final int x,
							  final int y, final int width, final int height) {
		Rectangle2D wrect = entity.getArea();
		final Rectangle srect = new Rectangle((int) (wrect.getX() * IGameScreen.SIZE_UNIT_PIXELS),
				(int) (wrect.getY() * IGameScreen.SIZE_UNIT_PIXELS), 
				(int) (wrect.getWidth() * IGameScreen.SIZE_UNIT_PIXELS),
				(int) (wrect.getHeight() * IGameScreen.SIZE_UNIT_PIXELS));
		
		final double DIVISOR = 1.414213562; // sqrt(2)
		
		if (entity.isBeingAttacked()) {
			// Draw red circle around
			g2d.setColor(Color.red);
			int circleHeight = (int) ((srect.height - 2) / DIVISOR);
			// Avoid showing much smaller area than the creature covers
			circleHeight = Math.max(circleHeight, srect.height - IGameScreen.SIZE_UNIT_PIXELS / 2);
			g2d.drawArc(srect.x, srect.y + srect.height - circleHeight, 
					srect.width + 0, circleHeight, 0, 360);
		}

		if (entity.isAttacking(User.get())) {
			// Draw orange circle around
			g2d.setColor(Color.orange);
			int circleHeight = (int) ((srect.height - 4) / DIVISOR);
			// Avoid showing much smaller area than the creature covers
			circleHeight = Math.max(circleHeight, srect.height - IGameScreen.SIZE_UNIT_PIXELS / 2 - 2);
			g2d.drawArc(srect.x + 1, srect.y + srect.height - circleHeight - 1, 
					srect.width - 2, circleHeight, 0, 360);
		}

		drawAttack(g2d, x, y, width, height);

		if (entity.isDefending()) {
			// Draw bottom right combat icon
			final int sx = srect.x + srect.width - ICON_OFFSET;
			final int sy = y + height - 2 * ICON_OFFSET;

			switch (entity.getResolution()) {
			case BLOCKED:
				blockedSprite.draw(g2d, sx, sy);
				break;

			case MISSED:
				missedSprite.draw(g2d, sx, sy);
				break;

			case HIT:
				hitSprite.draw(g2d, sx, sy);
				break;
			default:
				// cannot happen we are switching on enum
			}
		}
	}
	
	/**
	 * Draw the attacking effect.
	 * 
	 * @param g2d The graphics context 
	 * @param x x coordinate of the attacker
	 * @param y y coordinate of the attacker
	 * @param width width of the attacker
	 * @param height height of the attacker
	 */
	private void drawAttack(final Graphics2D g2d, final int x, final int y, final int width, final int height) {
		if (isAttacking) {
			if (bladeStrikeFrame < NUM_ATTACK_FRAMES) {
				RPEntity target = entity.getAttackTarget();
				
				if (rangedAttack) {
					drawDistanceAttack(g2d, entity, target, x, y, width, height);
				} else {
					drawStrike(g2d, x, y, width, height);
				}
				bladeStrikeFrame++;
			} else {
				bladeStrikeFrame = 0;
				isAttacking = false;
			}
		}
	}
	
	/**
	 * Draw a blade strike.
	 *  
	 * @param g2d
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void drawStrike(final Graphics2D g2d, final int x, final int y,
			final int width, final int height) {
		final Sprite sprite = attackSprite[bladeStrikeFrame];

		final int spriteWidth = sprite.getWidth();
		final int spriteHeight = sprite.getHeight();

		int sx;
		int sy;

		/*
		 * Align swipe image to be 16 px past the facing edge, centering
		 * in other axis.
		 * 
		 * Swipe image is 3x4 tiles, but really only uses partial areas.
		 * Adjust positions to match (or fix images to be
		 * uniform/centered).
		 */
		switch (entity.getDirection()) {
		case UP:
			sx = x + ((width - spriteWidth) / 2) + 16;
			sy = y - 16 - 32;
			break;

		case DOWN:
			sx = x + ((width - spriteWidth) / 2);
			sy = y + height - spriteHeight + 16;
			break;

		case LEFT:
			sx = x - 16;
			sy = y + ((height - spriteHeight) / 2) - 16;
			break;

		case RIGHT:
			sx = x + width - spriteWidth + 16;
			sy = y + ((height - spriteHeight) / 2) - ICON_OFFSET;
			break;

		default:
			sx = x + ((width - spriteWidth) / 2);
			sy = y + ((height - spriteHeight) / 2);
		}

		sprite.draw(g2d, sx, sy);
	}
	
	/**
	 * Draw a distance attack line.
	 * 
	 * @param g2d
	 * @param entity
	 * @param target
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void drawDistanceAttack(final Graphics2D g2d, final RPEntity entity, final RPEntity target,
			final int x, final int y, final int width, final int height) {
		Nature nature = entity.getShownDamageType(); 

		int startX = x + width / 2;
		int startY = y + height / 2;
		int endX = (int) (32 * (target.getX() + target.getWidth() / 2));
		// Target at the upper edge of the occupied area.
		// Getting the EntityView from an entity is tedious, and
		// still does not work reliable for everything (rats)
		int endY = (int) (32 * target.getY()); 

		int yLength = (endY - startY) / NUM_ATTACK_FRAMES;
		int xLength = (endX - startX) / NUM_ATTACK_FRAMES;

		startY += bladeStrikeFrame * yLength;
		endY = startY + yLength;

		startX += bladeStrikeFrame * xLength;
		endX = startX + xLength;

		g2d.setColor(arrowColor.get(nature));
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(ARROW_STROKE);
		g2d.drawLine(startX, startY, endX, endY);
		g2d.setStroke(oldStroke);
	}

	/**
	 * Get the full directional animation tile set for this entity.
	 * 
	 * @return A tile sprite containing all animation images.
	 */
	protected abstract Sprite getAnimationSprite();

	/**
	 * Get the number of tiles in the X axis of the base sprite.
	 * 
	 * @return The number of tiles.
	 */
	protected int getTilesX() {
		return 3;
	}

	/**
	 * Get the number of tiles in the Y axis of the base sprite.
	 * 
	 * @return The number of tiles.
	 */
	protected int getTilesY() {
		return 4;
	}

	/**
	 * Determine is the user can see this entity while in ghostmode.
	 * 
	 * @return <code>true</code> if the client user can see this entity while in
	 *         ghostmode.
	 */
	protected boolean isVisibleGhost() {
		return false;
	}

	//
	// StateEntity2DView
	//

	/**
	 * Populate keyed state sprites.
	 * 
	 * @param entity the entity to build sprites for
	 * @param map
	 *            The map to populate.
	 */
	@Override
	protected void buildSprites(T entity, final Map<Object, Sprite> map) {
		final Sprite tiles = getAnimationSprite();

		width = tiles.getWidth() / getTilesX();
		height = tiles.getHeight() / getTilesY();

		buildSprites(map, tiles, width, height);
		calculateOffset(entity, width, height);
		
		/*
		 * Set icons for a newly created entity.
		 * These need to be after the main sprite is ready, so that the
		 * dimensions are correct for the sprite placement.
		 */
		for (StatusIconManager handler : iconManagers) {
			handler.check(entity);
		}
	}

	//
	// Entity2DView
	//

	/**
	 * Build a list of entity specific actions. <strong>NOTE: The first entry
	 * should be the default.</strong>
	 * 
	 * @param list
	 *            The list to populate.
	 */
	@Override
	protected void buildActions(final List<String> list) {
		if (entity.getRPObject().has("menu")) {
			list.add(entity.getRPObject().get("menu"));
		}
		super.buildActions(list);

		if (entity.isAttackedBy(User.get())) {
			list.add(ActionType.STOP_ATTACK.getRepresentation());
		} else {
			list.add(ActionType.ATTACK.getRepresentation());
		}

		list.add(ActionType.PUSH.getRepresentation());
	}

	/**
	 * Draw the entity.
	 * 
	 * @param g2d
	 *            The graphics context.
	 * @param x
	 *            The drawn X coordinate.
	 * @param y
	 *            The drawn Y coordinate.
	 * @param width
	 *            The drawn entity width.
	 * @param height
	 *            The drawn entity height.
	 */
	@Override
	protected void draw(final Graphics2D g2d, final int x, final int y,
			final int width, final int height) {
		drawCombat(g2d, x, y, width, height);
		super.draw(g2d, x, y, width, height);

		if (Debug.SHOW_ENTITY_VIEW_AREA) {
			g2d.setColor(Color.cyan);
			g2d.drawRect(x, y, width, height);
		}

		drawFloaters(g2d, x, y, width);
	}

	/**
	 * Draw the top layer parts of an entity. This will be on down after all
	 * other game layers are rendered.
	 * 
	 * @param g2d
	 *            The graphics context.
	 * @param x
	 *            The drawn X coordinate.
	 * @param y
	 *            The drawn Y coordinate.
	 * @param width
	 *            The drawn entity width.
	 * @param height
	 *            The drawn entity height.
	 */
	@Override
	protected void drawTop(final Graphics2D g2d, final int x, final int y,
			final int width, final int height) {
		drawStatusBar(g2d, x, y, width);
	}

	/**
	 * Get the height.
	 * 
	 * @return The height (in pixels).
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/**
	 * Get the entity's visibility.
	 * 
	 * @return The visibility value (0-100).
	 */
	@Override
	protected int getVisibility() {
		/*
		 * Hide while in ghostmode.
		 */
		if (entity.isGhostMode()) {
			if (isVisibleGhost()) {
				return super.getVisibility() / 2;
			} else {
				return 0;
			}
		} else {
			return super.getVisibility();
		}
	}

	/**
	 * Get the width.
	 * 
	 * @return The width (in pixels).
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/**
	 * Determines on top of which other entities this entity should be drawn.
	 * Entities with a high Z index will be drawn on top of ones with a lower Z
	 * index.
	 * 
	 * Also, players can only interact with the topmost entity.
	 * 
	 * @return The drawing index.
	 */
	@Override
	public int getZIndex() {
		return 8000;
	}

	@Override
	protected void update() {
		super.update();

		if (titleChanged) {
			titleSprite = createTitleSprite();
			titleChanged = false;
		}
	}

	//
	// EntityChangeListener
	//

	/**
	 * An entity was changed.
	 * 
	 * @param entity
	 *            The entity that was changed.
	 * @param property
	 *            The property identifier.
	 */
	@Override
	public void entityChanged(final T entity, final Object property) {
		super.entityChanged(entity, property);

		if (property == RPEntity.PROP_ADMIN_LEVEL) {
			titleChanged = true;
			visibilityChanged = true;
		} else if (property == RPEntity.PROP_GHOSTMODE) {
			visibilityChanged = true;
		} else if (property == RPEntity.PROP_OUTFIT) {
			representationChanged = true;
		} else if (property == IEntity.PROP_TITLE) {
			titleChanged = true;
		} else if (property == RPEntity.PROP_TITLE_TYPE) {
			titleChanged = true;
		} else if (property == RPEntity.PROP_TEXT_INDICATORS) {
			onFloatersChanged();
		}
		
		for (StatusIconManager handler : iconManagers) {
			handler.check(property, entity);
		}
	}
	
	private void onFloatersChanged() {
		Iterator<RPEntity.TextIndicator> it = entity.getTextIndicators();
		Map<RPEntity.TextIndicator, Sprite> newFloaters = new HashMap<RPEntity.TextIndicator, Sprite>();
		
		while (it.hasNext()) {
			RPEntity.TextIndicator floater = it.next();
			Sprite sprite = floaters.get(floater);
			if (sprite == null) {
				sprite = TextSprite.createTextSprite(floater.getText(), floater.getType().getColor());
			}
			
			newFloaters.put(floater, sprite);
		}
		
		floaters = newFloaters;
	}

	/**
	 * Perform the default action.
	 */
	@Override
	public void onAction() {
		if (entity.getRPObject().has("menu")) {
			onAction(ActionType.USE);
		} else {
			super.onAction();
		}
	}

	/**
	 * Perform an action.
	 * 
	 * @param action
	 *            The action.
	 */
	@Override
	public void onAction(ActionType action) {
		ActionType at = action;
		if (at == null) {
			at = ActionType.USE;
		}
		if (isReleased()) {
			return;
		}
		RPAction rpaction;

		switch (at) {
		case ATTACK:
		case PUSH:
		case USE:
			at.send(at.fillTargetInfo(entity.getRPObject()));
			break;

		case STOP_ATTACK:
			rpaction = new RPAction();

			rpaction.put("type", at.toString());
			rpaction.put("attack", "");

			at.send(rpaction);
			break;

		default:
			super.onAction(at);
			break;
		}
	}
	
	/**
	 * A manager for a status icon. Observes property changes and shows and
	 * hides the icon as needed.
	 */
	abstract class StatusIconManager {
		/** Observed property */
		private final Object property;
		/** Icon sprite */
		private final Sprite sprite;
		private final HorizontalAlignment xAlign;
		private final VerticalAlignment yAlign;
		private final int xOffset, yOffset;
		/**
		 * For tracking changes that can have multiple "true" values (such
		 * as away messages).
		 */
		private boolean wasVisible;
		
		/**
		 * Create a new StatusIconManager.
		 * 
		 * @param property observed property
		 * @param sprite icon sprite
		 * @param xAlign
		 * @param yAlign
		 * @param xOffset
		 * @param yOffset
		 */
		StatusIconManager(Object property, Sprite sprite,
				HorizontalAlignment xAlign, VerticalAlignment yAlign,
				int xOffset, int yOffset) {
			this.property = property;
			this.sprite = sprite;
			this.xAlign = xAlign;
			this.yAlign = yAlign;
			this.xOffset = xOffset;
			this.yOffset = yOffset;
		}
		
		/**
		 * Check if the icon should be shown.
		 * 
		 * @param entity
		 * @return <code>true</code> if the icon should be shown,
		 * 	<code>false</code> otherwise
		 */
		abstract boolean show(T entity);
		
		/**
		 * Check the entity at a property change. Show or hide the icon if
		 * needed.
		 * 
		 * @param changedProperty
		 * @param entity
		 */
		void check(Object changedProperty, T entity) {
			if (property == changedProperty) {
				check(entity);
			}
		}
		
		/**
		 * Check the status of an entity, and show or hide the icon if
		 * needed.
		 * 
		 * @param entity
		 */
		void check(T entity) {
			setVisible(show(entity));
		}
		
		/**
		 * Attach or detach the icon sprite, depending on visibility.
		 * 
		 * @param visible
		 */
		private void setVisible(boolean visible) {
			if (visible) {
				// Avoid attaching the sprite more than once
				if (!wasVisible) {
					attachSprite(sprite, xAlign, yAlign, xOffset, yOffset);
					wasVisible = true;
				}
			} else {
				wasVisible = false;
				detachSprite(sprite);
			}
		}
	}

	/**
	 * Listener for attack status changes.
	 */
	private class AttackListener implements EntityChangeListener<T> {
		public void entityChanged(T entity, Object property) {
			if (property == RPEntity.PROP_ATTACK) {
				Nature nature = entity.getShownDamageType();
				if (nature == null) {
					isAttacking = false;
				} else {
					rangedAttack = entity.isDoingRangedAttack();
					if (!rangedAttack) {
						attackSprite = bladeStrikeSprites.get(nature).get(getState(entity));
					}
					isAttacking = true;
					bladeStrikeFrame = 0;
				}
			}
		}
	}
}
