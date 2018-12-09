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
package games.stendhal.client.core.rule.defaultruleset;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import games.stendhal.client.actions.SlashAction;
import games.stendhal.client.core.rule.defaultruleset.creator.FullActionCreator;
import games.stendhal.common.core.rule.defaultruleset.creator.AbstractCreator;

public class DefaultAction {
	
	// The logger instance.
	private static final Logger logger = Logger.getLogger(DefaultAction.class);
	
	private AbstractCreator<SlashAction> creator;
	
	// Action name.
	private String name;
	
	private Class<?> implementationClass;

	private String remainder;
	
	private int minimumParameters;
	
	private int maximumParameters;
	
	// All parameters HashMap.
	private Map<String, Integer> parameters;
	
	/**
	 * Creates a new {@link DefaultAction}
	 * @param name the name of that action
	 * @param clazzName class name
	 */
	public DefaultAction(String name, String clazzName) {
		try {
			this.name = name;
			this.implementationClass = Class.forName(clazzName);
			this.remainder = "";
			this.parameters = new HashMap<String, Integer>();
			this.minimumParameters = 0;
			this.maximumParameters = 0;
			this.buildCreator(implementationClass);
		} catch (ClassNotFoundException e) {
			logger.error("Error while creating DefaultAction", e);
		}
	}

	private void buildCreator(final Class< ? > implementation) {
		try {
			Constructor< ? > construct;
			construct = implementation.getConstructor(
					new Class[] { String.class, Map.class, String.class, int.class, int.class });

			this.creator = new FullActionCreator(this, construct);
		} catch (final NoSuchMethodException ex) {
			logger.error("No matching full constructor for SlashAction found.", ex);
		}
	}

	/**
	 * Creates a new instance using the configured implementation class of that action
	 *
	 * @return an instance of the specified implementation class
	 */
	public SlashAction getAction() {
		if (creator == null) {
			return null;
		}
		return creator.create();
	}

	/**
	 * @return the class object specified in the xml configuration
	 */
	public Class<?> getImplementationClass() {
		return implementationClass;
	}
	
	//method to get the name of the action
	public String getName() {
		return name;
	}

	//method to set a remainder if there is one
	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}

	//method to access remainder
	public String getRemainder() {
		return remainder;
	}
	
	// Methods to put all parameters from a hash map to the default action's one.
	public void setParameters(Map<String, Integer> parameterValues) {
		this.parameters.putAll(parameterValues);
	}
	
	// Accessor method for the parameters.
	public Map<String, Integer> getParameters() {
		return parameters;
	}

	// Method to set the minimum parameters.
	public void setMinimumParameters(int minParam) {
		this.minimumParameters = minParam;
	}

	// Method to access minimum parameters.
	public int getMinimumParameters()
	{
		return minimumParameters;
	}
	
	// Method to set maximum parameters.
	public void setMaximumParameters(int maxParam) {
		this.maximumParameters = maxParam;
	}
	
	// Method to access maximum parameters.
	public int getMaximumParameters()
	{
		return maximumParameters;
	}
	

}//DefaultAction
