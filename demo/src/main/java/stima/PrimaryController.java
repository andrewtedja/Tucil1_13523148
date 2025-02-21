package stima;

import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PrimaryController {
    @FXML private Label titleLabel;
    @FXML private Label subtitleLabel;
    @FXML private Button loadFileButton;
    @FXML private Button exitButton;
    @FXML private VBox mainContainer;
    
    @FXML
    public void initialize() {
        titleLabel.setText("IQ Puzzler Pro Solver");
        subtitleLabel.setText("Tucil 1 - IF2211 Strategi Algoritma");
    }

    @FXML
    private void handleLoadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Puzzle File");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        
        File selectedFile = fileChooser.showOpenDialog(mainContainer.getScene().getWindow());
        if (selectedFile != null) {
            try {
                App.loadFile(selectedFile.getAbsolutePath());
                switchToSecondary();
            } catch (Exception e) {
                System.err.println("Error!!!: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}