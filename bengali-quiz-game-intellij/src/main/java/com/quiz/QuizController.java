package com.quiz;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class QuizController {
    private Stage primaryStage;
    private DatabaseManager dbManager;
    private String currentUserId;
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;

    public QuizController(Stage primaryStage, DatabaseManager dbManager, String currentUserId) {
        this.primaryStage = primaryStage;
        this.dbManager = dbManager;
        this.currentUserId = currentUserId;
        this.questions = dbManager.getQuestions();
        this.currentQuestionIndex = 0;
        this.score = 0;
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz.fxml"));
            VBox root = loader.load();

            QuizViewController controller = loader.getController();
            controller.setQuizController(this);
            
            loadQuestion(controller);

            Scene scene = new Scene(root, 600, 400);
            primaryStage.setTitle("ইতিহাস কুইজ গেম");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadQuestion(QuizViewController viewController) {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            viewController.displayQuestion(question, currentQuestionIndex + 1, questions.size());
        } else {
            // Quiz completed
            endQuiz();
        }
    }

    public void answerQuestion(int selectedOption) {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            if (selectedOption == currentQuestion.getCorrectAnswer()) {
                score += 8; // 8 marks per question
            }
            currentQuestionIndex++;
        }
    }

    public void endQuiz() {
        // Save score to database
        dbManager.saveScore(currentUserId, score);
        
        // Show final score
        int bestScore = dbManager.getBestScore(currentUserId);
        String message = String.format("কুইজ সম্পন্ন!\n\nআপনার স্কোর: %d মার্ক\nসর্বোচ্চ স্কোর: %d মার্ক", 
                                       score, bestScore);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("কুইজ সম্পন্ন");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        
        // Return to login screen
        LoginController loginController = new LoginController(primaryStage, dbManager);
        loginController.show();
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }
    
    public void setCurrentUserId(String userId) {
        this.currentUserId = userId;
    }
}
