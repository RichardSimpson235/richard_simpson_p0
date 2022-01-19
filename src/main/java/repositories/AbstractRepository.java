package main.java.repositories;

import java.sql.Connection;

public abstract class AbstractRepository {

    // The connection object to the database
    protected final Connection connection;

    protected AbstractRepository(Connection connection) {
        this.connection = connection;
    }
}
