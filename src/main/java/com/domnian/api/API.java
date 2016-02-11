/*
 * ==================================================================
 * Copyright Domnian Dev. (c) 2015. All Rights Reserved
 * Any Code contained within this document, and any associated APIs
 * with similar branding are the sole property of Domnian Dev..
 * Distribution, reproduction, taking snippets, or claiming any
 * contents as your own will break the terms of the license, and
 * void any agreements with you, the third party. Thanks
 * ==================================================================
 */
package com.domnian.api;

import com.domnian.Backend;
import com.domnian.Main;
import com.domnian.command.CommandManager;
import com.domnian.command.PermissionLevel;
import org.schwering.irc.lib.IRCConnection;
import org.schwering.irc.lib.IRCUser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class API {

    private static IRCConnection conn;
    private static String ver = "Dev";

    public static void init() {
        conn = Backend.getConnection();
        ver = Backend.getVersion();
    }

    /**
     * Send a message to a Channel or User
     * @param target Channel/Nickname to send the message to - use "Main.CHANNEL" to get default channel;
     * @param message Message to Send.
     */
    public static void privateMessage(String target, String message) {
        conn.doPrivmsg(target, message);
    }

    public static void notice(String target, String message) {
        conn.doNotice(target, message);
    }

    public static void join(String channel) {
        conn.doJoin(channel);
    }

    public static void quit() {
        quit("Bot Disconnecting");
    }

    public static void quit(String message) {
        CommandManager.executeCommand("quit", getExecutor(), Main.CHANNEL, PermissionLevel.SELF);
    }

    public static IRCUser getExecutor() {
        return Main.getExecutor();
    }

    public static String getVersion() {
        return ver;
    }

    public static List<String> loadFile(String path) {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(path);
            Files.lines(file.toPath()).forEach(lines::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void action(String target, String msg) {
        conn.send("PRIVMSG " + target + " :\u0001ACTION " + msg + "\u0001");
    }

}
