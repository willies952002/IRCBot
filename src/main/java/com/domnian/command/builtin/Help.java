package com.domnian.command.builtin;

import com.domnian.command.BotCommand;
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
public class Help extends BotCommand {

    @Override
    public void execute(String chan, IRCUser user, String[] args) {
        String nick = user.getNick();
        conn.doNotice(nick, ">>------[ Domnian IRC Bot Core Help ]------<<");
        conn.doNotice(nick, "!help - Display This Help Message");
        conn.doNotice(nick, "!about - Display Information About This Bot");
        conn.doNotice(nick, "!quit - Shutdown The Running Bot Instance");
        conn.doNotice(nick, "!restart - Restart The Running Bot Instance");
        conn.doNotice(nick, "!die - Alias Command for !quit");
    }

}
