package com.domnian.command.builtin;

import com.domnian.Backend;
import com.domnian.api.API;
import com.domnian.command.BotCommand;
import com.domnian.command.PermissionLevel;
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
public class About extends BotCommand {

    public void execute(String chan, IRCUser user, String[] args, PermissionLevel ignored) {
        try {
            String nick = user.getNick();
            API.notice(nick, "About This Bot:");
            API.notice(nick, "  Version: " + Backend.getVersion());
            API.notice(nick, "  Developer: willies952002");
            API.notice(nick, "  Source: https://github.com/willies952002/IRCBot");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
