package com.gimconnect;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import net.runelite.client.ui.PluginPanel;

import net.runelite.client.game.ItemManager;

import javax.swing.BoxLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.Map;

public class GIMConnectPanel extends PluginPanel {

    private final Map<Integer, Integer> bankItems;
    private final ItemManager itemManager;
    private final JPanel resultsPanel;

    public GIMConnectPanel(Map<Integer, Integer> bankItems, ItemManager itemManager) {
        super();
        this.bankItems = bankItems;
        this.itemManager = itemManager;

        setLayout(new BorderLayout());

        // Search bar
        JTextField searchBar = new JTextField();
        searchBar.setToolTipText("Search for items in your group's bank");
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearchResults(searchBar.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearchResults(searchBar.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSearchResults(searchBar.getText());
            }
        });
        add(searchBar, BorderLayout.NORTH);

        // Results panel
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(resultsPanel);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateSearchResults(String searchTerm) {
        resultsPanel.removeAll();

        if (searchTerm.isEmpty()) {
            resultsPanel.revalidate();
            resultsPanel.repaint();
            return;
        }

        JPanel resultsContainer = new JPanel();
        resultsContainer.setLayout(new BoxLayout(resultsContainer, BoxLayout.Y_AXIS));

        bankItems.forEach((itemId, quantity) -> {
            String itemName = itemManager.getItemComposition(itemId).getName();
            if (itemName.toLowerCase().contains(searchTerm.toLowerCase())) {
                JLabel label = new JLabel(itemName + " - Qty: " + quantity);
                resultsContainer.add(label);
            }
        });

        resultsPanel.add(resultsContainer, BorderLayout.NORTH);
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}