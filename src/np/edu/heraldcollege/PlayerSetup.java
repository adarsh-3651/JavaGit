package np.edu.heraldcollege;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The PlayerSetup class represents the initial player setup screen.
 * It allows the user to choose between logging in as an existing player or creating a new player.
 */
public class PlayerSetup {

    private JFrame frame;

    /**
     * Constructor that initializes the PlayerSetup GUI components.
     * It sets up the layout and the buttons for existing and new players.
     */
    public PlayerSetup() {
        initializeGUI();
    }

    /**
     * Initializes the GUI components of the PlayerSetup window.
     * It creates and sets up the buttons and the logo image.
     */
    private void initializeGUI() {
        frame = new JFrame("Player Setup");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1075, 718);
        frame.setLocationRelativeTo(null); // Center the window on the screen

        // Button for Existing Player
        JButton btnExistingPlayer = new JButton("Existing Player");
        btnExistingPlayer.setBounds(140, 406, 307, 78);  // Set button size and position
        btnExistingPlayer.setFont(new Font("Tahoma", Font.BOLD, 20)); // Set font style and size
        btnExistingPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open LoginPage when "Existing Player" is clicked
                frame.setVisible(false);  // Hide the current frame
                new LoginPage().setVisible(true);  // Open LoginPage
            }
        });

        // Button for New Player
        JButton btnNewPlayer = new JButton("New Player");
        btnNewPlayer.setBounds(547, 406, 325, 78);  // Set button size and position
        btnNewPlayer.setFont(new Font("Tahoma", Font.BOLD, 20)); // Set font style and size
        btnNewPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open CompetitorManagerGUI when "New Player" is clicked
                frame.setVisible(false);  // Hide the current frame
                new CompetitorManagerGUI().setVisible(true);  // Open CompetitorManagerGUI
            }
        });

        // Set the layout for the content pane
        frame.getContentPane().setLayout(null);

        // Add buttons to the frame
        frame.getContentPane().add(btnExistingPlayer);
        frame.getContentPane().add(btnNewPlayer);
        
        // Add the logo image
        JLabel lblNewLabel = new JLabel(new ImageIcon("C:\\Users\\adars\\Desktop\\Herald College\\LEVEL 5\\SEMESTER 1\\Object-Oriented Design and Programming\\Coursework\\AdarshKumar_Mandal_2431875\\src\\np\\edu\\heraldcollege\\logo.jpg"));
        lblNewLabel.setBounds(21, 10, 1030, 332);  // Set image bounds
        frame.getContentPane().add(lblNewLabel);
        
        JButton btnNewButton = new JButton("Main Page");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		frame.dispose();
        		SwingUtilities.invokeLater(() -> new QuizApp().setVisible(true));
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnNewButton.setBounds(848, 554, 103, 21);
        frame.getContentPane().add(btnNewButton);

        // Make the frame visible
        frame.setVisible(true);
    }

    /**
     * Main method to launch the PlayerSetup window.
     * This method is called to initialize and display the PlayerSetup GUI.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        // Start the PlayerSetup GUI
        SwingUtilities.invokeLater(() -> new PlayerSetup());
    }
}
