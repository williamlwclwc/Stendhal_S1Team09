package games.stendhal.client.core.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import games.stendhal.client.core.rule.defaultruleset.DefaultAction;
import games.stendhal.server.core.config.SpellXMLLoader;



public class ActionsXMLLoader extends DefaultHandler {
	
	protected URI uri;
	
	private static final Logger logger = Logger.getLogger(ActionsXMLLoader.class);

	//name of action
	private String name;
	
	//parameter instead of attributes
	private Map<String, Integer> parameterValues;
	private boolean parameterTagFound;
	
	private String implementation;
	
	private String remainder;
	private boolean remainderFlag;
		
	private List<DefaultAction> loadedActions;
	
	
	
	public ActionsXMLLoader(final URI uri) {
		this.uri = uri;
	}


	public List<DefaultAction> load() throws SAXException {
		loadedActions = new LinkedList<DefaultAction>();
		// Use the default (non-validating) parser
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Parse the input
			final SAXParser saxParser = factory.newSAXParser();

			final InputStream is = SpellXMLLoader.class.getResourceAsStream(uri.getPath());

			if (is == null) {
				throw new FileNotFoundException("cannot find resource '" + uri
						+ "' in classpath");
			}
			try {
				saxParser.parse(is, this);
			} finally {
				is.close();
			}
		} catch (final ParserConfigurationException t) {
			logger.error(t);
		} catch (final IOException e) {
			logger.error(e);
			throw new SAXException(e);
		}
		return loadedActions;
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if(qName.equals("action")) {
			name = attributes.getValue("name");
		}
		if(qName.equals("implementation")) {
			implementation = attributes.getValue("class-name");
		}
		if(qName.equals("remainder")) {
			remainderFlag = true;
			remainder = attributes.getValue("name");
		}
		if(qName.equals("parameters")) {
			parameterTagFound = true;
			parameterValues= new HashMap<String, Integer>();
		}
		if(qName.equals("parameter") && parameterTagFound) {
			String tempName = null;
			int index = -1;
			
//			for (int i = 0; i < attributes.getLength(); i++) {
//				if (attributes.getQName(i).equals("name")) {
//					tempName = attributes.getValue(i);
//				} else if (attributes.getQName(i).equals("index")) {
//					index = Integer.parseInt(attributes.getValue(i));
//				}
//			}
			tempName = attributes.getValue("name");
			index = Integer.parseInt(attributes.getValue("index"));
		
			if ((tempName != null) && (index != -1)) {
				logger.debug(tempName + ":" + index );
				
				//put as key the tempName(type, x, y etc) and index as value
				parameterValues.put(tempName, index);
			}
				
		}
	}//startElements
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if(qName.equals("action")) {
			DefaultAction action = new DefaultAction(name, implementation);
			if(remainderFlag) {
				action.setRemainder(remainder);
				remainderFlag = false;
			}
			if(parameterValues != null && !parameterValues.isEmpty()) {
				action.setParameters(parameterValues);
			}
			loadedActions.add(action);
		}
		if(qName.equals("parameters")) {
			parameterTagFound = false;
		}
	}//endElement
	
	

}//ActionsXMLLoader