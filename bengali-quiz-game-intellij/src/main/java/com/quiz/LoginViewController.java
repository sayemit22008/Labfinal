package com.quiz;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginViewController {
    @FXML
    private TextField userIdField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Button registerButton;
    
    private LoginController loginController;
    
    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
    
    @FXML
    private void handleLogin() {
        String userId = userIdField.getText();
        String password = passwordField.getText();
        
        if (userId.isEmpty() || password.isEmpty()) {
            showAlert("ত্রুটি", "ইউজার আইডি এবং পাসওয়ার্ড পূরণ করুন।");
            return;
        }
        
        loginController.login(userId, password);
    }
    
    @FXML
    private void handleRegister() {
        String userId = userIdField.getText();
        String password = passwordField.getText();
        
        if (userId.isEmpty() || password.isEmpty()) {
            showAlert("ত্রুটি", "ইউজার আইডি এবং পাসওয়ার্ড পূরণ করুন।");
            return;
        }
        
        loginController.register(userId, password);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
