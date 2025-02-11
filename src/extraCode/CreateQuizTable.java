package extraCode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateQuizTable {
    private static final String URL = "jdbc:mysql://localhost:3306/competitiondb";
    private static final String USER = "root"; // Change to your MySQL username
    private static final String PASSWORD = ""; // Change to your MySQL password

    public static void main(String[] args) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Questions ("
                + "No INT AUTO_INCREMENT PRIMARY KEY, "
                + "question TEXT NOT NULL, "
                + "optionA VARCHAR(255) NOT NULL, "
                + "optionB VARCHAR(255) NOT NULL, "
                + "optionC VARCHAR(255) NOT NULL, "
                + "optionD VARCHAR(255) NOT NULL, "
                + "rightAnswer VARCHAR(255) NOT NULL, "
                + "level VARCHAR(50) NOT NULL" // Adding the 'Level' column
                + ");";
        
//        String createLoginTable="CREATE TABLE IF NOT EXISTS Player Login ("
//        	    +"Login name VARCHAR(255) PRIMARY KEY,"
//        	    +"Password VARCHAR(255) NOT NULL"
//        	+");";


        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(createTableSQL);
            //stmt.executeUpdate(createLoginTable);
            System.out.println("Table 'Questions' created successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}