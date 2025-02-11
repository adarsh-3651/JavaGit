package np.edu.heraldcollege;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * AdminPage is a GUI-based Java application for managing quiz questions.
 * 
 * Features:
 * - Add, update, and delete quiz questions from a MySQL database.
 * - Displays the existing questions in a JTable.
 * - Validates input before inserting or updating questions.
 * 
 * Database: `CompetitionDB`
 * Table: `Questions`
 * Columns: No (Primary Key), question, optionA, optionB, optionC, optionD, rightAnswer, level
 * 
 * @author Adarsh Kumar Mandal
 * @version
 */
public class AdminPage extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField questionField, optionAField, optionBField, optionCField, optionDField, answerField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnNewButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/CompetitionDB";
    private static final String USER = "root";
    private static final String PASS = "";
    private JComboBox<String> levelField;

    /**
     * Main method to launch the AdminPage.
     * 
     * @param args Command-line arguments (unused)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AdminPage frame = new AdminPage();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Constructor: Initializes the AdminPage GUI and loads questions from the database.
     */
    public AdminPage() {
        super("Admin Page - Manage Questions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        String[] columnNames = {"No", "Question", "A", "B", "C", "D", "Answer", "Level"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        contentPane.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Manage Questions"));
        
        formPanel.add(new JLabel("Question:"));
        questionField = new JTextField();
        formPanel.add(questionField);
        
        formPanel.add(new JLabel("Option A:"));
        optionAField = new JTextField();
        formPanel.add(optionAField);

        formPanel.add(new JLabel("Option B:"));
        optionBField = new JTextField();
        formPanel.add(optionBField);

        formPanel.add(new JLabel("Option C:"));
        optionCField = new JTextField();
        formPanel.add(optionCField);

        formPanel.add(new JLabel("Option D:"));
        optionDField = new JTextField();
        formPanel.add(optionDField);

        formPanel.add(new JLabel("Correct Answer:"));
        answerField = new JTextField();
        formPanel.add(answerField);

        formPanel.add(new JLabel("Level:"));
        
     // Creating a JComboBox with predefined levels
        levelField = new JComboBox<String>(new String[] {"Beginner", "Intermediate", "Advanced"});
        formPanel.add(levelField);


        contentPane.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Question");
        JButton updateButton = new JButton("Update Question");
        JButton deleteButton = new JButton("Delete Question");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        btnNewButton = new JButton("Main Page");
        btnNewButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new QuizApp().setVisible(true));
        });
        buttonPanel.add(btnNewButton);

        loadQuestions();

        addButton.addActionListener(e -> addQuestion());
        updateButton.addActionListener(e -> updateQuestion());
        deleteButton.addActionListener(e -> deleteQuestion());
    }

    /**
     * Loads questions from the database and displays them in the table.
     */
    private void loadQuestions() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Questions")) {

            tableModel.setRowCount(0);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("No"),
                        rs.getString("question"),
                        rs.getString("optionA"),
                        rs.getString("optionB"),
                        rs.getString("optionC"),
                        rs.getString("optionD"),
                        rs.getString("rightAnswer"),
                        rs.getString("level")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Validates user input before inserting or updating a question.
     * Ensures all fields are filled and the level is selected properly.
     *
     * @return true if input is valid, false otherwise.
     */
    private boolean validateInput() {
        if (questionField.getText().trim().isEmpty() ||
            optionAField.getText().trim().isEmpty() ||
            optionBField.getText().trim().isEmpty() ||
            optionCField.getText().trim().isEmpty() ||
            optionDField.getText().trim().isEmpty() ||
            answerField.getText().trim().isEmpty() ||
            levelField.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Get selected level
        String level = levelField.getSelectedItem().toString().trim();

        // Ensure it's a valid level
        if (!level.equals("Beginner") && 
            !level.equals("Intermediate") && 
            !level.equals("Advanced")) {
            
            JOptionPane.showMessageDialog(this, "Level must be 'Beginner', 'Intermediate', or 'Advanced'!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }



    /**
     * Adds a new question to the database.
     */
    private void addQuestion() {
        if (!validateInput()) return;

        String query = "INSERT INTO Questions (question, optionA, optionB, optionC, optionD, rightAnswer, level) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, questionField.getText().trim());
            pstmt.setString(2, optionAField.getText().trim());
            pstmt.setString(3, optionBField.getText().trim());
            pstmt.setString(4, optionCField.getText().trim());
            pstmt.setString(5, optionDField.getText().trim());
            pstmt.setString(6, answerField.getText().trim());
            
            pstmt.executeUpdate();
            loadQuestions();
            JOptionPane.showMessageDialog(this, "Question added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the selected question in the database.
     */
    private void updateQuestion() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a question to update!");
            return;
        }

        if (!validateInput()) return;

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        String query = "UPDATE Questions SET question=?, optionA=?, optionB=?, optionC=?, optionD=?, rightAnswer=?, level=? WHERE No=?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, questionField.getText().trim());
            pstmt.setString(2, optionAField.getText().trim());
            pstmt.setString(3, optionBField.getText().trim());
            pstmt.setString(4, optionCField.getText().trim());
            pstmt.setString(5, optionDField.getText().trim());
            pstmt.setString(6, answerField.getText().trim());
            pstmt.setInt(8, id);
            pstmt.executeUpdate();

            loadQuestions();
            JOptionPane.showMessageDialog(this, "Question updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        /**
         * Deletes a selected question from the database.
         * Demonstrates **Encapsulation** by keeping database logic inside the method.
         */
        private void deleteQuestion() {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Select a question to delete!");
                return;
            }

            int id = (int) tableModel.getValueAt(selectedRow, 0); 
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt = conn.createStatement()) {
                
                stmt.executeUpdate("DELETE FROM Questions WHERE No=" + id); // Executes delete command
                loadQuestions(); // Refreshes table after deletion
                JOptionPane.showMessageDialog(this, "Question deleted successfully!");
                clearFields(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /**
         * Clears all input fields in the form.
         * Demonstrates **Encapsulation** by grouping field clearing logic.
         */
        private void clearFields() {
            questionField.setText("");
            optionAField.setText("");
            optionBField.setText("");
            optionCField.setText("");
            optionDField.setText("");
            answerField.setText("");
        }
    }
