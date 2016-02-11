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

public enum PermissionLevel {

    SELF(10), // This Instance
    OWNER(6),
    ADMIN(5),
    OPERATOR(4),
    HALFOP(3),
    VOICE(2),
    BOT(1), // Unused at the moment
    DEFAULT(0); // Regular user, No Extra Modes

    int id = 0;

    PermissionLevel(int id) {
        this.id = id;
    }

    public static boolean check(PermissionLevel level, PermissionLevel required) {
        return level.id >= required.id;
    }
}
