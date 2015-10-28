package com.domnian.command;

import com.domnian.Backend;
import com.domnian.BotConfiguration;
import com.domnian.Util;
import org.schwering.irc.lib.IRCConnection;
import org.schwering.irc.lib.IRCUser;

import java.lang.reflect.Method;
import java.net.URLClassLoader;

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
public class CommandManager {

    public static void executeCommand(String command, IRCUser user) throws Exception {
        String className = "com.domnian.command.builtin." + properCase(command);
        Util.debug("Command Class: " + className);
        for ( Method m : Class.forName(className).getDeclaredMethods() ) {
            Util.debug("Declared Method: " + m.getName());
        }
        Method execute = Class.forName(className).getDeclaredMethod("execute");
        execute.invoke(BotConfiguration.getChannel(), user);
    }

    private static String properCase(String inputVal) {
        if (inputVal.length() == 0) return "";
        if (inputVal.length() == 1) return inputVal.toUpperCase();
        return inputVal.substring(0,1).toUpperCase() + inputVal.substring(1).toLowerCase();
    }

}