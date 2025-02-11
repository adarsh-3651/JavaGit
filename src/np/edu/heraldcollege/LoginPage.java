package np.edu.heraldcollege;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**
 * The LoginPage class represents the login page for the quiz application.
 * It allows users to enter their username and password to authenticate and access the quiz game page.
 */
@SuppressWarnings("serial")
public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    /**
     * Constructor that initializes the LoginPage GUI components.
     * Sets up the layout, labels, text fields, and login button.
     */
    public LoginPage() {
        setTitle("Quiz Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Create and add components to the frame
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());  // Empty label for spacing
        add(loginButton);

        // Add action listener to handle login button click
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the username and password entered by the user
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Authenticate user
                Competitor competitor = authenticateUser(username, password);
                if (competitor != null) {
                    // Login successful
                    JOptionPane.showMessageDialog(null, "Login Successful! Welcome " + competitor.getFirstName());
                    dispose();  // Close the login page
                    new QuizGamePage(competitor).setVisible(true);  // Open the quiz game page
                } else {
                    // Login failed
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Authenticates the user based on the provided username and password.
     * 
     * @param username the username entered by the user.
     * @param password the password entered by the user.
     * @return a Competitor object if authentication is successful, otherwise null.
     */
    private Competitor authenticateUser(String username, String password) {
        String query = "SELECT * FROM Competitor WHERE Username=? AND Password=?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the parameters for the prepared statement
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            int score;

            // Check if the result set has data
            if (rs.next()) {
                // Handle score parsing (currently not in use)
                @SuppressWarnings("unused")
                String scoreString = rs.getString("Score");  // Read the score as a String first
                try {
                    // (Commented out) Code to parse score if necessary
//                    String[] scoreParts = scoreString.split(",");
//                    if (scoreParts.length > 0) {
//                        score = Integer.parseInt(scoreParts[0]); // Or handle it as needed
//                    }
                } catch (NumberFormatException e) {
                    // Handle case where score parsing fails (currently not needed)
                    // System.out.println("Error parsing score: " + e.getMessage());
                }
                score = 0;  // Set score to 0 for now, you can modify this logic

                // Return a Competitor object with the retrieved data
                return new Competitor(
                        rs.getInt("CompetitorID"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("Level"),
                        query,  // The password is stored as a query here but should not be used in Competitor
                        null,   // Placeholder for password (could be removed later)
                        score,  // Use the parsed score value
                        rs.getString("Username")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if authentication fails
    }

    // Main method to launch the login page
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
