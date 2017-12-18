package edu.odu.cs480.adamhill;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    Board board = new Board();
	    ConnectFour connectFour = new ConnectFour(board);
	    Player humanPlayer = new Player();
	    AI ai = new AI(connectFour);

        System.out.println(board);
        System.out.println("Y = player piece, R = AI piece\n");

        //List<Long> aiTurnTimes = new ArrayList<>(); // This was here for testing and reporting purposes


        while (!connectFour.gameWon() && humanPlayer.canMakeMove()) {
            System.out.print("Where would you like to place your piece? 0-6: ");
            Scanner scanner = new Scanner(System.in);
            int col = scanner.nextInt();
            if (col < 0 || col > 6) {
                System.out.println("Invalid input");
            } else {
                if (!board.canAddChipToCol(col)) {
                    System.out.println("Cannot add chip there");
                    continue;
                }
                board.addChipToColumn(col, new Chip(Chip.Color.PLAYER));
                humanPlayer.makeMove();

                if (connectFour.hasPlayerWon()) {
                    System.out.println(board);
                    System.out.println("Player wins!");
                    /*long average = 0;
                    for (int i = 1; i < aiTurnTimes.size(); i++) {
                        average += aiTurnTimes.get(i);
                    }
                    average /= aiTurnTimes.size()-1;
                    System.out.println("Average turn time: " + average);*/
                    break;
                }

                //long startTime = System.nanoTime();
                int[] results = ai.takeTurn(board);
                /*long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000; //divide by 1000000 to get milliseconds
                System.out.println("Move determined in " + duration + " ms");
                aiTurnTimes.add(duration);*/
                //System.out.println("AI score: " + results[0] + ", position: " + results[1]);
                if (connectFour.hasAIWon()) {
                    System.out.println("AI wins!");

                    /*long average = 0;
                    for (int i = 1; i < aiTurnTimes.size(); i++) {
                        average += aiTurnTimes.get(i);
                    }
                    average /= aiTurnTimes.size()-1;
                    System.out.println("Average turn time: " + average);*/
                }

                System.out.println(board);
            }
        }
    }
}
