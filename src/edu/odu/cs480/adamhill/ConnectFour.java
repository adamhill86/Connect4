package edu.odu.cs480.adamhill;

/**
 * Checks to see if the game has been won
 */
public class ConnectFour {
    private Board board;

    public ConnectFour() {
        board = new Board();
    }

    public ConnectFour(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public boolean gameWon() {
        return (hasPlayerWon() || hasAIWon());
    }

    public boolean hasPlayerWon() {
        return (checkHorizontal(Chip.Color.PLAYER) || checkVertical(Chip.Color.PLAYER) || checkDiagonal(Chip.Color.PLAYER));
    }

    public boolean hasAIWon() {
        return (checkHorizontal(Chip.Color.AI) || checkVertical(Chip.Color.AI) || checkDiagonal(Chip.Color.AI));
    }

    public boolean checkHorizontal(Chip.Color color) {
        int numInARow = 0;

        // start checking in the lower left corner
        for (int i = Board.ROWS - 1; i >= 0; i--) {
            for (int j = 0; j < Board.COLS - 3; j++) { // -3 because we'll be looking ahead as many as 4 places
                if (board.getPosition(i, j) != null) {
                    if (board.getPosition(i, j).getColor() == color) {
                        numInARow++;
                        Chip next = board.getPosition(i, j + numInARow);
                        boolean looking = true;

                        while (looking) {
                            if (next != null) {
                                if (next.getColor() != color) {
                                    looking = false;
                                    numInARow = 0;
                                } else {
                                    numInARow++;
                                    if (numInARow == 4) {
                                        return true;
                                    }
                                    next = board.getPosition(i, j + numInARow);
                                }
                            } else {
                                looking = false;
                                numInARow = 0;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkVertical(Chip.Color color) {
        int numInARow = 0;

        // start checking in the lower left corner
        for (int i = Board.ROWS - 1; i >= 3; i--) { // 3 because we'll be looking ahead as many as 4 places
            for (int j = 0; j < Board.COLS; j++) {
                if (board.getPosition(i, j) != null) {
                    if (board.getPosition(i, j).getColor() == color) {
                        numInARow++;
                        Chip next = board.getPosition(i-numInARow, j);
                        boolean looking = true;

                        while (looking) {
                            if (next != null) {
                                if (next.getColor() != color) {
                                    looking = false;
                                    numInARow = 0;
                                } else {
                                    numInARow++;
                                    if (numInARow == 4) {
                                        return true;
                                    }
                                    next = board.getPosition(i - numInARow, j);
                                }
                            } else {
                                looking = false;
                                numInARow = 0;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkDiagonal(Chip.Color color) {
        int numInARow = 0;

        // start checking in the lower left corner and go up and right
        for (int i = Board.ROWS - 1; i >= 3; i--) { // 3 because we'll be looking ahead as many as 4 places
            for (int j = 0; j < Board.COLS - 3; j++) { // -3 because we'll be looking ahead as many as 4 places
                if (board.getPosition(i, j) != null) {
                    if (board.getPosition(i, j).getColor() == color) {
                        numInARow++;
                        Chip next = board.getPosition(i-numInARow, j + numInARow);
                        boolean looking = true;

                        while (looking) {
                            if (next != null) {
                                if (next.getColor() != color) {
                                    looking = false;
                                    numInARow = 0;
                                } else {
                                    numInARow++;
                                    if (numInARow == 4) {
                                        return true;
                                    }
                                    next = board.getPosition(i - numInARow, j + numInARow);
                                }
                            } else {
                                looking = false;
                                numInARow = 0;
                            }
                        }
                    }
                }
            }
        }

        //start checking in the lower right corner and go up and left
        for (int i = Board.ROWS - 1; i >= 3; i--) { // 3 because we'll be looking ahead as many as 4 places
            for (int j = Board.COLS - 1; j >= 3; j--) { // 3 because we'll be looking ahead as many as 4 places
                if (board.getPosition(i, j) != null) {
                    if (board.getPosition(i, j).getColor() == color) {
                        numInARow++;
                        Chip next = board.getPosition(i-numInARow, j - numInARow);
                        boolean looking = true;

                        while (looking) {
                            if (next != null) {
                                if (next.getColor() != color) {
                                    looking = false;
                                    numInARow = 0;
                                } else {
                                    numInARow++;
                                    if (numInARow == 4) {
                                        return true;
                                    }
                                    next = board.getPosition(i - numInARow, j - numInARow);
                                }
                            } else {
                                looking = false;
                                numInARow = 0;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
