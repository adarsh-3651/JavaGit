package extraCode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/"; // MySQL URL (without database)
        String user = "root"; // Change to your MySQL username
        String password = ""; // Change to your MySQL password

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE DATABASE CompetitionDB";
            stmt.executeUpdate(sql);
            System.out.println("Database 'CompetitionDB' created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
