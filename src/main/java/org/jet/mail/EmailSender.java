package org.jet.mail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * Class that abstracts building a {@link MimeMessage} and sending an email.
 * @author tgorthi
 * @since Dec 2019
 */
public class EmailSender
{
    private static final String HTML_CONTENT_TYPE = "text/html";
    private final MailSessionSupplier sessionSupplier;
    private final String fromEmailAddress;

    public EmailSender(final MailSessionSupplier sessionSupplier, final String fromEmailAddress)
    {
        this.sessionSupplier = sessionSupplier;
        this.fromEmailAddress = fromEmailAddress;
    }

    /**
     * @param recipientEmailIds recipient email addresses
     * @param subject email subject
     * @param body email body
     * @throws MessagingException
     */
    public void send(final List<String> recipientEmailIds, final String subject, String body) throws MessagingException
    {
        final MimeMessage message = new MimeMessage(sessionSupplier.get());

        message.setFrom(new InternetAddress(fromEmailAddress));

        for (String toEmail : recipientEmailIds)
        {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        }

        message.setSubject(subject);
        message.setContent(body, HTML_CONTENT_TYPE);

        Transport.send(message);
    }

    /**
     * @param toEmail recipient email address
     * @param subject email subject
     * @param body email body
     * @throws MessagingException
     */
    public void send(final String toEmail, final String subject, String body) throws MessagingException
    {
        send(List.of(toEmail), subject, body);
    }
}
