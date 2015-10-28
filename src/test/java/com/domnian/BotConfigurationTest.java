package com.domnian;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

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
public class BotConfigurationTest {

    @Before
    public void setUp() throws IOException {
        BotConfiguration.load("config.json");
    }

    @Test
    public void testGetHost() throws Exception {
        System.out.println("Host: " + BotConfiguration.getHost());
        assertEquals(BotConfiguration.getHost(), "irc.spi.gt");
    }

    @Test
    public void testGetPort() throws Exception {
        System.out.println("Port: " + BotConfiguration.getPort());
        assertEquals(BotConfiguration.getPort(), 6667);
    }

    @Test
    public void testIsSSL() throws Exception {
        System.out.println("SSL: " + BotConfiguration.isSSL());
        assertEquals(BotConfiguration.isSSL(), false);
    }

    @Test
    public void testGetPass() throws Exception {
        System.out.println("Pass: " + BotConfiguration.getPass());
        assertEquals(BotConfiguration.getPass(), null);
    }

    @Test
    public void testGetChannel() throws Exception {
        System.out.println("Channel: " + BotConfiguration.getChannel());
        assertEquals(BotConfiguration.getChannel(), "#willies952002");
    }

    @Test
    public void testGetNickName() throws Exception {
        System.out.println("Nick Name: " + BotConfiguration.getNickName());
        assertEquals(BotConfiguration.getNickName(), "WilliesIRCBot");
    }

    @Test
    public void testGetUserName() throws Exception {
        System.out.println("User Name: " + BotConfiguration.getUserName());
        assertEquals(BotConfiguration.getUserName(), "WilliesIRCBot");
    }

    @Test
    public void testGetRealName() throws Exception {
        System.out.println("Real Name: " + BotConfiguration.getRealName());
        assertEquals(BotConfiguration.getRealName(), "Willies IRC Bot");
    }
}