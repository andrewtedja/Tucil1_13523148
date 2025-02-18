public class FileData {
    private int N, M, P;
    private String S;
    private char[][] matrix;

    // ? Constructor
    public FileData(int N, int M, int P, String S, char [][] matrix) {
        this.N = N;
        this.M = M;
        this.P = P;
        this.S = S;
        this.matrix = matrix;
    }

    // ? Getters and Setters
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
}