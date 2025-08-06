module com.quiz {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    exports com.quiz;
    
    opens com.quiz to javafx.fxml;
}
