package org.jet.mail;

import javax.mail.Session;

/**
 * @author tgorthi
 * @since Dec 2019
 */
@FunctionalInterface
public interface MailSessionSupplier
{
    Session get();
}
