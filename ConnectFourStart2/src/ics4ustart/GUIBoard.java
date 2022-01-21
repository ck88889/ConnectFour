package ics4ustart;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.scene.input.MouseEvent;

public class GUIBoard extends Application {

	// set up constants for the game window
	final static int WIDTH = 530;
	final static int HEIGHT = 640;

	// Setup constants for the Board
	final int ROWS = 7;
	final int COLS = 7;

	boolean done = true;
	boolean singlePlayer = false;
	int turn = 0;
	String msg;

	@Override
	public void start(Stage stage) throws Exception {
		Font font = new Font("Arial", 25);
		Font popupFont = new Font("Arial", 18);

		Group root = new Group();
		Label result = new Label();

		// create the background of the game board
		Rectangle rect = new Rectangle(0, 0, 530, 540);
		rect.setFill(Color.SLATEGREY);
		root.getChildren().add(rect);

		// generates the initial game board
		Board board = new Board(ROWS, COLS);
		getBoard(board, root);

		Button replayBtn = new Button("Play Again");
		replayBtn.setFont(popupFont);
		replayBtn.relocate(530 - 120, 540 + 10);
		root.getChildren().add(replayBtn);

		getBoard(board, root);

		// creates pop up so user can choose player mode
		Rectangle popup = new Rectangle((WIDTH / 2) - (300 / 2), (HEIGHT / 2) - (200 / 2), 300, 200);
		popup.setFill(Color.LIGHTBLUE);
		popup.setStroke(Color.BLACK);
		root.getChildren().add(popup);

		Button SinglePlayerbtn = new Button("Single Player");
		Button TwoPlayerbtn = new Button("Two Player");
		SinglePlayerbtn.setFont(popupFont);
		TwoPlayerbtn.setFont(popupFont);

		TwoPlayerbtn.relocate((WIDTH / 2) - (300 / 2) + 90, (HEIGHT / 2) - (200 / 2) + 70);
		SinglePlayerbtn.relocate((WIDTH / 2) - (300 / 2) + 82, (HEIGHT / 2) - (200 / 2) + 120);
		root.getChildren().add(TwoPlayerbtn);
		root.getChildren().add(SinglePlayerbtn);

		Label popupTitle = new Label("Choose player mode:");
		popupTitle.setFont(popupFont);
		popupTitle.relocate((WIDTH / 2) - (300 / 2) + 60, (HEIGHT / 2) - (200 / 2) + 30);
		root.getChildren().add(popupTitle);

		// checks for the player mode the user chooses
		SinglePlayerbtn.setOnAction((event) -> {
			singlePlayer = true;
			done = false;

			msg = "You are player one (red).";
			result.setText(msg);
			root.getChildren().remove(popup);
			root.getChildren().remove(popupTitle);
			root.getChildren().remove(TwoPlayerbtn);
			root.getChildren().remove(SinglePlayerbtn);
		});

		TwoPlayerbtn.setOnAction((event) -> {
			singlePlayer = false;
			done = false;

			msg = "It is player one's turn (red).";
			result.setText(msg);
			root.getChildren().remove(popup);
			root.getChildren().remove(popupTitle);
			root.getChildren().remove(TwoPlayerbtn);
			root.getChildren().remove(SinglePlayerbtn);
		});

		replayBtn.setOnAction((event) -> {
			if(!root.getChildren().contains(popupTitle)) {
				board.reset();
				getBoard(board, root);

				done = true;
				singlePlayer = false;
				turn = 0;
				root.getChildren().add(popup);
				root.getChildren().add(popupTitle);
				root.getChildren().add(TwoPlayerbtn);
				root.getChildren().add(SinglePlayerbtn);
			}

		});

		// Lets user place game pieces and checks for winner
		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (done == false) {
					int column = findColumn(event.getSceneX(), board);

					if (singlePlayer == false) {
						// checks whose turn it is
						if (turn % 2 == 0) {
							if (column == 0 || checkFull(board)) {
								msg = "Invalid click";
								
							} else {
								board.placePiece(column, CellState.P1);
								turn = turn + 1;
								msg = "It is player two's turn (yellow).";
							}
						} else {
							if (column == 0 || checkFull(board)) {
								msg = "Invalid click";
								
							} else {
								board.placePiece(column, CellState.P2);
								turn = turn + 1;
								msg = "It is player one's turn (red).";
							}
						}
					} else {
						board.placePiece(column, CellState.P1);

						// check if any player has won
						if (board.checkAcross() == 1 || board.checkVertical() == 1 || board.checkDiagonalOne() == 1
								|| board.checkDiagonalTwo() == 1) {
							msg = "Player one won!";
							done = true;
						} else if (board.checkAcross() == 2 || board.checkVertical() == 2
								|| board.checkDiagonalOne() == 2 || board.checkDiagonalTwo() == 2) {
							msg = "Player two won!";
							done = true;
						} else {
							int genCol = (int) (Math.random() * (COLS - 1 + 1) + 1);
							board.placePiece(genCol, CellState.P2);
						}
					}
					
					// generates game board
					getBoard(board, root);

					// check if any player has won
					if (board.checkAcross() == 1 || board.checkVertical() == 1 || board.checkDiagonalOne() == 1
							|| board.checkDiagonalTwo() == 1) {
						msg = "Player one won!";
						done = true;
					} else if (board.checkAcross() == 2 || board.checkVertical() == 2 || board.checkDiagonalOne() == 2
							|| board.checkDiagonalTwo() == 2) {
						msg = "Player two won!";
						done = true;
					}
					
					result.setText(msg);
				}
			}
		});
		
		//tell user the board is full
		if(checkFull(board)) {
			msg = "Board is full.";
		}
		

		// shows a message to the player
		result.setFont(font);
		result.relocate(20, 555);
		result.setAlignment(Pos.CENTER);
		root.getChildren().add(result);

		Scene scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
		stage.setTitle("Connect Four");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void test(Button replayBtn) {

	}

	/**
	 * Finds which column the user has clicked in
	 * 
	 * @param coorX
	 * @return
	 */
	public int findColumn(double coorX, Board board) {
		int foundCol = 0;
		int count = 1;

		for (int i = 1; i < 8; i++) {
			int x1 = 0 + ((i - 1) * 75);
			int x2 = 80 + ((i - 1) * 75);
			if (x1 < coorX && x2 > coorX) {
				foundCol = i;
			}
		}

		for (int i = 0; i < 7; i++) {
			if (board.getPiece(i, (foundCol -1)).getState() == CellState.P1 || board.getPiece(i, (foundCol -1)).getState() == CellState.P2) {
				count = count + 1;
			}
		}

		if (count > 7) {
			return 0;
		} else {
			return foundCol;
		}
	}
	
	public boolean checkFull(Board board) {
		int count  = 1;
		
		for (int i = 0; i < 7; i++) {
			for(int x = 0; x < 7; x++) {
				if (board.getPiece(i, x).getState() == CellState.P1 || board.getPiece(i, x).getState() == CellState.P2) {
					count = count + 1;
				}
			}
		}
		
		if(count > 49) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Generates a game board with new pieces added by the player(s)
	 * 
	 * @param board
	 * @param root
	 */
	public void getBoard(Board board, Group root) {
		for (int r = 0; r < ROWS; r++) {
			for (int c = 0; c < COLS; c++) {
				int y = 45 + (r * 75);
				int x = 40 + (c * 75);
				Circle circ = new Circle(x, y, 30);
				if (board.getPiece(r, c).getState() == CellState.EMPTY) {
					circ.setFill(Color.WHITE);
				} else if (board.getPiece(r, c).getState() == CellState.P1) {
					circ.setFill(Color.RED);
				} else if (board.getPiece(r, c).getState() == CellState.P2) {
					circ.setFill(Color.YELLOW);
				}

				circ.setStroke(Color.BLACK);
				root.getChildren().add(circ);
			}
		}
	}

}
