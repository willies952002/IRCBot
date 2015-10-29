package com.domnian;

import com.domnian.command.CommandManager;
import org.json.JSONObject;
import org.schwering.irc.lib.IRCEventListener;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.util.IRCModeParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.domnian.Backend.getVersion;

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
    private static String VERSION_REPLY = "VERSION DomnianIRCBot " + getVersion() + " / " + sysInfo();

    private static String sysInfo() {
        String osName = System.getProperty("os.name");
        String osVer = System.getProperty("os.version");
        String osArch = "";
        if ( osName.contains("Linux") ) {
            try {
                Process proc = Runtime.getRuntime().exec("uname -m");
                InputStream in = proc.getInputStream();
                Scanner sc = new Scanner(in);
                osArch = sc.nextLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            osArch = "ERROR (OS Not Yet Supported)";
        }
        return osName + " " + osVer + " [" + osArch + "]";

    }

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
            System.out.println("Invite: Bot Invited To " + chan + " by " + inviter.getNick());
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
            try {
                CommandManager.executeCommand(command, user);
            } catch (Exception e) {
                Util.error(e.getMessage());
                e.printStackTrace();
            }
        }
        if ( msg.equals("VERSION") ) {
            try {
                Backend.getConnection().doNotice(user.getNick(), VERSION_REPLY);
            } catch (Exception e) {
                Backend.getConnection().doNotice(user.getNick(), "VERSION Error Occurred While Obtaining Version");
                e.printStackTrace();
            }
        }
        if ( msg.equals("PING") ) {
            Backend.getConnection().doNotice(user.getNick(), "Pong");
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
