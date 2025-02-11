package np.edu.heraldcollege;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The QuizGamePage class represents the main page for the quiz game.
 * It displays questions and options in a random order, tracks the score, and handles game logic.
 */
@SuppressWarnings("serial")
public class QuizGamePage extends JFrame {
    private Competitor competitor;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score;
    
    private JLabel nameLabel, scoreLabel, questionLabel;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ButtonGroup optionsGroup;
    private JButton nextButton;

    /**
     * Constructor for the QuizGamePage.
     * Initializes the game page, retrieves questions randomly based on the competitor's level,
     * and sets up the UI elements.
     * 
     * @param competitor The competitor object representing the current player.
     */
    public QuizGamePage(Competitor competitor) {
        this.competitor = competitor;
        this.questions = fetchQuestions(competitor.getLevel());
        this.score = competitor.getScore();
        
        setTitle("Quiz Game - Level: " + competitor.getLevel());
        setSize(1026, 775);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        nameLabel = new JLabel("Player: " + competitor.getFirstName());
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        nameLabel.setBounds(58, 0, 424, 123);
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        scoreLabel.setBounds(616, 0, 396, 123);

        questionLabel = new JLabel("");
        questionLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        questionLabel.setBounds(58, 122, 506, 123);
        optionA = new JRadioButton();
        optionA.setBounds(58, 429, 506, 49);
        optionB = new JRadioButton();
        optionB.setBounds(58, 271, 506, 59);
        optionC = new JRadioButton();
        optionC.setBounds(58, 511, 506, 49);
        optionD = new JRadioButton();
        optionD.setBounds(58, 354, 506, 49);
        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        nextButton = new JButton("Next");
        nextButton.setForeground(new Color(128, 128, 192));
        nextButton.setBounds(705, 374, 232, 90);
        getContentPane().setLayout(null);

        getContentPane().add(nameLabel);
        getContentPane().add(scoreLabel);
        getContentPane().add(questionLabel);
        getContentPane().add(optionA);
        getContentPane().add(optionB);
        getContentPane().add(optionC);
        getContentPane().add(optionD);
        getContentPane().add(nextButton);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!checkAnswer()) {  // If wrong answer is chosen, game ends
                    endGame();
                } else if (currentQuestionIndex < questions.size() - 1) {
                    currentQuestionIndex++;
                    displayQuestion();
                } else {
                    endGame();
                }
            }
        });

        displayQuestion();
    }

    /**
     * Fetches a list of questions from the database based on the player's level.
     * The questions are then shuffled to appear in a random order.
     * 
     * @param level The level of the current player.
     * @return A shuffled list of questions for the given level.
     */
    private List<Question> fetchQuestions(String level) {
        List<Question> questionList = new ArrayList<>();
        String query = "SELECT * FROM Questions WHERE level=?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/competitiondb", "root", "");
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, level);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questionList.add(new Question(rs.getString("question"),
                        rs.getString("optionA"), rs.getString("optionB"),
                        rs.getString("optionC"), rs.getString("optionD"),
                        rs.getString("rightAnswer")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.shuffle(questionList); // Shuffle the questions randomly
        return questionList;
    }

    /**
     * Displays the current question and options on the UI.
     */
    private void displayQuestion() {
        Question q = questions.get(currentQuestionIndex);
        questionLabel.setText(q.getQuestion());
        optionA.setText(q.getOptionA());
        optionB.setText(q.getOptionB());
        optionC.setText(q.getOptionC());
        optionD.setText(q.getOptionD());
        optionsGroup.clearSelection();
    }

    /**
     * Checks if the selected answer is correct.
     * 
     * @return true if the selected answer is correct, false otherwise.
     */
    private boolean checkAnswer() {
        Question q = questions.get(currentQuestionIndex);
        if ((optionA.isSelected() && q.getRightAnswer().equals(optionA.getText())) ||
            (optionB.isSelected() && q.getRightAnswer().equals(optionB.getText())) ||
            (optionC.isSelected() && q.getRightAnswer().equals(optionC.getText())) ||
            (optionD.isSelected() && q.getRightAnswer().equals(optionD.getText()))) {
            score++;
            scoreLabel.setText("Score: " + score);
            return true;
        }
        return false; // Wrong answer selected
    }

    /**
     * Ends the game by showing the final score and asking if the player wants to play again.
     * If the player chooses to play again, the game restarts.
     * Otherwise, the application will close.
     */
    private void endGame() {
        sendScoreToHandler(); // Send score to Score_Handling class
        int choice = JOptionPane.showConfirmDialog(null,
                "Game Over! Your final score: " + score + "\nDo you want to play again?",
                "Game Over",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            dispose();  // Close the current window
            SwingUtilities.invokeLater(() -> new QuizGamePage(competitor).setVisible(true)); // Display the new window
        }
        else {
            dispose();
            JOptionPane.showMessageDialog(null, "Thank you for playing the game.");
            SwingUtilities.invokeLater(() -> new QuizApp().setVisible(true));
        }
    }

    /**
     * Sends the final score to the Score_Handling class to update the competitor's score.
     */
    private void sendScoreToHandler() {
        Score_Handling.updateScore(competitor.getCompetitorID(), score);
    }

    /**
     * Main method to test the game with a sample competitor.
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Competitor testCompetitor = new Competitor(1, "TestUser", "Test", "Beginner", "Nepal", new int[]{}, 1, "testuser");
        SwingUtilities.invokeLater(() -> new QuizGamePage(testCompetitor).setVisible(true));
    }
}
