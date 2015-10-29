package com.domnian.command;

import com.domnian.BotConfiguration;
import com.domnian.Util;
import com.domnian.command.builtin.About;
import com.domnian.command.builtin.EMC;
import com.domnian.command.builtin.Restart;
import org.schwering.irc.lib.IRCUser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    public static void executeCommand(String command, IRCUser user) {
        BotCommand cmd;
        switch (command) {
            case "about": new About().execute(BotConfiguration.getChannel(), user); break;
            case "emc": new EMC().execute(BotConfiguration.getChannel(), user); break;
            case "restart": new Restart().execute(BotConfiguration.getChannel(), user); break;
        }
    }

    @Deprecated
    public static void INVALID_executeCommand(String command, IRCUser user) {
        String className = "com.domnian.command.builtin." + properCase(command);
        Util.debug("Command Class: " + className);
        Class cmdClass = null;
        try {
            cmdClass = Class.forName(className);
            Method execute = cmdClass.getDeclaredMethod("execute");
            execute.invoke(BotConfiguration.getChannel(), user);
        } catch (ClassNotFoundException e) {
            Util.error("No Such Command: " + command);
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static String properCase(String inputVal) {
        if (inputVal.length() == 0) return "";
        if (inputVal.length() == 1) return inputVal.toUpperCase();
        return inputVal.substring(0,1).toUpperCase() + inputVal.substring(1).toLowerCase();
    }

}