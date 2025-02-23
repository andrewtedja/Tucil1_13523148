package stima.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
    // ? Getters
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public char getCell(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return grid[row][col];
        }
        return ' ';
    }

    // ? MAIN FUNCTIONS
    public void printBoard() {
        for (int i = 0; i<rows; i++) {
            System.out.println();
            for (int j = 0; j<cols; j++) {
                String cellStr = " " + grid[i][j] + " ";
                
                if (grid[i][j] == '.') {
                    System.out.print(cellStr);
                } else {
                    int pieceNum = grid[i][j] - '0';
                    String colorCode = getColorForPiece(pieceNum);
                    System.out.print(colorCode + cellStr + "\033[0m");
                }
            }
        }
        System.out.println();
    }
    
    private String getColorForPiece(int pieceId) {
        if (pieceId % 7 == 0) return "\033[31m"; // Red
        if (pieceId % 7 == 1) return "\033[32m"; // Green
        if (pieceId % 7 == 2) return "\033[34m"; // Blue
        if (pieceId % 7 == 3) return "\033[35m"; // Magenta
        if (pieceId % 7 == 4) return "\033[36m"; // Cyan
        if (pieceId % 7 == 5) return "\033[33m"; // Yellow
        if (pieceId % 7 == 6) return "\033[37m"; // White
        return "\033[0m"; 
    }

    public boolean canPlacePiece(Piece piece, int startRow, int startCol) {
        char[][] shape = piece.getShape();
        int pieceRows = shape.length;
        int pieceCols = shape[0].length;

        // check if piece fits
        if (startRow < 0 || startCol < 0 || startRow + pieceRows > rows || startCol + pieceCols > cols) {
            return false;
        }
        // check if piece overlap (already .)
        for (int i = 0; i < pieceRows; i++) {
            for (int j = 0; j < pieceCols; j++) {
                if (shape[i][j] != '.' && grid[startRow + i][startCol + j] != '.') {
                    return false;
                }
            }
        }

        return true;
    }           

    public boolean isFullyFilled() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '.' || grid[i][j] == ' ') {
                    return false; 
                }
            }
        }
        return true;
    }

    public boolean placePiece(Piece piece, int startRow, int startCol) {
        return placePiece(piece, startRow, startCol, piece.getId());
    }
    
    public boolean placePiece(Piece piece, int startRow, int startCol, char pieceChar) {
        if (!canPlacePiece(piece, startRow, startCol)) {
            return false;
        }
        char[][] shape = piece.getShape();
        for (int i = 0; i< shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != '.') {
                    grid[startRow + i][startCol + j] = pieceChar;
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

    public void writeSolutionToOutput(String folderPath, String filePath) {
        File folder = new File(folderPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fullPath = folderPath + File.separator + filePath;
        try (PrintWriter writer = new PrintWriter(new FileWriter(fullPath))) {
            for (int i = 0 ; i < rows; i++) {
                StringBuilder line = new StringBuilder();
                for (int j = 0;  j < cols; j++) {
                    line.append(grid[i][j]);
                }
                writer.println(line.toString());
            }
        } catch (IOException e) {
        System.err.println("Error writing: " + e.getMessage());
        }
    }
}

