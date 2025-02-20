package stima;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.*;

import stima.model.Main;
import stima.model.Board;
import stima.model.FileData;
import stima.model.Solver;
import stima.model.Piece;
import stima.model.ReadInput;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Board board;
    private static boolean solved = false;
    
    @Override
    public void start(Stage stage) throws IOException {

        String filename = "test/file2.txt";
        FileData fileData = ReadInput.readFile(filename);
        board = new Board(fileData);
        ArrayList<Piece> pieceList = new ArrayList<>();
        Solver solver = new Solver(false);
        solved = solver.solve(board, pieceList);


        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("IQPuzzler: filename");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Board getBoard() {
        return board;
    }

    public static boolean isSolved() {
        return solved;
    }

    public static void main(String[] args) {
        launch();
    }

}