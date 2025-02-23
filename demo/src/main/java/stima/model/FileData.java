package stima.model;
import java.util.ArrayList;


public class FileData {
    private int N, M, P;
    private String S;
    private char[][] matrix;
    private ArrayList<Piece> pieceList; 

    public FileData(int N, int M, int P, String S, char[][] matrix, ArrayList<Piece> pieceList) {
        this.N = N;
        this.M = M;
        this.P = P;
        this.S = S;
        this.matrix = matrix;
        this.pieceList = pieceList;
    }

    // Getters and Setters
    public int getN() {
        return N;
    }
    public int getM() {
        return M;
    }
    public int getP() {
        return P;
    }
    public String getS() {
        return S;
    }
    public char[][] getMatrix() {
        return matrix;
    }
    public ArrayList<Piece> getPieceList() {
        return pieceList;
    }
    public void setPieceList(ArrayList<Piece> pieceList) {
        this.pieceList = pieceList;
    }
}

