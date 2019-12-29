package org.jet.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A transaction wrapper, to abstracts boiler plate code for Transaction Management.
 * A typical transaction follows :
 * 1. BEGIN;
 * 2. RUN SQL COMMAND:
 * 3. COMMIT; OR ROLLBACK;
 *
 * This class providers the java equivalent for the above process :
 * Transaction tx = new Transaction(connection);
 * tx.begin;
 * Connection conn = tx.getConnection();
 * // use the connection to run any sql command.
 * tx.commit(); or tx.rollback();
 *
 * @author tgorthi
 * @since Dec 2019
 */
public class Transaction implements AutoCloseable
{
    private final Connection connection;
    private boolean transactionBegan = false;

    public Transaction(final Connection connection)
    {
        this.connection = connection;
    }

    public Transaction(final ConnectionSupplier supplier) throws SQLException
    {
        this.connection = supplier.get();
    }

    public Connection getConnection() throws SQLException
    {
        if (transactionBegan)
        {
            return connection;
        }

        throw new SQLException("Transaction#begin() needs to be invoked before fetching the connection");
    }

    public void begin() throws SQLException
    {
        transactionBegan = true;
        connection.setAutoCommit(false);
    }

    public void commit()
    {
        try
        {
            connection.commit();
        }
        catch (SQLException e)
        {
            try
            {
                connection.rollback();
            }
            catch (SQLException exp)
            {
                throw new RuntimeException("Failed to rollback transaction", exp);
            }
        }
        finally
        {
            try
            {
                connection.setAutoCommit(true);
                connection.close();
            }
            catch (SQLException exp)
            {
                throw new RuntimeException("Failed to close the connection", exp);
            }
        }
    }

    public void rollback()
    {
        try
        {
            connection.rollback();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Failed to rollback transaction", e);
        }
        finally
        {
            try
            {
                connection.setAutoCommit(true);
                connection.close();
            }
            catch (SQLException exp)
            {
                throw new RuntimeException("Failed to close the connection", exp);
            }
        }
    }

    @Override
    public void close() throws Exception
    {
        try
        {
            if (!connection.isClosed())
            {
                connection.rollback();
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        catch (SQLException exp)
        {
            throw new RuntimeException("Failed to close the connection", exp);
        }
    }
}
