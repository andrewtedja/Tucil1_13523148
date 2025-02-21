package stima;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import stima.model.Board;
import stima.model.FileData;
import stima.model.Piece;
import stima.model.Solver;
import java.util.Timer;
import java.util.TimerTask;
import javafx.concurrent.Task;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Graphics2D;
import java.awt.Color as AWTColor;

public class SecondaryController {
    @FXML private Label fileNameLabel;
    @FXML private Label statusLabel;
    @FXML private Label infoLabel;
    @FXML private Button solveButton;
    @FXML private Button stopButton;
    @FXML private Button backButton;
    @FXML private Canvas boardCanvas;
    @FXML private Label statisticsLabel;

    @FXML private Label saveInputLabel;
    @FXML private TextField saveResponseField;
    @FXML private Button saveConfirmButton;

    private ArrayList<Piece> pieceList;
    private boolean visualizeMode = false;
    private boolean debugWriteToFile = false;
    private Solver solver;
    private Task<Void> solverTask;
    private Timer updateTimer;
    private final int UPDATE_INTERVAL_MS = 1; 
    private Board currentBoard;

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
        if (pieceId % 14 == 0) {
            return Color.BLUE;
        } else if (pieceId % 14 == 1) {
            return Color.GREEN;
        } else if (pieceId % 14 == 2) {
            return Color.VIOLET;
        } else if (pieceId % 14 == 3) {
            return Color.SALMON;
        } else if (pieceId % 14 == 4) {
            return Color.ORANGE;
        } else if (pieceId % 14 == 5) {
            return Color.PINK;
        } else if (pieceId % 14 == 6) {
            return Color.CYAN;
        } else if (pieceId % 14 == 7) {
            return Color.RED;
        } else if (pieceId % 14 == 8) {
            return Color.MAGENTA;
        } else if (pieceId % 14 == 9) {
            return Color.CHOCOLATE;
        } else if (pieceId % 14 == 10) {
            return Color.DARKSEAGREEN;
        } else if (pieceId % 14 == 11) {
            return Color.GOLD;
        } else if (pieceId % 14 == 12) {
            return Color.LIGHTGRAY;
        } else if (pieceId % 14 == 13) {
            return Color.LIGHTGREEN;
        } else {
            return Color.YELLOW;
        }
    }

    private AWTColor getAWTPieceColor(int pieceId) {
        switch (pieceId % 11) {
            case 0: return AWTColor.BLUE;
            case 1: return AWTColor.GREEN;
            case 2: return AWTColor.MAGENTA; 
            case 3: return new AWTColor(250, 128, 114); 
            case 4: return AWTColor.ORANGE;
            case 5: return AWTColor.PINK;
            case 6: return AWTColor.CYAN;
            case 7: return AWTColor.RED;
            case 8: return AWTColor.MAGENTA;
            case 9: return new AWTColor(210, 105, 30); 
            default: return AWTColor.YELLOW;
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
                    // System.out.println("asd!");
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

        solver = new Solver(visualizeMode, App.getDebugWriteToFile(), App.getFolderPath(), App.getFilePath());
        Board board = App.getBoard();
        currentBoard = board;

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
                        // System.out.println("Solution found!");
                        statusLabel.setText("Solution found!");
                        showSaveInput();
                    } else {
                        // System.out.println("Sasdasd!");
                        statusLabel.setText("No solution!");
                        hideSaveInput();
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

    private void showSaveInput() {
        saveInputLabel.setVisible(true);
        saveResponseField.setVisible(true);
        saveConfirmButton.setVisible(true);
        saveResponseField.clear(); 
    }

    private void hideSaveInput() {
        saveInputLabel.setVisible(false);
        saveResponseField.setVisible(false);
        saveConfirmButton.setVisible(false);
    }


    // ! SAVE SOLUTION AS IMAEG (BONUS)
    private void saveSolutionAsImage(Board board, String folderPath, String filePath) throws IOException {
        int rows = board.getRows();
        int cols = board.getCols();
        int cellsize = 100;
        int width = cols * cellsize;
        int height = rows * cellsize;

        BufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(AWTColor.WHITE);
        g2d.fillRect(0, 0, width, height);
    }

    @FXML
    private void handleSaveConfirm() {
        String response = saveResponseField.getText().trim();
        if (response.equalsIgnoreCase("ya")) {
            currentBoard.writeSolutionToOutput(App.getFolderPath(), App.getFilePath());
            statusLabel.setText("Solution ditemukan! (saved)");
        } else {
            statusLabel.setText("Solusi ditemukan! (not saved)");
        }
        hideSaveInput();
    }
    // Stop not load here

    @FXML
    private void handleStop() {
        if (solver != null) {
            solver.stop();
            if (solverTask != null && solverTask.isRunning()) {

                solverTask.cancel(); 
            }

            if (updateTimer != null) {
                updateTimer.cancel();
                updateTimer = null;
            }
            Platform.runLater(() -> {
                statusLabel.setText("Stopped!");
                drawBoard(App.getBoard());

                updateStatistics();
                hideSaveInput();

                solveButton.setDisable(false);
                stopButton.setDisable(true);
            });
        }
    }

    private void updateStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Banyak kasus yang ditinjau (Attempts): ").append(App.getAttempts()).append("\n");
        stats.append("Waktu pencarian (Runtime): ").append(App.getRuntime()).append(" ms");
        statisticsLabel.setText(stats.toString());
    }


    // ! DEBUG
    @FXML
    private void toggleDebug() {
        visualizeMode = !visualizeMode;
        debugWriteToFile = !debugWriteToFile;
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

