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
package com.domnian.command.builtin;

import com.domnian.api.API;
import com.domnian.command.BotCommand;
import com.domnian.command.PermissionLevel;
import org.schwering.irc.lib.IRCUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Time extends BotCommand {

    @Override
    public void execute(String chan, IRCUser user, String[] args, PermissionLevel level) {
        SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss a, Z");
        Date today = Calendar.getInstance().getTime();
        API.notice(user.getNick(), "The Current Time Is: " + df.format(today));
    }

}
