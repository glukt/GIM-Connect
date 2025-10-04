package com.gimconnect;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

public interface GIMConnectConfig extends Config
{
	@ConfigItem(
			keyName = "groupId",
			name = "Group ID",
			description = "The ID of your GIM group."
	)
	default String groupId()
	{
		return "";
	}

	@ConfigItem(
			keyName = "groupSecret",
			name = "Group Secret",
			description = "The secret key for your GIM group."
	)
	default String groupSecret()
	{
		return "";
	}
}