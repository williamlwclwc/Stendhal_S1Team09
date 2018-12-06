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
	
	//all parameters hashmap
	private Map<String, Integer> parameters;
	
	//constructor
	public DefaultAction(String name, String clazzName) {
		try {
			this.name = name;
			this.implementationClass = Class.forName(clazzName);
			this.remainder = "";
			this.parameters = new HashMap<String, Integer>();
		} catch (ClassNotFoundException e) {
			logger.error("Error while creating DefaultAction", e);
		}
	}
	

	public Class<?> getImplementationClass() {
		return implementationClass;
	}
	
	public String getName() {
		return name;
	}

	public void setRemainder(String remainder) {
		this.remainder = remainder;
	}

	public String getRemainder() {
		return remainder;
	}
	

	public void setParameters(Map<String, Integer> parameterValues) {
		this.parameters.putAll(parameterValues);
	}
	
	public Map<String, Integer> getParameters() {
		return parameters;
	}
	
	

}//DefaultAction
