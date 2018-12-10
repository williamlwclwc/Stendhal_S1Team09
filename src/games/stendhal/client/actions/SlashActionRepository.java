/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
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

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import games.stendhal.client.core.config.ActionsXMLLoader;
import games.stendhal.client.core.rule.defaultruleset.DefaultAction;

import java.util.List;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Manages Slash Action Objects.
 */
public class SlashActionRepository {

  /** Set of client supported Actions. */
  private static HashMap<String, SlashAction> actions = new HashMap<String, SlashAction>();

  /**
   * Registers the available Action.
   */
  public static void register() {
    URI uri;
    final SlashAction msg = new MessageAction();
    final SlashAction supporta = new SupportAnswerAction();
    final SlashAction who = new WhoAction();
    final SlashAction help = new HelpAction();
    final GroupMessageAction groupMessage = new GroupMessageAction();
	try
	{
	  uri = new URI("/data/conf/actions.xml");
      ActionsXMLLoader actionsLoader = new ActionsXMLLoader(uri);
    
      // If the xml contained any errors, we don't want everything to crash.    
      try
      {
        List<DefaultAction> listOfXMLActions = actionsLoader.load();
        for (DefaultAction eachAction : listOfXMLActions)
        {
          actions.put(eachAction.getName(), eachAction.getAction());
        }
      }
      catch (Exception e)
      {
        // No need to do anything special
    	  e.printStackTrace();
      }
	} catch (URISyntaxException e1) {
		e1.printStackTrace();
	}
    
    actions.put("/", new RemessageAction());
    actions.put("alter", new AlterAction());
    actions.put("atlas", new AtlasBrowserLaunchCommand());

    actions.put("clear", new ClearChatLogAction());
    actions.put("clickmode", new ClickModeAction());
    actions.put("clientinfo", new ClientInfoAction());
    actions.put("commands", help);
    actions.put("config", new ConfigAction());

    actions.put("drop", new DropAction());

    actions.put("cast", new CastSpellAction());

    actions.put("gmhelp", new GMHelpAction());
    actions.put("group", new GroupManagementAction(groupMessage));

    actions.put("help", help);

    actions.put("ignore", new IgnoreAction());

    actions.put("mute", new MuteAction());

    actions.put("names", who);

    actions.put("p", groupMessage);
    actions.put("profile", new ProfileAction());

    actions.put("quit", new QuitAction());

    actions.put("status", new SentenceAction()); // Alias for /sentence
    actions.put("settings", new SettingsAction());

    actions.put("sound", new SoundAction());
    actions.put("volume", new VolumeAction());
    actions.put("vol", new VolumeAction());

    actions.put("postmessage", new StoreMessageAction());

    actions.put("summonat", new SummonAtAction());
    actions.put("summon", new SummonAction());

    actions.put("takescreenshot", new ScreenshotAction());

    actions.putAll(BareBonesBrowserLaunchCommandsFactory.createBrowserCommands());

    /* Movement */
    actions.put("stopwalk", new AutoWalkStopAction());
    actions.put("movecont", new MoveContinuousAction());

    // PvP challenge actions
    actions.put("challenge", new CreateChallengeAction());
    actions.put("accept", new AcceptChallengeAction());
  }

  /**
   * Gets the Action object for the specified Action name.
   *
   * @param name
   *            name of Action
   * @return Action object
   */
  public static SlashAction get(String name) {
    String temp = name.toLowerCase(Locale.ENGLISH);
    return actions.get(temp);
  }

  /**
   * Get all known command names.
   *
   * @return set of commands
   */
  public static Set<String> getCommandNames() {
    return actions.keySet();
  }
}
