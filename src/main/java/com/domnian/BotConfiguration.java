package com.domnian;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

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

    private static JSONObject configJson;
    private static JSONObject connectJson;
    private static JSONObject identJson;

    public static void writeDefault(String fileName) throws Exception {
        writeDefault(new File(System.getProperty("user.dir") + File.separator + fileName));
    }

    private static void writeDefault(File file) throws Exception {
        JSONObject defaultJson = new JSONObject();
        JSONObject connectDefault = new JSONObject();
        JSONObject identDefault = new JSONObject();
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
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(defaultJson.toString().getBytes("UTF-8"));
        fos.close();
    }

    public static void load(String fileName) throws IOException {
        load(new File(System.getProperty("user.dir") + File.separator + fileName));
    }

    public static void load(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        fis.read(buffer);
        configJson = new JSONObject(new String(buffer));
        connectJson = configJson.getJSONObject("connect");
        identJson = configJson.getJSONObject("ident");
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

}
