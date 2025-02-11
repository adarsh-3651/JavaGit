package np.edu.heraldcollege;

import javax.swing.*;
import java.awt.EventQueue;

/**
 * The QuizApp class represents the main window of the Quiz Application.
 * It provides buttons to navigate to the quiz game, admin page, or view scores.
 */
public class QuizApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private int loginAttempts = 0;

    /**
     * Main method to launch the Quiz Application.
     * Initializes the QuizApp frame and sets it visible.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                QuizApp frame = new QuizApp();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Constructor that initializes the main QuizApp window.
     * Sets up the layout, buttons, and actions for navigating the application.
     */
    public QuizApp() {
        setTitle("Quiz Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 483, 374);  // Adjusted size to accommodate new button
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Quiz Application");
        welcomeLabel.setBounds(120, 30, 200, 25);
        contentPane.add(welcomeLabel);

        // Button for Admin login
        JButton adminButton = new JButton("Admin");
        adminButton.addActionListener(e -> enterAdminPassword());
        adminButton.setBounds(170, 80, 100, 30);
        contentPane.add(adminButton);

        // Button to play the quiz game
        JButton playQuizButton = new JButton("Play Quiz");
        playQuizButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Welcome to the Quiz Game!");
            dispose();
            SwingUtilities.invokeLater(() -> new PlayerSetup());
        });
        playQuizButton.setBounds(170, 120, 100, 30);
        contentPane.add(playQuizButton);

        // Button to view scores
        JButton scoreButton = new JButton("Score");
        scoreButton.addActionListener(e -> {
            dispose();
            openCompetitorViewer();
        });
        scoreButton.setBounds(170, 160, 100, 30);
        contentPane.add(scoreButton);
        
        // Logo image
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setIcon(new ImageIcon("C:\\Users\\adars\\Desktop\\Herald College\\LEVEL 5\\SEMESTER 1\\Object-Oriented Design and Programming\\Coursework\\AdarshKumar_Mandal_2431875\\src\\np\\edu\\heraldcollege\\logoo.jpg"));
        lblNewLabel.setBounds(0, 237, 469, 76);
        contentPane.add(lblNewLabel);
    }

    /**
     * Prompts the user to enter the admin password. The user has 3 attempts to enter the correct password.
     * If the password is correct, the admin page is opened.
     * If the password is incorrect, the user is informed of the remaining attempts.
     * If the user exceeds 3 attempts, the application will close.
     */
    private void enterAdminPassword() {
        while (loginAttempts < 3) {
            JPasswordField passwordField = new JPasswordField(10);
            int option = JOptionPane.showConfirmDialog(null, passwordField,
                    "Enter Admin Password:", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String password = new String(passwordField.getPassword());
                if (password.equals("adarsh147")) { 
                	dispose();
                    openAdminPage();
                    return;
                } else {
                    loginAttempts++;
                    if (loginAttempts >= 3) {
                        JOptionPane.showMessageDialog(null, "Wrong password entered 3 times. Closing application.");
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect password. You have " + (3 - loginAttempts) + " attempts left.");
                    }
                }
            }
            else if(option == JOptionPane.CANCEL_OPTION){
                // Restart the application if canceled
               // SwingUtilities.invokeLater(() -> new QuizApp().setVisible(true));
                return;
            }
            else {
                System.exit(0); // Close the application if the dialog is closed unexpectedly
            }
        }
    }

    /**
     * Opens the Admin Page when the correct password is entered.
     * Disposes of the current QuizApp window and opens a new AdminPage window.
     */
    private void openAdminPage() {
        AdminPage adminPage = new AdminPage();
        dispose();
        adminPage.setVisible(true);
    }

    /**
     * Opens the Competitor Viewer window, where scores and competitor data can be viewed.
     * Disposes of the current QuizApp window and opens the CompetitorViewerGUI window.
     */
    private void openCompetitorViewer() {
        CompetitorViewerGUI competitorViewerGUI = new CompetitorViewerGUI();
        dispose();
        competitorViewerGUI.frame.setVisible(true);
    }
}
