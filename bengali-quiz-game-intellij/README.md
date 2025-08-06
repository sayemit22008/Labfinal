# Bengali Quiz Game

A JavaFX-based quiz game with Bengali historical questions, user authentication, and score tracking.

## Features

- **Login System**: User registration and login functionality
- **Bengali Interface**: All text in Bengali language
- **Historical Questions**: Two historical questions about Bangladesh
- **Score Tracking**: Each question worth 8 marks, scores saved to database
- **Best Score Display**: Shows current and best scores

## Questions Included

1. বাংলাদেশের স্বাধীনতা যুদ্ধ কোন সালে শুরু হয়েছিল? (When did Bangladesh's independence war start?)
   - Answer: ১৯৭১ সাল (1971)

2. মহান ভাষা আন্দোলন কোন সালে ঘটেছিল? (When did the great language movement occur?)
   - Answer: ১৯৫২ সাল (1952)

## Technology Stack

- **Java 17**
- **JavaFX 20** - GUI framework
- **SQLite** - Database for user accounts and scores
- **Maven** - Build tool

## How to Run

### Prerequisites

1. Java 17 or higher
2. Maven
3. IntelliJ IDEA (recommended)

### Steps

1. Open the project in IntelliJ IDEA
2. Let IntelliJ import the Maven dependencies
3. Run the application using one of these methods:

   **Method 1: Using IntelliJ**
   - Right-click on `QuizApplication.java`
   - Select "Run 'QuizApplication'"

   **Method 2: Using Maven**
   ```bash
   mvn clean javafx:run
   ```

   **Method 3: Using Command Line**
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.quiz.QuizApplication"
   ```

## Project Structure

```
bengali-quiz-game/
├── src/main/java/com/quiz/
│   ├── QuizApplication.java       # Main application class
│   ├── DatabaseManager.java       # Database operations
│   ├── Question.java              # Question model
│   ├── LoginController.java       # Login screen controller
│   ├── LoginViewController.java   # Login FXML controller
│   ├── QuizController.java        # Quiz screen controller
│   └── QuizViewController.java    # Quiz FXML controller
├── src/main/resources/
│   ├── login.fxml                 # Login screen layout
│   └── quiz.fxml                  # Quiz screen layout
├── pom.xml                        # Maven configuration
└── README.md                      # This file
```

## Database Schema

The application creates three tables automatically:

1. **users** - User accounts
2. **questions** - Quiz questions
3. **scores** - User scores with timestamps

## Usage

1. **Registration**: First-time users can register with a user ID and password
2. **Login**: Enter your credentials to access the quiz
3. **Quiz**: Answer the two historical questions (8 marks each)
4. **Results**: View your score and best score after completion

## Development Notes

- The application uses SQLite database stored locally as `quiz.db`
- All Bengali text is properly encoded in UTF-8
- The interface is designed to be simple and user-friendly
- Scores are automatically saved after each quiz attempt

## Extending the Quiz

To add more questions:

1. Add them to the `insertDefaultQuestions()` method in `DatabaseManager.java`
2. Or insert them directly into the database

## Troubleshooting

If you encounter issues:

1. Ensure Java 17+ is installed
2. Check that JavaFX modules are properly loaded
3. Verify Maven dependencies are downloaded
4. Check console for any error messages

## License

This project is for educational purposes.
