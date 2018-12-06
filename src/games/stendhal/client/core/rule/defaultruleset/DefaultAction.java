package games.stendhal.client.core.rule.defaultruleset;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class DefaultAction {
	
	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(DefaultAction.class);
	
	/** Action name. */
	private String name;
	
	private Class<?> implementationClass;

	private String remainder;
	
	private int minimumParameters;
	
	private int maximumParameters;
	
	//all parameters hashmap
	private Map<String, Integer> parameters;
	
	//constructor
	public DefaultAction(String name, String clazzName) {
		try {
			this.name = name;
			this.implementationClass = Class.forName(clazzName);
			this.remainder = "";
			this.parameters = new HashMap<String, Integer>();
			this.minimumParameters = 0;
			this.maximumParameters = 0;
		} catch (ClassNotFoundException e) {
			logger.error("Error while creating DefaultAction", e);
		}
	}
	
	//method to get the implementation class
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
	
	//methods to put all parameters from a hash map to the default action's one
	public void setParameters(Map<String, Integer> parameterValues) {
		this.parameters.putAll(parameterValues);
	}
	
	////accessor method for the parameters
	public Map<String, Integer> getParameters() {
		return parameters;
	}

	//method to set the minimum parameters
	public void setMinimumParameters(int minParam) {
		this.minimumParameters = minParam;
	}

	//method to access minimum parameters
	public int getMinimumParameters()
	{
		return minimumParameters;
	}
	
	//method to set maximum parameters
	public void setMaximumParameters(int maxParam) {
		this.maximumParameters = maxParam;
	}
	
	//method to access maximum parameters
	public int getMaximumParameters()
	{
		return maximumParameters;
	}
	

}//DefaultAction
