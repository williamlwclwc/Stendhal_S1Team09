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
package games.stendhal.client.core.rule.defaultruleset.creator;

import java.lang.reflect.Constructor;

import games.stendhal.client.actions.SlashAction;
import games.stendhal.common.core.rule.defaultruleset.creator.AbstractCreator;
import games.stendhal.client.core.rule.defaultruleset.DefaultAction;

abstract class AbstractActionCreator extends AbstractCreator<SlashAction> {

	protected final DefaultAction defaultAction;

	public AbstractActionCreator(DefaultAction defaultAction, Constructor<?> construct) {
		super(construct, "SlashAction");
		this.defaultAction = defaultAction;
	}

}
