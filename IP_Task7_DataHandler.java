package project_jsp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataHandler {
    private Connection conn;
    
    // Azure SQL connection credentials.
    private String server = "nguy0132-sql-server.database.windows.net";
    private String database = "cs-dsa-4513-sql-db";
    private String username = "nguy0132";
    private String password = "sN8!CVD$NBvib*7J9L23";
    
    // Resulting connection string.
    final private String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
            + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", server, database, username, password);
    
    // Initialize and save the database connection.
    private void getDBConnection() throws SQLException {
        if (conn != null) {
            return;
        }
        
        this.conn = DriverManager.getConnection(url);
    }
    
    // Return the result of selecting everything from the table.
    public ResultSet getAllCustomers() throws SQLException {
        getDBConnection();
        
        final String sqlQuery = "SELECT * FROM Customer";
        final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        return stmt.executeQuery();
    }
    
    // Inserts a record into the Customer table with the given attribute values.
    public boolean addCustomer(String customerName, String customerAddress, int category) throws SQLException {
        
        // Prepare the database connection.
        getDBConnection();
        
        // Prepare the SQL statement to insert into Customer table.
        final String sqlQuery = 
                "INSERT INTO Customer (name, address, category) "
                + "VALUES ('"+customerName+"', '"+customerAddress+"', '"+category+"')";
        
        // Execute query.
        final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        return stmt.executeUpdate() == 1;
    }
}