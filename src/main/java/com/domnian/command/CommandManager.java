package com.domnian.command;

import com.domnian.Main;
import com.domnian.Util;
import com.domnian.command.builtin.About;
import com.domnian.command.builtin.Help;
import com.domnian.command.builtin.Quit;
import com.domnian.command.builtin.Restart;
import org.schwering.irc.lib.IRCUser;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

/**
 * ==================================================================
 * Copyright Domnian Dev. (c) 2015. All Rights Reserved
 * Any Code contained within this document, and any associated APIs
 * with similar branding are the sole property of Domnian Dev..
 * Distribution, reproduction, taking snippets, or claiming any
 * contents as your own will break the terms of the license, and
 * void any agreements with you, the third party. Thanks
 * ==================================================================
 */
public class CommandManager {

    private static HashMap<String, BotCommand> commands = new HashMap<>();
    
    public static void executeCommand(String command, IRCUser user) {
        String[] split = command.split(" ");
        String[] args = new String[split.length - 1];
        for ( int i = 1; i < split.length; i++ ) {
            args[i-1] = split[i];
        }
        BotCommand cmd = commands.get(split[0].toLowerCase());
        if ( cmd != null ) {
            cmd.execute(Main.CHANNEL, user, args);
        } else {
            Util.info("No Such Command: " + command);
            //Backend.getConnection().doNotice(user.getNick(), "No Such Command: " + command);
        }
    }

    public static void load() {
        commands.put("help", new Help());
        commands.put("about", new About());
        commands.put("quit", new Quit());
        commands.put("restart", new Restart());
        commands.put("die", new Quit());
        File dir = new File("commands");
        if (!dir.exists()) {
            Util.severe("No commands loaded! Cannot find directory 'commands' - Creating it");
            dir.mkdir();
            return;
        }
        ClassLoader loader;
        try {
            loader = new URLClassLoader(new URL[] { dir.toURI().toURL() },
                    CommandManager.class.getClassLoader());
        } catch (MalformedURLException ex) {
            Util.error("Error while configuring command class loader");
            ex.printStackTrace();
            return;
        }
        for (File file : dir.listFiles()) {
            if (!file.getName().endsWith(".class")) {
                continue;
            }
            String name = file.getName().substring(0,
                    file.getName().lastIndexOf("."));

            try {
                Class<?> clazz = loader.loadClass(name);
                Object object = clazz.newInstance();
                if (!(object instanceof BotCommand)) {
                    Util.info("Not a Command: " + clazz.getSimpleName());
                    continue;
                }
                BotCommand command = (BotCommand) object;
                commands.put(clazz.getSimpleName().toLowerCase(), command);
                Util.info("Loaded Command: " + command.getClass().getSimpleName());
            }
            catch (Exception | Error ex) {
                Util.error("Error loading '" + name + "' command! Command disabled.");
                ex.printStackTrace();
            }
        }
    }

}