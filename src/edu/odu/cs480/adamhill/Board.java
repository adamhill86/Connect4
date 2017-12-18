package edu.odu.cs480.adamhill;

import java.util.Arrays;

/**
 * Represents the game board.
 */
public class Board {
    public static final int ROWS = 6;
    public static final int COLS = 7;

    private Chip[][] gameBoard;

    public Board() {
        gameBoard = new Chip[ROWS][COLS];
    }

    public Chip[][] getGameBoard() {
        return gameBoard;
    }

    // Peak at a specific position
    public Chip getPosition(int row, int col) {
        return gameBoard[row][col];
    }

    public void setPosition(int row, int col, Chip chip) {
        gameBoard[row][col] = chip;
    }

    /**
     * Checks to see if there's an open space in a given column
     * @param col
     * @return
     */
    public boolean canAddChipToCol(int col) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (gameBoard[i][col] == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to add a chip to a given column. Will only work if the column isn't already full
     * @param col
     * @param chip
     */
    public void addChipToColumn(int col, Chip chip) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (gameBoard[i][col] == null) {
                gameBoard[i][col] = chip;
                break;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (j == 0) {
                    sb.append("| ");
                }
                if (gameBoard[i][j] == null) {
                    sb.append("  | ");
                } else if (gameBoard[i][j].getColor() == Chip.Color.AI) {
                    sb.append("R | ");
                } else {
                    sb.append("Y | ");
                }
            }
            sb.append("\n");
        }
        sb.append('|');
        for (int i = 0; i < 27; i++) {
            sb.append('-');
        }
        sb.append("|\n");

        for (int i = 0; i < 7; i++) {
            sb.append("| ").append(i).append(" ");
        }
        sb.append("|\n");

        return sb.toString();
    }

    /**
     * Copy constructor
     * @param oldBoard
     */
    public Board(Board oldBoard) {
        gameBoard = new Chip[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gameBoard[i][j] = oldBoard.getPosition(i, j);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return Arrays.deepEquals(gameBoard, board.gameBoard);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(gameBoard);
    }
}
