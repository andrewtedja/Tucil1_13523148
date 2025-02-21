package stima;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import stima.model.Board;
import stima.model.FileData;
import stima.model.Piece;
import stima.model.Solver;
import java.util.Timer;
import java.util.TimerTask;
import javafx.concurrent.Task;

public class SecondaryController {
    @FXML private Label fileNameLabel;
    @FXML private Label statusLabel;
    @FXML private Label infoLabel;
    @FXML private Button solveButton;
    @FXML private Button stopButton;
    @FXML private Button backButton;
    @FXML private Canvas boardCanvas;
    @FXML private Label statisticsLabel;
    private ArrayList<Piece> pieceList;
    private boolean visualizeMode = false;
    private Solver solver;
    private Task<Void> solverTask;
    private Timer updateTimer;
    private final int UPDATE_INTERVAL_MS = 1; 

    @FXML
    private void initialize() {
        if (App.getCurrentFileName().isEmpty()) {
            statusLabel.setText("No file loaded!!!");
            solveButton.setDisable(true);
            return;
        }

        fileNameLabel.setText(App.getCurrentFileName());
        FileData fileData = App.getFileData();
        pieceList = fileData.getPieceList();

        // Display pieces
        StringBuilder info = new StringBuilder();
        info.append("Board Rows (N): ").append(fileData.getN()).append("\n");
        info.append("Board Columns (M): ").append(fileData.getM()).append("\n");
        info.append("Puzzle Type (S): ").append(fileData.getS()).append("\n");
        info.append("Number of Pieces (P): ").append(pieceList.size());
        infoLabel.setText(info.toString());

        drawEmptyBoard();
        statusLabel.setText("Bruteforce Algorithm Solver");
        statisticsLabel.setText("");
        stopButton.setDisable(true);
    }

    private void drawEmptyBoard() {
        Board board = App.getBoard();
        if (board == null) {
            return;
        }

        int rows = board.getRows();
        int cols = board.getCols();

        double cellSize = Math.min(
            boardCanvas.getWidth() / cols,
            boardCanvas.getHeight() / rows
        );

        GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());

        gc.setStroke(Color.BLACK); 
        for (int i = 0; i <= rows; i++) {
            gc.strokeLine(0, i * cellSize, cols * cellSize, i * cellSize);
        }
        for (int j = 0; j <= cols; j++) {
            gc.strokeLine(j * cellSize, 0, j * cellSize, rows * cellSize);
        }
    }


    private Color getPieceColor(int pieceId) {
        switch (pieceId % 11) {
            case 0: return Color.BLUE;
            case 1: return Color.GREEN;
            case 2: return Color.VIOLET;
            case 3: return Color.SALMON;
            case 4: return Color.ORANGE;
            case 5: return Color.PINK;
            case 6: return Color.CYAN;
            case 7: return Color.RED;
            case 8: return Color.MAGENTA;
            case 9: return Color.CHOCOLATE;
            default: return Color.YELLOW;
        }
    }

    private void drawBoard(Board board) {
        if (board == null) return;
        
        int rows = board.getRows();
        int cols = board.getCols();
        
        double cellSize = Math.min(
            boardCanvas.getWidth() / cols,
            boardCanvas.getHeight() / rows
        );
        
        GraphicsContext gc = boardCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, boardCanvas.getWidth(), boardCanvas.getHeight());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char cell = board.getCell(i, j);

                if (cell != '.') {
                    int pieceId = cell - '0';
                    gc.setFill(getPieceColor(pieceId));
                    gc.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
                gc.setStroke(Color.BLACK);
                gc.strokeRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    @FXML
    private void handleSolve() {
        solveButton.setDisable(true);
        stopButton.setDisable(false);
        backButton.setDisable(true);
        statusLabel.setText("Solving...");

        solver = new Solver(visualizeMode);
        Board board = App.getBoard();

        // * TIMER SOLVE
        if (updateTimer != null) {
            updateTimer.cancel();
        }
        updateTimer = new Timer();
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    drawBoard(board);
                    statusLabel.setText("Solving... Attempts: " + solver.getAttempt());
                });
            }
        }, 0, UPDATE_INTERVAL_MS);

        solverTask = new Task<>() {
            @Override
            protected Void call() {
                boolean result = solver.solve(board, pieceList);
                if (updateTimer != null) {
                    updateTimer.cancel();
                    updateTimer = null;
                }

                App.setSolved(result);
                App.setStatistics(solver.getAttempt(), solver.getRuntime());

                Platform.runLater(() -> {
                    if (result) {
                        statusLabel.setText("Solution found!");
                        solveButton.setDisable(false);
                    } else {
                        statusLabel.setText("No solution!");
                    }
                    
                    drawBoard(board);
                    updateStatistics();
                    
                    solveButton.setDisable(false);
                    stopButton.setDisable(true);
                    backButton.setDisable(false);
                });
                return null;
            }
        };
        new Thread(solverTask).start();
    }

    @FXML
    private void handleStop() {
        if (solver != null) {
            solver.stop();
            if (solverTask != null && solverTask.isRunning()) {
                solverTask.cancel(); // stop task
            }

            if (updateTimer != null) {
                updateTimer.cancel();
                updateTimer = null;
            }
            Platform.runLater(() -> {
                statusLabel.setText("Stopped!");
                drawBoard(App.getBoard());
                updateStatistics();
                solveButton.setDisable(false);
                stopButton.setDisable(true);
                backButton.setDisable(false);
            });
        }
    }

    private void updateStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Banyak kasus yang ditinjau (Attempts): ").append(App.getAttempts()).append("\n");
        stats.append("Waktu pencarian (Runtime): ").append(App.getRuntime()).append(" ms");
        statisticsLabel.setText(stats.toString());
    }

    // ! VISUALIZATION DEBUG
    @FXML
    private void toggleVisualization() {
        visualizeMode = !visualizeMode;
    }

    @FXML
    private void switchToPrimary() throws IOException {
        if (updateTimer != null) {
            updateTimer.cancel();
            updateTimer = null;
        }
        App.setRoot("primary");
    }
}

