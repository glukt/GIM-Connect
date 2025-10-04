package com.gimconnect;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class GIMConnectPluginTest {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(GIMConnectPlugin.class);
        RuneLite.main(args);
    }
}
