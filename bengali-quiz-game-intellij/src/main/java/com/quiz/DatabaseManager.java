package com.quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:quiz.db";
    
    public void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            createTables(conn);
            insertDefaultQuestions(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void createTables(Connection conn) throws SQLException {
        // Create users table
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL
            )
        """;
        
        // Create questions table
        String createQuestionsTable = """
            CREATE TABLE IF NOT EXISTS questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                question_text TEXT NOT NULL,
                option1 TEXT NOT NULL,
                option2 TEXT NOT NULL,
                option3 TEXT NOT NULL,
                option4 TEXT NOT NULL,
                correct_answer INTEGER NOT NULL
            )
        """;
        
        // Create scores table
        String createScoresTable = """
            CREATE TABLE IF NOT EXISTS scores (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id TEXT NOT NULL,
                score INTEGER NOT NULL,
                date_taken TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users (user_id)
            )
        """;
        
        Statement stmt = conn.createStatement();
        stmt.execute(createUsersTable);
        stmt.execute(createQuestionsTable);
        stmt.execute(createScoresTable);
    }
    
    private void insertDefaultQuestions(Connection conn) throws SQLException {
        // Check if questions already exist
        String checkQuery = "SELECT COUNT(*) FROM questions";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(checkQuery);
        if (rs.next() && rs.getInt(1) > 0) {
            return; // Questions already exist
        }
        
        // Insert Bengali historical questions
        String insertQuestion = """
            INSERT INTO questions (question_text, option1, option2, option3, option4, correct_answer) 
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        
        PreparedStatement pstmt = conn.prepareStatement(insertQuestion);
        
        // Question 1
        pstmt.setString(1, "বাংলাদেশের স্বাধীনতা যুদ্ধ কোন সালে শুরু হয়েছিল?");
        pstmt.setString(2, "১৯৭০ সাল");
        pstmt.setString(3, "১৯৭১ সাল");
        pstmt.setString(4, "১৯৭২ সাল");
        pstmt.setString(5, "১৯৭৩ সাল");
        pstmt.setInt(6, 2); // Correct answer is option 2
        pstmt.executeUpdate();
        
        // Question 2
        pstmt.setString(1, "মহান ভাষা আন্দোলন কোন সালে ঘটেছিল?");
        pstmt.setString(2, "১৯৫০ সাল");
        pstmt.setString(3, "১৯৫১ সাল");
        pstmt.setString(4, "১৯৫২ সাল");
        pstmt.setString(5, "১৯৫৩ সাল");
        pstmt.setInt(6, 3); // Correct answer is option 3
        pstmt.executeUpdate();
    }
    
    public boolean loginUser(String userId, String password) {
        String query = "SELECT * FROM users WHERE user_id = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, userId);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean registerUser(String userId, String password) {
        String query = "INSERT INTO users (user_id, password) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, userId);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions";
        
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Question question = new Question(
                    rs.getInt("id"),
                    rs.getString("question_text"),
                    rs.getString("option1"),
                    rs.getString("option2"),
                    rs.getString("option3"),
                    rs.getString("option4"),
                    rs.getInt("correct_answer")
                );
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return questions;
    }
    
    public void saveScore(String userId, int score) {
        String query = "INSERT INTO scores (user_id, score) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, userId);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int getBestScore(String userId) {
        String query = "SELECT MAX(score) as best_score FROM scores WHERE user_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("best_score");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
