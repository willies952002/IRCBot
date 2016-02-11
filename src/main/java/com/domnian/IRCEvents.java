package com.domnian;

import com.domnian.api.API;
import com.domnian.command.CommandManager;
import com.domnian.command.PermissionTable;
import com.domnian.command.PermissionLevel;
import org.schwering.irc.lib.IRCEventListener;
import org.schwering.irc.lib.IRCUser;
import org.schwering.irc.lib.util.IRCModeParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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

    private static final String ATHEME_NS_MSG = "This nickname is registered. Please choose a different nickname, or identify via /msg NickServ identify <password>.";
    private static final String ANOPE_NS_MSG = "This nickname is registered and protected.  If it is your nick, type /msg NickServ IDENTIFY password.  Otherwise, please choose a different nick.";
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
    public void onError(String msg) {
        onError(-1, msg);
    }

    @Override
    public void onError(int id, String msg) {
        Util.error("Error [" + id + "]: " + msg);
        if ( id == 464 &&  msg.equals("Invalid Password" ) ) {
            Util.severe("Incorrect Server Password!");
            System.exit(1);
        }
    }

    @Override
    public void onInvite(String chan, IRCUser inviter, String invited) {
        if ( invited.equals(BotConfiguration.getNickName()) ) {
            System.out.println("Invite: Bot Invited To " + chan + " by " + inviter.getNick());
            Backend.getConnection().doJoin(chan);
        }
    }

    @Override
    public void onJoin(String chan, IRCUser user) {
        if ( chan.equals(BotConfiguration.getChannel()) ) {
            List<String> lines = API.loadFile("resources/" + chan.substring(1) + "-welcome.txt");
            API.notice(user.getNick(), lines.get(0));
        }
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
    public void onNotice(String target, IRCUser user, String msg) {
        Util.info("NOTICE: " + target + " <" + user.getNick() + "> " + msg);
        if ( target.equals(BotConfiguration.getNickName()) ) {
            if ( user.getNick().equals("NickServ") ) {
                if ( msg.equals(ATHEME_NS_MSG) || msg.equals(ANOPE_NS_MSG)) {
                    if ( BotConfiguration.isAuth() ) {
                        Backend.getConnection().doPrivmsg("NickServ", "IDENTIFY " + BotConfiguration.getNickServPass());
                    } else {
                        Util.severe("Nick requires Authentication but Authentication is Disabled!");
                        System.exit(1);
                    }
                }
            }
        }
    }

    @Override
    public void onPart(String s, IRCUser ircUser, String s1) {

    }

    @Override
    public void onPing(String msg) {
        Util.debug("PING: " + msg);
    }

    @Override
    public void onPrivmsg(String target, IRCUser user, String msg) {
        Util.info(user.getNick() + " : " + msg);
        if ( msg.startsWith("!") ) {
            String command = msg.substring(1);
            try {
                //TODO get user permission level
                PermissionLevel level = PermissionTable.get(target, user.getNick());
                CommandManager.executeCommand(command, user, target, level);
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
        if ( msg.equals("TIME") ) {
            CommandManager.executeCommand("time", user, target, PermissionLevel.SELF);
        }
    }

    @Override
    public void onQuit(IRCUser ircUser, String s) {

    }

    @Override
    public void onReply(int code, String msg1, String msg2) {
        Util.info("Reply [" + code + "]: " + msg1 + " : " + msg2);
        if ( code == 376 || code == 332 ) {
            Backend.getConnection().doMode(BotConfiguration.getNickName(), "+B");
        }
        if ( code == 396 ) {
            Backend.getConnection().doJoin(BotConfiguration.getChannel());
        }
        if ( code == 353 ) {
            PermissionTable.build(msg1, msg2);
        }
    }

    @Override
    public void onTopic(String s, IRCUser ircUser, String s1) {

    }

    @Override
    public void unknown(String s, String s1, String s2, String s3) {

    }
}
