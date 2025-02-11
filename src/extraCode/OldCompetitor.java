package extraCode;

/**
 * Competitor class representing details of a competition participant.
 * Includes attributes like ID, name, competition level, and extra attribute (e.g., country).
 */
public class OldCompetitor {
    // Instance variables
    private int competitorID;
    private String firstName;
    private String lastName;
    private String level; // e.g., Beginner, Intermediate, Advanced
    private String country;

    // Constructor
    public OldCompetitor(int competitorID, String firstName, String lastName, String level, String country) {
        this.competitorID = competitorID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.country = country;
    }

    // Getters and Setters
    public int getCompetitorID() {
        return competitorID;
    }

    public void setCompetitorID(int competitorID) {
        this.competitorID = competitorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Placeholder for calculating the overall score.
     * Currently returns a default score of 5.
     */
    public double getOverallScore() {
        return 5.0;
    }

    /**
     * Returns full details of the competitor.
     */
    public String getFullDetails() {
        return "Competitor number " + competitorID + ", name " + firstName + " " + lastName + 
               ", country " + country + ".\n" +
               firstName + " is a " + level + " and has an overall score of " + getOverallScore() + ".";
    }

    /**
     * Returns short details of the competitor.
     */
    public String getShortDetails() {
        return "CN " + competitorID + " (" + firstName.charAt(0) + lastName.charAt(0) + ") has an overall score of " + getOverallScore() + ".";
    }

    /**
     * Main method to test the Competitor class functionality.
     */
    public static void main(String[] args) {
        // Creating sample competitors
        OldCompetitor c1 = new OldCompetitor(200, "Alice", "Green", "Beginner", "USA");
        OldCompetitor c2 = new OldCompetitor(201, "Bob", "Brown", "Intermediate", "Canada");
        OldCompetitor c3 = new OldCompetitor(202, "Carol", "White", "Advanced", "UK");

        // Testing methods
        System.out.println(c1.getFullDetails());
        System.out.println(c2.getShortDetails());
        System.out.println(c3.getFullDetails());
    }
}

