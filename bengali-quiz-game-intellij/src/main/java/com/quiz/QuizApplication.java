package com.quiz;

import javafx.application.Application;
import javafx.stage.Stage;

public class QuizApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize database
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.initializeDatabase();
        
        // Create and show login controller
        LoginController loginController = new LoginController(primaryStage, dbManager);
        loginController.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
