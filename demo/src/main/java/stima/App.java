package stima;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import stima.model.Board;
import stima.model.FileData;
import stima.model.ReadInput;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Board board;
    private static FileData fileData;
    private static String currentFileName = "";
    private static boolean solved = false;
    private static boolean debugWriteToFile = false;
    private static String folderPath = "test";
    private static String filePath = "output.txt";
    public static boolean writeSolutionToOutput = false;

    private static int attempts = 0;
    private static int runtime = 0;
    
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 1200, 600);
        scene.getStylesheets().add(getClass().getResource("/stima/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("IQPuzzler");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void loadFile(String filename) {
        currentFileName = filename;
        fileData = ReadInput.readFile(filename);
        board = new Board(fileData);
        solved = false;
    }
    public static Board getBoard() {
        return board;
    }

    public static FileData getFileData() {
        return fileData;
    }
    
    public static String getCurrentFileName() {
        return currentFileName;
    }

    public boolean getWriteSolutionToOutput(String folderPath, String filePath) {
        return writeSolutionToOutput;
    }

    public static String getFolderPath() {
        return folderPath;
    }

    public static boolean getDebugWriteToFile() {
        return debugWriteToFile;
    }

    public static String getFilePath() {
        return filePath;
    }
    
    public static boolean isSolved() {
        return solved;
    }

    public static void setSolved(boolean isSolved) {
        solved = isSolved();
    }

    public static void setStatistics(int attempts, int runtime) {
        App.attempts = attempts;
        App.runtime = runtime;
    }
    public static int getAttempts() {
        return attempts;
    }
    
    public static int getRuntime() {
        return runtime;
    }

    public static void main(String[] args) {
        launch();
    }

}