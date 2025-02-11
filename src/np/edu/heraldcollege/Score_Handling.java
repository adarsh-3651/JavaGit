package np.edu.heraldcollege;

import java.sql.*;

/**
 * The Score_Handling class handles the logic for updating and calculating scores
 * of competitors in the quiz game. It manages both individual scores and overall scores
 * by interacting with the database.
 */
public class Score_Handling {
    private static final String URL = "jdbc:mysql://localhost:3306/competitiondb";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static double averageScore;

    /**
     * Updates the score of a specific competitor by appending the new score to the existing scores.
     * It retrieves the current score of the competitor, appends the new score, and updates the record
     * in the database.
     * 
     * @param competitorId The unique ID of the competitor whose score needs to be updated.
     * @param newScore The new score that needs to be added for the competitor.
     */
    public static void updateScore(int competitorId, int newScore) {
        String selectQuery = "SELECT score FROM competitor WHERE competitorid = ?";
        String updateQuery = "UPDATE competitor SET score = ? WHERE competitorid = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            // Retrieve existing score
            selectStmt.setInt(1, competitorId);
            ResultSet rs = selectStmt.executeQuery();

            String updatedScore = String.valueOf(newScore);
            if (rs.next()) {
                String existingScore = rs.getString("score");
                if (existingScore != null && !existingScore.isEmpty()) {
                    updatedScore = existingScore + "," + newScore;
                }
            }

            // Update the score in the database
            updateStmt.setString(1, updatedScore);
            updateStmt.setInt(2, competitorId);
            updateStmt.executeUpdate();

            System.out.println("Score updated successfully for competitor ID: " + competitorId);

            // Update the overall score after adding a new score
            updateOverallScore(competitorId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the overall score of a competitor by calculating the average of all their scores
     * stored in the database and updating the overall score field.
     * 
     * @param competitorId The unique ID of the competitor whose overall score needs to be updated.
     */
    private static void updateOverallScore(int competitorId) {
        double averageScore = 0;
        String selectQuery = "SELECT score FROM competitor WHERE competitorid = ?";
        String updateQuery = "UPDATE competitor SET overallscore = ? WHERE competitorid = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            selectStmt.setInt(1, competitorId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String scoresStr = rs.getString("score");
                if (!scoresStr.isEmpty()) {
                    String[] scoresArray = scoresStr.split(",");
                    int sum = 0, count = 0;
                    for (String score : scoresArray) {
                        try {
                            sum += Integer.parseInt(score.trim());
                            count++;
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid score detected: " + score);
                        }
                    }
                    if (count > 0) {
                        averageScore = (double) sum / count;

                        // Update the overall score in the database
                        updateStmt.setDouble(1, averageScore);
                        updateStmt.setInt(2, competitorId);
                        updateStmt.executeUpdate();

                        System.out.println("Overall score updated successfully for competitor ID: " + competitorId + " to " + averageScore);
                    }
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the average score of a competitor based on their historical scores.
     * 
     * @return The average score of the competitor.
     */
    public static double getAverageScore() {
        return averageScore;
    }

    /**
     * Main method to test the updateScore method with an example competitor ID and score.
     * 
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        updateScore(1, 85);  // Example usage: updating score for competitor ID 1
    }
}
