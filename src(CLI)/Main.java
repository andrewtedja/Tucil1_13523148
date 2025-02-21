// import java.util.Scanner;
/*
    todo 
    *  READ INPUT
    ?   - read input (open from bin/txt file)
    ?   - extract N (rows), M (cols), P (num. of pieces)
    ?   - extract type (DEFAULT -> (implement first)
    ?   - read P pieces, 
    ?   - create piece -> char[][] (static)
    ?   - store pieces in ArrayList<Piece> (dynamic array)
    ! created: puzzle reader, piece (shape and ID), List<piece> 

    * BOARD
    ?   - create board N x M (char [][], static)
    ?   - printBoard()
    ?   - canPlacePiece(Piece, row, col) -> check if a piece fits
    ?   - placePiece(Piece, row, col) -> place a piece
    ?   - removePiece(Piece, row, col) -> remove a piece
    ! created: Board, char[][] grid (Board state)
    * PIECE
    ?   - implement rotate() -> (0, 90, 180, 270) 
    ?   - implement flip() -> (horizontal, vertical)
    ?   - getAllOrientations() -> store unique orientations 
    ! use hashset to find unique orientations (flipped/rotated), then store in an arraylist

    * SOLVER
    ?   - solve(Board board, List<Piece> pieces) -> use recursion to place pieces
    ?   - Try piece at every position with all orientation
    ?   - Backtrack if doesnt fit, remove, and try another
    ?   - Stop when board filled, print solution board (just 1), otherwise "No Solution"
    ! created: Solver

    * MAIN
    ?   - read input
    ?   - create board
    ?   - create pieces and pass to solver
    ?   - solve
    ?   - print board state before and after solving
*/

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String fileName = "test/file.txt";
        FileData fileData = ReadInput.readFile(fileName);

        Board board = new Board(fileData);

        ArrayList<Piece> pieceList = fileData.getPieceList();
        
        System.out.println("\nPuzzle Information:");
        System.out.println("N (row) = " + fileData.getN());
        System.out.println("M (col) = " + fileData.getM());
        System.out.println("P (num. pieces) = " + fileData.getP());
        System.out.println("S (Type) = " + fileData.getS());
        System.out.println("Number of pieces loaded: " + pieceList.size());
        System.out.println();
        
        boolean visualize = false;
        Solver solver = new Solver(visualize);
        
        // Start solving process
        boolean solved = solver.solve(board, pieceList);

        if (solved) {
            System.out.println("\nSolution found!");
            System.out.println("Final Solved Board:");
            board.printBoard();
        } else {
            System.out.println("\nNo solution exists for this puzzle.");
            System.out.println("Final Board State (incomplete):");
            board.printBoard();
        }

        System.out.println("\nSolver Statistics:");
        System.out.println("Number of attempts: " + solver.getAttempt());
        System.out.println("Runtime: " + solver.getRuntime() + " ms");
        

        // // ! TESTING : PRINT ALL PIECES ORIENTATIONS
        // System.out.println("Pieces from file: ");
        
        // for (Piece piece : pieceList) {
        //     System.out.println("Piece " + (pieceList.indexOf(piece) + 1) + ":");
        //     for (char[] row : piece.getShape()) {
        //         System.out.println(new String(row));
        //     }
        //     System.out.println("All Orientations:");
        //     for (Piece orientation : piece.getAllOrientations()) {
        //         System.out.println("Orientation:");
        //         for (char[] row : orientation.getShape()) {
        //             System.out.println(new String(row));
        //         }
        //         System.out.println(); // Separate orientations with a newline for clarity
        //     }
        // }
    }
}

// Piece rotatedPiece = piece.rotate();
// System.out.println("Rotated Piece:");
// for (char[] row : rotatedPiece.getShape()) {
//     System.out.println(new String(row));
// }
// Piece flipHorizontalPiece = piece.flipHorizontal();
// System.out.println("Flip Horizontal:");
// for (char[] row : flipHorizontalPiece.getShape()) {
//     System.out.println(new String(row));
// }
// Piece flipVerticalPiece = piece.flipVertical();
// System.out.println("Flip vertical:");
// for (char[] row : flipVerticalPiece.getShape()) {
//     System.out.println(new String(row));
// }





