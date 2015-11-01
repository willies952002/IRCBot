package com.domnian.module;

import com.domnian.Backend;
import com.domnian.Main;
import org.schwering.irc.lib.IRCConnection;
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
public abstract class Module {

    public IRCConnection conn = Backend.getConnection();
    public ModuleManager manager = Main.getModuleManager();

}
