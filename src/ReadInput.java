import java.io.*;
import java.util.*;

public class ReadInput {
    // ? HELPER FUNCTION
    private static char leadingNonSpace(char[] row) {
        for (char c : row) {
            if (c != ' ') return c;
        }
        return ' ';
    }

    // * Read file -> Matrix
    public static FileData readFile(String filename) {
        int N = 0;
        int M = 0;
        int P = 0;
        String S = "";
        ArrayList<char[]> matrixList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // ? FIRST LINE (row, col, num. pieces)
            String[] firstLine = reader.readLine().split("\\s+");
            if (firstLine.length >= 3) {
                N = Integer.parseInt(firstLine[0]);
                M = Integer.parseInt(firstLine[1]);
                P = Integer.parseInt(firstLine[2]);
            }
            // ? SECOND LINE (S/Type)
            S = reader.readLine().strip();

            // ? REST (Pieces Matrix)
            String line;
            while ((line = reader.readLine()) != null) {
                // remove trailing (after alphabet) spaces
                if (!line.trim().isEmpty()) {
                    matrixList.add(line.stripTrailing().toCharArray());
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR READING FILE: " + e.getMessage() + " !");
        }

        return new FileData(N, M, P, S, matrixList.toArray(new char[0][]));
    }


    // * Pieces Pre-process
    public static ArrayList<char[][]> createPieces(char[][] matrix) {
        // ? piecelist: din matrix, store all pieces of matrix (char[][])
        // ? tempPiece: temp din array, store rows with same alphabet
        // ? target: hold leading character
        // ? leadingChar: first non-space char

        ArrayList<char[][]> pieceList = new ArrayList<>();
        ArrayList<char[]> tempPiece = new ArrayList<>();

        if (matrix.length == 0) {
            return pieceList;
        }

        char currChar = 0;

        for (char[] row : matrix) {
            char leadingChar = leadingNonSpace(row);

            if (leadingChar == ' ') {
                continue;
            } 
            if (currChar != leadingChar && !tempPiece.isEmpty()) {
                pieceList.add(tempPiece.toArray(new char[0][]));
                tempPiece = new ArrayList<>();
            }

            currChar = leadingChar;
            tempPiece.add(row);
        }

        // Add the last collected group
        if (!tempPiece.isEmpty()) {
            pieceList.add(tempPiece.toArray(new char[0][]));
        }

        return pieceList;
    }

    // * MAIN
    // public static void main(String[] args) {
    //     String fileName = "src/file.txt";
    //     FileData fileData = readFile(fileName);

    //     System.out.println("N (row) = " + fileData.getN());
    //     System.out.println("M (col) = " + fileData.getM());
    //     System.out.println("P (num. pieces) = " + fileData.getP());
    //     System.out.println("S (Type) = " + fileData.getS());

    //     ArrayList<char[][]> pieceList = createPieces(fileData.getMatrix());

    //     System.out.println("Pieces from file: ");
    //     for (char[][] piece : pieceList) {
    //         System.out.println("Piece " + (pieceList.indexOf(piece) + 1) + ":");
    //         for (char[] row : piece) {
    //             System.out.println(new String(row));
    //         }
    //     }
    // }
}


