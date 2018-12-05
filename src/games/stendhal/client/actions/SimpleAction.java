package games.stendhal.client.actions;

import java.util.HashMap;
import java.util.Map;

import games.stendhal.client.ClientSingletonRepository;
import games.stendhal.common.StringHelper;
import marauroa.common.game.RPAction;

public class SimpleAction implements SlashAction {
	
	final private String name; // name of the action
	// a hash map of parameters
	final private Map<String, Integer> parameters = new HashMap<String, Integer>(); 
	final private String remainderName; // name of remainder
	final private int max; // maximum parameters
	final private int min; // minimum parameters
	
	// SimpleAction constructor
	SimpleAction(String name, HashMap<String, Integer> parameters, String remainderName, int max, int min) {
		this.name = name;
		this.parameters.putAll(parameters);
		this.remainderName = remainderName;
		this.max = max;
		this.min = min;
	}
	
	@Override
	public boolean execute(String[] params, String remainder) {
		final RPAction action = new RPAction();
		// see if the given parameters are valid
		if(params != null && (params.length > max || params.length < min)) {
			return false;
		}
		action.put("type", name);
		// if the action has remainder
		if(remainderName != null) {
			action.put(remainderName, StringHelper.unquote(remainder));
		}
		// if the action has parameters
		if(params != null){
			for(Map.Entry<String, Integer> entry : parameters.entrySet()) {
				action.put(entry.getKey(), params[entry.getValue()]);
			}
		}
		
		ClientSingletonRepository.getClientFramework().send(action);
		
		return true;
	}

	@Override
	public int getMaximumParameters() {
		return max; // return maximum of parameters
	}

	@Override
	public int getMinimumParameters() {
		return min; // return minimum of parameters
	}

}
