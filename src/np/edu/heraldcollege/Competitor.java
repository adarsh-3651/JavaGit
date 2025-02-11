package np.edu.heraldcollege;

import java.util.Arrays;

/**
 * The Competitor class represents a participant in a competition.
 * This class demonstrates Object-Oriented Programming (OOP) principles such as:
 * - Encapsulation: Private fields with public getters and setters.
 * - Abstraction: Methods like getOverallScore() provide an abstracted way of calculating scores.
 * - Polymorphism: Overriding the calculation logic based on competition levels.
 * - Inheritance (Potential): This class can be extended for specific competitor types.
 */
public class Competitor {
    // Instance variables (Encapsulation: Private variables with controlled access)
    private int competitorID;
    private String firstName;
    private String lastName;
    private String level; // e.g., Beginner, Intermediate, Advanced
    private String country;
    private int[] scores; // Array to store scores
    private int id; // Added for the new structure
    private String username; // Added for the new structure

    /**
     * Constructor to initialize a Competitor object.
     * Demonstrates Constructor Overloading.
     *
     * @param competitorID Unique ID for the competitor
     * @param firstName First name of the competitor
     * @param lastName Last name of the competitor
     * @param level Competition level (Beginner, Intermediate, Advanced)
     * @param country Country of the competitor
     * @param scores Array of scores obtained by the competitor
     * @param id Additional ID parameter
     * @param username Competitor's username
     */
    public Competitor(int competitorID, String firstName, String lastName, String level, String country, int[] scores, int id, String username) {
        this.competitorID = competitorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.country = country;
        this.id = id;
        this.username = username;
    }

    // Encapsulation: Getters and Setters
    public int getCompetitorID() { return competitorID; }
    public void setCompetitorID(int competitorID) { this.competitorID = competitorID; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public int[] getScores() { return scores; }
    public void setScores(int[] score) { this.scores = score; }
    public int getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    /**
     * Calculates the overall score for the competitor.
     * Uses polymorphism by adjusting scores based on the competition level.
     *
     * @return Overall adjusted score
     */
    public double getOverallScore() {
        if (scores == null || scores.length == 0) {
            return 0.0;
        }

        int sum = 0, min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;

        // Find sum, min, and max scores
        for (int score : scores) {
            sum += score;
            if (score < min) min = score;
            if (score > max) max = score;
        }

        // Exclude highest and lowest scores from the calculation
        sum -= (min + max);
        double average = (double) sum / (scores.length - 2);

        // Adjust average based on competition level (Polymorphism: Different behavior per level)
        switch (level.toLowerCase()) {
            case "beginner":
                return average * 0.9;
            case "intermediate":
                return average;
            case "advanced":
                return average * 1.1;
            default:
                return average;
        }
    }

    /**
     * Returns detailed information about the competitor.
     *
     * @return Full details of the competitor as a formatted string.
     */
    public String getFullDetails() {
        return "Competitor number " + competitorID + ", name " + firstName + " " + lastName + 
               ", country " + country + ".\n" +
               firstName + " is a " + level + ", received these scores: " + Arrays.toString(scores) +
               ", and has an overall score of " + String.format("%.2f", getOverallScore()) + "." +
               "\nUsername: " + username + ", ID: " + id;
    }

    /**
     * Returns a short summary of the competitor's details.
     *
     * @return Short details including initials and overall score.
     */
    public String getShortDetails() {
        return "CN " + competitorID + " (" + firstName.charAt(0) + lastName.charAt(0) + 
               ") has an overall score of " + String.format("%.2f", getOverallScore()) + "." +
               "\nUsername: " + username;
    }

    /**
     * Computes the total score of a competitor.
     *
     * @return Sum of all scores.
     */
    public int getScore() {
        if (scores != null && scores.length > 0) {
            int totalScore = 0;
            for (int score : scores) {
                totalScore += score;
            }
            return totalScore; 
        }
        return 0;
    }

    /**
     * Main method to demonstrate the functionality of the Competitor class.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Sample scores for competitors
        int[] scores1 = {4, 3, 5, 2, 4};
        int[] scores2 = {3, 4, 4, 5, 4};
        int[] scores3 = {5, 5, 4, 4, 5};

        // Creating sample competitors
        Competitor c1 = new Competitor(200, "Alice", "Green", "Beginner", "USA", scores1, 1, "alice123");
        Competitor c2 = new Competitor(201, "Bob", "Brown", "Intermediate", "Canada", scores2, 2, "bob456");
        Competitor c3 = new Competitor(202, "Carol", "White", "Advanced", "UK", scores3, 3, "carol789");

        // Testing methods
        System.out.println(c1.getFullDetails());
        System.out.println(c2.getShortDetails());
        System.out.println(c3.getFullDetails());
    }
}
