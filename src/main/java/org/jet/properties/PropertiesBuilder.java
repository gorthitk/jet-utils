package org.jet.properties;

import java.util.Properties;

/**
 * Builder pattern implementation for Building java {@link Properties}
 * @author tgorthi
 * @since Dec 2019
 */
public class PropertiesBuilder
{
    private final Properties properties;

    private PropertiesBuilder()
    {
        this.properties = new Properties();
    }

    private PropertiesBuilder(final Properties properties)
    {
        this.properties = properties;
    }

    public static PropertiesBuilder builder()
    {
        return new PropertiesBuilder();
    }

    public static PropertiesBuilder builder(final Properties properties)
    {
        return new PropertiesBuilder(properties);
    }

    public PropertiesBuilder addProperty(final PropertyTypes type, final String user)
    {
        properties.setProperty(type.getPropertyKey(), user);
        return this;
    }

    public PropertiesBuilder addProperty(final String key, final String user)
    {
        properties.setProperty(key, user);
        return this;
    }

    public Properties build()
    {
        return properties;
    }
}
