package np.edu.heraldcollege;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

/**
 * CompetitorViewerGUI class allows viewing and managing competitor information
 * from the database based on selected levels.
 */
public class CompetitorViewerGUI {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/CompetitionDB";  // Database URL
    private static final String USER = "root";  // Database username
    private static final String PASSWORD = "";  // Database password

    public JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;

    /**
     * Constructor for initializing the CompetitorViewerGUI.
     */
    public CompetitorViewerGUI() {
        initializeGUI();
        showLevelSelectionDialog();
    }

    /**
     * Initializes the main GUI components such as the table, buttons, and layout.
     */
    private void initializeGUI() {
        frame = new JFrame("Competitor List");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1055, 705);
        frame.getContentPane().setLayout(null);

        // Define columns without Username and Password
        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name", "Level", "Country", "Score", "Overall Score"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 1000, 617);
        frame.getContentPane().add(scrollPane);

        // Select Level Button
        JButton btnSelectLevel = new JButton("Select Level");
        btnSelectLevel.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnSelectLevel.setBounds(445, 627, 125, 21);
        frame.getContentPane().add(btnSelectLevel);

        // Hide competitor details button
        JButton btnHideDetails = new JButton("Hide Competitors Detail");
        btnHideDetails.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnHideDetails.setBounds(616, 627, 139, 21);
        frame.getContentPane().add(btnHideDetails);

        // Main Page Button
        JButton btnMainPage = new JButton("Main Page");
        btnMainPage.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnMainPage.setBounds(801, 627, 101, 21);
        frame.getContentPane().add(btnMainPage);
        
        JButton btnNewButton = new JButton("Report");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		displayTopCompetitorReport();
        	}
        });
        btnNewButton.setBounds(268, 627, 85, 21);
        frame.getContentPane().add(btnNewButton);

        // Hide details action
        btnHideDetails.addActionListener(e -> tableModel.setRowCount(0));

        // Action listener for "Main Page" button
        btnMainPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                SwingUtilities.invokeLater(() -> new QuizApp().setVisible(true));
            }
        });

        // Action listener for "Select Level" button
        btnSelectLevel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLevelSelectionDialog();
            }
        });

        frame.setVisible(true);
    }

    /**
     * Shows a pop-up with three level selection buttons: Beginner, Intermediate, Advanced.
     */
    private void showLevelSelectionDialog() {
        JDialog levelDialog = new JDialog(frame, "Select Level", true);
        levelDialog.setSize(300, 200);
        levelDialog.getContentPane().setLayout(null);

        JButton btnBeginner = new JButton("Beginner");
        btnBeginner.setBounds(90, 20, 120, 30);
        levelDialog.getContentPane().add(btnBeginner);

        JButton btnIntermediate = new JButton("Intermediate");
        btnIntermediate.setBounds(90, 60, 120, 30);
        levelDialog.getContentPane().add(btnIntermediate);

        JButton btnAdvanced = new JButton("Advanced");
        btnAdvanced.setBounds(90, 100, 120, 30);
        levelDialog.getContentPane().add(btnAdvanced);

        // Action Listeners
        btnBeginner.addActionListener(e -> {
            viewCompetitorsByLevel("Beginner");
            levelDialog.dispose();
        });

        btnIntermediate.addActionListener(e -> {
            viewCompetitorsByLevel("Intermediate");
            levelDialog.dispose();
        });

        btnAdvanced.addActionListener(e -> {
            viewCompetitorsByLevel("Advanced");
            levelDialog.dispose();
        });

        levelDialog.setLocationRelativeTo(frame);
        levelDialog.setVisible(true);
    }

    /**
     * Fetches and displays competitors based on the selected level.
     */
    private void viewCompetitorsByLevel(String level) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Competitor WHERE Level = ?")) {
            
            stmt.setString(1, level);
            ResultSet rs = stmt.executeQuery();

            tableModel.setRowCount(0); // Clear the table before populating
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getInt("CompetitorID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("Level"),
                    rs.getString("Country"),
                    rs.getString("Score"),
                    rs.getString("OverallScore")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error fetching data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    /**
     * Displays a report of the top competitor with the highest overall score.
     * The details include the competitor's ID, name, level, country, score, and overall score.
     */
    private void displayTopCompetitorReport() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Competitor ORDER BY OverallScore DESC LIMIT 1")) {

            if (rs.next()) {
                // Get competitor details
                int competitorID = rs.getInt("CompetitorID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String level = rs.getString("Level");
                String country = rs.getString("Country");
                String score = rs.getString("Score");
                String overallScore = rs.getString("OverallScore");

                // Display competitor details with highest overall score
                String reportDetails = String.format("Top Competitor:\nID: %d\nFirst Name: %s\nLast Name: %s\nLevel: %s\nCountry: %s\nScore: %s\nOverall Score: %s",
                        competitorID, firstName, lastName, level, country, score, overallScore);

                JOptionPane.showMessageDialog(frame, reportDetails, "Top Competitor Report", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No competitors found in the database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error generating report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
