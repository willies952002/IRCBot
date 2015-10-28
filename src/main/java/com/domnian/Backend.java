package com.domnian;

import org.schwering.irc.lib.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
        if (BotConfiguration.getPass() != null) {
            confBuilder.password(BotConfiguration.getPass());
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
            Util.info("Setting Mode +B");
            connection.doMode(BotConfiguration.getNickName(), "+B");
            Util.info("Sending Private Message to willies952002");
            connection.doPrivmsg("willies952002", "Hello Sir");
        } else {
            Util.severe("Unable to Connect To IRC Server - Exiting");
            System.exit(1);
        }
        Util.line();

    }

    public static IRCConnection getConnection() {
        return connection;
    }

    public static String getVersion() throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream fis = new FileInputStream(new File("pom.xml"));
        byte[] dataBytes = new byte[1024];
        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();
        StringBuffer sb = new StringBuffer("");
        for (byte mdbyte : mdbytes) {
            sb.append(Integer.toString((mdbyte & 0xff) + 0x100, 16).substring(1));
        }
        String impVersion = Backend.class.getPackage().getImplementationVersion();
        return impVersion + "-" + sb.toString().substring(0, 8);
    }
}
