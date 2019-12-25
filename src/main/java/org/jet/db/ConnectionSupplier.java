package org.jet.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author tgorthi
 * @since Dec 2019
 */
@FunctionalInterface
public interface ConnectionSupplier
{
    Connection get() throws SQLException;
}
