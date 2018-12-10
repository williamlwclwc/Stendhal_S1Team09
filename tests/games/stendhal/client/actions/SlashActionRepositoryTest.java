/***************************************************************************
 *                   (C) Copyright 2003-2015 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.client.actions;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

public class SlashActionRepositoryTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SlashActionRepository.register();
	}

	/**
	 * Tests for get.
	 */
	@Test
	public void testGet() {
		assertThat(SlashActionRepository.get("alter").toString(), containsString("AlterAction"));
		assertThat(SlashActionRepository.get("/").toString(), containsString("RemessageAction"));
		assertThat(SlashActionRepository.get("addbuddy").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("adminlevel").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("altercreature").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("alterquest").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("answer").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("away").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("drop").toString(), containsString("DropAction"));
		assertThat(SlashActionRepository.get("gag").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("gmhelp").toString(), containsString("GMHelpAction"));
		assertThat(SlashActionRepository.get("grumpy").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("help").toString(), containsString("HelpAction"));
		assertThat(SlashActionRepository.get("ignore").toString(), containsString("IgnoreAction"));
		assertThat(SlashActionRepository.get("inspect").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("invisible").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("jail").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("msg").toString(), containsString("MessageAction"));
		assertThat(SlashActionRepository.get("quit").toString(), containsString("QuitAction"));
		assertThat(SlashActionRepository.get("removebuddy").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("sound").toString(), containsString("SoundAction"));
		assertThat(SlashActionRepository.get("summonat").toString(), containsString("SummonAtAction"));
		assertThat(SlashActionRepository.get("summon").toString(), containsString("SummonAction"));
		assertThat(SlashActionRepository.get("supportanswer").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("support").toString(), containsString("SupportAction"));
		assertThat(SlashActionRepository.get("teleport").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("teleportto").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("tellall").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("tell").toString(), containsString("MessageAction"));
		assertThat(SlashActionRepository.get("where").toString(), containsString("SimpleAction"));
		assertThat(SlashActionRepository.get("who").toString(), containsString("SimpleAction"));
	}

}
