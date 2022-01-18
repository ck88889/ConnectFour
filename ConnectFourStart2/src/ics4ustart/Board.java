package ics4ustart;

import java.util.Random;

/**
 * Represents a 2 dimensional Board for Grid based games.
 * 
 * @author Hutchison
 * @version 1.0
 *
 */
public class Board {
	// lll
	private Cell[][] board;
	private int rows;
	private int cols;

	/**
	 * Constructor for Boards.
	 * 
	 * @param aRows number of rows in board
	 * @param aCols number of columns in board
	 */
	public Board(int aRows, int aCols) {
		board = new Cell[aRows][aCols];
		rows = aRows;
		cols = aCols;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				board[i][j] = new Cell(CellState.EMPTY); // no color
			}
		}
	}

	/**
	 * Obtain the current number of rows.
	 * 
	 * @return number of rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Obtain the current number of columns.
	 * 
	 * @return number of columns
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * Obtain a single value from board 2d array
	 * 
	 * @param x
	 * @param y
	 * @return a single value from board 2d array
	 */
	public Cell getPiece(int x, int y) {
		return board[x][y];
	}

	public CellState checkAcross() {
		int countP1 = 0;
		int countP2 = 0;

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				if (board[r][c].getState() == CellState.P1) {
					countP1 = countP1 + 1;
				} else {
					countP1 = 0;
				}
				if (board[r][c].getState() == CellState.P2) {
					countP2 = countP2 + 1;
				} else {
					countP2 = 0;
				}
				if (countP1 == 4) {
					return CellState.P1;
				}
				if (countP2 == 4) {
					return CellState.P2;
				}
			}
		}
		return CellState.EMPTY;
	}

	public CellState checkVertical() {
		int countP1 = 0;
		int countP2 = 0;

		for (int c = 0; c < cols; c++) {
			for (int r = 0; r < rows; r++) {
				if (board[r][c].getState() == CellState.P1) {
					countP1 = countP1 + 1;
				} else {
					countP1 = 0;
				}
				if (board[r][c].getState() == CellState.P2) {
					countP2 = countP2 + 1;
				} else {
					countP2 = 0;
				}
				if (countP1 == 4) {
					return CellState.P1;
				}
				if (countP2 == 4) {
					return CellState.P2;
				}
			}
		}
		return CellState.EMPTY;
	}

	public CellState checkDiagonalOne() {
		CellState player1 = CellState.P1;
		CellState player2 = CellState.P2;
		for (int r = 0; r < rows - 3; r++) {
			for (int c = 0; c < cols - 3; c++) {
				if (board[r][c].getState() == player1 && board[r + 1][c + 1].getState() == player1
						&& board[r + 2][c + 2].getState() == player1 && board[r + 3][c + 3].getState() == player1) {
					return CellState.P1;
				} else if (board[r][c].getState() == player2 && board[r + 1][c + 1].getState() == player2
						&& board[r + 2][c + 2].getState() == player2 && board[r + 3][c + 3].getState() == player2
						&& board[r + 4][c + 4].getState() == player2) {
					return CellState.P2;
				}
			}
		}
		return CellState.EMPTY;
	}

	/**
	 * Check if a proposed location is valid.
	 * 
	 * @param rowIndex row index to check
	 * @param colIndex column index to check
	 * @return true if index value is valid, otherwise false
	 */
	public boolean isValid(int rowIndex, int colIndex) {
		if (board[rowIndex][colIndex].getState() == CellState.EMPTY) {
			return true;
		}

		return false;
	}

	public void display() {
		System.out.println("BOARD");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.printf("%s ", board[i][j]);
			}
			System.out.println();
		}
	}

	public void placePiece(int column, CellState player) {
		for (int i = 6; i > -1; i--) {
			if (isValid(i, column - 1)) {
				board[i][column - 1].setState(player);
				break;
			}
		}

	}
}
