package edu.odu.cs480.adamhill;

public class Player {
    private int numMovesRemaining;

    public Player() {
        numMovesRemaining = 21;
    }

    public int getNumMovesRemaining() {
        return numMovesRemaining;
    }

    /**
     * Subtracts one from numMovesRemaining.
     */
    public void makeMove() {
        if (numMovesRemaining > 0) {
            numMovesRemaining--;
        }
    }

    public boolean canMakeMove() {
        return numMovesRemaining > 0;
    }
}
