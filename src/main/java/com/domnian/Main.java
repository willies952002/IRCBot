package com.domnian;

import com.domnian.api.API;
import com.domnian.command.CommandManager;
import com.domnian.module.ModuleManager;
import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.impl.DefaultIRCUser;

import java.io.IOException;
import java.util.Scanner;

import static java.util.Arrays.asList;

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
public class Main {

    public static String CHANNEL;
    private static ModuleManager manager;
    private static IRCUser executor;

    public static void main(String[] args) {
        OptionParser parser = new OptionParser() {
            {
                acceptsAll(asList("?", "help"), "Show Program Help");
                acceptsAll(asList("gui", "desktop"), "Enable GUI - Not Yet Implemented");
                acceptsAll(asList("v", "version"), "Show Program Version");
            }
        };
        OptionSet options = null;
        try {
            options = parser.parse(args);
        } catch (OptionException e) {
            Util.severe(e.getMessage());
            e.printStackTrace();
        }

        if ((options == null) || (options.has("?"))) {
            try {
                parser.printHelpOn(System.out);
                System.exit(0);
            } catch (IOException e) {
                Util.severe(e.getMessage());
            }
        } else if (options.has("v")) {
            try {
                Util.info("Domnian IRC Bot Version: " + Backend.getVersion());
                System.exit(0);
            } catch (Exception e) {
                Util.severe(e.getMessage());
            }
        } else {
            try {
                Backend.init();
            } catch (Exception e) {
                Util.severe("Unable to Initialize Backend");
            }
        }

        // Load Bot Commands Commands
        CommandManager.load();

        // Create Command Executor
        IRCUser executor = new DefaultIRCUser(BotConfiguration.getNickName(), BotConfiguration.getUserName(), "irc.domnian.com");

        API.init();
        CHANNEL = BotConfiguration.getChannel();
        manager = ModuleManager.load();

        while ( true ) {
            Scanner scanner = new Scanner(System.in);
            if ( scanner.hasNext() ) {
                String msg = scanner.nextLine();
                if ( msg.startsWith("/") ) {
                    String command = msg.substring(1);
                    Util.info("Outbound Command: " + command);
                    String[] arg = command.split(" ");
                    switch (arg[0]) {
                        case "join": Backend.getConnection().doJoin(arg[1]); CHANNEL = arg[1]; break;
                        case "quit": API.quit(); break;
                    }
                } else {
                    Backend.getConnection().doPrivmsg(CHANNEL, msg);
                }
            }
        }

    }

    public static ModuleManager getModuleManager() { return manager; }

    public static IRCUser getExecutor() {
        return executor;
    }
}
