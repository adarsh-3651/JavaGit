package extraCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdateQuizQuestions {
    private static final String URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root"; // Change to your MySQL username
    private static final String PASSWORD = ""; // Change to your MySQL password

    public static void main(String[] args) {
        String updateSQL = "UPDATE Questions SET question = ?, optionA = ?, optionB = ?, optionC = ?, optionD = ?, rightAnswer = ?, level = ? WHERE No = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {

            // Only the 30 new questions
            Object[][] updatedQuestions = {
                {6, "What is the capital of France?", "Berlin", "Madrid", "Paris", "Rome", "Paris", "Beginner"},
                {7, "What is the square root of 16?", "2", "3", "4", "5", "4", "Beginner"},
                {8, "Which gas do plants absorb from the atmosphere?", "Oxygen", "Nitrogen", "Carbon Dioxide", "Hydrogen", "Carbon Dioxide", "Beginner"},
                {9, "What is the largest mammal?", "Elephant", "Blue Whale", "Shark", "Giraffe", "Blue Whale", "Beginner"},
                {10, "Which ocean is the largest?", "Atlantic", "Indian", "Pacific", "Arctic", "Pacific", "Beginner"},
                {11, "What is the chemical symbol for water?", "H2O", "CO2", "O2", "H2", "H2O", "Beginner"},
                {12, "Which country is known as the Land of the Rising Sun?", "South Korea", "China", "Japan", "Thailand", "Japan", "Beginner"},
                {13, "What is the smallest continent?", "Asia", "Europe", "Australia", "Africa", "Australia", "Beginner"},
                {14, "What is the largest desert in the world?", "Sahara", "Arabian", "Gobi", "Antarctic", "Antarctic", "Intermediate"},
                {15, "How many continents are there?", "5", "6", "7", "8", "7", "Intermediate"},
                {16, "What is the currency of Japan?", "Won", "Yuan", "Yen", "Ringgit", "Yen", "Intermediate"},
                {17, "What is the tallest mountain in the world?", "K2", "Mount Fuji", "Mount Everest", "Kangchenjunga", "Mount Everest", "Intermediate"},
                {18, "Who wrote the play 'Romeo and Juliet'?", "Shakespeare", "Dickens", "Austen", "Hemingway", "Shakespeare", "Intermediate"},
                {19, "What is the chemical symbol for oxygen?", "O", "O2", "Ox", "O3", "O", "Intermediate"},
                {20, "Which element has the atomic number 1?", "Hydrogen", "Oxygen", "Helium", "Carbon", "Hydrogen", "Intermediate"},
                {21, "In which year did World War II end?", "1942", "1944", "1945", "1950", "1945", "Advanced"},
                {22, "What is the largest island in the world?", "Australia", "Greenland", "New Guinea", "Borneo", "Greenland", "Advanced"},
                {23, "Who painted the Mona Lisa?", "Van Gogh", "Picasso", "Da Vinci", "Monet", "Da Vinci", "Advanced"},
                {24, "What is the hardest natural substance on Earth?", "Diamond", "Gold", "Iron", "Graphene", "Diamond", "Advanced"},
                {25, "Who discovered penicillin?", "Einstein", "Fleming", "Newton", "Curie", "Fleming", "Advanced"},
                {26, "Which country has the most pyramids?", "Egypt", "Sudan", "Mexico", "Peru", "Sudan", "Advanced"},
                {27, "Which planet has the most moons?", "Mars", "Jupiter", "Saturn", "Uranus", "Saturn", "Advanced"},
                {28, "What is the largest species of shark?", "Great White Shark", "Whale Shark", "Hammerhead Shark", "Tiger Shark", "Whale Shark", "Advanced"},
                {29, "Which element is the main component of diamonds?", "Carbon", "Oxygen", "Nitrogen", "Silicon", "Carbon", "Advanced"},
                {30, "What is the second planet from the Sun?", "Venus", "Earth", "Mars", "Mercury", "Venus", "Advanced"},
                {31, "Who invented the telephone?", "Bell", "Edison", "Tesla", "Faraday", "Bell", "Advanced"},
                {32, "Which planet is known as the Red Planet?", "Mars", "Venus", "Saturn", "Jupiter", "Mars", "Advanced"},
                {33, "What is the longest river in the world?", "Amazon", "Nile", "Yangtze", "Mississippi", "Amazon", "Advanced"},
                {34, "Which city is known as the Big Apple?", "Los Angeles", "Chicago", "New York", "Miami", "New York", "Advanced"},
                {35, "What is the tallest building in the world?", "Burj Khalifa", "Eiffel Tower", "Shanghai Tower", "Tokyo Tower", "Burj Khalifa", "Advanced"}
            };

            for (Object[] q : updatedQuestions) {
                pstmt.setString(1, (String) q[1]); // Question
                pstmt.setString(2, (String) q[2]); // Option A
                pstmt.setString(3, (String) q[3]); // Option B
                pstmt.setString(4, (String) q[4]); // Option C
                pstmt.setString(5, (String) q[5]); // Option D
                pstmt.setString(6, (String) q[6]); // Correct Answer
                pstmt.setString(7, (String) q[7]); // Difficulty Level
                pstmt.setInt(8, (int) q[0]); // Question ID
                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("30 quiz questions updated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
