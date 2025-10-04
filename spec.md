GIM Connect - RuneLite Plugin - Development Specification

Version: 0.1
Author: glukt
Date: 2025-10-03
1. Project Vision & Goals

   Vision: To create a fully-integrated RuneLite plugin that enhances the Group Ironman experience by providing seamless, in-game tools for team coordination, resource management, and celebrating shared progress.

   Core Problem: Group Ironman players lack efficient, in-game tools to track shared resources across their team's banks, forcing them to rely on out-of-game communication and third-party websites. This plugin aims to solve that directly within the RuneLite client.

2. Current Project Status (as of Oct 3, 2025)

   Status: Pre-development / Specification Complete

   Description: The project is in the planning phase. This specification document outlines the full scope of work for the initial Minimum Viable Product (MVP). Phase 1 is fully defined and ready for implementation.

3. Phase 1 Development Tasks (MVP: Shared Bank Search)

This section details the actionable tasks required to build the core functionality of the plugin.

    KEY: ALWAYS look to the reference plugins in each task for established patterns and code structure. This will significantly expedite development.

Task 1: Core Plugin & Configuration Setup

    Description: Establish the basic plugin boilerplate, including the configuration panel for user settings and the registration of the sidebar panel.

    Requirements:

        The plugin must have a configuration panel in RuneLite settings.

        The panel must contain two string input fields: Group ID and Group Secret.

    Reference Guide:

        Plugin: Notes (or any simple, official RuneLite plugin)

        Use for: Template for the Config interface, the main Plugin class, and registering the PluginPanel with a sidebar navigation button.

Task 2: Bank Data Collection & Client-Side Syncing

    Description: Implement the logic to scan a player's bank contents and prepare it for syncing to the backend service.

    Requirements:

        Must detect when the bank interface is opened to begin a scan.

        Must iterate through all items in the bank container, recording each item's ID and quantity.

        Data sync must be triggered after the bank interface is closed to minimize network traffic.

    Reference Guide:

        Plugin: Group Ironmen Tracker

        Use for: A direct, working example of iterating through the bank container, collecting item data, serializing it for transmission, and using game events to trigger the sync.

Task 3: Backend Setup & Communication (Firebase Firestore)

    Description: Configure the Firestore database and implement the Java logic within the plugin to communicate with it.

    Requirements:

        The plugin must include the necessary Java library for Firestore.

        All communication must be asynchronous to prevent client freezes.

        Data must be written to Firestore following this specific schema:

    {
      "groups": {
        "YOUR_GROUP_ID": {
          "members": {
            "Player_A_RSN": {
              "lastUpdated": "...",
              "bank": { "ItemID": Quantity, ... }
            }
          }
        }
      }
    }

    Reference Guide:

        Plugin: Group Ironmen Tracker

        Use for: Its handling of authenticated HTTP requests to an external service is a strong blueprint for structuring the communication logic with Firestore.

Task 4: UI Panel Implementation

    Description: Build the in-game sidebar panel that allows users to search and view the combined bank data.

    Requirements:

        The panel must contain a text search bar at the top.

        Below the search bar, a scrollable results area must display the items.

        Typing in the search bar must dynamically filter the results in real-time.

        Each result must be formatted as: [Item Icon] Item Name - Qty: [Quantity] (Player: [RSN]).

    Reference Guide:

        Plugin: Bank Tags

        Use for: An ideal template for the UI layout (search bar + scrollable list), client-side search filtering logic, and displaying items with their icons and names.

4. Future Phases & Feature Development

The following features are planned for subsequent releases after the successful launch of the Phase 1 MVP.

    Phase 2: Smart Wishlist & Notifications:

        A system for creating a shared item wishlist.

        In-game notifications when a wished-for item is obtained by any group member.

        A "supply request" system to ping teammates.

    Phase 3: Quality of Life & Expansion:

        A persistent, shared "Group Drop Log" to track all unique and valuable drops for the team.

        A "Team Status" panel for at-a-glance information on teammate stats, location, and current activity.

5. Reference Plugins

This section lists useful reference plugins for common patterns and functionality.

    - **Bank Tags**
        - **Use for:** UI layout (search bar + scrollable list), client-side search filtering logic, and displaying items with their icons and names.

    - **Group Ironmen Tracker**
        - **Use for:** A direct, working example of iterating through the bank container, collecting item data, serializing it for transmission, and using game events to trigger the sync. Its handling of authenticated HTTP requests to an external service is a strong blueprint for structuring the communication logic with Firestore.

    - **Inventory Setups**
        - **Use for:** A good reference for bank filtering and showing required items. Can be adapted for displaying search results.