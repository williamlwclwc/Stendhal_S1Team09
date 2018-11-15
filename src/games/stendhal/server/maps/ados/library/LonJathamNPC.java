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
        addGreeting("Good morning! Are you a #CS student?");
        addReply("yes",
                 "Wonderful! I hope at The #University of Manchester. What do you want to know about?");
        
        addReply("no",
                 "Oh no! CS is much more enjoyable at The #University of Manchester. If you want, I can tell you more about it.");
        
        addReply("University",
                "One of the best in the UK and with the best community in the world.");
        
        
        addReply("courses",
             "We have Object Oriented Programming in #Java, #Algorithms and Imperative Programming, First Year Team #Project, #Distributed Systems ... and many more!");
        addReply("lecturers",
                 "We have the best experts teaching in their fields of research and me, Lon Jatham, of course!");
        addReply("teachers",
                 "We have the best experts teaching in their fields of research and me, Lon Jatham, of course!");
        addReply("you",
                 "My name is Lon Jatham and I teach Java using my own book: Java just in #Time!");
        addReply("Lon Jatham",
                 "My name is Lon Jatham and I teach Java using my own book: Java just in #Time!");
       
        addReply("CS",
                 "A very demanding subject.");
        addReply("Java Just in Time",
                 "Using neither the confusing 'objects first' approach, nor the confidence destroying 'objects late' ordering, students are instead taken gently from their natural 'task oriented' view of problem solving, through the basics of programming and then soon onto objects.");
        addReply("Object Oriented Programming in Java",
                 "The course assumes no previous experience of programming, and is based on the book Java Just in Time");
        addReply("Algorithms and Imperative Programming",
                 "This is a two-semester practical introduction to algorithms and data structures, concentrating on devising and using algorithms, including algorithm design and performance");
        addReply("Distributed Systems",
                 "This course unit aims to provide students with a basic understanding of distributed computing, drawing on their general experience as users of distributed applications to inform the discovery, description and classification of fundamental concepts in distributed systems.");
        addReply("First Year Team Project",
                 "This course unit is all about teamwork, communication, and active learning");
        addReply("Engineering",
                 "This course introduces digital logic and its application in computer organisation and design.");
        addReply("Architecture",
                 "This unit aims to introduce computer instruction sets and the binary representation of information");
        addReply("Artificial Intelligence",
                 "The course teaches some of the fundamental techniques used currently in Artificial Intelligence: primarily how to represent knowledge and recognise patters in a probabilistic fashion.");
        addReply("Machine Learning",
                 "Machine learning is concerned with creating learning models that allow a computer to exhibit behaviour that would normally require a human.");
        addGoodbye("Good Morning!");

      }
    };
    npc.setEntityClass("lonjatham4");
    npc.setPosition(3, 8);
    npc.initHP(1000);
    npc.setDescription("You see Lon Jatham. A total legend in Java.");
    zone.add(npc);
  }
}
