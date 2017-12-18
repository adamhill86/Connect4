package edu.odu.cs480.adamhill;

public class AI extends Player{
    private ConnectFour connectFour;
    private final int MAX_DEPTH = 8;
    private boolean hasTakenFirstTurn;

    // scoring constants
    private final int ONE_IN_A_ROW = 1;
    private final int TWO_IN_A_ROW = 10;
    private final int THREE_IN_A_ROW = 1000;
    private final int FOUR_IN_A_ROW = Integer.MAX_VALUE;

    public AI(ConnectFour connectFour) {
        super();
        this.connectFour = connectFour;
        hasTakenFirstTurn = false;
    }

    /**
     * This method either calls takeFirstTurn if the AI hasn't had its first turn yet or minimax/alphabeta if it has.
     * @param board
     * @return The score and column that represents the best possible move [0] = score, [1] = column
     */
    public int[] takeTurn(Board board) {
        if (!hasTakenFirstTurn) {
            int col = takeFirstTurn(board);
            hasTakenFirstTurn = true;
            return new int[]{ONE_IN_A_ROW, col};
        } else {
            //int[] results = minimax(board, MAX_DEPTH, true);
            int[] results = alphaBeta(board, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            board.addChipToColumn(results[1], new Chip(Chip.Color.AI));
            return results;
        }
    }

    /**
     * This method calculates the total score for the current board state.
     * @param board The current board state
     * @param color The player whose turn it is
     * @return The total score
     */
    private int evaluateState(Board board, Chip.Color color) {
        int score = 0;

        score += evalHorizontal(board, color);
        score += evalVertical(board, color);
        score += evalDiagonals(board, color);

        if (color == Chip.Color.PLAYER) {
            return -score;
        }

        return score;
    }

    /**
     * This method scores the board based on a variety of possible horizontal 2-, 3-, and 4-in-a-row combinations
     * @param board The current board state
     * @param color The player whose turn it is
     * @return The horizontal score
     */
    private int evalHorizontal(Board board, Chip.Color color) {
        // Check for each possible combination of two pieces and two open spaces
        int score = 0;

        // check for four in a row (winning state)
        //System.out.println("Inside Check 4");
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS - 3; j++) {
                //System.out.println("Position i: " + i + ", j: " + j);
                Chip current = board.getPosition(i, j);
                Chip second = board.getPosition(i, j+1);
                Chip third = board.getPosition(i, j+2);
                Chip fourth = board.getPosition(i, j+3);

                if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    return FOUR_IN_A_ROW;
                }
            }
        }

        // check for an open-ended three in a row (OXXXO)
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS - 4; j++) { // -4 because we'll be looking ahead as many as 4 places
                Chip current = board.getPosition(i, j);
                Chip second = board.getPosition(i, j+1);
                Chip third = board.getPosition(i, j+2);
                Chip fourth = board.getPosition(i, j+3);
                Chip fifth = board.getPosition(i, j+4);
                if (current == null &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color) &&
                        fifth == null) {
                    score += THREE_IN_A_ROW;
                }
            }
        }

        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS - 3; j++) { // -3 because we'll be looking ahead as many as 3 places
                //System.out.println("Position i: " + i + ", j: " + j);
                Chip current = board.getPosition(i, j);
                Chip second = board.getPosition(i, j+1);
                Chip third = board.getPosition(i, j+2);
                Chip fourth = board.getPosition(i, j+3);

                // Three in a row
                // XXXO - X=piece, O=open space
                if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        fourth == null) {
                    score += THREE_IN_A_ROW;
                } // OXXX
                else if (current == null &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += THREE_IN_A_ROW;
                } // XOXX
                else if ((current != null && current.getColor() == color) &&
                        second == null &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += THREE_IN_A_ROW;
                } // XXOX
                else if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        third == null &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += THREE_IN_A_ROW;
                }



                // Two in a row
                // XXOO
                else if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        third == null &&
                        fourth == null) {
                    score += TWO_IN_A_ROW;
                } // XOXO
                else if ((current != null && current.getColor() == color) &&
                        second == null &&
                        (third != null && third.getColor() == color) &&
                        fourth == null) {
                    score += TWO_IN_A_ROW;
                } // XOOX
                else if ((current != null && current.getColor() == color) &&
                        second == null &&
                        third == null &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += TWO_IN_A_ROW;
                } // OXXO
                else if (current == null &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        fourth == null) {
                    score += TWO_IN_A_ROW;
                } // OXOX
                else if (current == null &&
                        (second != null && second.getColor() == color) &&
                        third == null &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += TWO_IN_A_ROW;
                } // OOXX
                else if (current == null &&
                        second == null &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color))  {
                    score += TWO_IN_A_ROW;
                }
            }
        }
        return score;
    }

    /**
     * This method scores the board based on a variety of possible vertical 2-, 3-, and 4-in-a-row combinations
     * @param board The current board state
     * @param color The player whose turn it is
     * @return The vertical score
     */
    private int evalVertical(Board board, Chip.Color color) {
        int score = 0;

        // check for four in a row (winning state)
        for (int j = 0; j < Board.COLS; j++) {
            for (int i = 0; i < Board.ROWS - 3; i++) {
                //System.out.println("Position i: " + i + ", j: " + j);
                Chip current = board.getPosition(i, j);
                Chip second = board.getPosition(i+1, j);
                Chip third = board.getPosition(i+2, j);
                Chip fourth = board.getPosition(i+3, j);

                if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    return FOUR_IN_A_ROW;
                }
            }
        }

        // check for two or three in a row
        // O    O
        // O    X
        // X    X
        // X or X
        for (int j = 0; j < Board.COLS; j++) {
            for (int i = 0; i < Board.ROWS - 3; i++) {
                //System.out.println("Position i: " + i + ", j: " + j);
                Chip current = board.getPosition(i, j);
                Chip second = board.getPosition(i+1, j);
                Chip third = board.getPosition(i+2, j);
                Chip fourth = board.getPosition(i+3, j);

                // three
                if (current == null &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += THREE_IN_A_ROW;
                } // two
                else if (current == null &&
                        second == null &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += TWO_IN_A_ROW;
                }
            }
        }

        return score;
    }

    /**
     * This method scores the board based on a variety of possible diagonal 2-, 3-, and 4-in-a-row combinations
     * @param board The current board state
     * @param color The player whose turn it is
     * @return The diagonal score
     */
    private int evalDiagonals(Board board, Chip.Color color) {
        int score = 0;

        // check for four in a row /
        for (int i = Board.ROWS - 1; i > Board.ROWS - 4; i--) { // check rows 5 - 3 (inclusive)
            for (int j = 0; j < Board.COLS - 3; j++) {
                Chip current = board.getPosition(i, j);
                Chip second = board.getPosition(i-1, j+1);
                Chip third = board.getPosition(i-2, j+2);
                Chip fourth = board.getPosition(i-3, j+3);

                if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    return FOUR_IN_A_ROW;
                }

                // check for 3 in a row /

                //    O
                //   X
                //  X
                // X
                if (current == null &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += THREE_IN_A_ROW;
                }

                //    X
                //   O
                //  X
                // X
                else if ((current != null && current.getColor() == color) &&
                        second == null &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += THREE_IN_A_ROW;
                }

                //    X
                //   X
                //  O
                // X
                else if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        third == null &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += THREE_IN_A_ROW;
                }

                //    X
                //   X
                //  X
                // O
                else if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        fourth == null) {
                    score += THREE_IN_A_ROW;
                }

                // check for 2 in a row /

                //    X
                //   X
                //  O
                // O
                else if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        third == null &&
                        fourth == null) {
                    score += TWO_IN_A_ROW;
                }

                //    X
                //   O
                //  X
                // O
                else if ((current != null && current.getColor() == color) &&
                        second == null &&
                        (third != null && third.getColor() == color) &&
                        fourth == null) {
                    score += TWO_IN_A_ROW;
                }

                //    X
                //   O
                //  O
                // X
                else if ((current != null && current.getColor() == color) &&
                        second == null &&
                        third == null &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += TWO_IN_A_ROW;
                }

                //    O
                //   X
                //  X
                // O
                else if (current == null &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        fourth == null) {
                    score += TWO_IN_A_ROW;
                }

                //    O
                //   X
                //  O
                // X
                else if (current == null &&
                        (second != null && second.getColor() == color) &&
                        third == null &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += TWO_IN_A_ROW;
                }

                //    O
                //   O
                //  X
                // X
                else if (current == null &&
                        second == null &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color))  {
                    score += TWO_IN_A_ROW;
                }
            }
        }

        // check for four in a row \
        for (int i = 0; i < Board.ROWS - 3; i++) {
            for (int j = 0; j < Board.COLS - 3; j++) {
                Chip current = board.getPosition(i, j);
                Chip second = board.getPosition(i+1, j+1);
                Chip third = board.getPosition(i+2, j+2);
                Chip fourth = board.getPosition(i+3, j+3);

                if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    return FOUR_IN_A_ROW;
                }

                // check for 3 in a row \

                // O
                //  X
                //   X
                //    X
                if (current == null &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += THREE_IN_A_ROW;
                }

                // X
                //  O
                //   X
                //    X
                else if ((current != null && current.getColor() == color) &&
                        second == null &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += THREE_IN_A_ROW;
                }

                // X
                //  X
                //   O
                //    X
                else if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        third == null &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += THREE_IN_A_ROW;
                }

                // X
                //  X
                //   X
                //    O
                else if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        fourth == null) {
                    score += THREE_IN_A_ROW;
                }

                // check for 2 in a row \
                // X
                //  X
                //   O
                //    O
                else if ((current != null && current.getColor() == color) &&
                        (second != null && second.getColor() == color) &&
                        third == null &&
                        fourth == null) {
                    score += TWO_IN_A_ROW;
                }

                // X
                //  O
                //   X
                //    O
                else if ((current != null && current.getColor() == color) &&
                        second == null &&
                        (third != null && third.getColor() == color) &&
                        fourth == null) {
                    score += TWO_IN_A_ROW;
                }

                // X
                //  O
                //   O
                //    X
                else if ((current != null && current.getColor() == color) &&
                        second == null &&
                        third == null &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += TWO_IN_A_ROW;
                }

                // O
                //  X
                //   X
                //    O
                else if (current == null &&
                        (second != null && second.getColor() == color) &&
                        (third != null && third.getColor() == color) &&
                        fourth == null) {
                    score += TWO_IN_A_ROW;
                }

                // O
                //  X
                //   O
                //    X
                else if (current == null &&
                        (second != null && second.getColor() == color) &&
                        third == null &&
                        (fourth != null && fourth.getColor() == color)) {
                    score += TWO_IN_A_ROW;
                }

                // O
                //  O
                //   X
                //    X
                else if (current == null &&
                        second == null &&
                        (third != null && third.getColor() == color) &&
                        (fourth != null && fourth.getColor() == color))  {
                    score += TWO_IN_A_ROW;
                }
            }
        }

        return score;
    }

    /**
     * Depth-limited minimax algorithm
     * @param board The current board state being evaluated
     * @param depth The current depth in the game tree
     * @param isMaxPlayer True if AI (maximizing), false if human (minimizing)
     * @return The score and column that represents the best possible move [0] = score, [1] = column
     */
    private int[] minimax(Board board, int depth, boolean isMaxPlayer) {
        ConnectFour connectFour = new ConnectFour(board);
        int bestScore = (isMaxPlayer) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestPosition = 0;
        int SCORE_COL = 0; // score index in the returned array
        int COLUMN_COL = 1; // column index in the returned array
        Chip.Color color = (isMaxPlayer) ? Chip.Color.AI : Chip.Color.PLAYER;

        if (connectFour.hasAIWon()) {
            //System.out.println("\n\n********\nAI will win with this state:\n");
            //System.out.println(board);
            bestScore = evaluateState(board, color);
            return new int[]{bestScore, bestPosition};
        } else if (connectFour.hasPlayerWon()) {
            //System.out.println("\n\n********\nOh no, the player has won with this state:\n");
            //System.out.println(board);
            bestScore = evaluateState(board, color);
            //System.out.println(bestScore);
            return new int[]{bestScore, bestPosition};
        }

        if (depth == 0 || connectFour.hasAIWon() || connectFour.hasPlayerWon()) {
            bestScore = evaluateState(board, color);
        } else {
            for (int j = 0; j < Board.COLS; j++) {
                if (board.canAddChipToCol(j)) {
                    Board newBoard = new Board(board);
                    Chip chip = new Chip(color);
                    newBoard.addChipToColumn(j, chip);

                    if (isMaxPlayer) {
                        ConnectFour newC4 = new ConnectFour(newBoard);
                        if (newC4.hasAIWon()) {
                            //System.out.println("Found a winning state. The AI should probably take it.");
                            bestScore = evaluateState(board, color);
                            bestPosition = j;
                            //System.out.println("Score: " + bestScore + ", position: " + bestPosition);
                            return new int[]{bestScore, bestPosition};
                        }
                        //System.out.println("Max Best score: " + bestScore);
                        int[] results = minimax(newBoard, depth - 1, false);
                        int currentScore = results[SCORE_COL];
                        if (currentScore > bestScore) {
                            bestScore = currentScore;
                            bestPosition = j;
                        }
                    } else {
                        ConnectFour newC4 = new ConnectFour(newBoard);
                        if (newC4.hasPlayerWon()) {
                            //System.out.println("Found a winning state. The AI should probably take it.");
                            bestScore = evaluateState(board, color);
                            bestPosition = j;
                            //System.out.println("Score: " + bestScore + ", position: " + bestPosition);
                            return new int[]{bestScore, bestPosition};
                        }
                        //System.out.println("Min Best score: " + bestScore);
                        int[] results = minimax(newBoard, depth - 1, true);
                        int currentScore = results[SCORE_COL];
                        if (currentScore < bestScore) {
                            //System.out.println("Best score changed from " + bestScore + " to " + currentScore);
                            bestScore = currentScore;
                            bestPosition = j;
                        }
                    }
                }
            }
        }
        return new int[]{bestScore, bestPosition};
    }

    /**
     * An alpha beta pruning implementation
     * @param board The current board state
     * @param depth The current depth in the game tree
     * @param alpha The best score MAX has seen so far
     * @param beta The best score MIN has seen so far
     * @param isMaxPlayer True if AI (maximizing), false if human (minimizing)
     * @return The score and column that represents the best possible move [0] = score, [1] = column
     */
    private int[] alphaBeta(Board board, int depth, int alpha, int beta, boolean isMaxPlayer) {
        ConnectFour connectFour = new ConnectFour(board);
        Chip.Color color = (isMaxPlayer) ? Chip.Color.AI : Chip.Color.PLAYER;
        int bestScore = (isMaxPlayer) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int bestPosition = 0;

        if (depth == 0 || connectFour.hasPlayerWon() || connectFour.hasAIWon()) {
            bestScore = evaluateState(board, color);
        } else {
            for (int j = 0; j < Board.COLS; j++) {
                if (board.canAddChipToCol(j)) {
                    Board newBoard = new Board(board);
                    Chip chip = new Chip(color);
                    newBoard.addChipToColumn(j, chip);

                    if (isMaxPlayer) {
                        ConnectFour newC4 = new ConnectFour(newBoard);
                        if (newC4.hasAIWon()) {
                            //System.out.println("Found a winning state. The AI should probably take it.");
                            bestScore = evaluateState(board, color);
                            bestPosition = j;
                            //System.out.println("Score: " + bestScore + ", position: " + bestPosition);
                            return new int[]{bestScore, bestPosition};
                        }
                        int[] results = alphaBeta(newBoard, depth - 1, alpha, beta, false);
                        //bestScore = Math.max(bestScore, results[0]);
                        if (results[0] > bestScore) {
                            bestScore = results[0];
                            bestPosition = j;
                        }
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    } else {
                        ConnectFour newC4 = new ConnectFour(newBoard);
                        if (newC4.hasPlayerWon()) {
                            //System.out.println("Found a winning state. The AI should probably take it.");
                            bestScore = evaluateState(board, color);
                            bestPosition = j;
                            //System.out.println("Score: " + bestScore + ", position: " + bestPosition);
                            return new int[]{bestScore, bestPosition};
                        }
                        int[] results = alphaBeta(newBoard, depth - 1, alpha, beta, true);
                        //bestScore = Math.min(bestScore, results[0]);
                        if (results[0] < bestScore) {
                            bestScore = results[0];
                            bestPosition = j;
                        }
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
        }
        return new int[]{bestScore, bestPosition};
    }

    /**
     * This method controls what the AI does on its first turn.
     * If the player doesn't take the middle column, it will.
     * Otherwise, it will take the column to the right.
     * @param board
     * @return The column in which a piece was placed
     */
    private int takeFirstTurn(Board board) {
        // try to make first move in row 5, column 3
        int BOTTOM_ROW = 5;
        int MIDDLE_COLUMN = Board.COLS / 2;
        int moveColumn = 0;

        if (board.getPosition(BOTTOM_ROW, MIDDLE_COLUMN) == null) {
            moveColumn = MIDDLE_COLUMN;
        } // if the middle spot isn't open, try the columns around it
        else if (board.getPosition(BOTTOM_ROW, MIDDLE_COLUMN + 1) == null) {
            moveColumn = MIDDLE_COLUMN + 1;
        } else if (board.getPosition(BOTTOM_ROW, MIDDLE_COLUMN - 1) == null) {
            moveColumn = MIDDLE_COLUMN -1; // This really shouldn't execute but it's here just in case I guess?
        }

        board.addChipToColumn(moveColumn, new Chip(Chip.Color.AI));
        return moveColumn;
    }
}
