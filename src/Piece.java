import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Piece {
    private char[][] shape;
    private int id;
    private int rows;
    private int cols;

    // ? Constructor
    public Piece(char[][] shape, int id) {
        this.shape = shape;
        this.id = id;
        this.rows = shape.length;
        this.cols = shape[0].length;
    }

    // ? Getters and Setters
    public char[][] getShape() {
        return shape;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Rotate 90
    public Piece rotate() {
        char[][] rotated = new char[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        return new Piece(rotated, id);
    }

    // Flip horizontal
    public Piece flipHorizontal() {
        char[][] flipped = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flipped[i][cols - 1 - j] = shape[i][j];
            }
        }
        return new Piece(flipped, id);
    }

    // Flip vertical
    public Piece flipVertical() {
        char[][] flipped = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flipped[rows - 1 - i][j] = shape[i][j];
            }
        }
        return new Piece(flipped, id);
    }

    public List<Piece> getAllOrientations() {
        Set<String> uniqueOrientations = new HashSet<>();
        List<Piece> orientations = new ArrayList<>();
        Piece current = this;

        // 4 rotations (0, 90, 180, 270)
        for (int r = 0; r < 4; r++) {
            Piece flippedHorizontally = current.flipHorizontal();
            Piece flippedVertically = current.flipVertical();
            addIfUnique(orientations, uniqueOrientations, current);
            addIfUnique(orientations, uniqueOrientations, flippedHorizontally);
            addIfUnique(orientations, uniqueOrientations, flippedVertically);

            // Rotate to the next orientation
            current = current.rotate();
        }
        return orientations;
    }

    // ? Helpers
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
}

