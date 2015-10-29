package com.domnian.command.builtin;

import com.domnian.Backend;
import com.domnian.command.BotCommand;
import org.schwering.irc.lib.IRCConnection;
import org.schwering.irc.lib.IRCUser;

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
public class Restart implements BotCommand {
    @Override
    public void execute(String chan, IRCUser user) {
        try {
            IRCConnection conn = Backend.getConnection();
            conn.doPrivmsg(chan, "Restaring - I'll Be Right Back!");
            Runtime.getRuntime().exec("bash run.sh");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
