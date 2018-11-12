package games.stendhal.server.maps.ados.city;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;

public class LonJathamNPC implements ZoneConfigurator {

  /**
   * Configure a zone.
   *
   * @param  zone    The zone to be configured.
   * @param  attributes  Configuration attributes.
   */
  @Override
  public void configureZone(StendhalRPZone zone, Map<String, String> attributes)
  {
    buildNPC(zone);
  }

  
  private void buildNPC(StendhalRPZone zone)
  {
    final SpeakerNPC npc = new SpeakerNPC("Lon Jatham")
    {
      @Override
      protected void createPath()
      {
        final List<Node> nodes = new LinkedList<Node>();
        nodes.add(new Node(10, 10));
        nodes.add(new Node(12, 10));
        nodes.add(new Node( 8, 10));
        setPath(new FixedPath(nodes, true));
      }

      @Override
      protected void createDialog()
      {
        addGreeting("Hello. Are you a cs student?");
        // addGreeting("Good morning! Are you a Computer Science student?");
        addReply("yes",
                 "Perfect! I hope in the University Of Manchester. Ask me whatever you want.");
        // addReply("yes",
        //          "Wonderful! I hope at #The #University #of #Manchester. What do you want to know about?"
        addReply("no",
                 "What a shame. Cs is so much fun in the University Of Manchester. You can ask for information.");
        // addReply("no",
        //          "Oh no! CS is much more enjoyable at #The #University #of #Manchester. If you want, I can tell you more about it.");
        addReply("courses",
             "We have Java, C, Team Project, Python and many more.");
        // addReply("courses",
        //      "We have #Object #Oriented #Programming #in #Java, #Algorithms #and #Imperative #Programming, #First #Year #Team #Project, #Distributed #Systems ... and many more!");
        addReply("lecturers",
                 "Myself Lon Jatham and many more.");
        // addReply("lecturers",
        //          "We have the best experts teaching in their fields of research and me, Lon Jatham, of course!");
        // addReply("teachers",
        //          "We have the best experts teaching in their fields of research and me, Lon Jatham, of course!");
        addReply("you",
                 "I teach Java!");
        // addReply("you",
        //          "My name is Lon Jatham and I teach Java using #my own book: #Java #just #in #time!");
        // addReply("Lon Jatham",
        //          "My name is Lon Jatham and I teach Java using #my own book: #Java #just #in #time!");
        addGoodbye("Bye bye.");
        // addGoodbye("Stay fresh!");

      }
    };
    npc.setEntityClass("lonjatham");
    npc.setPosition(10, 10);
    npc.initHP(1000);
    npc.setDescription("You see Lon Jatham. A total legend in Java.");
    zone.add(npc);
  }
}
