package stima.model;
/*
todo 
    *  READ INPUT
    ?   - read input (open from bin/txt file)
    ?   - extract N (rows), M (cols), P (num. of pieces)
    ?   - extract type (DEFAULT -> (implement first)
    ?   - read P pieces, store as Piece object
    ?   - store pieces in ArrayList<Piece> (dynamic array)
    ! created: file reader, piece (shape), pieceList
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ReadInput {
    // ? HELPER FUNCTION
    private static char leadingNonSpace(char[] row) {
        for (char c : row) {
            if (c != ' ') return c;
        }
        return ' ';
    }

    private static void normalizeAndAddPiece(ArrayList<char[]> pieceRows, ArrayList<Piece> pieceList) {
        int maxW = 0;
        for (char[] row : pieceRows) {
            maxW = Math.max(maxW, row.length);
        }
        // maxW -> num. cols
        // [{ a, a}]
        // [{ a,  }]
        
        char[][] normalizedPiece = new char[pieceRows.size()][maxW];

        
        char pieceChar = '.';
        for (char[] row : pieceRows) {
            for (char c : row) {
                if (c != ' ' && c != '.') {
                    pieceChar = c;
                    break;
                }
            }
            if (pieceChar != '.') break;
        }
        
        for (int i = 0; i < pieceRows.size(); i++) {
            char[] originalRow = pieceRows.get(i);
            for (int j = 0; j < maxW; j++) {
                // System.out.println("j: " + j);
                if (j < originalRow.length) {
                    if (originalRow[j] == ' ') {
                        normalizedPiece[i][j] = '.';
                    } else {
                        normalizedPiece[i][j] = originalRow[j];
                    }
                } else {
                    normalizedPiece[i][j] = '.';
                }
            }
        }
        
        pieceList.add(new Piece(normalizedPiece, pieceChar));
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
            } else {
                throw new IllegalArgumentException("File format incorrect!");
            }
            
            // * VALIDATION
            if (N <= 0 || M <= 0) {
                throw new IllegalArgumentException("N/M must be positive!");
            }

            if (P <= 0 || P > 26) {
                throw new IllegalArgumentException("P must be positive and less than 26!");
            }

            // ? SECOND LINE (S/Type)
            S = reader.readLine().strip();
            if (S.isEmpty()) {
                throw new IllegalArgumentException("Type cannot be empty!");
            } else if (!S.equals("DEFAULT") && !S.equals("CUSTOM") && !S.equals("PIECES")) {
                throw new IllegalArgumentException("Type hanya DEFAULT/CUSTOM/PYRAMID!");
            }

            // ? REST (Pieces Matrix)
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    matrixList.add(line.stripTrailing().toCharArray());
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR READING FILE: " + e.getMessage() + " !");
        }

        ArrayList<Piece> pieceList = createPieces(matrixList.toArray(new char[0][]));
        
        if (pieceList.size() != P) {
            throw new IllegalArgumentException("Number of pieces does not match P!");
        }
        
        // * SAME ALPHABET PIECES VALIDATION
        Set<Character> uniqueAlphabet = new HashSet<>();
        for (Piece piece : pieceList) {
            if (!Character.isUpperCase(piece.getId())) {
                throw new IllegalArgumentException("Pieces must be in uppercase!");
            }
            if (uniqueAlphabet.contains(piece.getId())) {
                throw new IllegalArgumentException("Pieces must have unique alphabets! (Duplicate Piece)");
            }
            uniqueAlphabet.add(piece.getId());
        }

        return new FileData(N, M, P, S, matrixList.toArray(new char[0][]), pieceList);
    }

    // * Pieces Pre-process
    public static ArrayList<Piece> createPieces(char[][] matrix) {
        // ? piecelist: din matrix, store all pieces of matrix (char[][])
        // ? tempPiece: temp din array, store rows with same alphabet
        // ? target: hold leading character
        // ? leadingChar: first non-space char

        ArrayList<Piece> pieceList = new ArrayList<>();
        ArrayList<char[]> tempPiece = new ArrayList<>();

        if (matrix.length == 0) {
            return pieceList;
        }

        char currChar = 0; 

        for (char[] row : matrix) {
            char leadingChar = leadingNonSpace(row);

            if (leadingChar == ' ') {
                // System.out.println(leadingChar);
                continue;
            } 
            if (leadingChar != currChar && !tempPiece.isEmpty()) {
                normalizeAndAddPiece(tempPiece, pieceList);
                tempPiece = new ArrayList<>();
            }
        
            currChar = leadingChar;
            tempPiece.add(row);
        }

        if (!tempPiece.isEmpty()) {
            normalizeAndAddPiece(tempPiece, pieceList);
        }

        return pieceList;
    }

    // * MAIN TEST
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







