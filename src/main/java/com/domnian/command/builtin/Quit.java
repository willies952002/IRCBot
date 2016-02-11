package com.domnian.command.builtin;

import com.domnian.Backend;
import com.domnian.BotConfiguration;
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
public class Quit extends BotCommand {

    @Override
    public void execute(String chan, IRCUser user, String[] args, PermissionLevel level) {
        if ( PermissionLevel.check(level, BotConfiguration.getManagePerm()) ) {
            API.privateMessage(chan, "Good Bye!");
            Backend.getConnection().doQuit("Bot Disconnecting");
            System.exit(0);
        } else {
            API.notice(user.getNick(), "I'm very sorry, but you don't have permission to do that.");
        }
    }

}
