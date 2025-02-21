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

    private boolean showTesting = false;



    public Solver() {
        this.showTesting = false;
    }
    
    public Solver(boolean showTesting) {
        this.showTesting = showTesting;

    }

    public boolean solve(Board board, ArrayList<Piece> pieceList) {
        startTime = System.nanoTime();
        boolean result = solveHelper(board, pieceList, 0);
        endTime = System.nanoTime();

        runtime = (int) (endTime - startTime) / 1000000;
        return result;
    }

    private boolean solveHelper(Board board, ArrayList<Piece> pieceList, int pieceIndex) {

        if (pieceIndex == pieceList.size()) {
            if (board.isFullyFilled()) {
                return true;
            }
            return false;
        }

        Piece piece = pieceList.get(pieceIndex);

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                for (Piece orientation: piece.getAllOrientations()) {
                    if (board.canPlacePiece(orientation, i, j)) {
                        if (showTesting) {
                            clearConsole();
                            board.printBoard();
                        }
                        
                        board.placePiece(orientation, i, j);
                        attempt++;
                        if (solveHelper(board, pieceList, pieceIndex + 1)){
                            return true;
                        }
                        
                        // ! BACKTRACKING
                        if (showTesting) {
                            clearConsole();
                            board.printBoard();
                        }
                        board.removePiece(orientation, i, j);
                    }
                }
            }
        }
        return false;
    }
    
    private void clearConsole() {
        System.out.print("\033[H\033[2J"); 
        System.out.flush();
    }
    
    // ? Getter
    public int getAttempt() {
        return attempt;
    }

    public int getRuntime() {
        return runtime;
    }

}

