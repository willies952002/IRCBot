package com.domnian;

import java.io.File;

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
public class BackendTest {

    @org.junit.Before
    public void setUp() throws Exception {
        File config = new File("config.json");
        if ( config.createNewFile() ) {
            BotConfiguration.writeDefault(config);
        }
    }

    @org.junit.Test
    public void testInit() throws Exception {
        Backend.init();
    }
}