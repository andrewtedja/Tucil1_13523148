package stima.model;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;

public class Solver {
    private int attempt = 0;
    private long startTime = 0;
    private long endTime = 0;
    private int runtime = 0;
    private volatile boolean stopped;
    
    private boolean showTesting = false;
    private boolean debugWriteToFile = false;
    private PrintWriter debugWriter;
    private String debugFile;

    public Solver() {
        this.showTesting = false;
    }
    
    public Solver(boolean showTesting, boolean debugWriteToFile, String folderPath, String filePath) {
        this.showTesting = showTesting;
        this.debugWriteToFile = debugWriteToFile;

        if (debugWriteToFile) {
            try {
                File folder = new File(folderPath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                
                this.debugFile = folderPath + File.separator + filePath;
                this.debugWriter = new PrintWriter(new FileWriter(debugFile));
            } catch (IOException e) {
                System.err.println("Error debug file: " + e.getMessage());
                this.debugWriteToFile = false;
            }
        }
    }

    public boolean solve(Board board, ArrayList<Piece> pieceList) {
        startTime = System.nanoTime();
        stopped = false;

        if (debugWriteToFile) {
            writeBoard(board);
        }

        boolean result = solveHelper(board, pieceList, 0);
        endTime = System.nanoTime();
        runtime = (int) (endTime - startTime) / 1000000;
        if (runtime < 0) {
            runtime = -runtime;
        }
        return result;
    }

    private boolean solveHelper(Board board, ArrayList<Piece> pieceList, int pieceIndex) {
        if (stopped) return false;

        if (pieceIndex > pieceList.size()) {
            return false;
        }
        if (pieceIndex == pieceList.size()) {
            return board.isFullyFilled();
        }
        
        Piece piece = pieceList.get(pieceIndex);
        
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                for (Piece orientation: piece.getAllOrientations()) {
                    // System.out.println("testinside");
                    if (board.canPlacePiece(orientation, i, j)) {
                        // System.out.println("testinsideinside");
                        if (showTesting) {
                            // System.out.println("testing");

                            board.printBoard();
                        }
                        
                        board.placePiece(orientation, i, j);
                        attempt++;

                        if (debugWriteToFile) {
                            writeBoard(board);
                        }
                        if (solveHelper(board, pieceList, pieceIndex + 1)){
                            return true;
                        }
                        // System.out.println("donee1");
                        // ! BACKTRACKING
                        board.removePiece(orientation, i, j);
                        if (showTesting) {
                            board.printBoard();
                        }


                        if (debugWriteToFile) {
                            writeBoard(board);
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public void stop() {
        stopped = true; 
    }
    
    // private void clearConsole() {
    //     System.out.print("\033[H\033[2J"); 
    //     System.out.flush();
    // }
    
    // ? Getter
    public int getAttempt() {
        return attempt;
    }

    public int getRuntime() {
        return runtime;
    }

    // ? Helper
    private void writeBoard(Board board) {
        for (int i = 0; i < board.getRows(); i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < board.getCols(); j++) {
                line.append(" ").append(board.getCell(i, j)).append(" ");
            }
            debugWriter.println(line.toString());
        }
    }
}


