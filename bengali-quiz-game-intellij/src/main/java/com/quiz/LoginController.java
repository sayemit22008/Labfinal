package com.quiz;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Stage primaryStage;
    private DatabaseManager dbManager;

    public LoginController(Stage primaryStage, DatabaseManager dbManager) {
        this.primaryStage = primaryStage;
        this.dbManager = dbManager;
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            VBox root = loader.load();

            LoginViewController controller = loader.getController();
            controller.setLoginController(this);

            Scene scene = new Scene(root, 300, 200);
            primaryStage.setTitle("কুইজ গেম - লগইন");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(String userId, String password) {
        if (dbManager.loginUser(userId, password)) {
            showQuiz(userId);
        } else {
            showAlert("ত্রুটি", "ভুল ইউজার আইডি বা পাসওয়ার্ড।");
        }
    }

    public void register(String userId, String password) {
        if (dbManager.registerUser(userId, password)) {
            showAlert("সাফল্য", "সফলভাবে নিবন্ধিত হয়েছেন। এখন লগইন করুন।");
        } else {
            showAlert("ত্রুটি", "এই ইউজার আইডি ইতিমধ্যে নিবন্ধিত।");
        }
    }

    private void showQuiz(String userId) {
        QuizController quizController = new QuizController(primaryStage, dbManager, userId);
        quizController.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
