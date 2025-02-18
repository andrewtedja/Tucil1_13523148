/* 
todo
    * BOARD
    ?   - create board N x M (char [][], static)
    ?   - printBoard()
    ?   - canPlacePiece(Piece, row, col) -> check if a piece fits
    ?   - placePiece(Piece, row, col) -> place a piece
    ?   - removePiece(Piece, row, col) -> remove a piece
    ! created: Board, char[][] grid (Board state)
*/

public class Board {
    private char[][] grid;
    private int rows;
    private int cols;

    public Board(FileData fileData) {
        this.rows = fileData.getN();
        this.cols = fileData.getM();
        this.grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = '.';
            }
        }
    }
    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean canPlacePiece(Piece piece, int startRow) {
        for (int i = 0; i < piece.getShape().length; i++) {
            for (int j = 0; j < piece.getShape()[0].length; j++) {
                if (piece.getShape()[i][j] != '.' && grid[startRow + i][j] != '.') {
                    return false;
                }
            }
        }
        return true;
    }


    // todo -> Add Pieces
    // todo -> Remove Pieces
}


