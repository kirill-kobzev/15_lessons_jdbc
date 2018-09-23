package lessons_jdbc.connectionManager;

import java.sql.Connection;

public interface ConnectionManager {
    public Connection getConnection();
}
