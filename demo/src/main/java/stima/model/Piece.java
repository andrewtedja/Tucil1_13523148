package stima.model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Piece {
    private char[][] shape;
    private int rows;
    private int cols;
    private char id;
    private List<Piece> allOrientations;

    public Piece(char[][] shape, char id) {
        this.shape = shape;
        this.rows = shape.length;
        this.cols = shape[0].length;
        this.id = id;
        this.allOrientations = null;
    }

    // Rotate 90
    public Piece rotate() {
        if (shape == null || shape.length == 0 || shape[0].length == 0) {
            return this;
        }
        char[][] rotated = new char[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        return new Piece(rotated, id);
    }

    public Piece flipHorizontal() {
        char[][] flipped = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flipped[i][cols - 1 - j] = shape[i][j];
            }
        }
        return new Piece(flipped, id);
    }

    public List<Piece> getAllOrientationsList() {
        Set<String> uniqueOrientations = new HashSet<>();
        List<Piece> orientations = new ArrayList<>();
        Piece current = this;
        for (int r = 0; r < 4; r++) {
            addIfUnique(orientations, uniqueOrientations, current);

            Piece flippedHorizontally = current.flipHorizontal();
            addIfUnique(orientations, uniqueOrientations, flippedHorizontally);
            current = current.rotate();
        }
        return orientations;
    }
    public void initializeOrientations() {
        this.allOrientations = getAllOrientationsList();
    }

    public List<Piece> getAllOrientations() {
        if (allOrientations == null) {
            initializeOrientations(); 
        }
        return allOrientations;
    }
    private void addIfUnique(List<Piece> orientations, Set<String> uniqueOrientations, Piece piece) {
        String rep = pieceToString(piece.getShape());
        if (uniqueOrientations.add(rep)) {
            orientations.add(piece);
        }
    }

    private String pieceToString(char[][] shape) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : shape) {
            sb.append(Arrays.toString(row));
        }
        return sb.toString();
    }

    
    // ? Getters and Setters
    public char[][] getShape() {
        return shape;
    }
    
    public char getId() {
        return id;
    }

    public void setId(char id) {
        this.id = id;
    }

    public Piece flipVertical() {
        char[][] flipped = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flipped[rows - 1 - i][j] = shape[i][j];
            }
        }
        return new Piece(flipped, id);
    }
}