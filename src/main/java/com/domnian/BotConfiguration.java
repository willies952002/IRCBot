package com.domnian;

import com.domnian.command.PermissionLevel;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
public class BotConfiguration {

    private static JSONObject connectJson;
    private static JSONObject identJson;
    private static JSONObject authJson;
    private static String manage;

    public static void writeDefault(String fileName) throws Exception {
        writeDefault(new File(System.getProperty("user.dir") + File.separator + fileName));
    }

    public static void writeDefault(File file) throws Exception {
        JSONObject defaultJson = new JSONObject();
        JSONObject connectDefault = new JSONObject();
        JSONObject identDefault = new JSONObject();
        JSONObject authDefault = new JSONObject();
        connectDefault.put("host", "irc.spi.gt");
        connectDefault.put("port", 6667);
        connectDefault.put("ssl", false);
        connectDefault.put("pass", "");
        connectDefault.put("channel", "#willies952002");
        defaultJson.put("connect", connectDefault);
        identDefault.put("user", "WilliesIRCBot");
        identDefault.put("nick", "WilliesIRCBot");
        identDefault.put("real", "Willies IRC Bot");
        defaultJson.put("ident", identDefault);
        authDefault.put("run", false);
        authDefault.put("email", "email@example.com");
        authDefault.put("password", "");
        defaultJson.put("auth", authDefault);
        defaultJson.put("manage", "admin");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(defaultJson.toString().getBytes("UTF-8"));
        fos.close();
    }

    public static void load(String fileName) throws Exception {
        load(new File(System.getProperty("user.dir") + File.separator + fileName));
    }

    public static void load(File file) throws Exception {
        if ( file.createNewFile() ) {
            BotConfiguration.writeDefault(file);
        }
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        fis.read(buffer);
        JSONObject configJson = new JSONObject(new String(buffer));
        connectJson = configJson.getJSONObject("connect");
        identJson = configJson.getJSONObject("ident");
        authJson = configJson.getJSONObject("auth");
        manage = configJson.getString("manage");
    }


    public static String getHost() {
        String host = connectJson.getString("host");
        boolean valid = !host.equals("");
        return  valid ? host : "irc.spi.gt";
    }

    /**
     * Get the IRC Port Number
     * Will return one of the following:
     * if configured port is valid: configured port
     * if not valid and {#isSSL} is true: 6697
     * else: 6667
     * @return IRC Port
     */
    public static int getPort() {
        int port = connectJson.getInt("port");
        return ((port > 0) && (port < 65536)) ? port : (isSSL() ? 6697 : 6667);
    }

    public static boolean isSSL() {
        return connectJson.getBoolean("ssl");
    }

    public static String getPass() {
        String pass = connectJson.getString("pass");
        return (pass.length() > 0) ? pass : null;
    }

    public static String getChannel() {
        String channel = connectJson.getString("channel");
        return (channel.length() > 0) ? channel : "#willies952002";
    }
    public static String getNickName() {
        String nick = identJson.getString("nick");
        return (nick.length() > 0) ? nick : "NullNick";
    }

    public static String getUserName() {
        String user = identJson.getString("user");
        return (user.length() > 0) ? user : "NullUser";
    }

    public static String getRealName() {
        String real = identJson.getString("real");
        return (real.length() > 0) ? real : "NullReal";
    }

    public static boolean isAuth() {
        return authJson.getBoolean("run");
    }

    public static String getAuthPass() {
        return isAuth() ? authJson.getString("password") : "";
    }

    public static String getAuthEmail() {
        return isAuth() ? authJson.getString("email") : "";
    }

    public static PermissionLevel getManagePerm() {
        return PermissionLevel.valueOf(manage.toUpperCase());
    }
}
