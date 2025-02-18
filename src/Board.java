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

    // ? Constructor
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


    // ? Getters and setters
    public int getRows() { return rows; }
    public int getCols() { return cols;}

    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean canPlacePiece(Piece piece, int startRow, int startCol) {
        char[][] shape = piece.getShape();
        int pieceRows = shape.length;
        int pieceCols = shape[0].length;

        // Check if piece fits
        if (startRow < 0 || startCol < 0|| startRow + pieceRows > rows || startCol + pieceCols > cols) {
            return false;
        }

        // Check if piece overlaps or not
        for (int i = 0; i < pieceRows; i++) {
            for (int j = 0; j < pieceCols; j++) {
                if (shape[i][j] != '.' && grid[startRow + i][startCol + j] != '.') {
                    return false;
                }
            }
        }

        return true;
    }           

    public boolean placePiece(Piece piece, int startRow, int startCol) {
        if (!canPlacePiece(piece, startRow, startCol)) {
            return false;
        }
        char[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != '.') {
                    grid[startRow + i][startCol + j] = shape[i][j];
                }
            }
        }
        return true;
    }   

    public void removePiece(Piece piece, int startRow, int startCol) {
        char[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != '.') {
                    grid[startRow + i][startCol + j] = '.';
                }
            }
        }
    }
}


