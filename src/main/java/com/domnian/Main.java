package com.domnian;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;

import static java.util.Arrays.asList;

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
public class Main {

    public static void main(String[] args) {
        OptionParser parser = new OptionParser() {
            {
                acceptsAll(asList("?", "help"), "Show Program Help");
                acceptsAll(asList("gui", "desktop"), "Enable GUI - Not Yet Implemented");
                acceptsAll(asList("v", "version"), "Show Program Version");
            }
        };
        OptionSet options = null;
        try {
            options = parser.parse(args);
        } catch (OptionException e) {
            Util.severe(e.getMessage());
            e.printStackTrace();
        }

        if ((options == null) || (options.has("?"))) {
            try {
                parser.printHelpOn(System.out);
            } catch (IOException e) {
                Util.severe(e.getMessage());
            }
        } else if (options.has("v")) {
            try {
                Util.info("Version: " + Backend.getVersion());
            } catch (Exception e) {
                Util.severe(e.getMessage());
            }
        } else {
        }

    }

}
