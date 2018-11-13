package games.stendhal.server.maps.ados.library;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;

public class LonJathamNPC implements ZoneConfigurator
{

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
        nodes.add(new Node( 3,  8));
        nodes.add(new Node( 6,  8));
        nodes.add(new Node( 6, 10));
        nodes.add(new Node( 8, 10));
        nodes.add(new Node( 5, 10));
        nodes.add(new Node( 5,  8));
        nodes.add(new Node( 7,  8));
        nodes.add(new Node( 7,  6));
        nodes.add(new Node( 6,  6));
        nodes.add(new Node( 6,  3));
        nodes.add(new Node( 6,  8));
        setPath(new FixedPath(nodes, true));
      }

      @Override
      protected void createDialog()
      {
        addGreeting("Hello. Are you a cs student?");
        // addGreeting("Good morning! Are you a #Computer #Science student?");
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
        // addReply("The University of Manchester",
        //          "One of the best in the UK and with the best community in the world.");
        // addReply("Computer Science",
        //          "A very demanding subject.");
        // Java Just in time
        // Object ORiented Programming in Java
        // Algorithms and Imperative Programming
        // Distributed Systems
        // First Year Team Project
        addGoodbye("Bye bye.");
        // addGoodbye("Stay fresh!");

      }
    };
    npc.setEntityClass("lonjatham4");
    npc.setPosition(3, 8);
    npc.initHP(1000);
    npc.setDescription("You see Lon Jatham. A total legend in Java.");
    zone.add(npc);
  }
}
