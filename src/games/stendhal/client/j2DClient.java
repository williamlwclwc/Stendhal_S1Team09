package games.stendhal.client;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.net.*;
import java.io.*;

import marauroa.client.*;
import marauroa.common.net.*;
import marauroa.common.game.*;

/**
 * The main hook of our game. This class with both act as a manager
 * for the display and central mediator for the game logic. 
 * 
 * Display management will consist of a loop that cycles round all
 * entities in the game asking them to move and then drawing them
 * in the appropriate place. With the help of an inner class it
 * will also allow the player to control the main ship.
 * 
 * As a mediator it will be informed when entities within our game
 * detect events (e.g. alient killed, played died) and will take
 * appropriate game actions.
 * 
 * @author Kevin Glass
 */
public class j2DClient extends Canvas {
	/** The stragey that allows us to use accelerate page flipping */
	private BufferStrategy strategy;
	private boolean gameRunning=true;
	
    private boolean leftPressed=false, rightPressed=false, upPressed=false, downPressed=false;
    private ariannexp client;
    
    protected StaticGameObject staticObjects;
	
	/**
	 * Construct our game and set it running.
	 */
    public j2DClient() {
		// create a frame to contain our game
		JFrame container = new JFrame("Stendhal Java 2D");
		
		// get hold the content of the frame and set up the resolution of the game
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(640,480));
		panel.setLayout(null);
		
		// setup our canvas size and put it into the content of the frame
		setBounds(0,0,640,480);
		panel.add(this);
		
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);
		
		// finally make the window visible 
		container.pack();
		container.setResizable(false);
		container.setVisible(true);
		
		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
                gameRunning=false;
			}
		});
		
		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed
		addKeyListener(new KeyInputHandler());
		
		// request the focus so key events come to us
		requestFocus();

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		// initialise the entities in our game so there's something
		// to see at startup
		initialise();
		
		// Start the main game loop, note: this method will not
		// return until the game has finished running. Hence we are
		// using the actual main thread to run the game.
		gameLoop();
	}
	
	/**
	 * Initialise the starting state of the entities (ship and aliens). Each
	 * entitiy will be added to the overall list of entities in the game.
	 */
    private void initialise() 
      {
      client=new ariannexp()
        {
        protected void onPerception(MessageS2CPerception message)
          {
          }
        
        protected List<TransferContent> onTransferREQ(List<TransferContent> items)
          {
          for(TransferContent item: items)
            {
            item.ack=true;
            }
         
          return items;
          }
        
        protected void onTransfer(List<TransferContent> items)
          {
          System.out.println("Transfering ----");
          for(TransferContent item: items)
            {
            System.out.println(item);
            for(byte ele: item.data) System.out.print((char)ele);
            
            try
              {
              staticObjects.addLayer(new StringReader(new String(item.data)),item.name);
              }
            catch(java.io.IOException e)          
              {
              System.exit(0);
              }
            }
          }
     
        protected void onAvailableCharacters(String[] characters)
          {
          chooseCharacter(characters[0]);
          }
        
        protected void onServerInfo(String[] info)
          {
          }

        protected void onError(int code, String reason)
          {
          System.out.println(reason);
          }
        };

      staticObjects=new StaticGameObject(640,480);
      }
	
	/**
	 * The main game loop. This loop is running during all game
	 * play as is responsible for the following activities:
	 * <p>
	 * - Working out the speed of the game loop to update moves
	 * - Moving the game entities
	 * - Drawing the screen contents (entities, text)
	 * - Updating game events
	 * - Checking Input
	 * <p>
	 */
	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
        int fps=0;
        
//        try
//          {
//          client.connect("127.0.0.1",32160);
//          }
//        catch(SocketException e)
//          {
//          return;
//          }
//      
//        client.login("miguel","password");
        try
          {
          staticObjects.addLayer(new BufferedReader(new FileReader("maps/city_layer0.txt")),"0");
          staticObjects.addLayer(new BufferedReader(new FileReader("maps/city_layer1.txt")),"1");
          }
        catch(java.io.IOException e)          
          {
          System.exit(0);
          }
        
        long oldTime=System.nanoTime();
		
		// keep looping round til the game ends
		while (gameRunning) {
		    fps++;
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			
			// Get hold of a graphics context for the accelerated 
			// surface and blank it out
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			
			// cycle round asking each entity to move itself
            staticObjects.draw(g);
            
    		g.setColor(Color.white);
    		String message="Test of Stendhal running under Java";
			g.drawString(message,(640-g.getFontMetrics().stringWidth(message))/2,200);
			
			// finally, we've completed drawing so clear up the graphics
			// and flip the buffer over
			g.dispose();
			strategy.show();
			
//            client.loop(0);
            
			if(System.nanoTime()-oldTime>1000000000)
			  {
			  oldTime=System.nanoTime();
			  System.out.println("FPS: "+fps);
			  fps=0;
			  }
		}
		
//		client.logout();
		System.exit(0);
	}
	
	/**
	 * A class to handle keyboard input from the user. The class
	 * handles both dynamic input during game play, i.e. left/right 
	 * and shoot, and more static type input (i.e. press any key to
	 * continue)
	 * 
	 * This has been implemented as an inner class more through 
	 * habbit then anything else. Its perfectly normal to implement
	 * this as seperate class if slight less convienient.
	 * 
	 * @author Kevin Glass
	 */
	private class KeyInputHandler extends KeyAdapter {
		/**
		 * Notification from AWT that a key has been pressed. Note that
		 * a key being pressed is equal to being pushed down but *NOT*
		 * released. Thats where keyTyped() comes in.
		 *
		 * @param e The details of the key that was pressed 
		 */
		public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }
  } 
		
		/**
		 * Notification from AWT that a key has been released.
		 *
		 * @param e The details of the key that was released 
		 */
		public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
        }

		/**
		 * Notification from AWT that a key has been typed. Note that
		 * typing a key means to both press and then release it.
		 *
		 * @param e The details of the key that was typed. 
		 */
		public void keyTyped(KeyEvent e) {
			// if we hit escape, then quit the game
			if (e.getKeyChar() == 27) {
                gameRunning=false;
            }
		}
	}
	
	/**
	 * The entry point into the game. We'll simply create an
	 * instance of class which will start the display and game
	 * loop.
	 * 
	 * @param argv The arguments that are passed into our game
	 */
	public static void main(String argv[]) {
        new j2DClient();
	}
}