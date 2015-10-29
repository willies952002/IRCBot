package com.domnian;

import com.domnian.command.CommandManager;
import com.domnian.command.builtin.About;
import org.schwering.irc.lib.IRCConnection;
import org.schwering.irc.lib.IRCEventListener;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.util.IRCModeParser;

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
public class IRCEvents implements IRCEventListener {
    @Override
    public void onRegistered() {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onError(String s) {
        Util.error("[null]: " + s);
    }

    @Override
    public void onError(int i, String s) {
        Util.error("[" + i + "]: " + s);
    }

    @Override
    public void onInvite(String chan, IRCUser inviter, String invited) {
        if ( invited.equals(BotConfiguration.getNickName()) ) {
            System.out.println("Invite: Bot Invited To " + chan);
            Backend.getConnection().doJoin(chan);
        }
    }

    @Override
    public void onJoin(String s, IRCUser ircUser) {

    }

    @Override
    public void onKick(String s, IRCUser ircUser, String s1, String s2) {

    }

    @Override
    public void onMode(String s, IRCUser ircUser, IRCModeParser ircModeParser) {

    }

    @Override
    public void onMode(IRCUser ircUser, String s, String s1) {

    }

    @Override
    public void onNick(IRCUser ircUser, String s) {

    }

    @Override
    public void onNotice(String s, IRCUser ircUser, String s1) {

    }

    @Override
    public void onPart(String s, IRCUser ircUser, String s1) {

    }

    @Override
    public void onPing(String s) {

    }

    @Override
    public void onPrivmsg(String target, IRCUser user, String msg) {
        Util.info(user.getNick() + " : " + msg);
        if ( msg.startsWith("!") ) {
            String command = msg.substring(1);
            Util.info("Command: " + command);
            try {
                CommandManager.executeCommand(command, user);
            } catch (Exception e) {
                Util.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onQuit(IRCUser ircUser, String s) {

    }

    @Override
    public void onReply(int i, String s, String s1) {
        System.out.println("Reply [" + i + "]: " + s1);
        if ( i == 396 ) {
            Backend.getConnection().doJoin(BotConfiguration.getChannel());
        }
    }

    @Override
    public void onTopic(String s, IRCUser ircUser, String s1) {

    }

    @Override
    public void unknown(String s, String s1, String s2, String s3) {

    }
}
