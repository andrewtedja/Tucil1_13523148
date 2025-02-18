// import java.util.Scanner;
/*
    todo 
    *  READ INPUT
    ?   - read input (open from bin/txt file)
    ?   - extract N (rows), M (cols), P (num. of pieces)
    ?   - extract type (DEFAULT -> (implement first)
    ?   - read P pieces, store as Piece object
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
    ?   - create piece -> char[][] (static)
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



public class Main {
    public static void main(String[] args) {
        
    }
}

