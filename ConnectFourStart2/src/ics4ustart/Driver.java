package ics4ustart;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * Text (console) based driver for testing purposes.
 * @author Hutchison
 *
 */
public class Driver {

	public static void main(String[] args) {
		//bye
		// Setup constants for the Board
		final int ROWS = 7;
		final int COLS = 7;

		// create the board
		Board board = new Board(ROWS, COLS);
		board.display();
		
		// console input
		Scanner in = new Scanner(System.in);
		System.out.println("Enter '1' for Single Player mode or '2' for Two Player mode: ");
		int mode = in.nextInt();
		
		//initialize variables
		boolean done = false;
		String value = "";
		int column = 0;
		int turn = 0;
		
		//loop that continues while the game is in progress
		while (!done) {
			turn = turn + 1;
			//gets column number from corresponding player and places piece accordingly
			if (checkTurn(turn) == 2) {
				column = getColumn(in, 1, COLS, turn, mode); 
				board.placePiece(column, CellState.P2);
			}
			else {
				column = getColumn(in, 1, COLS, turn, mode); 
				board.placePiece(column, CellState.P1);
			}
			
			// Check for winners
			board.display();
			if (board.checkAcross() == CellState.P1 || board.checkVertical() == CellState.P1|| board.checkDiagonalOne() == CellState.P1|| board.checkDiagonalTwo() == CellState.P1) {
				done = true;
				System.out.println("Player one won!");
			} else if (board.checkAcross() == CellState.P2 || board.checkVertical() == CellState.P2|| board.checkDiagonalOne() == CellState.P2|| board.checkDiagonalTwo() == CellState.P2) {
				done = true;
				System.out.println("Player two won!");
			}
		}
	}
	
	/**
 	* Helper method to ensure column value is valid.
 	* @param in, min, max, turn, mode
 	* @return valid column number
 	*/
	private static int getColumn(Scanner in, int min, int max, int turn, int mode) {
		boolean valid = false;
		int column = 0;
		
		//Prompts user for column number if two player mode has been selected or player one in single player mode
		//Checks if column number is valid
		if (mode == 2 || checkTurn(turn) == 1 ) {
		while (!valid) {
			String prompt = String.format("Player " + checkTurn(turn)+ ": Which column (%d,%d) :",min,max); 
			System.out.print(prompt);
			if (in.hasNextInt()) {
				column = in.nextInt();
				if (column >= min && column <= max) {
					valid = true;
				} else {
					System.out.println("Invalid numeric value provided.");
				}
			} else {
				System.out.println("Not a number.");
			}
			in.nextLine();
		}
		}
		//Generates a random column for the AI player in single player mode
		else if(mode == 1) {
			column = (int)(Math.random()* (max - min + 1)+ min);
		}
		return column;
	}
	/**
 	* Checks which player's turn it is
 	* @param turn - counter representing whose turn it is
 	* @return integer representing which player's turn it is
 	*/
	public static int checkTurn(int turn) {
		if (turn%2 == 0) {
			return 2;
		}
		else {
			return 1;
		}
	}
}
