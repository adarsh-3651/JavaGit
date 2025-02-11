package np.edu.heraldcollege;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages database operations and interactions related to competitors.
 * <p>
 * This class handles database connectivity and CRUD operations for competitors,
 * using a MySQL database. It ensures proper handling of competitor data and 
 * supports operations like adding, removing, and retrieving competitor details.
 */

public class CompetitorManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 

    /**
     * Saves a Competitor to the database.
     */
    public void saveCompetitor(Competitor competitor) {
        String query = "INSERT INTO Competitors (CompetitorID, FirstName, LastName, Level, Country, Scores) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, competitor.getCompetitorID());
            pstmt.setString(2, competitor.getFirstName());
            pstmt.setString(3, competitor.getLastName());
            pstmt.setString(4, competitor.getLevel());
            pstmt.setString(5, competitor.getCountry());
            pstmt.setString(6, arrayToString(competitor.getScores()));

            pstmt.executeUpdate();
            System.out.println("Competitor saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all competitors from the database.
     */
    public List<Competitor> getAllCompetitors() {
        List<Competitor> competitors = new ArrayList<>();
        String query = "SELECT * FROM Competitors";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("CompetitorID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String level = rs.getString("Level");
                String country = rs.getString("Country");
                int[] scores = stringToArray(rs.getString("Scores"));

                competitors.add(new Competitor(id, firstName, lastName, level, country, scores, id, country));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return competitors;
    }

    /**
     * Converts an array to a comma-separated string.
     */
    private String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int num : array) {
            sb.append(num).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * Converts a comma-separated string back to an array.
     */
    private int[] stringToArray(String str) {
        String[] parts = str.split(",");
        int[] array = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Integer.parseInt(parts[i]);
        }
        return array;
    }
}
