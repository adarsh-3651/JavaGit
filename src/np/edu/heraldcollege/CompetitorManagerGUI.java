package np.edu.heraldcollege;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * A graphical user interface for managing competitor registration in a competition.
 * <p>
 * This class allows users to register new competitors by entering their details, such as 
 * first name, last name, level, country, username, and password. It interacts with a 
 * MySQL database to store competitor information.
 */
public class CompetitorManagerGUI {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/CompetitionDB";  // Database URL
    private static final String USER = "root";  // Database username
    private static final String PASSWORD = "";  // Database password

    private JFrame frame;  // Main frame of the GUI
    private JTextField txtFirstName, txtLastName, txtCountry, txtUsername;  // Text fields for input
    private JPasswordField txtPassword;  // Password field for input
    private JComboBox<String> txtLevel;
    /**
     * Main method to launch the application.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CompetitorManagerGUI app = new CompetitorManagerGUI();
            app.setVisible(true);
        });
    }

    /**
     * Constructor to initialize the GUI components.
     */
    public CompetitorManagerGUI() {
        initializeGUI();
    }

    /**
     * Initializes the main GUI components including panels, labels, and buttons.
     */
    private void initializeGUI() {
        frame = new JFrame("Competition Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(818, 747);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel formPanel = createFormPanel();
        frame.getContentPane().add(formPanel, BorderLayout.CENTER);

        JPanel actionPanel = createActionPanel();
        frame.getContentPane().add(actionPanel, BorderLayout.SOUTH);
    }

    /**
     * Makes the frame visible.
     * @param visible A boolean indicating if the frame should be visible
     */
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    /**
     * Creates the form panel containing input fields for competitor registration.
     * @return The form panel
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Removed CompetitorID field
        JLabel label = new JLabel("First Name:");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(0, 1, 353, 71);
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panel.add(label);
        txtFirstName = new JTextField();
        txtFirstName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtFirstName.setBounds(363, 1, 353, 59);
        panel.add(txtFirstName);

        JLabel label_1 = new JLabel("Last Name:");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        label_1.setBounds(0, 82, 353, 71);
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panel.add(label_1);
        txtLastName = new JTextField();
        txtLastName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtLastName.setBounds(363, 82, 353, 59);
        panel.add(txtLastName);

        JLabel label_2 = new JLabel("Level:");
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setBounds(0, 163, 353, 71);
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
        panel.add(label_2);

        JLabel label_3 = new JLabel("Country:");
        label_3.setHorizontalAlignment(SwingConstants.CENTER);
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label_3.setBounds(0, 244, 353, 71);
        panel.add(label_3);
        txtCountry = new JTextField();
        txtCountry.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtCountry.setBounds(363, 244, 353, 59);
        panel.add(txtCountry);

        JLabel label_4 = new JLabel("Username:");
        label_4.setHorizontalAlignment(SwingConstants.CENTER);
        label_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label_4.setBounds(0, 325, 353, 71);
        panel.add(label_4);
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtUsername.setBounds(363, 325, 353, 59);
        panel.add(txtUsername);

        JLabel label_5 = new JLabel("Password:");
        label_5.setHorizontalAlignment(SwingConstants.CENTER);
        label_5.setFont(new Font("Tahoma", Font.PLAIN, 20));
        label_5.setBounds(0, 406, 353, 71);
        panel.add(label_5);
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtPassword.setBounds(363, 406, 353, 59);
        panel.add(txtPassword);

        JButton btnRegister = new JButton("Register New Player");
        btnRegister.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnRegister.setBounds(205, 526, 276, 48);
        panel.add(btnRegister);
        
        JButton btnNewButton = new JButton("Main");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        		SwingUtilities.invokeLater(() -> new QuizApp().setVisible(true));
        	}
        });
        btnNewButton.setBounds(591, 544, 85, 21);
        panel.add(btnNewButton);
        
        txtLevel = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"}); // Fixed shadowing
        txtLevel.setFont(new Font("Tahoma", Font.BOLD, 20));
        txtLevel.setBounds(363, 163, 353, 59);
        panel.add(txtLevel);
        
        btnRegister.addActionListener(e -> registerNewUser());

        return panel;
    }


    /**
     * Creates an action panel for additional controls or actions (currently unused).
     * @return The action panel
     */
    private JPanel createActionPanel() {
        return new JPanel();
    }

    /**
     * Registers a new competitor in the database.
     * Validates the input fields and inserts the competitor's details into the database.
     */
    private void registerNewUser() {
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String country = txtCountry.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim(); // Convert char[] to String
		String level = txtLevel.getSelectedItem().toString().trim(); // Corrected way to get selected value
        // Check if fields are empty
        if (firstName.isEmpty() || lastName.isEmpty() || level.isEmpty() || country.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if Level is valid (Beginner, Intermediate, or Advanced)
        if (!(level.equalsIgnoreCase("Beginner") || level.equalsIgnoreCase("Intermediate") || level.equalsIgnoreCase("Advanced"))) {
            JOptionPane.showMessageDialog(null, "Level must be one of the following: Beginner, Intermediate, or Advanced", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // SQL query to insert competitor into database (CompetitorID will be auto-generated)
        String query = "INSERT INTO Competitor (FirstName, LastName, Level, Country, Username, Password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, level);
            stmt.setString(4, country);
            stmt.setString(5, username);
            stmt.setString(6, password);

            int result = stmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Competitor Registration Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
                SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
            } else {
                JOptionPane.showMessageDialog(null, "Error in registration.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
