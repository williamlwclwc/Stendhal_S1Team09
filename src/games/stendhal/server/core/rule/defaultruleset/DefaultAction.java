package games.stendhal.server.core.rule.defaultruleset;


import org.apache.log4j.Logger;


public class DefaultAction {
	private static final Logger logger = Logger.getLogger(DefaultAction.class);

	private String name;
	
	private Class<?> implementationClass;
	
	public DefaultAction() {

	}

	
	public String getName() {
		return name;
	}
	
	

}
