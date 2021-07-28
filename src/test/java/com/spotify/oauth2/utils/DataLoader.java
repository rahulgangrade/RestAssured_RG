package com.spotify.oauth2.utils;

import java.util.Properties;

public class DataLoader {
	private final Properties properties;
	private static DataLoader dataLoader;

	public DataLoader() {
		properties = PropertyUtils.propertyLoader("src/test/java/com/spotify/oauth2/resources/config.properties");
	}

	public static DataLoader getInstance() {
		if (dataLoader == null) {
			dataLoader = new DataLoader();
		}
		return dataLoader;
	}

	
	public String getPlaylistId() {
		String prop = properties.getProperty("playListId");
		if (prop != null)
			return prop;
		else
			throw new RuntimeException("property playListId is not specified in the properties file");
	}
}