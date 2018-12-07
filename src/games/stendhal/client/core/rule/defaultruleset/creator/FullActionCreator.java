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
import java.lang.reflect.InvocationTargetException;

import games.stendhal.client.core.rule.defaultruleset.DefaultAction;
import games.stendhal.client.actions.SlashAction;

public class FullActionCreator extends AbstractActionCreator {

	public FullActionCreator(DefaultAction defaultAction, Constructor<?> construct) {
		super(defaultAction, construct);
	}

	@Override
	protected SlashAction createObject() throws IllegalAccessException,
			InstantiationException, InvocationTargetException {
		return (SlashAction) construct.newInstance(defaultAction.getName(),
				defaultAction.getParameters(), defaultAction.getRemainder(),
				defaultAction.getMaximumParameters(), defaultAction.getMinimumParameters());
	}

}
