package games.stendhal.server.maps.ados.library;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.ZonePlayerAndNPCTestImpl;



public class LonJathamNPCTest extends ZonePlayerAndNPCTestImpl{
	private static final String ZONE_NAME = "testzone";
	
	//private Player player = null;
	//private SpeakerNPC lonJathamNPC = null;
	//private Engine lonJathamEngine = null;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setupZone(ZONE_NAME, new LonJathamNPC());
	}
	
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
//		final StendhalRPZone zone = new StendhalRPZone("int_fado_tavern");
//		MockStendlRPWorld.get().addRPZone(zone);
//		new LonJathamNPC().configureZone(zone, null);
		
		//player = createPlayer("player");
		
	}
	
	public LonJathamNPCTest() {
		setNpcNames("Lon Jatham");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new LonJathamNPC(), ZONE_NAME);
		
	}

	
	@Test
	public void testInteractionWithLonJatham() {
		final SpeakerNPC lonJathamNPC = getNPC("Lon Jatham");
		final Engine lonJathamEngine = lonJathamNPC.getEngine();
		
		lonJathamEngine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		
		assertTrue(lonJathamNPC.isTalking());
		assertEquals("Good morning! Are you a #Computer #Science student?", getReply(lonJathamNPC));
		
		lonJathamEngine.step(player, "yes");
		assertEquals("Wonderful! I hope at #The #University #of #Manchester. What do you want to know about?", getReply(lonJathamNPC));

		lonJathamEngine.step(player, "courses");
		assertEquals("We have #Object #Oriented #Programming #in #Java, #Algorithms #and #Imperative #Programming, #First #Year #Team #Project, #Distributed #Systems ... and many more!", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "lecturers");
		assertEquals("We have the best experts teaching in their fields of research and me, Lon Jatham, of course!", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "teachers");
		assertEquals("We have the best experts teaching in their fields of research and me, Lon Jatham, of course!", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "you");
		assertEquals("My name is Lon Jatham and I teach Java using #my own book: #Java #just #in #time!", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Lon Jatham");
		assertEquals("My name is Lon Jatham and I teach Java using #my own book: #Java #just #in #time!", getReply(lonJathamNPC));
		//lonJathamEngine.step(player, "The University of Manchester");
		//assertEquals("One of the best in the UK and with the best community in the world.", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Computer Science");
		assertEquals("A very demanding subject.", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Java Just in time");
		assertEquals("Using neither the confusing 'objects first' approach, nor the confidence destroying 'objects late' ordering, students are instead taken gently from their natural 'task oriented' view of problem solving, through the basics of programming and then soon onto objects.", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Object Oriented Programming in Java");
		assertEquals("The course assumes no previous experience of programming, and is based on the book #Java #Just #in #Time", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Algorithms and Imperative Programming");
		assertEquals("This is a two-semester practical introduction to algorithms and data structures, concentrating on devising and using algorithms, including algorithm design and performance", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Distributed Systems");
		assertEquals("This course unit aims to provide students with a basic understanding of distributed computing, drawing on their general experience as users of distributed applications to inform the discovery, description and classification of fundamental concepts in distributed systems.", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "First Year Team Project");
		assertEquals("This course unit is all about teamwork, communication, and active learning", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Computer Engineering");
		assertEquals("This course introduces digital logic and its application in computer organisation and design.", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Computer Architecture");
		assertEquals("This unit aims to introduce computer instruction sets and the binary representation of information", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Artificial Intelligence");
		assertEquals("The course teaches some of the fundamental techniques used currently in Artificial Intelligence: primarily how to represent knowledge and recognise patters in a probabilistic fashion.", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Machine Learning");
		assertEquals("Machine learning is concerned with creating learning models that allow a computer to exhibit behaviour that would normally require a human.", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "		");
				assertEquals("", getReply(lonJathamNPC));
		//end conversation
		lonJathamEngine.step(player, ConversationPhrases.GOODBYE_MESSAGES.get(0));
		assertFalse(lonJathamNPC.isTalking());
		assertEquals("Bye bye.", getReply(lonJathamNPC));
	}
	
	@Test
	public void testNoQuestionOfPlayer() {
		final SpeakerNPC lonJathamNPC = getNPC("Lon Jatham");
		final Engine lonJathamEngine = lonJathamNPC.getEngine();
		
		
		lonJathamEngine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(lonJathamNPC.isTalking());
		
		assertEquals("Good morning! Are you a #Computer #Science student?", getReply(lonJathamNPC));
		
		lonJathamEngine.step(player, "no");
		assertEquals("Oh no! CS is much more enjoyable at #The #University #of #Manchester. If you want, I can tell you more about it.", getReply(lonJathamNPC));
		
		lonJathamEngine.step(player, "courses");
		assertEquals("We have #Object #Oriented #Programming #in #Java, #Algorithms #and #Imperative #Programming, #First #Year #Team #Project, #Distributed #Systems ... and many more!", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "lecturers");
		assertEquals("We have the best experts teaching in their fields of research and me, Lon Jatham, of course!", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "teachers");
		assertEquals("We have the best experts teaching in their fields of research and me, Lon Jatham, of course!", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "you");
		assertEquals("My name is Lon Jatham and I teach Java using #my own book: #Java #just #in #time!", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Lon Jatham");
		assertEquals("My name is Lon Jatham and I teach Java using #my own book: #Java #just #in #time!", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "The University of Manchester");
		assertEquals("One of the best in the UK and with the best community in the world.", getReply(lonJathamNPC));
		lonJathamEngine.step(player, "Computer Science");
		assertEquals("A very demanding subject.", getReply(lonJathamNPC));
		//end conversation
		lonJathamEngine.step(player, ConversationPhrases.GOODBYE_MESSAGES.get(0));
		assertFalse(lonJathamNPC.isTalking());
		assertEquals("Bye bye.", getReply(lonJathamNPC));

	}
}
