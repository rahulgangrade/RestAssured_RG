package com.spotify.oauth2.utils;

import java.util.Properties;

public class ConfigLoader {
	private final Properties properties;
	private static ConfigLoader configLoader;

	public ConfigLoader() {
		properties = PropertyUtils.propertyLoader("src/test/java/com/spotify/oauth2/resources/config.properties");
	}

	public static ConfigLoader getInstance() {
		if (configLoader == null) {
			configLoader = new ConfigLoader();
		}
		return configLoader;
	}

	public String getClientId() {
		String prop = properties.getProperty("client_id");
		if (prop != null)
			return prop;
		else
			throw new RuntimeException("property client_id is not specified in the properties file");
	}

	public String getSecret() {
		String prop = properties.getProperty("client_secret");
		if (prop != null)
			return prop;
		else
			throw new RuntimeException("property client_secret is not specified in the properties file");
	}

	public String getGrantType() {
		String prop = properties.getProperty("grant_type");
		if (prop != null)
			return prop;
		else
			throw new RuntimeException("property grant_type is not specified in the properties file");
	}

	public String getRefreshToken() {
		String prop = properties.getProperty("refresh_token");
		if (prop != null)
			return prop;
		else
			throw new RuntimeException("property refresh_token is not specified in the properties file");
	}
	public String getUser() {
		String prop = properties.getProperty("user");
		if (prop != null)
			return prop;
		else
			throw new RuntimeException("property user is not specified in the properties file");
	}
}