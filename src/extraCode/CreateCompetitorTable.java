package extraCode;

import java.sql.*;

public class CreateCompetitorTable {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        // Updated SQL to include Username and Password fields
        String createTableSQL = "CREATE TABLE Competitor (" +
                                "CompetitorID INT AUTO_INCREMENT PRIMARY KEY," +
                                "FirstName VARCHAR(100) NOT NULL," +
                                "LastName VARCHAR(100) NOT NULL," +
                                "Level VARCHAR(50) NOT NULL," +
                                "Score VARCHAR(100) ," +  
                                "OverallScore VARCHAR(100) ," +  
                                "Country VARCHAR(100) NOT NULL," +
                                "Username VARCHAR(100) UNIQUE NOT NULL," +  // Unique username
                                "Password VARCHAR(255) NOT NULL" +  // Store password (consider hashing it)
                                ")";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(createTableSQL);
            System.out.println("Competitor table created successfully with Username and Password fields.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
