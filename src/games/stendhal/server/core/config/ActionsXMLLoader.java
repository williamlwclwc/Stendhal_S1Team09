package games.stendhal.server.core.config;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

import org.xml.sax.helpers.DefaultHandler;



public class ActionsXMLLoader extends DefaultHandler {
	
	protected URI uri;
	
	public ActionsXMLLoader(final URI uri) {
		this.uri = uri;
	}


	public List<DefaultAction> load() {
		
		final List<DefaultAction> list = new LinkedList<DefaultAction>();
		// TODO Auto-generated method stub
		return list;
	}
	
	
	

}
