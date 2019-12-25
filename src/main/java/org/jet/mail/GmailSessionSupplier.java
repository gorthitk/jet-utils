package org.jet.mail;

import org.jet.properties.PropertiesBuilder;
import org.jet.properties.PropertyTypes;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * @author tgorthi
 * @since Dec 2019
 */
public class GmailSessionSupplier implements MailSessionSupplier
{
    private static final String GMAIL_HOST = "smtp.gmail.com";
    private static final String GMAIL_HOST_PORT = "465";
    private static final String GMAIL_SMTP_SOCKET_FACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";
    private static final String GMAIL_HOST_AUTH = "true";

    private final String emailId;
    private final String password;

    public GmailSessionSupplier(final String emailId, final String password)
    {
        this.emailId = emailId;
        this.password = password;
    }

    @Override
    public Session get()
    {
        final Properties properties = PropertiesBuilder.builder(System.getProperties())
                .addProperty(PropertyTypes.MAIL_SMTP_HOST, GMAIL_HOST)
                .addProperty(PropertyTypes.MAIL_SMTP_PORT, GMAIL_HOST_PORT)
                .addProperty(PropertyTypes.MAIL_SMTP_SOCKET_FACTORY_CLASS, GMAIL_SMTP_SOCKET_FACTORY_CLASS)
                .addProperty(PropertyTypes.MAIL_SMTP_AUTH, GMAIL_HOST_AUTH)
                .build();

        final Session session = Session.getInstance(properties, new GmailPasswordAuthentication(emailId, password));

        // Used to debug SMTP issues
        session.setDebug(true);
        return session;
    }

    private static class GmailPasswordAuthentication extends Authenticator
    {
        private final String emailId;
        private final String password;

        public GmailPasswordAuthentication(final String emailId, final String password)
        {
            this.emailId = emailId;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication()
        {

            return new PasswordAuthentication(emailId, password);

        }
    }
}
