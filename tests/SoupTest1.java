import static org.junit.Assert.*;

import org.junit.Test;


import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import games.stendhal.server.maps.fado.tavern.MaidNPC;
import games.stendhal.server.maps.quests.AbstractQuest;
import games.stendhal.server.maps.quests.Soup;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;
import utilities.RPClass.ItemTestHelper;

public class SoupTest1 {
	
	private double karma1 = 0;

	
	private Player player = null;

	private SpeakerNPC npc = null;
	private Engine en = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		ItemTestHelper.generateRPClasses();
	}
	
	@Before
	public void setUp() {
		final StendhalRPZone zone = new StendhalRPZone("int_fado_tavern");
		MockStendlRPWorld.get().addRPZone(zone);
		new MaidNPC().configureZone(zone, null);


		AbstractQuest quest = new Soup();
		quest.addToWorld();

		player = PlayerTestHelper.createPlayer("snow");
		karma1 = player.getKarma();
	}
	

	@Test
	public void test() {
		npc = SingletonRepository.getNPCList().get("Old Mother Helena");
		en = npc.getEngine();
		player.setXP(100);
		

		
		PlayerTestHelper.equipWithItem(player, "carrot");
		PlayerTestHelper.equipWithItem(player, "spinach");
		PlayerTestHelper.equipWithItem(player, "courgette");
		PlayerTestHelper.equipWithItem(player, "collard");
		PlayerTestHelper.equipWithItem(player, "cauliflower");
		PlayerTestHelper.equipWithItem(player, "broccoli");
		PlayerTestHelper.equipWithItem(player, "leek");
		PlayerTestHelper.equipWithItem(player, "salad");
		PlayerTestHelper.equipWithItem(player, "onion");
		

		
		en.step(player, "hi");
		assertEquals("Hello, stranger. You look weary from your travels. I know what would #revive you.", getReply(npc));
		en.step(player, "revive");
		assertEquals("My special soup has a magic touch. I need you to bring me the #ingredients.", getReply(npc));
		en.step(player, "ingredients");
		assertEquals("I need 9 ingredients before I make the soup: #carrot, #spinach, #courgette, #collard, #salad, #onion, #cauliflower, #broccoli, and #leek. Will you collect them?", getReply(npc));
		
		en.step(player, "yes");
		assertEquals("You made a wise choice. Do you have anything I need already?", getReply(npc));
		en.step(player, "yes");
		assertEquals("What did you bring?", getReply(npc));
		en.step(player, "everything");
		
		assertEquals("The soup's on the table for you, it will heal you. Tell me if I can help you with anything else.", getReply(npc));
		assertEquals(150, player.getXP());
		karma1 = karma1 + 5.0;
		assertEquals(karma1, player.getKarma(), 1);
				
		
	}

}