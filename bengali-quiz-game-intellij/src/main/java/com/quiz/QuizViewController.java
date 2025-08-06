package com.quiz;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class QuizViewController {
    @FXML
    private Label questionLabel;
    
    @FXML
    private RadioButton option1Radio;
    
    @FXML
    private RadioButton option2Radio;
    
    @FXML
    private RadioButton option3Radio;
    
    @FXML
    private RadioButton option4Radio;
    
    @FXML
    private ToggleGroup optionGroup;
    
    @FXML
    private Button nextButton;
    
    @FXML
    private Label questionNumberLabel;
    
    private QuizController quizController;
    private Question currentQuestion;
    
    @FXML
    private void initialize() {
        optionGroup = new ToggleGroup();
        option1Radio.setToggleGroup(optionGroup);
        option2Radio.setToggleGroup(optionGroup);
        option3Radio.setToggleGroup(optionGroup);
        option4Radio.setToggleGroup(optionGroup);
    }
    
    public void setQuizController(QuizController quizController) {
        this.quizController = quizController;
    }
    
    public void displayQuestion(Question question, int questionNumber, int totalQuestions) {
        this.currentQuestion = question;
        
        questionNumberLabel.setText(String.format("প্রশ্ন %d/%d", questionNumber, totalQuestions));
        questionLabel.setText(question.getQuestionText());
        
        option1Radio.setText(question.getOption1());
        option2Radio.setText(question.getOption2());
        option3Radio.setText(question.getOption3());
        option4Radio.setText(question.getOption4());
        
        // Clear previous selection
        optionGroup.selectToggle(null);
        
        // Update button text for last question
        if (questionNumber == totalQuestions) {
            nextButton.setText("সম্পন্ন");
        } else {
            nextButton.setText("পরবর্তী");
        }
    }
    
    @FXML
    private void handleNext() {
        RadioButton selectedRadio = (RadioButton) optionGroup.getSelectedToggle();
        
        if (selectedRadio == null) {
            showAlert("ত্রুটি", "একটি উত্তর নির্বাচন করুন।");
            return;
        }
        
        int selectedOption = 0;
        if (selectedRadio == option1Radio) {
            selectedOption = 1;
        } else if (selectedRadio == option2Radio) {
            selectedOption = 2;
        } else if (selectedRadio == option3Radio) {
            selectedOption = 3;
        } else if (selectedRadio == option4Radio) {
            selectedOption = 4;
        }
        
        // Submit answer
        quizController.answerQuestion(selectedOption);
        
        // Load next question or end quiz
        quizController.loadQuestion(this);
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
