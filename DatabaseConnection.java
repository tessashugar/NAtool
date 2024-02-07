import java.sql.*;

public class DatabaseConnection {
    private final String url = "jdbc:postgresql://localhost:5432/systemsEvolutionDatabase";
    private final String user = "postgres";
    private final String password = "SEIpassword";

    /**
     * Connect to the PostgreSQL database
     * @return conn (Connection object)
     */
    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return conn;
    } //end connect method
} //end class

