import java.util.Scanner;

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
	final static int HEIGHT = 610;

	// Setup constants for the Board
	final int ROWS = 7;
	final int COLS = 7;

	boolean done = false;
	int turn = 0;
	String msg = "It is player one's turn.";

	@Override
	public void start(Stage stage) throws Exception {
		Font font = new Font("Arial", 30);

		Group root = new Group();

		// create the background of the game board
		Rectangle rect = new Rectangle(0, 0, 530, 540);
		rect.setFill(Color.SLATEGREY);
		root.getChildren().add(rect);

		// generates the inital game board
		Board board = new Board(ROWS, COLS);
		getBoard(board, root);

		Label result = new Label();

		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				if (done == false) {
					int column = findColumn(event.getSceneX());

					// checks whose turn it is
					if (turn % 2 == 0) {
						board.placePiece(column, CellState.P2);
						msg = "It is player two's turn.";
					} else {
						board.placePiece(column, CellState.P1);
						msg = "It is player one's turn.";
					}

					// generates game board
					getBoard(board, root);

					// check if any player has won
					if (board.checkAcross() == CellState.P1 || board.checkVertical() == CellState.P1) {
						msg = "Player two won!";
						done = true;
					} else if (board.checkAcross() == CellState.P2 || board.checkVertical() == CellState.P2) {
						msg = "Player one won!";
						done = true;
					}

					turn = turn + 1;
					result.setText(msg);
				}
			}
		});

		// shows a message to the player
		result.setText(msg);
		result.setFont(font);
		result.relocate(25, 555);
		root.getChildren().add(result);

		Scene scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
		stage.setTitle("Connect Four");
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Finds which column the user has clicked in
	 * 
	 * @param coorX
	 * @return
	 */
	public int findColumn(double coorX) {
		for (int i = 1; i < 8; i++) {
			int x1 = 5 + ((i - 1) * 75);
			int x2 = 75 + ((i - 1) * 75);
			if (x1 < coorX && x2 > coorX) {
				return i;
			}
		}

		return 0;
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
					circ.setFill(Color.YELLOW);
				} else if (board.getPiece(r, c).getState() == CellState.P2) {
					circ.setFill(Color.RED);
				}

				circ.setStroke(Color.BLACK);
				root.getChildren().add(circ);
			}
		}
	}

}
