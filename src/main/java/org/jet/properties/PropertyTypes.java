package org.jet.properties;

/**
 * @author tgorthi
 * @since Dec 2019
 */
public enum PropertyTypes
{
    DB_USER("user"),
    DB_PASSWORD("password"),

    MAIL_SMTP_HOST("mail.smtp.host"),
    MAIL_SMTP_PORT("mail.smtp.port"),
    MAIL_SMTP_SOCKET_FACTORY_CLASS("mail.smtp.socketFactory.class"),
    MAIL_SMTP_AUTH("mail.smtp.auth")
    ;

    private final String m_propertyKey;

    PropertyTypes(String key)
    {
        m_propertyKey = key;
    }

    public String getPropertyKey()
    {
        return m_propertyKey;
    }
}
