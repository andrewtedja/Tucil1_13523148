module stima {
    requires javafx.controls;
    requires javafx.fxml;

    opens stima to javafx.fxml;
    exports stima;
    exports stima.model;
}
