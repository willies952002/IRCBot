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
package com.domnian.command;

import com.domnian.BotConfiguration;
import com.domnian.Util;
import com.domnian.api.API;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class PermissionTable {

    private static HashMap<String, HashMap<String, PermissionLevel>> perms = new HashMap<>();

    public static void build(String pointer, String names) {
        String chan = pointer.substring(pointer.indexOf("#"));
        Util.line();
        Util.info("Building Permission Cache for " + chan);
        Util.line();
        String[] a = names.split(" ");
        List<String> b = new ArrayList<>();
        Collections.addAll(b, a);
        HashMap<String, PermissionLevel> d = new HashMap<>();
        b.forEach((rawnick) -> {
            String nick = rawnick.substring(1);
            if ( nick.equals(BotConfiguration.getNickName()) ) {
                d.put(nick, PermissionLevel.SELF);
            } else if ( rawnick.equals(BotConfiguration.getNickName() )) {
                d.put(rawnick, PermissionLevel.SELF);
            } else {
                switch (rawnick.charAt(0)) {
                    case '~': d.put(nick, PermissionLevel.OWNER); break;
                    case '&': d.put(nick, PermissionLevel.ADMIN); break;
                    case '@': d.put(nick, PermissionLevel.OPERATOR); break;
                    case '%': d.put(nick, PermissionLevel.HALFOP); break;
                    case '+': d.put(nick, PermissionLevel.VOICE); break;
                    default: d.put(rawnick, PermissionLevel.DEFAULT); break;
                }
            }
        });
        perms.put(chan, d);
        Util.success("Permission Cache Built Successfully");
        Util.line();
    }

    public static PermissionLevel get(String target, String nick) {
        return perms.get(target).get(nick);
    }

    public static void debug(String nick, boolean console) {
        if ( console ) {
            Util.line();
            Util.debug("Permission Cache Debug");
            Util.line();
            perms.forEach((chan, permissions) -> {
                Util.debug("Channel: " + chan);
                permissions.forEach((user, perm) -> {
                    Util.debug(" - " + user + " = " + perm.toString());
                });
            });
        } else {
            API.notice(nick, "Permission Cache Debug");
            perms.forEach((chan, permissions) -> {
                API.notice(nick, "Channel: " + chan);
                permissions.forEach((user, perm) -> {
                    API.notice(nick, " - " + user + " = " + perm.toString());
                });
            });
        }
    }

}
