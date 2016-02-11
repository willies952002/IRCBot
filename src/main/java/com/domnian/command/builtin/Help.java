package com.domnian.command.builtin;

import com.domnian.BotConfiguration;
import com.domnian.api.API;
import com.domnian.command.BotCommand;
import com.domnian.command.CommandManager;
import com.domnian.command.PermissionLevel;
import org.schwering.irc.lib.IRCUser;

import java.lang.reflect.Field;

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
    public void execute(String chan, IRCUser user, String[] args, PermissionLevel level) {
        String nick = user.getNick();
        API.notice(nick, ">>------[ Domnian IRC Bot Core Help ]------<<");
        API.notice(nick, "!help - Display This Help Message (May cause conflicts)");
        API.notice(nick, "!about - Display Information About This Bot");
        API.notice(nick, "!dhelp - Alias Command for !help");
        if ( PermissionLevel.check(level, BotConfiguration.getManagePerm()) ) {
            API.notice(nick, "!restart - Restart The Running Bot Instance");
            API.notice(nick, "!quit - Shutdown The Running Bot Instance");
            API.notice(nick, "!die - Alias Command for !quit");
        }

        API.notice(nick, " ");
        API.notice(nick, ">>------[ Installed Commands ]------<<");
        CommandManager.getInstalledCommands().forEach((cmd, exe) -> {
            try {
                Field desc = exe.getClass().getDeclaredField("desc");
                desc.setAccessible(true);
                API.notice(nick, "!" + cmd + " - " + desc.get(exe));
                desc.setAccessible(false);
            } catch (Exception e) {
                API.notice(nick, "An Internal Exception Occured When Listing Installed Commands - " + cmd);
                e.printStackTrace();
            }
        });
    }

}
