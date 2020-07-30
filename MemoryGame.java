/**
 * Name: Eric Truong
 * Date: September 23, 2019
 * Description: Given 16 cards, user tries to find matching cards, game ends when user finds all 8 pairs
 */

import java.util.Scanner;

public class MemoryGame {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        boolean gameEnd = true;


        //loop if user wants to continue playing
        while (gameEnd != false) {
            //create the arrays
            String [][] gameBoard = {{ "A", "B", "C", "D"},
                    {"E", "F", "G", "H"},
                    {"A", "B", "C", "D"},
                    {"E", "F", "G", "H"}};
            boolean [][] gameState = {{ false, false, false, false },
                    {false, false, false, false},
                    {false, false, false, false},
                    {false, false, false, false}};

            boolean flipped = true;
            String roundEnd = "";
            String gameRequest = "";
            int matches = 0;
            int firstPick = 0;
            int secondPick = 0;

            shuffleBoard(gameBoard);

            displayBoard(gameBoard, gameState);

            //loop until user wants to quit after a round
            while (matches != 8) {
                //user puts in first card, checks if it's flipped, if not then it gets flipped
                firstPick = getChoice();
                flipped = checkFlipped(firstPick, gameState);
                while (flipped != false) {
                    System.out.print("Location already entered. Try again: ");
                    firstPick = getChoice();
                    flipped = checkFlipped(firstPick, gameState);
                }
                flipChoice(firstPick, gameState);

                //user puts in second card, checks if it's flipped, if not then it gets flipped
                displayBoard(gameBoard, gameState);
                secondPick = getChoice();
                flipped = checkFlipped(secondPick, gameState);
                while (flipped != false) {
                    System.out.print("Location already entered. Try again: ");
                    secondPick = getChoice();
                    flipped = checkFlipped(secondPick, gameState);
                }
                flipChoice(secondPick, gameState);

                displayBoard(gameBoard, gameState);

                boolean matched = isMatch(firstPick, secondPick, gameBoard);
                if (matched == true) {
                    System.out.print("A match has been found!");
                    matches++;
                } else {
                    System.out.print("A match was not found");
                    flipChoice(firstPick, gameState);
                    flipChoice(secondPick, gameState);
                }
                System.out.println("");

                //prompt user if they want to quit the round
                if (matches < 8) {
                    System.out.print("Quit? Y/N: ");
                    roundEnd = input.nextLine();
                    while (!roundEnd.equals("Y") && !roundEnd.equals("y") && !roundEnd.equals("N") && !roundEnd.equals("n")){
                        System.out.print("Invalid input. \n" +
                                "Quit? Y/N: ");
                        roundEnd = input.nextLine();
                    }
                    if (roundEnd.equals("Y") || roundEnd.equals("y")) {
                        matches = 8;
                        gameEnd = false;
                    }
                }

                displayBoard(gameBoard, gameState);

            }

            if (matches == 8 && gameEnd != false){
                System.out.println("Winner!");
            }

            //prompt user if they want to replay
            System.out.print("Play again? (Y/N): ");
            gameRequest = input.nextLine();
            while (!gameRequest.equals("Y") && !gameRequest.equals("y") && !gameRequest.equals("N") && !gameRequest.equals("n")){
                System.out.print("Invalid input. \n" +
                        "Play again? Y/N: ");
                gameRequest = input.nextLine();
            }
            if (gameRequest.equals("N") || gameRequest.equals("n")){
                gameEnd = false;
            }
            else{
                gameEnd = true;

            }
        }
    }

    /**
     * Method asks for user's selection
     * @return  returns user's selection
     */
    public static int getChoice () {
        System.out.print("Please select a number: ");
        int userChoice = CheckInput.getIntRange(1, 16);
        return userChoice;
    }

    /**
     * Displays the board in a 4x4 setting, if a card is face down, display 1 - 16, face up displays A - H
     * @param gameBoard 2D array of the letters A - H
     * @param gameState 2D array containing whether or not the cards should be face down or up
     */
    public static void displayBoard(String[][] gameBoard, boolean[][] gameState) {
        for (int i = 0; i < gameBoard.length; i++) {
            System.out.println("+----+ +----+ +----+ +----+");
            System.out.println("|    | |    | |    | |    |");
            for (int j = 0; j < gameBoard.length; j++){
                if (gameState[i][j] == true){
                    System.out.print("| " + gameBoard[i][j] + "  | ");
                }
                else {
                    if (i == 0) {
                        System.out.print("| " + (j + 1) + "  | ");
                    }
                    else if (i == 1){
                        System.out.print("| " + (j + 5) + "  | ");
                    }
                    else if (i == 2){
                        if (j == 0) {
                            System.out.print("| " + (j + 9) + "  | ");
                        }
                        else{
                            System.out.print("| " + (j + 9) + " | ");
                        }
                    }
                    else if (i == 3){
                        System.out.print("| " + (j + 13) + " | ");
                    }
                }
            }
            System.out.println("");
            System.out.println("|    | |    | |    | |    |");
            System.out.println("+----+ +----+ +----+ +----+");
            System.out.println("");


        }
    }

    /**
     * Given the board, choose two positions and swap them, do this about 100 times
     * @param gameBoard the game board
     * @return          the shuffled board
     */
    public static void shuffleBoard(String [][] gameBoard) {
        for (int i = 0; i < 100; i++) {
            String swap = "";
            String secondSwap = "";
            int upperBound = 3;
            int lowerBound = 0;
            int row = 0;
            int column = 0;
            int secondrow = 0;
            int secondcolumn = 0;
            while (swap.equals(secondSwap)) {
                for (int j = 0; j < 2; j++) {
                    row = (int) ((Math.random() * (upperBound - lowerBound + 1)) + lowerBound);
                    column = (int) ((Math.random() * (upperBound - lowerBound + 1)) + lowerBound);
                    if (j == 0) {
                        swap = gameBoard[row][column];
                        secondrow = row;
                        secondcolumn = column;
                    } else {
                        secondSwap = gameBoard[row][column];
                    }

                }
            }
            gameBoard[row][column] = swap;
            gameBoard[secondrow][secondcolumn] = secondSwap;

        }
    }

    /**
     * Given a user's choice, flp that card
     * @param userChoice    user's card choice
     * @param gameState     array of boolean values
     */
    public static void flipChoice(int userChoice, boolean[][] gameState) {
        int row = 0;
        int column = 0;
        if (userChoice < 5) {
            row = 0;
            column = (userChoice - 1) % 4;
            if (gameState[row][column] == false) {
                gameState[row][column] = true;
            }
            else if (gameState[row][column] == true){
                gameState[row][column] = false;
            }
        }
        else if (userChoice > 4 && userChoice < 9) {
            row = 1;
            column = (userChoice - 1) % 4;
            if (gameState[row][column] == false) {
                gameState[row][column] = true;
            }
            else if (gameState[row][column] == true){
                gameState[row][column] = false;
            }

        }
        else if (userChoice > 8 && userChoice < 13) {
            row = 2;
            column = (userChoice - 1) % 4;
            if (gameState[row][column] == false) {
                gameState[row][column] = true;
            }
            else if (gameState[row][column] == true){
                gameState[row][column] = false;
            }

        }
        else if (userChoice > 12) {
            row = 3;
            column = (userChoice - 1) % 4;
            if (gameState[row][column] == false) {
                gameState[row][column] = true;
            }
            else if (gameState[row][column] == true){
                gameState[row][column] = false;
            }

        }
    }

    /**
     * Given two cards, check if they match
     * @param firstChoice   first card user picked
     * @param secondChoice  second card user picked
     * @param gameBoard     2D array containing letters A - H
     * @return              returns true if it's a match, otherwise it's false
     */
    public static boolean isMatch(int firstChoice, int secondChoice, String[][] gameBoard) {
        String firstPick = "";
        String secondPick = "";
        int row = 0;
        int column = 0;
        if (firstChoice <= 4){
            row = 0;
            column = (firstChoice - 1) % 4;
            firstPick = gameBoard[row][column];
        }
        else if (firstChoice > 4 && firstChoice <= 8){
            row = 1;
            column = (firstChoice - 1) % 4;
            firstPick = gameBoard[row][column];
        }
        else if (firstChoice > 8 && firstChoice <= 12){
            row = 2;
            column = (firstChoice - 1) % 4;
            firstPick = gameBoard[row][column];
        }
        else if (firstChoice > 12){
            row = 3;
            column = (firstChoice - 1) % 4;
            firstPick = gameBoard[row][column];
        }
        if (secondChoice <= 4){
            row = 0;
            column = (secondChoice - 1) % 4;
            secondPick = gameBoard[row][column];
        }
        else if (secondChoice > 4 && secondChoice <= 8){
            row = 1;
            column = (secondChoice - 1) % 4;
            secondPick = gameBoard[row][column];
        }
        else if (secondChoice > 8 && secondChoice <= 12){
            row = 2;
            column = (secondChoice - 1) % 4;
            secondPick = gameBoard[row][column];
        }
        else if (secondChoice > 12){
            row = 3;
            column = (secondChoice - 1) % 4;
            secondPick = gameBoard[row][column];
        }
        if (firstPick.equals(secondPick)){
            return true;
        }
        return false;
    }

    /**
     * Given the user's choice, check to see if the card has been flipped
     * @param userChoice    user's choice
     * @param gameState     2D array containing whether or not cards are face down or up
     * @return              returns true if the card has been flipped, otherwise it's false
     */
    public static boolean checkFlipped (int userChoice, boolean[][] gameState) {
        int row = 0;
        int column = 0;
        boolean checkState = false;
        if (userChoice <= 4){
            row = 0;
            column = (userChoice - 1) % 4;
            checkState = gameState[row][column];
            if (checkState == true){
                return true;
            }
        }
        else if (userChoice > 4 && userChoice <= 8){
            row = 1;
            column = (userChoice - 1) % 4;
            checkState = gameState[row][column];
            if (checkState == true){
                return true;
            }
        }
        else if (userChoice > 8 && userChoice <= 12){
            row = 2;
            column = (userChoice - 1) % 4;
            checkState = gameState[row][column];
            if (checkState == true){
                return true;
            }
        }
        else if (userChoice > 12){
            row = 3;
            column = (userChoice - 1) % 4;
            checkState = gameState[row][column];
            if (checkState == true){
                return true;
            }
        }
        return false;
    }
}
