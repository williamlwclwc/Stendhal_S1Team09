package games.stendhal.client.actions;

import java.util.HashMap;
import java.util.Map;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.common.StringHelper;
import marauroa.common.game.RPAction;

public class SimpleAction implements SlashAction{
	
	final String name; // name of the action
	// a hash map of parameters
	Map<String, Integer> Parameters = new HashMap<String, Integer>(); 
	final String remainderName; // name of remainder
	final int Max; // maximum parameters
	final int Min; // minimum parameters
	
	// SimpleAction constructor
	SimpleAction(String name, HashMap<String, Integer> Parameters, String remainderName, int Max, int Min)
	{
		this.name = name;
		this.Parameters.putAll(Parameters);
		this.remainderName = remainderName;
		this.Max = Max;
		this.Min = Min;
	}
	
	@Override
	public boolean execute(String[] params, String remainder) {
		final RPAction action = new RPAction();
		// see if the given parameters are valid
		if(params != null && (params.length > Max || params.length < Min))
		{
			return false;
		}
		action.put("type", name);
		// if the action has remainder
		if(remainderName != null)
		{
			action.put(remainderName, StringHelper.unquote(remainder));
		}
		// if the action has parameters
		if(params != null)
		{
			for(Map.Entry<String, Integer> entry : Parameters.entrySet())
			{
				action.put(entry.getKey(), entry.getValue());
			}
		}
		
		ClientSingletonRepository.getClientFramework().send(action);
		
		return true;
	}

	@Override
	public int getMaximumParameters() {
		return Max; // return maximum of parameters
	}

	@Override
	public int getMinimumParameters() {
		return Min; // return minimum of parameters
	}

}
