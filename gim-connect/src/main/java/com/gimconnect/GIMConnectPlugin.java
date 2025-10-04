package com.gimconnect;

import com.google.inject.Provides;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import net.runelite.client.eventbus.Subscribe;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.ItemContainer;
import net.runelite.api.InventoryID;
import java.util.HashMap;
import java.util.Map;

import net.runelite.client.game.ItemManager;

@Slf4j
@PluginDescriptor(
    name = "GIM Connect"
)
public class GIMConnectPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private GIMConnectConfig config;

    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    private ItemManager itemManager;

    private GIMConnectPanel panel;
    private NavigationButton navButton;

    private final Map<Integer, Integer> bankItems = new HashMap<>();

    @Override
    protected void startUp() throws Exception {
        log.info("GIM Connect started!");

        panel = new GIMConnectPanel(bankItems, itemManager);
        final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.png");

        navButton = NavigationButton.builder()
            .tooltip("GIM Connect")
            .icon(icon)
            .priority(10)
            .panel(panel)
            .build();

        clientToolbar.addNavigation(navButton);
    }

    @Override
    protected void shutDown() throws Exception {
        log.info("GIM Connect stopped!");
        clientToolbar.removeNavigation(navButton);
        bankItems.clear();
    }

    @Subscribe
    public void onItemContainerChanged(ItemContainerChanged event) {
        if (event.getContainerId() == InventoryID.BANK.getId()) {
            ItemContainer bankContainer = client.getItemContainer(InventoryID.BANK);
            if (bankContainer != null) {
                bankItems.clear();
                for (net.runelite.api.Item item : bankContainer.getItems()) {
                    if (item.getId() > 0) {
                        bankItems.put(item.getId(), item.getQuantity());
                    }
                }
                log.info("Bank snapshot updated. Items: " + bankItems.size());
            }
        }
    }

    @Provides
    GIMConnectConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(GIMConnectConfig.class);
    }
}
