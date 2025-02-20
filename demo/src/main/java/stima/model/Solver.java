package stima.model;
import java.util.ArrayList;

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
        attempt++;

        if (pieceIndex == pieceList.size()) {
            return board.isFullyFilled();
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

