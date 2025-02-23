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
        gc.setFont(new javafx.scene.text.Font(cellSize / 5)); 
        gc.setTextAlign(javafx.scene.text.TextAlignment.CENTER);
        gc.setTextBaseline(javafx.geometry.VPos.CENTER);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char cell = board.getCell(i, j);

                if (cell != '.') {
                    // System.out.println("asd!");
                    int pieceId = cell - '0';
                    gc.setFill(getPieceColor(pieceId));
                    gc.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    
                    gc.setFill(Color.BLACK); 
                    gc.fillText(String.valueOf(cell), (j + 0.5) * cellSize, (i + 0.5) * cellSize);
                }
                gc.setStroke(Color.BLACK);
                gc.strokeRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    private Color getPieceColor(int pieceId) {
        switch (pieceId % 26) {
            case 0: return Color.BLUE;
            case 1: return Color.GREEN;
            case 2: return Color.MAGENTA; 
            case 3: return Color.ORANGE;
            case 4: return Color.PINK;
            case 5: return Color.CYAN;
            case 6: return Color.RED;
            case 7: return Color.MAGENTA;
            case 8: return Color.LIGHTGRAY;
            case 9: return Color.WHITE;
            case 10: return Color.DARKGRAY;
            case 11: return Color.YELLOW;
            case 12: return Color.BROWN;
            case 13: return Color.PURPLE;
            case 14: return Color.TEAL;
            case 15: return Color.INDIGO;
            case 16: return Color.VIOLET;
            case 17: return Color.GOLD;
            case 18: return Color.SILVER;
            case 19: return Color.BEIGE;
            case 20: return Color.IVORY;
            case 21: return Color.KHAKI;
            case 22: return Color.LAVENDER;
            case 23: return Color.MAROON;
            case 24: return Color.NAVY;
            case 25: return Color.OLIVE;
            default: return Color.BLACK;
        }
    }

    private java.awt.Color getAWTPieceColor(int pieceId) { 
        switch (pieceId % 26) {
            case 0: return java.awt.Color.BLUE;
            case 1: return java.awt.Color.GREEN;
            case 2: return java.awt.Color.MAGENTA; 
            case 3: return java.awt.Color.ORANGE;
            case 4: return java.awt.Color.PINK;
            case 5: return java.awt.Color.CYAN;
            case 6: return java.awt.Color.RED;
            case 7: return java.awt.Color.MAGENTA;
            case 8: return java.awt.Color.LIGHT_GRAY;
            case 9: return java.awt.Color.WHITE;
            case 10: return java.awt.Color.DARK_GRAY;
            case 11: return java.awt.Color.YELLOW;
            case 12: return new java.awt.Color(165, 42, 42);
            case 13: return new java.awt.Color(128, 0, 128); 
            case 14: return new java.awt.Color(0, 128, 128); 
            case 15: return new java.awt.Color(75, 0, 130); 
            case 16: return new java.awt.Color(238, 130, 238); 
            case 17: return new java.awt.Color(255, 215, 0); 
            case 18: return new java.awt.Color(192, 192, 192); 
            case 19: return new java.awt.Color(245, 245, 220); 
            case 20: return new java.awt.Color(255, 255, 240); 
            case 21: return new java.awt.Color(240, 230, 140); 
            case 22: return new java.awt.Color(230, 190, 255); 
            case 23: return new java.awt.Color(128, 0, 0);
            case 24: return new java.awt.Color(0, 0, 128); 
            case 25: return new java.awt.Color(128, 128, 0); 
            default: return java.awt.Color.BLACK;
        }
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
    private void saveSolutionAsImage(Board board, String folderPath, String filePath)  {
        int rows = board.getRows();
        int cols = board.getCols();
        int cellsize = 100;
        int width = cols * cellsize;
        int height = rows * cellsize;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(java.awt.Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, cellsize / 5)); 
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char cell = board.getCell(i, j);
                if (cell != '.') {
                    int pieceId = cell - '0';
                    g2d.setColor(getAWTPieceColor(pieceId));
                    g2d.fillRect(j * cellsize, i * cellsize, cellsize, cellsize);

                    g2d.setColor(java.awt.Color.BLACK);
                    g2d.drawString(String.valueOf(cell), (int)((j + 0.5) * cellsize), (int)((i + 0.5) * cellsize));
                }
                g2d.setColor(java.awt.Color.BLACK);
                g2d.drawRect(j * cellsize, i * cellsize, cellsize, cellsize);
            }
        }
        g2d.dispose();
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File outputFile = new File(folderPath + File.separator + filePath);
        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            System.err.println(e.getMessage());
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
                        statusLabel.setText("Solution found!");
                        showSaveInput();
                    } else {
                        statusLabel.setText("No solution!");
                        hideSaveInput();
                        solveButton.isDisable();
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
    private void handleSaveConfirm() {
        String response = saveResponseField.getText().trim();
        if (response.equalsIgnoreCase("ya")) {
            currentBoard.writeSolutionToOutput(App.getFolderPath(), App.getFilePath());
            saveSolutionAsImage(currentBoard, App.getFolderPath(), "solution.png");
            statusLabel.setText("Solution ditemukan! (saved in test/output.txt and test/solution.png)");
        } else {
            statusLabel.setText("Solusi ditemukan! (not saved)");
        }
        hideSaveInput();
    }
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



