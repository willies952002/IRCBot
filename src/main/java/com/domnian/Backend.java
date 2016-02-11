package com.domnian;

import org.schwering.irc.lib.IRCConfigBuilder;
import org.schwering.irc.lib.IRCConnection;
import org.schwering.irc.lib.IRCConnectionFactory;

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
public class Backend {

    private static IRCConnection connection;

    public static void init() throws Exception {
        Util.line();
        Util.info("Loading IRC Bot Version: " + getVersion());
        Util.line();
        Util.info("Loading Configuration from File: config.json");
        BotConfiguration.load("config.json");
        Util.info("Creating IRC Configuration");
        IRCConfigBuilder confBuilder = IRCConfigBuilder.newBuilder();
        confBuilder.encoding("UTF-8");
        confBuilder.host(BotConfiguration.getHost());
        confBuilder.port(BotConfiguration.getPort());
        confBuilder.autoPong(true);
        confBuilder.nick(BotConfiguration.getNickName());
        confBuilder.realname(BotConfiguration.getRealName());
        confBuilder.username(BotConfiguration.getUserName());
        if (BotConfiguration.getServerPass() != null) {
            confBuilder.password(BotConfiguration.getServerPass());
        }
        confBuilder.stripColors(true);
        Util.info("Creating IRC Connection");
        connection = IRCConnectionFactory.newConnection(confBuilder.build());
        Util.info("Registering IRC Events");
        connection.addIRCEventListener(new IRCEvents());
        Util.line();
        Util.info("Connecting to IRC Server");
        connection.connect();
        if ( connection.isConnected() ) {
            Util.success("Connected To IRC Server");
            Util.info("Connection Local Host: " + connection.getLocalAddress().getHostAddress());
            Util.info("Connection Nick: " + connection.getNick());
            Util.info("Connection Port: " + connection.getPort());
            Util.info("Joining Channel " + BotConfiguration.getChannel());
            connection.doJoin(BotConfiguration.getChannel());
        } else {
            Util.severe("Unable to Connect To IRC Server - Exiting");
            System.exit(1);
        }
        Util.line();

    }

    public static IRCConnection getConnection() {
        return connection;
    }

    public static String getVersion() {
        return Main.class.getPackage().getImplementationVersion();
    }

    public static void reconnect() throws Exception {
        Util.info("Trying to Reconnecting...");
        if ( connection.isConnected() ) {
            Util.error("Already Connected, Aborting Reconnection!");
        } else {
            connection.connect();
            if ( connection.isConnected() ) {
                Util.success("Successfully Reconnected to " + BotConfiguration.getHost());
            }
        }
    }

}
