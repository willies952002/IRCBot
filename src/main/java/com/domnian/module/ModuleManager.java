package com.domnian.module;

import com.domnian.Util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

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
public class ModuleManager {

    private static HashMap<String, Module> modules = new HashMap<>();

    public static ModuleManager load() {
        ModuleManager manager = new ModuleManager();
        File dir = new File("modules");
        if (!dir.exists()) {
            Util.severe("No Modules loaded! Cannot find directory 'modules' - Creating it");
            dir.mkdir();
            return null;
        }
        ClassLoader loader;
        try {
            loader = new URLClassLoader(new URL[] { dir.toURI().toURL() },
                    manager.getClass().getClassLoader());
        } catch (MalformedURLException ex) {
            Util.error("Error while configuring module class loader");
            ex.printStackTrace();
            return null;
        }
        for (File file : dir.listFiles()) {
            if (!file.getName().endsWith(".class")) {
                continue;
            }
            String name = file.getName().substring(0,
                    file.getName().lastIndexOf("."));

            try {
                Class<?> clazz = loader.loadClass(name);
                Object object = clazz.newInstance();
                if (!(object instanceof Module)) {
                    Util.info("Not a Module: " + clazz.getSimpleName());
                    continue;
                }
                Module module = (Module) object;
                modules.put(clazz.getSimpleName().toLowerCase(), module);
                Util.info("Loaded Module: " + module.getClass().getSimpleName());
            }
            catch (Exception | Error ex) {
                Util.error("Error loading '" + name + "' modules! Module disabled.");
                ex.printStackTrace();
            }
        }
        manager.setModules(modules);
        return manager;
    }

    private void setModules(HashMap<String, Module> modules) {
        ModuleManager.modules = modules;
    }

}