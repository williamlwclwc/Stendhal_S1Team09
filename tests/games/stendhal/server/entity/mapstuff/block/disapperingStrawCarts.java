/***************************************************************************
 *               (C) Copyright 2003-2013 - Faiumoni e.V                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.mapstuff.block;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.common.Direction;

import games.stendhal.server.core.engine.StendhalRPZone;
//import games.stendhal.server.core.rp.StendhalRPAction;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import utilities.RPClass.BlockTestHelper;



public class disapperingStrawCarts {

	@BeforeClass
	public static void beforeClass() {
		BlockTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
        MockStendlRPWorld.get();
	}

	
	@Test
	public void test() {
		 //StendhalRPZone z = SingletonRepository.getRPWorld().getZone("0_ados_forest_w2");
		         Block b = new Block(true);
		         b.setPosition(9, 5);
		         
		         StendhalRPZone z = new StendhalRPZone("test", 10, 10);
		         z.add(b);
		         Player p = PlayerTestHelper.createPlayer("pusher");
		         z.setEntryPoint(9, 5);
		         p.setDirection(Direction.LEFT);
		         z.placeObjectAtEntryPoint(p);
		         
		     
		
		         assertThat(Integer.valueOf(b.getX()), is(Integer.valueOf(8)));
		 		 assertThat(Integer.valueOf(b.getY()), is(Integer.valueOf(5)));
		 		 
				 assertThat(Integer.valueOf(p.getY()), is(Integer.valueOf(5)));

		        assertThat(Integer.valueOf(p.getX()), is(Integer.valueOf(9)));        
		


	}


				
}

