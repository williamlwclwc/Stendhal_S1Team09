package games.stendhal.server.maps.ados.library;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.NPC;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.ZonePlayerAndNPCTestImpl;



public class LonJathamNPCTest extends ZonePlayerAndNPCTestImpl{
	private static final String ZONE_NAME = "int_ados_library";
	
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
	public void testDescriptionOfLonJatham() {
		SingletonRepository.getRPWorld();
		final LonJathamNPC LonJathamConfigurator = new LonJathamNPC();

		final StendhalRPZone zone = new StendhalRPZone("int_ados_library");
		LonJathamConfigurator.configureZone(zone, null);
		assertFalse(zone.getNPCList().isEmpty());
		final NPC lonJathamNPC = zone.getNPCList().get(0);
		assertThat(lonJathamNPC.getName(), is("Lon Jatham"));
		assertThat(lonJathamNPC.getDescription(), is("You see Lon Jatham. A total legend in Java."));
	}
	
	@Test
	public void testInteractionWithLonJatham() {
		final SpeakerNPC lonJathamNPC = getNPC("Lon Jatham");
		final Engine lonJathamEngine = lonJathamNPC.getEngine();
		
		//lonJathamNPC = SingletonRepository.getNPCList().get("Lon Jatham");
		//lonJathamEngine = lonJathamNPC.getEngine();
		
		lonJathamEngine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(lonJathamNPC.isTalking());
		assertEquals("Hello. Are you a cs student?", getReply(lonJathamNPC));
		
		lonJathamEngine.step(player, "yes");
		assertEquals("Perfect! I hope in the University Of Manchester. Ask me whatever you want.", getReply(lonJathamNPC));

		
		lonJathamEngine.step(player, "courses");
		assertEquals("We have Java, C, Team Project, Python and many more.", getReply(lonJathamNPC));
		
		lonJathamEngine.step(player, "lecturers");
		assertEquals("Myself Lon Jatham and many more.", getReply(lonJathamNPC));

		lonJathamEngine.step(player, "you");
		assertEquals("I teach Java!", getReply(lonJathamNPC));

		
		//end conversation
		lonJathamEngine.step(player, ConversationPhrases.GOODBYE_MESSAGES.get(0));
		assertFalse(lonJathamNPC.isTalking());
		assertEquals("Bye bye.", getReply(lonJathamNPC));
//		fail("Not implemented");
	}
	
	@Test
	public void testNoQuestionOfPlayer() {
		final SpeakerNPC lonJathamNPC = getNPC("Lon Jatham");
		final Engine lonJathamEngine = lonJathamNPC.getEngine();
		
		//lonJathamNPC = SingletonRepository.getNPCList().get("Lon Jatham");
		//lonJathamEngine = lonJathamNPC.getEngine();
		
		
		lonJathamEngine.step(player, ConversationPhrases.GREETING_MESSAGES.get(0));
		assertTrue(lonJathamNPC.isTalking());
		assertEquals("Hello. Are you a cs student?", getReply(lonJathamNPC));
		
		lonJathamEngine.step(player, "no");
		assertEquals("What a shame. Cs is so much fun in the University Of Manchester. You can ask for information.", getReply(lonJathamNPC));
		
		lonJathamEngine.step(player, "courses");
		assertEquals("We have Java, C, Team Project, Python and many more.", getReply(lonJathamNPC));
		
		lonJathamEngine.step(player, "lecturers");
		assertEquals("Myself Lon Jatham and many more.", getReply(lonJathamNPC));

		lonJathamEngine.step(player, "you");
		assertEquals("I teach Java!", getReply(lonJathamNPC));

		
		//end conversation
		lonJathamEngine.step(player, ConversationPhrases.GOODBYE_MESSAGES.get(0));
		assertFalse(lonJathamNPC.isTalking());
		assertEquals("Bye bye.", getReply(lonJathamNPC));
		
//		fail("Not implemented");

	}
}
